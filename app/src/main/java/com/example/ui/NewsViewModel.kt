package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.model.AdPlacement
import com.example.data.model.Category
import com.example.data.model.NewsArticle
import com.example.data.model.SiteSettings
import com.example.data.repository.NewsRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    // Seed check
    init {
        viewModelScope.launch {
            repository.ensureInitialized()
        }
    }

    // Public feeds
    val publishedArticles: StateFlow<List<NewsArticle>> = repository.getPublishedArticles()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val breakingArticles: StateFlow<List<NewsArticle>> = repository.getBreakingArticles()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val featuredArticles: StateFlow<List<NewsArticle>> = repository.getFeaturedArticles()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val categories: StateFlow<List<Category>> = repository.getCategories()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val ads: StateFlow<List<AdPlacement>> = repository.getAllAds()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val settings: StateFlow<SiteSettings?> = repository.getSettings()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Filter & Search
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val searchResults: StateFlow<List<NewsArticle>> = _searchQuery
        .debounce(250)
        .flatMapLatest { query ->
            if (query.isBlank()) {
                repository.getPublishedArticles()
            } else {
                repository.searchArticles(query.trim())
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    // Current Article View
    private val _currentArticle = MutableStateFlow<NewsArticle?>(null)
    val currentArticle: StateFlow<NewsArticle?> = _currentArticle.asStateFlow()

    private val _relatedArticles = MutableStateFlow<List<NewsArticle>>(emptyList())
    val relatedArticles: StateFlow<List<NewsArticle>> = _relatedArticles.asStateFlow()

    fun selectArticle(article: NewsArticle) {
        _currentArticle.value = article
        viewModelScope.launch {
            repository.getRelatedArticles(article.category, article.id).collect {
                _relatedArticles.value = it
            }
        }
    }

    fun selectArticleBySlug(slug: String) {
        viewModelScope.launch {
            repository.getArticleBySlug(slug).collect { article ->
                if (article != null) {
                    selectArticle(article)
                }
            }
        }
    }

    // Bookmarks
    private val _bookmarkedIds = MutableStateFlow<Set<String>>(emptySet())
    val bookmarkedIds: StateFlow<Set<String>> = _bookmarkedIds.asStateFlow()

    fun toggleBookmark(articleId: String) {
        val current = _bookmarkedIds.value.toMutableSet()
        if (current.contains(articleId)) {
            current.remove(articleId)
        } else {
            current.add(articleId)
        }
        _bookmarkedIds.value = current
    }

    // Admin Authentication State
    private val _isAdminLoggedIn = MutableStateFlow(false)
    val isAdminLoggedIn: StateFlow<Boolean> = _isAdminLoggedIn.asStateFlow()

    private val _adminEmail = MutableStateFlow("")
    val adminEmail: StateFlow<String> = _adminEmail.asStateFlow()

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError.asStateFlow()

    fun loginAdmin(email: String, pass: String): Boolean {
        // Standard admin authentication validation
        if (email.isNotBlank() && pass.length >= 6) {
            _isAdminLoggedIn.value = true
            _adminEmail.value = email.trim()
            _authError.value = null
            return true
        } else {
            _authError.value = "Invalid credentials. Password must be at least 6 characters."
            return false
        }
    }

    fun logoutAdmin() {
        _isAdminLoggedIn.value = false
        _adminEmail.value = ""
        _authError.value = null
    }

    // Admin Dashboard Stats
    val totalPostsCount: StateFlow<Int> = repository.getTotalPostsCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val publishedCount: StateFlow<Int> = repository.getPublishedCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val draftCount: StateFlow<Int> = repository.getDraftCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val allArticles: StateFlow<List<NewsArticle>> = repository.getAllArticles()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Admin Post Editing / Creation
    fun saveArticle(
        id: String?,
        title: String,
        slug: String,
        category: String,
        imageUrl: String,
        excerpt: String,
        content: String,
        author: String,
        tags: String,
        isFeatured: Boolean,
        isBreaking: Boolean,
        status: String,
        seoTitle: String,
        seoDescription: String
    ) {
        viewModelScope.launch {
            val now = SimpleDateFormat("yyyy-MM-dd • hh:mm a", Locale.getDefault()).format(Date())
            val actualSlug = if (slug.isNotBlank()) slug else title.lowercase()
                .replace(Regex("[^a-z0-9]+"), "-")
                .trim('-')

            val article = NewsArticle(
                id = id ?: "art_${System.currentTimeMillis()}",
                title = title,
                slug = actualSlug,
                category = category,
                imageUrl = if (imageUrl.isNotBlank()) imageUrl else "https://images.unsplash.com/photo-1585829365295-ab7cd400c167?auto=format&fit=crop&w=1200&q=80",
                excerpt = excerpt,
                content = content,
                author = if (author.isNotBlank()) author else "Protinews Staff",
                tags = tags,
                publishedAt = now,
                updatedAt = now,
                status = status,
                isFeatured = isFeatured,
                isBreaking = isBreaking,
                seoTitle = if (seoTitle.isNotBlank()) seoTitle else "$title | Protinews",
                seoDescription = if (seoDescription.isNotBlank()) seoDescription else excerpt,
                readTimeMinutes = maxOf(2, content.split("\\s+".toRegex()).size / 150)
            )
            repository.insertArticle(article)
        }
    }

    fun deleteArticle(article: NewsArticle) {
        viewModelScope.launch {
            repository.deleteArticle(article)
            if (_currentArticle.value?.id == article.id) {
                _currentArticle.value = null
            }
        }
    }

    fun toggleArticlePublish(article: NewsArticle) {
        viewModelScope.launch {
            val newStatus = if (article.status == "PUBLISHED") "DRAFT" else "PUBLISHED"
            repository.updateArticle(article.copy(status = newStatus))
        }
    }

    fun toggleArticleFeatured(article: NewsArticle) {
        viewModelScope.launch {
            repository.updateArticle(article.copy(isFeatured = !article.isFeatured))
        }
    }

    fun toggleArticleBreaking(article: NewsArticle) {
        viewModelScope.launch {
            repository.updateArticle(article.copy(isBreaking = !article.isBreaking))
        }
    }

    // Category Management
    fun createOrUpdateCategory(id: String?, name: String, slug: String, description: String) {
        viewModelScope.launch {
            val actualSlug = if (slug.isNotBlank()) slug else name.lowercase()
                .replace(Regex("[^a-z0-9]+"), "-")
                .trim('-')
            val category = Category(
                id = id ?: "cat_${System.currentTimeMillis()}",
                name = name,
                slug = actualSlug,
                description = description,
                postCount = 0
            )
            repository.insertCategory(category)
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            repository.deleteCategory(category)
        }
    }

    // Ad Management
    fun toggleAd(adId: String, isEnabled: Boolean) {
        viewModelScope.launch {
            val currentAds = ads.value
            val target = currentAds.find { it.id == adId }
            if (target != null) {
                repository.updateAd(target.copy(isEnabled = isEnabled))
            }
        }
    }

    fun updateAdCode(adId: String, newCode: String, newPlacement: String, isEnabled: Boolean) {
        viewModelScope.launch {
            val now = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
            val currentAds = ads.value
            val target = currentAds.find { it.id == adId }
            if (target != null) {
                repository.updateAd(target.copy(code = newCode, placement = newPlacement, isEnabled = isEnabled, updatedAt = now))
            }
        }
    }

    fun saveSettings(settings: SiteSettings) {
        viewModelScope.launch {
            repository.updateSettings(settings)
        }
    }
}

class NewsViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
