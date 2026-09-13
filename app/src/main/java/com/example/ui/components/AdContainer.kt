package com.example.ui.components

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.data.model.AdPlacement

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun AdContainer(
    adPlacement: AdPlacement?,
    modifier: Modifier = Modifier,
    heightDp: Int = 140
) {
    if (adPlacement == null || !adPlacement.isEnabled) {
        return
    }

    val context = LocalContext.current

    val resolvedHeight = remember(adPlacement.code, heightDp) {
        val heightMatch = Regex("""['"]?height['"]?\s*[:=]\s*['"]?(\d+)['"]?""").find(adPlacement.code)
        if (heightMatch != null) {
            val parsed = heightMatch.groupValues[1].toIntOrNull() ?: heightDp
            if (parsed > heightDp) minOf(parsed + 10, 270) else heightDp
        } else {
            heightDp
        }
    }

    val htmlData = remember(adPlacement.code) {
        """
        <!DOCTYPE html>
        <html>
        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
            <style>
                * { box-sizing: border-box; margin: 0; padding: 0; }
                html, body {
                    background-color: transparent;
                    margin: 0;
                    padding: 0;
                    width: 100%;
                    min-height: 100%;
                    overflow-x: hidden;
                    font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif;
                }
                .ad-wrapper {
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    width: 100%;
                    min-height: 100%;
                    text-align: center;
                }
                .ad-wrapper iframe, .ad-wrapper img {
                    max-width: 100% !important;
                    margin: 0 auto;
                    display: block;
                }
            </style>
            <script>
                // Polyfill and fallback for document.write / document.writeln used by ad networks (e.g. Adsterra invoke.js)
                (function() {
                    var origWrite = document.write.bind(document);
                    var origWriteln = document.writeln.bind(document);

                    function appendHtml(content) {
                        var target = document.getElementById('ad-slot') || document.querySelector('.ad-wrapper') || document.body;
                        if (!target) return;
                        try {
                            var range = document.createRange();
                            range.selectNode(target);
                            var fragment = range.createContextualFragment(content);
                            target.appendChild(fragment);
                        } catch (e) {
                            var temp = document.createElement('div');
                            temp.innerHTML = content;
                            while (temp.firstChild) {
                                target.appendChild(temp.firstChild);
                            }
                        }
                    }

                    document.write = function(content) {
                        if (document.readyState === 'loading') {
                            try {
                                origWrite(content);
                            } catch (e) {
                                appendHtml(content);
                            }
                        } else {
                            appendHtml(content);
                        }
                    };

                    document.writeln = function(content) {
                        document.write(content + '\n');
                    };
                })();
            </script>
        </head>
        <body>
            <div id="ad-slot" class="ad-wrapper">
                ${adPlacement.code}
            </div>
        </body>
        </html>
        """.trimIndent()
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            .padding(top = 4.dp, bottom = 6.dp, start = 8.dp, end = 8.dp)
            .testTag("ad_container_${adPlacement.id}"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "ADVERTISEMENT",
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                letterSpacing = 1.sp
            )
            Text(
                text = adPlacement.placement,
                fontSize = 9.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            )
        }

        AndroidView(
            factory = { ctx ->
                WebView(ctx).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    settings.apply {
                        // (1) JavaScript enabled & popup opening
                        javaScriptEnabled = true
                        javaScriptCanOpenWindowsAutomatically = true

                        // (2) DOM storage enabled & database storage
                        domStorageEnabled = true
                        databaseEnabled = true

                        // (3) Mixed content mode - allow loading ad scripts and resources over HTTP and HTTPS
                        mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

                        // Additional ad-friendly settings
                        allowContentAccess = true
                        allowFileAccess = true
                        loadWithOverviewMode = true
                        useWideViewPort = true
                        cacheMode = WebSettings.LOAD_DEFAULT

                        // Remove in-app WebView indicator (; wv) so ad networks don't reject requests
                        val defaultUa = userAgentString
                        if (defaultUa.contains("; wv")) {
                            userAgentString = defaultUa.replace("; wv", "")
                        }
                    }

                    // Enable third-party cookies for ad tracking and verification
                    val cookieManager = CookieManager.getInstance()
                    cookieManager.setAcceptCookie(true)
                    cookieManager.setAcceptThirdPartyCookies(this, true)

                    setBackgroundColor(android.graphics.Color.TRANSPARENT)

                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                            val url = request?.url?.toString() ?: return false
                            if (url.startsWith("http://") || url.startsWith("https://")) {
                                if (!url.startsWith("https://protinews.com")) {
                                    try {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        context.startActivity(intent)
                                        return true
                                    } catch (e: Exception) {
                                        // Ignore and let webView handle
                                    }
                                }
                            }
                            return false
                        }
                    }

                    webChromeClient = WebChromeClient()

                    tag = htmlData
                    loadDataWithBaseURL("https://protinews.com/", htmlData, "text/html", "UTF-8", null)
                }
            },
            update = { webView ->
                // Only reload when ad script actually changes, avoiding constant resets during recomposition
                if (webView.tag != htmlData) {
                    webView.tag = htmlData
                    webView.loadDataWithBaseURL("https://protinews.com/", htmlData, "text/html", "UTF-8", null)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(resolvedHeight.dp)
        )
    }
}
