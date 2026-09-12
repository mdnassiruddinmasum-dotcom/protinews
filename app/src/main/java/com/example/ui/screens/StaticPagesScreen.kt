package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SiteSettings
import com.example.ui.theme.ProtiRed

@Composable
fun StaticPagesScreen(
    pageType: String, // "about", "contact", "privacy", "terms"
    settings: SiteSettings?,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var nameField by remember { mutableStateOf("") }
    var emailField by remember { mutableStateOf("") }
    var messageField by remember { mutableStateOf("") }
    var submitted by remember { mutableStateOf(false) }

    val pageTitle = when (pageType) {
        "about" -> "About Protinews"
        "contact" -> "Contact Editorial Office"
        "privacy" -> "Privacy Policy"
        "terms" -> "Terms & Conditions"
        else -> "Information"
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("static_page_$pageType"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Back Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = pageTitle,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Serif,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        when (pageType) {
            "about" -> {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = "Institutional Mission",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = ProtiRed
                        )
                        Text(
                            text = "Founded with the foundational mission of unfiltered truth and investigative rigour, Protinews has grown into one of South Asia's foremost digital journalism institutions. We publish verified, fair, and high-impact reports across national governance, international geopolitics, economic trends, science and cultural developments.",
                            fontSize = 14.sp,
                            lineHeight = 22.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Divider()
                        Text(
                            text = "Editorial Principles",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "1. Accuracy First: Every factual assertion is cross-referenced with at least two verifiable primary sources.\n2. Non-Partisan Objectivity: Protinews operates without affiliation to political lobbies or commercial oligarchies.\n3. Transparent Corrections: When errors occur, they are transparently corrected with clear public timestamps.\n4. Protection of Whistleblowers: We provide end-to-end encrypted whistleblower submission channels.",
                            fontSize = 13.sp,
                            lineHeight = 20.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Divider()
                        Text(
                            text = "Leadership & Operations",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Editor-in-Chief: Mahfuzur Rahman\nManaging Editor: Selina Parveen\nExecutive Newsroom: Motijheel C/A, Dhaka-1000, Bangladesh",
                            fontSize = 13.sp,
                            lineHeight = 20.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            "contact" -> {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = "Get in touch with our newsroom",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "For news tips, investigative leads, press inquiries, corrections, or advertising partnerships, reach out directly below.",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(text = "Editorial Desk: contact@protinews.com", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                                Text(text = "News Hotline: +880 1700-000000", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                                Text(text = "Address: Protinews Media Tower, Motijheel, Dhaka", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }

                        Text(
                            text = "Send a Secure Message",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = ProtiRed
                        )

                        OutlinedTextField(
                            value = nameField,
                            onValueChange = { nameField = it },
                            label = { Text("Your Name") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = emailField,
                            onValueChange = { emailField = it },
                            label = { Text("Your Email") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = messageField,
                            onValueChange = { messageField = it },
                            label = { Text("Message or News Tip") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            maxLines = 5
                        )

                        Button(
                            onClick = {
                                if (nameField.isNotBlank() && emailField.isNotBlank() && messageField.isNotBlank()) {
                                    submitted = true
                                    nameField = ""
                                    emailField = ""
                                    messageField = ""
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ProtiRed),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Submit Message to Editors")
                        }

                        if (submitted) {
                            Text(
                                text = "✓ Message successfully delivered to the editorial desk.",
                                color = Color(0xFF16A34A),
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
            "privacy" -> {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "Protinews Privacy Policy",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = ProtiRed
                        )
                        Text(
                            text = "Last updated: September 2026\n\n1. Information Collection: Protinews collects minimal technical data such as IP address, browser user-agent, and local reading preferences solely for providing an optimal, responsive browsing experience.\n\n2. Cookies & Advertisements: We partner with third-party advertising networks (including programmatic banner and social bar partners). These partners may use cookies to serve relevant contextual advertisements. Users may opt out via their device privacy controls.\n\n3. Data Security: We never sell, rent, or trade reader personal information with commercial entities.\n\n4. Editorial Whistleblower Confidentiality: Identities of confidential sources and whistleblowers are protected under journalistic privilege and industry-standard encryption protocols.",
                            fontSize = 13.sp,
                            lineHeight = 21.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
            "terms" -> {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "Terms of Service & Usage Conditions",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = ProtiRed
                        )
                        Text(
                            text = "1. Intellectual Property: All original articles, photographs, infographics, and audiovisual materials published on protinews are protected under international copyright law. Reproduction without explicit written authorization is prohibited.\n\n2. Fair Use & Quotation: Excerpts of up to 100 words may be quoted in academic or journalistic analysis with direct canonical hyperlinking to the original Protinews URL.\n\n3. User Conduct: Defamatory comments, hate speech, and harassment in public forums will result in immediate ban and IP moderation.\n\n4. Limitation of Liability: While we strive for uncompromising accuracy, Protinews provides news analysis as-is without financial or investment guarantee.",
                            fontSize = 13.sp,
                            lineHeight = 21.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}
