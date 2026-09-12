package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.data.model.Category
import com.example.ui.theme.ProtiDarkRed
import com.example.ui.theme.ProtiRed
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ProtinewsHeader(
    selectedCategory: String,
    onSelectCategory: (String) -> Unit,
    categories: List<Category>,
    onOpenSearch: () -> Unit,
    onOpenAdmin: () -> Unit,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateString = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.ENGLISH).format(Date())

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .border(width = 1.dp, color = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))
            .testTag("protinews_main_header")
    ) {
        // Top utility strip: Edition, Live Date & Quick Admin Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                .padding(horizontal = 14.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$dateString • Bangladesh & World Edition",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Admin button
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .clickable { onOpenAdmin() }
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                        .testTag("header_admin_button"),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AdminPanelSettings,
                        contentDescription = "Admin Portal",
                        tint = ProtiRed,
                        modifier = Modifier.size(13.dp)
                    )
                    Text(
                        text = "Admin",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = ProtiRed
                    )
                }
            }
        }

        // Main Brand & Logo Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Hamburger Menu icon
            IconButton(
                onClick = onMenuClick,
                modifier = Modifier.testTag("header_menu_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu Drawer",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            // Protinews Logo
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { onSelectCategory("Home") }
                    .testTag("header_logo")
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Black)) {
                            append("proti")
                        }
                        withStyle(style = SpanStyle(color = ProtiRed, fontWeight = FontWeight.Black)) {
                            append("news")
                        }
                        withStyle(style = SpanStyle(color = ProtiDarkRed, fontWeight = FontWeight.Black)) {
                            append("•")
                        }
                    },
                    fontSize = 28.sp,
                    fontFamily = FontFamily.Serif,
                    letterSpacing = (-0.5).sp
                )
                Text(
                    text = "TRUTH IN JOURNALISM • REAL-TIME PORTAL",
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    letterSpacing = 1.2.sp
                )
            }

            // Search Icon Button
            IconButton(
                onClick = onOpenSearch,
                modifier = Modifier.testTag("header_search_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search News",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        // Horizontal Category Navigation bar
        val scrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 10.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val allNavItems = listOf("Home", "Latest News") + categories.map { it.name }

            allNavItems.forEach { catName ->
                val isSelected = selectedCategory == catName
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) ProtiRed else Color.Transparent)
                        .clickable { onSelectCategory(catName) }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .testTag("category_nav_$catName")
                ) {
                    Text(
                        text = catName,
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}
