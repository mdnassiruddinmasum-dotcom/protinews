package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AdPlacement
import com.example.data.model.Category
import com.example.data.model.NewsArticle
import com.example.ui.components.AdContainer
import com.example.ui.components.NewsCard
import com.example.ui.theme.ProtiRed

@Composable
fun CategoryArticlesScreen(
    categoryName: String,
    categoryInfo: Category?,
    articles: List<NewsArticle>,
    ads: List<AdPlacement>,
    bookmarkedIds: Set<String>,
    onArticleClick: (NewsArticle) -> Unit,
    onBookmarkToggle: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val categoryArticles = if (categoryName == "Latest News") {
        articles
    } else {
        articles.filter { it.category.equals(categoryName, ignoreCase = true) }
    }

    val headerAd = ads.find { it.placement == "Below Header" && it.isEnabled }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("category_articles_screen"),
        contentPadding = PaddingValues(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Back Navigation Bar & Category Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(4.dp)
                                .height(22.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(ProtiRed)
                        )
                        Text(
                            text = categoryName,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Serif,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    if (categoryInfo != null && categoryInfo.description.isNotBlank()) {
                        Text(
                            text = categoryInfo.description,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Optional ad
        if (headerAd != null) {
            item {
                AdContainer(adPlacement = headerAd, heightDp = 120)
            }
        }

        // Articles Count
        item {
            Text(
                text = "${categoryArticles.size} articles in this section",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = ProtiRed
            )
        }

        // Articles
        items(categoryArticles) { article ->
            NewsCard(
                article = article,
                onArticleClick = onArticleClick,
                onBookmarkToggle = onBookmarkToggle,
                isBookmarked = bookmarkedIds.contains(article.id)
            )
        }
    }
}
