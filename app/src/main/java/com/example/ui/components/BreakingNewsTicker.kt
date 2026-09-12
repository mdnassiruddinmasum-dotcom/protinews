package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.NewsArticle
import com.example.ui.theme.ProtiBreaking
import kotlinx.coroutines.delay

@Composable
fun BreakingNewsTicker(
    breakingArticles: List<NewsArticle>,
    onArticleClick: (NewsArticle) -> Unit,
    modifier: Modifier = Modifier
) {
    if (breakingArticles.isEmpty()) return

    var currentIndex by remember { mutableIntStateOf(0) }

    // Auto rotate breaking ticker
    LaunchedEffect(breakingArticles) {
        if (breakingArticles.size > 1) {
            while (true) {
                delay(6000)
                currentIndex = (currentIndex + 1) % breakingArticles.size
            }
        }
    }

    val currentArticle = breakingArticles.getOrNull(currentIndex) ?: return

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF1E293B))
            .clickable { onArticleClick(currentArticle) }
            .padding(horizontal = 12.dp, vertical = 7.dp)
            .testTag("breaking_news_ticker"),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Breaking badge
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(ProtiBreaking)
                .padding(horizontal = 8.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.FlashOn,
                contentDescription = "Breaking News",
                tint = Color.White,
                modifier = Modifier.size(14.dp)
            )
            Text(
                text = "BREAKING",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 0.8.sp
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        // Headline
        AnimatedContent(
            targetState = currentArticle,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            modifier = Modifier.weight(1f),
            label = "ticker_headline"
        ) { article ->
            Text(
                text = article.title,
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "Read Article",
            tint = Color.LightGray,
            modifier = Modifier.size(16.dp)
        )
    }
}
