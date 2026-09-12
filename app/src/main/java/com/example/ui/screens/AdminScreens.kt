package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AdPlacement
import com.example.data.model.Category
import com.example.data.model.NewsArticle
import com.example.data.model.SiteSettings
import com.example.ui.NewsViewModel
import com.example.ui.components.AdContainer
import com.example.ui.theme.ProtiBreaking
import com.example.ui.theme.ProtiRed

@Composable
fun AdminMainContainer(
    viewModel: NewsViewModel,
    onBackToPublicSite: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isLoggedIn by viewModel.isAdminLoggedIn.collectAsState()

    if (!isLoggedIn) {
        AdminLoginScreen(
            viewModel = viewModel,
            onBackToSite = onBackToPublicSite
        )
    } else {
        AdminDashboardScaffold(
            viewModel = viewModel,
            onBackToSite = onBackToPublicSite
        )
    }
}

@Composable
fun AdminLoginScreen(
    viewModel: NewsViewModel,
    onBackToSite: () -> Unit
) {
    var email by remember { mutableStateOf("admin@protinews.com") }
    var password by remember { mutableStateOf("AdminPass2026!") }
    val authError by viewModel.authError.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
            .padding(24.dp)
            .testTag("admin_login_screen"),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 440.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(ProtiRed.copy(alpha = 0.1f))
                        .padding(14.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AdminPanelSettings,
                        contentDescription = "Admin",
                        tint = ProtiRed,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Text(
                    text = "protinews Admin",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Serif,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "Secure portal for editors & digital media administrators",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 16.sp
                )

                if (authError != null) {
                    Text(
                        text = authError ?: "",
                        color = ProtiRed,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Admin Email / Username") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("admin_email_input"),
                    singleLine = true,
                    leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) }
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("admin_password_input"),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) }
                )

                Button(
                    onClick = { viewModel.loginAdmin(email, password) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("admin_login_button"),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ProtiRed)
                ) {
                    Text(
                        text = "Log In to Editorial Console",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                TextButton(
                    onClick = onBackToSite,
                    modifier = Modifier.testTag("admin_back_to_site")
                ) {
                    Text("← Return to Public Protinews Portal", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
fun AdminDashboardScaffold(
    viewModel: NewsViewModel,
    onBackToSite: () -> Unit
) {
    var currentTab by remember { mutableStateOf("Dashboard") } // "Dashboard", "Posts", "Add Post", "Categories", "Ads", "Settings"
    var editingArticle by remember { mutableStateOf<NewsArticle?>(null) }

    val adminEmail by viewModel.adminEmail.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("admin_dashboard_scaffold")
    ) {
        // Top Admin Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F172A))
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(ProtiRed)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text("ADMIN", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Black)
                }
                Text(
                    text = "protinews Console",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = adminEmail,
                    color = Color(0xFF94A3B8),
                    fontSize = 11.sp,
                    maxLines = 1
                )

                IconButton(
                    onClick = onBackToSite,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = "View Public Site", tint = Color.White, modifier = Modifier.size(18.dp))
                }

                IconButton(
                    onClick = { viewModel.logoutAdmin() },
                    modifier = Modifier
                        .size(32.dp)
                        .testTag("admin_logout_button")
                ) {
                    Icon(imageVector = Icons.Default.Logout, contentDescription = "Logout", tint = Color(0xFFEF4444), modifier = Modifier.size(18.dp))
                }
            }
        }

        // Sub Navigation Tabs (Dashboard, Posts, Add New Post, Categories, Ads, Settings)
        val scrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .horizontalScroll(scrollState)
                .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                .padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val tabs = listOf("Dashboard", "Posts", "Add New Post", "Categories", "Advertisements", "Settings")
            tabs.forEach { tab ->
                val isSelected = currentTab == tab && editingArticle == null
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) ProtiRed else Color.Transparent)
                        .clickable {
                            currentTab = tab
                            editingArticle = null
                        }
                        .padding(horizontal = 12.dp, vertical = 7.dp)
                        .testTag("admin_tab_$tab")
                ) {
                    Text(
                        text = tab,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // Main Tab Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp)
        ) {
            when {
                editingArticle != null -> {
                    AdminPostEditor(
                        article = editingArticle,
                        viewModel = viewModel,
                        onDone = {
                            editingArticle = null
                            currentTab = "Posts"
                        }
                    )
                }
                currentTab == "Dashboard" -> {
                    AdminDashboardOverview(
                        viewModel = viewModel,
                        onCreatePostClick = { currentTab = "Add New Post" },
                        onEditArticle = { article -> editingArticle = article }
                    )
                }
                currentTab == "Posts" -> {
                    AdminPostsManager(
                        viewModel = viewModel,
                        onEditArticle = { article -> editingArticle = article },
                        onCreatePost = { currentTab = "Add New Post" }
                    )
                }
                currentTab == "Add New Post" -> {
                    AdminPostEditor(
                        article = null,
                        viewModel = viewModel,
                        onDone = { currentTab = "Posts" }
                    )
                }
                currentTab == "Categories" -> {
                    AdminCategoryManager(viewModel = viewModel)
                }
                currentTab == "Advertisements" -> {
                    AdminAdManager(viewModel = viewModel)
                }
                currentTab == "Settings" -> {
                    AdminSettingsManager(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun AdminDashboardOverview(
    viewModel: NewsViewModel,
    onCreatePostClick: () -> Unit,
    onEditArticle: (NewsArticle) -> Unit
) {
    val totalPosts by viewModel.totalPostsCount.collectAsState()
    val publishedCount by viewModel.publishedCount.collectAsState()
    val draftCount by viewModel.draftCount.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val allArticles by viewModel.allArticles.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Welcome strip
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = ProtiRed.copy(alpha = 0.08f)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "Newsroom Control Center",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = ProtiRed
                        )
                        Text(
                            text = "Real-time synchronization active with Protinews live frontend.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Button(
                        onClick = onCreatePostClick,
                        colors = ButtonDefaults.buttonColors(containerColor = ProtiRed),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.testTag("dashboard_create_post_btn")
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("New Post", fontSize = 12.sp)
                    }
                }
            }
        }

        // 4 Key Metric Cards
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MetricCard(
                    title = "Total Posts",
                    value = totalPosts.toString(),
                    color = Color(0xFF0284C7),
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title = "Published",
                    value = publishedCount.toString(),
                    color = Color(0xFF16A34A),
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title = "Drafts",
                    value = draftCount.toString(),
                    color = Color(0xFFF59E0B),
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title = "Categories",
                    value = categories.size.toString(),
                    color = Color(0xFF8B5CF6),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Recent posts header
        item {
            Text(
                text = "RECENT POSTS IN SYSTEM",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        items(allArticles.take(5)) { article ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onEditArticle(article) },
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = article.category.uppercase(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = ProtiRed
                            )
                            if (article.isBreaking) {
                                Text(
                                    text = "⚡ BREAKING",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ProtiBreaking
                                )
                            }
                            if (article.isFeatured) {
                                Text(
                                    text = "★ FEATURED",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFD97706)
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(if (article.status == "PUBLISHED") Color(0xFFDCFCE7) else Color(0xFFFEF3C7))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = article.status,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (article.status == "PUBLISHED") Color(0xFF16A34A) else Color(0xFFD97706)
                                )
                            }
                        }

                        Text(
                            text = article.title,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = "Author: ${article.author} • ${article.publishedAt}",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MetricCard(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = title, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Black, color = color)
        }
    }
}

@Composable
fun AdminPostsManager(
    viewModel: NewsViewModel,
    onEditArticle: (NewsArticle) -> Unit,
    onCreatePost: () -> Unit
) {
    val allArticles by viewModel.allArticles.collectAsState()
    val categories by viewModel.categories.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    val filtered = remember(allArticles, searchQuery, selectedCategory) {
        allArticles.filter { article ->
            val matchesCategory = selectedCategory == "All" || article.category.equals(selectedCategory, ignoreCase = true)
            val matchesQuery = searchQuery.isBlank() || article.title.contains(searchQuery, ignoreCase = true) || article.content.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Search and Actions bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Filter articles...") },
                modifier = Modifier
                    .weight(1f)
                    .testTag("admin_post_search_field"),
                singleLine = true,
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) }
            )

            Button(
                onClick = onCreatePost,
                colors = ButtonDefaults.buttonColors(containerColor = ProtiRed),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Add Post", fontSize = 12.sp)
            }
        }

        // Category pills
        val scrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            (listOf("All") + categories.map { it.name }).forEach { cat ->
                val isSelected = selectedCategory == cat
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) ProtiRed else MaterialTheme.colorScheme.surfaceVariant)
                        .clickable { selectedCategory = cat }
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = cat,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // Article count
        Text(
            text = "Found ${filtered.size} articles",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Article Table / List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filtered) { article ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = article.category.uppercase(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = ProtiRed
                            )

                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                // Toggle status button
                                Button(
                                    onClick = { viewModel.toggleArticlePublish(article) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (article.status == "PUBLISHED") Color(0xFFDCFCE7) else Color(0xFFFEF3C7),
                                        contentColor = if (article.status == "PUBLISHED") Color(0xFF16A34A) else Color(0xFFD97706)
                                    ),
                                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
                                    shape = RoundedCornerShape(4.dp),
                                    modifier = Modifier.height(24.dp)
                                ) {
                                    Text(text = article.status, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                }

                                // Toggle Featured
                                IconButton(
                                    onClick = { viewModel.toggleArticleFeatured(article) },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = if (article.isFeatured) Icons.Default.Star else Icons.Default.StarBorder,
                                        contentDescription = "Featured",
                                        tint = if (article.isFeatured) Color(0xFFD97706) else Color.Gray,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }

                                // Toggle Breaking
                                IconButton(
                                    onClick = { viewModel.toggleArticleBreaking(article) },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.FlashOn,
                                        contentDescription = "Breaking",
                                        tint = if (article.isBreaking) ProtiBreaking else Color.Gray,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }

                        Text(
                            text = article.title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = article.excerpt,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "By ${article.author} • ${article.publishedAt}",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                OutlinedButton(
                                    onClick = { onEditArticle(article) },
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                    shape = RoundedCornerShape(4.dp),
                                    modifier = Modifier.height(28.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(12.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Edit", fontSize = 11.sp)
                                }

                                Button(
                                    onClick = { viewModel.deleteArticle(article) },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                    shape = RoundedCornerShape(4.dp),
                                    modifier = Modifier.height(28.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(12.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Delete", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminPostEditor(
    article: NewsArticle?,
    viewModel: NewsViewModel,
    onDone: () -> Unit
) {
    val categories by viewModel.categories.collectAsState()

    var title by remember { mutableStateOf(article?.title ?: "") }
    var slug by remember { mutableStateOf(article?.slug ?: "") }
    var category by remember { mutableStateOf(article?.category ?: categories.firstOrNull()?.name ?: "Bangladesh") }
    var imageUrl by remember { mutableStateOf(article?.imageUrl ?: "") }
    var excerpt by remember { mutableStateOf(article?.excerpt ?: "") }
    var content by remember { mutableStateOf(article?.content ?: "") }
    var author by remember { mutableStateOf(article?.author ?: "Editorial Staff") }
    var tags by remember { mutableStateOf(article?.tags ?: "") }
    var isFeatured by remember { mutableStateOf(article?.isFeatured ?: false) }
    var isBreaking by remember { mutableStateOf(article?.isBreaking ?: false) }
    var status by remember { mutableStateOf(article?.status ?: "PUBLISHED") }
    var seoTitle by remember { mutableStateOf(article?.seoTitle ?: "") }
    var seoDescription by remember { mutableStateOf(article?.seoDescription ?: "") }

    var categoryExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("admin_post_editor"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (article == null) "Create News Article" else "Edit News Article",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                TextButton(onClick = onDone) {
                    Text("Cancel")
                }
            }
        }

        // Title
        item {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    if (slug.isBlank() || slug == article?.slug) {
                        slug = it.lowercase().replace(Regex("[^a-z0-9]+"), "-").trim('-')
                    }
                },
                label = { Text("Article Headline / Title *") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("editor_title_input")
            )
        }

        // Slug & Category row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = slug,
                    onValueChange = { slug = it },
                    label = { Text("URL Slug") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )

                // Category selector
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedButton(
                        onClick = { categoryExpanded = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    ) {
                        Text(text = "Category: $category", fontSize = 12.sp, maxLines = 1)
                    }
                    DropdownMenu(
                        expanded = categoryExpanded,
                        onDismissRequest = { categoryExpanded = false }
                    ) {
                        categories.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat.name) },
                                onClick = {
                                    category = cat.name
                                    categoryExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }

        // Featured Image URL with presets
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("Featured Image URL *") },
                    placeholder = { Text("https://...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("editor_image_input")
                )

                // Quick image suggestion presets
                Text(text = "Quick Presets:", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val presets = listOf(
                        "Padma Bridge / Transit" to "https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?auto=format&fit=crop&w=1200&q=80",
                        "Climate / Nature" to "https://images.unsplash.com/photo-1532187863486-abf9dbad1b69?auto=format&fit=crop&w=1200&q=80",
                        "Tech / AI" to "https://images.unsplash.com/photo-1518770660439-4636190af475?auto=format&fit=crop&w=1200&q=80",
                        "Business / Finance" to "https://images.unsplash.com/photo-1611974789855-9c2a0a7236a3?auto=format&fit=crop&w=1200&q=80",
                        "Cricket / Stadium" to "https://images.unsplash.com/photo-1540747913346-19e32dc3e97e?auto=format&fit=crop&w=1200&q=80"
                    )
                    presets.forEach { (label, url) ->
                        OutlinedButton(
                            onClick = { imageUrl = url },
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.height(26.dp)
                        ) {
                            Text(label, fontSize = 10.sp)
                        }
                    }
                }
            }
        }

        // Short Description / Excerpt
        item {
            OutlinedTextField(
                value = excerpt,
                onValueChange = { excerpt = it },
                label = { Text("Short Description / Excerpt *") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("editor_excerpt_input"),
                maxLines = 3
            )
        }

        // Full Article Content
        item {
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Full Article Content (paragraphs separated by blank lines) *") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .testTag("editor_content_input")
            )
        }

        // Author & Tags
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = author,
                    onValueChange = { author = it },
                    label = { Text("Author / Reporter") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = tags,
                    onValueChange = { tags = it },
                    label = { Text("Tags (comma separated)") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }
        }

        // Toggles: Featured, Breaking, Status
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Publish Immediately (Status: $status)", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        Switch(
                            checked = status == "PUBLISHED",
                            onCheckedChange = { status = if (it) "PUBLISHED" else "DRAFT" }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Mark as Featured Story", fontSize = 13.sp)
                        Switch(checked = isFeatured, onCheckedChange = { isFeatured = it })
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Mark as Breaking News (Ticker)", fontSize = 13.sp)
                        Switch(checked = isBreaking, onCheckedChange = { isBreaking = it })
                    }
                }
            }
        }

        // SEO Fields
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(text = "Search Engine Optimization (SEO)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                OutlinedTextField(
                    value = seoTitle,
                    onValueChange = { seoTitle = it },
                    label = { Text("SEO Meta Title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = seoDescription,
                    onValueChange = { seoDescription = it },
                    label = { Text("SEO Meta Description") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 2
                )
            }
        }

        // Save Button
        item {
            Button(
                onClick = {
                    if (title.isNotBlank() && content.isNotBlank()) {
                        viewModel.saveArticle(
                            id = article?.id,
                            title = title,
                            slug = slug,
                            category = category,
                            imageUrl = imageUrl,
                            excerpt = excerpt,
                            content = content,
                            author = author,
                            tags = tags,
                            isFeatured = isFeatured,
                            isBreaking = isBreaking,
                            status = status,
                            seoTitle = seoTitle,
                            seoDescription = seoDescription
                        )
                        onDone()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("editor_save_post_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = ProtiRed),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text(if (article == null) "Publish Article to Protinews" else "Update Article", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun AdminCategoryManager(viewModel: NewsViewModel) {
    val categories by viewModel.categories.collectAsState()
    val allArticles by viewModel.allArticles.collectAsState()

    var newCategoryName by remember { mutableStateOf("") }
    var newCategoryDesc by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(text = "Manage News Categories", fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif)
            Text(text = "Control sections available across header navigation and home feeds.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        // Add Category Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Add New Category", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    OutlinedTextField(
                        value = newCategoryName,
                        onValueChange = { newCategoryName = it },
                        label = { Text("Category Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("admin_new_category_name"),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = newCategoryDesc,
                        onValueChange = { newCategoryDesc = it },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 2
                    )
                    Button(
                        onClick = {
                            if (newCategoryName.isNotBlank()) {
                                viewModel.createOrUpdateCategory(null, newCategoryName, "", newCategoryDesc)
                                newCategoryName = ""
                                newCategoryDesc = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ProtiRed),
                        modifier = Modifier.testTag("admin_add_category_btn")
                    ) {
                        Text("Create Category")
                    }
                }
            }
        }

        // Category list
        item {
            Text(text = "Active Categories (${categories.size})", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }

        items(categories) { cat ->
            val count = allArticles.count { it.category.equals(cat.name, ignoreCase = true) }
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(text = cat.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(text = "Slug: /category/${cat.slug} • $count published articles", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        if (cat.description.isNotBlank()) {
                            Text(text = cat.description, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }

                    IconButton(onClick = { viewModel.deleteCategory(cat) }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFEF4444), modifier = Modifier.size(18.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun AdminAdManager(viewModel: NewsViewModel) {
    val ads by viewModel.ads.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("admin_ad_manager"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(text = "Advertisement Management", fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif)
            Text(text = "Control live advertising scripts and containers across designated placements.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        items(ads) { ad ->
            var expandedCode by remember { mutableStateOf(false) }
            var editCode by remember { mutableStateOf(ad.code) }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = ad.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text(text = "Placement: ${ad.placement}", fontSize = 11.sp, color = ProtiRed, fontWeight = FontWeight.SemiBold)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(text = if (ad.isEnabled) "ACTIVE" else "DISABLED", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = if (ad.isEnabled) Color(0xFF16A34A) else Color.Gray)
                            Switch(
                                checked = ad.isEnabled,
                                onCheckedChange = { viewModel.toggleAd(ad.id, it) },
                                modifier = Modifier.testTag("toggle_ad_${ad.id}")
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = { expandedCode = !expandedCode },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(text = if (expandedCode) "Hide Ad Script" else "View / Edit Ad Script", fontSize = 12.sp)
                    }

                    if (expandedCode) {
                        OutlinedTextField(
                            value = editCode,
                            onValueChange = { editCode = it },
                            label = { Text("HTML / Script Code") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .testTag("ad_code_input_${ad.id}")
                        )

                        Button(
                            onClick = {
                                viewModel.updateAdCode(ad.id, editCode, ad.placement, ad.isEnabled)
                                expandedCode = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ProtiRed),
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Save Ad Script")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminSettingsManager(viewModel: NewsViewModel) {
    val settings by viewModel.settings.collectAsState()

    var siteName by remember { mutableStateOf(settings?.siteName ?: "protinews") }
    var tagline by remember { mutableStateOf(settings?.tagline ?: "Truth in Journalism • Real-time News Portal") }
    var email by remember { mutableStateOf(settings?.contactEmail ?: "editor@protinews.com") }
    var phone by remember { mutableStateOf(settings?.phone ?: "+880 1700-000000") }
    var address by remember { mutableStateOf(settings?.address ?: "Protinews Tower, Motijheel, Dhaka") }
    var saved by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(text = "Global Portal Settings", fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif)
            Text(text = "Institutional credentials and editorial information.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = siteName,
                        onValueChange = { siteName = it },
                        label = { Text("Site Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = tagline,
                        onValueChange = { tagline = it },
                        label = { Text("Tagline") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Contact Email") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Phone Hotline") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Bureau Address") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            viewModel.saveSettings(
                                SiteSettings(
                                    siteName = siteName,
                                    tagline = tagline,
                                    contactEmail = email,
                                    phone = phone,
                                    address = address
                                )
                            )
                            saved = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ProtiRed),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Save Portal Settings")
                    }

                    if (saved) {
                        Text("✓ Configuration successfully updated.", color = Color(0xFF16A34A), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
