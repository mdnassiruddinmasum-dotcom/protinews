package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ProtiDarkRed
import com.example.ui.theme.ProtiRed

@Composable
fun ProtinewsFooter(
    onNavigateToPage: (String) -> Unit,
    onSelectCategory: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var subscriberEmail by remember { mutableStateOf("") }
    var subscribedMessage by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF0B1120))
            .padding(top = 24.dp, bottom = 32.dp, start = 16.dp, end = 16.dp)
            .testTag("protinews_footer"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Brand logo
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.Black)) {
                        append("proti")
                    }
                    withStyle(style = SpanStyle(color = ProtiRed, fontWeight = FontWeight.Black)) {
                        append("news")
                    }
                    withStyle(style = SpanStyle(color = ProtiDarkRed, fontWeight = FontWeight.Black)) {
                        append("•")
                    }
                },
                fontSize = 26.sp,
                fontFamily = FontFamily.Serif
            )
            Text(
                text = "Independent, verified journalism delivering high-impact breaking news and in-depth investigative reports across Bangladesh and the global arena.",
                fontSize = 12.sp,
                color = Color(0xFF94A3B8),
                lineHeight = 17.sp
            )
        }

        Divider(color = Color(0xFF1E293B))

        // Newsletter subscription box
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = "DAILY MORNING BRIEFING",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 0.8.sp
            )
            Text(
                text = "Receive top stories, editorial scoops, and market intelligence every morning.",
                fontSize = 12.sp,
                color = Color(0xFF94A3B8)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = subscriberEmail,
                    onValueChange = { subscriberEmail = it },
                    placeholder = { Text("Enter your email address", fontSize = 12.sp, color = Color(0xFF64748B)) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = ProtiRed,
                        unfocusedBorderColor = Color(0xFF334155),
                        focusedContainerColor = Color(0xFF1E293B),
                        unfocusedContainerColor = Color(0xFF1E293B)
                    )
                )

                Button(
                    onClick = {
                        if (subscriberEmail.isNotBlank()) {
                            subscribedMessage = true
                            subscriberEmail = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ProtiRed),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("subscribe_btn")
                ) {
                    Icon(imageVector = Icons.Default.Send, contentDescription = "Subscribe", tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }

            if (subscribedMessage) {
                Text(
                    text = "✓ Thank you for subscribing to Protinews Daily Dispatch.",
                    fontSize = 11.sp,
                    color = Color(0xFF34D399),
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Divider(color = Color(0xFF1E293B))

        // Navigation links
        Text(
            text = "EXPLORE CATEGORIES",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            letterSpacing = 0.8.sp
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Bangladesh", "International", "Politics", "Business", "Technology").forEach { cat ->
                    Text(
                        text = "• $cat",
                        fontSize = 12.sp,
                        color = Color(0xFFCBD5E1),
                        modifier = Modifier.clickable { onSelectCategory(cat) }
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Sports", "Entertainment", "Lifestyle", "Opinion").forEach { cat ->
                    Text(
                        text = "• $cat",
                        fontSize = 12.sp,
                        color = Color(0xFFCBD5E1),
                        modifier = Modifier.clickable { onSelectCategory(cat) }
                    )
                }
            }
        }

        Divider(color = Color(0xFF1E293B))

        // Legal & Institutional links
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "About Us",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF94A3B8),
                modifier = Modifier.clickable { onNavigateToPage("about") }
            )
            Text(
                text = "Contact",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF94A3B8),
                modifier = Modifier.clickable { onNavigateToPage("contact") }
            )
            Text(
                text = "Privacy Policy",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF94A3B8),
                modifier = Modifier.clickable { onNavigateToPage("privacy") }
            )
            Text(
                text = "Terms & Conditions",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF94A3B8),
                modifier = Modifier.clickable { onNavigateToPage("terms") }
            )
        }

        // Editorial copyright notice
        Text(
            text = "© 2026 protinews Media Network Ltd. All rights reserved. Protinews is committed to fearless, objective and truthful reportage.",
            fontSize = 10.sp,
            color = Color(0xFF64748B),
            lineHeight = 14.sp
        )
    }
}
