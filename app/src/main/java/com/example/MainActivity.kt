package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.local.NewsDatabase
import com.example.data.model.NewsArticle
import com.example.data.repository.NewsRepository
import com.example.ui.NewsViewModel
import com.example.ui.NewsViewModelFactory
import com.example.ui.components.AdContainer
import com.example.ui.components.BreakingNewsTicker
import com.example.ui.components.ProtinewsHeader
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.ProtiDarkRed
import com.example.ui.theme.ProtiRed
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = NewsDatabase.getDatabase(applicationContext, lifecycleScope)
        val repository = NewsRepository(database.newsDao())
        val viewModelFactory = NewsViewModelFactory(repository)

        setContent {
            MyApplicationTheme {
                val viewModel: NewsViewModel = viewModel(factory = viewModelFactory)
                ProtinewsApp(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProtinewsApp(viewModel: NewsViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val publishedArticles by viewModel.publishedArticles.collectAsState()
    val breakingArticles by viewModel.breakingArticles.collectAsState()
    val featuredArticles by viewModel.featuredArticles.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val ads by viewModel.ads.collectAsState()
    val settings by viewModel.settings.collectAsState()
    val bookmarkedIds by viewModel.bookmarkedIds.collectAsState()

    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val currentArticle by viewModel.currentArticle.collectAsState()
    val relatedArticles by viewModel.relatedArticles.collectAsState()

    // Navigation State
    var currentScreen by remember { mutableStateOf("home") } // "home", "article", "search", "category", "admin", "static"
    var selectedCategoryName by remember { mutableStateOf("Home") }
    var staticPageType by remember { mutableStateOf("about") } // "about", "contact", "privacy", "terms"

    // Back button handling
    BackHandler(enabled = currentScreen != "home") {
        when (currentScreen) {
            "article" -> currentScreen = if (selectedCategoryName != "Home") "category" else "home"
            "search" -> currentScreen = "home"
            "category" -> {
                selectedCategoryName = "Home"
                currentScreen = "home"
            }
            "admin" -> currentScreen = "home"
            "static" -> currentScreen = "home"
            else -> currentScreen = "home"
        }
    }

    // Social Bar Ad
    val socialBarAd = ads.find { it.placement == "Social Bar" && it.isEnabled }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(310.dp),
                drawerContainerColor = MaterialTheme.colorScheme.surface
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp)
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
                                fontSize = 26.sp,
                                fontFamily = FontFamily.Serif
                            )
                            Text(
                                text = "Independent News Portal",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Divider()
                    }

                    item {
                        Text(
                            text = "SECTIONS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            letterSpacing = 1.sp
                        )
                    }

                    item {
                        NavigationDrawerItem(
                            label = { Text("Home", fontWeight = FontWeight.SemiBold) },
                            selected = currentScreen == "home" && selectedCategoryName == "Home",
                            onClick = {
                                selectedCategoryName = "Home"
                                currentScreen = "home"
                                coroutineScope.launch { drawerState.close() }
                            },
                            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) }
                        )
                    }

                    items(categories) { cat ->
                        NavigationDrawerItem(
                            label = { Text(cat.name) },
                            selected = currentScreen == "category" && selectedCategoryName == cat.name,
                            onClick = {
                                selectedCategoryName = cat.name
                                currentScreen = "category"
                                coroutineScope.launch { drawerState.close() }
                            }
                        )
                    }

                    item {
                        Divider(modifier = Modifier.padding(vertical = 6.dp))
                        Text(
                            text = "PORTAL & POLICIES",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            letterSpacing = 1.sp
                        )
                    }

                    item {
                        NavigationDrawerItem(
                            label = { Text("About Protinews") },
                            selected = currentScreen == "static" && staticPageType == "about",
                            onClick = {
                                staticPageType = "about"
                                currentScreen = "static"
                                coroutineScope.launch { drawerState.close() }
                            },
                            icon = { Icon(imageVector = Icons.Default.Info, contentDescription = null) }
                        )
                    }

                    item {
                        NavigationDrawerItem(
                            label = { Text("Contact Editorial Desk") },
                            selected = currentScreen == "static" && staticPageType == "contact",
                            onClick = {
                                staticPageType = "contact"
                                currentScreen = "static"
                                coroutineScope.launch { drawerState.close() }
                            },
                            icon = { Icon(imageVector = Icons.Default.Mail, contentDescription = null) }
                        )
                    }

                    item {
                        NavigationDrawerItem(
                            label = { Text("Privacy Policy") },
                            selected = currentScreen == "static" && staticPageType == "privacy",
                            onClick = {
                                staticPageType = "privacy"
                                currentScreen = "static"
                                coroutineScope.launch { drawerState.close() }
                            },
                            icon = { Icon(imageVector = Icons.Default.Shield, contentDescription = null) }
                        )
                    }

                    item {
                        NavigationDrawerItem(
                            label = { Text("Terms & Conditions") },
                            selected = currentScreen == "static" && staticPageType == "terms",
                            onClick = {
                                staticPageType = "terms"
                                currentScreen = "static"
                                coroutineScope.launch { drawerState.close() }
                            },
                            icon = { Icon(imageVector = Icons.Default.Description, contentDescription = null) }
                        )
                    }

                    item {
                        Divider(modifier = Modifier.padding(vertical = 6.dp))
                        NavigationDrawerItem(
                            label = { Text("Admin Panel", color = ProtiRed, fontWeight = FontWeight.Bold) },
                            selected = currentScreen == "admin",
                            onClick = {
                                currentScreen = "admin"
                                coroutineScope.launch { drawerState.close() }
                            },
                            icon = { Icon(imageVector = Icons.Default.AdminPanelSettings, contentDescription = null, tint = ProtiRed) }
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                if (currentScreen != "admin") {
                    Column {
                        ProtinewsHeader(
                            selectedCategory = selectedCategoryName,
                            onSelectCategory = { cat ->
                                selectedCategoryName = cat
                                if (cat == "Home") {
                                    currentScreen = "home"
                                } else {
                                    currentScreen = "category"
                                }
                            },
                            categories = categories,
                            onOpenSearch = { currentScreen = "search" },
                            onOpenAdmin = { currentScreen = "admin" },
                            onMenuClick = { coroutineScope.launch { drawerState.open() } }
                        )

                        // Breaking News Ticker under header
                        BreakingNewsTicker(
                            breakingArticles = breakingArticles,
                            onArticleClick = { article ->
                                viewModel.selectArticle(article)
                                currentScreen = "article"
                            }
                        )

                        // Top Social Bar Ad if enabled
                        if (socialBarAd != null) {
                            Box(modifier = Modifier.padding(horizontal = 14.dp, vertical = 2.dp)) {
                                AdContainer(adPlacement = socialBarAd, heightDp = 100)
                            }
                        }
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentScreen) {
                    "home" -> {
                        HomeScreen(
                            featuredArticles = featuredArticles,
                            latestArticles = publishedArticles,
                            categories = categories,
                            ads = ads,
                            bookmarkedIds = bookmarkedIds,
                            onArticleClick = { article ->
                                viewModel.selectArticle(article)
                                currentScreen = "article"
                            },
                            onCategoryClick = { cat ->
                                selectedCategoryName = cat
                                currentScreen = "category"
                            },
                            onBookmarkToggle = { id -> viewModel.toggleBookmark(id) },
                            onNavigateToPage = { page ->
                                staticPageType = page
                                currentScreen = "static"
                            }
                        )
                    }
                    "article" -> {
                        currentArticle?.let { article ->
                            ArticleDetailScreen(
                                article = article,
                                relatedArticles = relatedArticles,
                                allArticles = publishedArticles,
                                ads = ads,
                                bookmarkedIds = bookmarkedIds,
                                onBackClick = {
                                    currentScreen = if (selectedCategoryName != "Home") "category" else "home"
                                },
                                onArticleClick = { next ->
                                    viewModel.selectArticle(next)
                                },
                                onBookmarkToggle = { id -> viewModel.toggleBookmark(id) }
                            )
                        } ?: run {
                            // Fallback if no article selected
                            LaunchedEffect(Unit) { currentScreen = "home" }
                        }
                    }
                    "search" -> {
                        SearchScreen(
                            searchQuery = searchQuery,
                            onSearchQueryChange = { viewModel.updateSearchQuery(it) },
                            searchResults = searchResults,
                            categories = categories,
                            bookmarkedIds = bookmarkedIds,
                            onArticleClick = { article ->
                                viewModel.selectArticle(article)
                                currentScreen = "article"
                            },
                            onBookmarkToggle = { id -> viewModel.toggleBookmark(id) },
                            onBackClick = { currentScreen = "home" }
                        )
                    }
                    "category" -> {
                        CategoryArticlesScreen(
                            categoryName = selectedCategoryName,
                            categoryInfo = categories.find { it.name.equals(selectedCategoryName, ignoreCase = true) },
                            articles = publishedArticles,
                            ads = ads,
                            bookmarkedIds = bookmarkedIds,
                            onArticleClick = { article ->
                                viewModel.selectArticle(article)
                                currentScreen = "article"
                            },
                            onBookmarkToggle = { id -> viewModel.toggleBookmark(id) },
                            onBackClick = {
                                selectedCategoryName = "Home"
                                currentScreen = "home"
                            }
                        )
                    }
                    "static" -> {
                        StaticPagesScreen(
                            pageType = staticPageType,
                            settings = settings,
                            onBackClick = { currentScreen = "home" }
                        )
                    }
                    "admin" -> {
                        AdminMainContainer(
                            viewModel = viewModel,
                            onBackToPublicSite = { currentScreen = "home" }
                        )
                    }
                }
            }
        }
    }
}
