package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AdPlacement
import com.example.data.model.Category
import com.example.data.model.NewsArticle
import com.example.ui.components.*

@Composable
fun HomeScreen(
    featuredArticles: List<NewsArticle>,
    latestArticles: List<NewsArticle>,
    categories: List<Category>,
    ads: List<AdPlacement>,
    bookmarkedIds: Set<String>,
    onArticleClick: (NewsArticle) -> Unit,
    onCategoryClick: (String) -> Unit,
    onBookmarkToggle: (String) -> Unit,
    onNavigateToPage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val headerAd = ads.find { it.placement == "Below Header" && it.isEnabled }
    val midAd1 = ads.find { it.placement == "Between Hero & Latest" && it.isEnabled }
    val midAd2 = ads.find { it.placement == "Between Article Lists" && it.isEnabled }
    val footerAd = ads.find { it.placement == "Footer Area" && it.isEnabled }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("home_screen_lazy_column"),
        contentPadding = PaddingValues(bottom = 0.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Below Header Ad
        if (headerAd != null) {
            item {
                Box(modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp)) {
                    AdContainer(adPlacement = headerAd, heightDp = 130)
                }
            }
        }

        // Hero News Section
        item {
            Box(modifier = Modifier.padding(horizontal = 14.dp)) {
                HeroNewsSection(
                    featuredArticles = featuredArticles,
                    onArticleClick = onArticleClick,
                    onBookmarkToggle = onBookmarkToggle,
                    bookmarkedIds = bookmarkedIds
                )
            }
        }

        // Ad between Hero & Latest
        if (midAd1 != null) {
            item {
                Box(modifier = Modifier.padding(horizontal = 14.dp)) {
                    AdContainer(adPlacement = midAd1, heightDp = 120)
                }
            }
        }

        // Latest News Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp)
            ) {
                Text(
                    text = "LATEST HEADLINES",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Serif,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Real-time updates as verified by the Protinews newsroom",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Latest News Cards
        val nonHeroLatest = latestArticles.drop(1).take(5)
        items(nonHeroLatest) { article ->
            Box(modifier = Modifier.padding(horizontal = 14.dp)) {
                NewsCard(
                    article = article,
                    onArticleClick = onArticleClick,
                    onBookmarkToggle = onBookmarkToggle,
                    isBookmarked = bookmarkedIds.contains(article.id)
                )
            }
        }

        // Ad Between Article Lists
        if (midAd2 != null) {
            item {
                Box(modifier = Modifier.padding(horizontal = 14.dp)) {
                    AdContainer(adPlacement = midAd2, heightDp = 130)
                }
            }
        }

        // Category Sections
        val priorityCategories = listOf("Bangladesh", "International", "Politics", "Business", "Technology", "Sports", "Entertainment")
        priorityCategories.forEach { catName ->
            val catArticles = latestArticles.filter { it.category.equals(catName, ignoreCase = true) }
            if (catArticles.isNotEmpty()) {
                item {
                    Box(modifier = Modifier.padding(horizontal = 14.dp)) {
                        CategorySection(
                            categoryName = catName,
                            articles = catArticles,
                            onArticleClick = onArticleClick,
                            onViewAllClick = onCategoryClick,
                            onBookmarkToggle = onBookmarkToggle,
                            bookmarkedIds = bookmarkedIds
                        )
                    }
                }
            }
        }

        // Footer Ad
        if (footerAd != null) {
            item {
                Box(modifier = Modifier.padding(horizontal = 14.dp)) {
                    AdContainer(adPlacement = footerAd, heightDp = 120)
                }
            }
        }

        // Global Footer
        item {
            ProtinewsFooter(
                onNavigateToPage = onNavigateToPage,
                onSelectCategory = onCategoryClick
            )
        }
    }
}
