package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.NewsArticle
import com.example.ui.theme.ProtiRed

@Composable
fun CategorySection(
    categoryName: String,
    articles: List<NewsArticle>,
    onArticleClick: (NewsArticle) -> Unit,
    onViewAllClick: (String) -> Unit,
    onBookmarkToggle: (String) -> Unit,
    bookmarkedIds: Set<String>,
    modifier: Modifier = Modifier
) {
    if (articles.isEmpty()) return

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("category_section_$categoryName"),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Section Header with red accent bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(20.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(ProtiRed)
                )
                Text(
                    text = categoryName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Serif,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            TextButton(
                onClick = { onViewAllClick(categoryName) },
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                modifier = Modifier.testTag("view_all_$categoryName")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = "View All",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = ProtiRed
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(13.dp),
                        tint = ProtiRed
                    )
                }
            }
        }

        // Display lead article in category + sub-articles
        val lead = articles.firstOrNull()
        val others = articles.drop(1).take(2)

        if (lead != null) {
            NewsCard(
                article = lead,
                onArticleClick = onArticleClick,
                onBookmarkToggle = onBookmarkToggle,
                isBookmarked = bookmarkedIds.contains(lead.id)
            )
        }

        if (others.isNotEmpty()) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                others.forEach { article ->
                    NewsCard(
                        article = article,
                        onArticleClick = onArticleClick,
                        onBookmarkToggle = onBookmarkToggle,
                        isBookmarked = bookmarkedIds.contains(article.id)
                    )
                }
            }
        }
    }
}
