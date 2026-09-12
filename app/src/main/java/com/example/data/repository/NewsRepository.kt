package com.example.data.repository

import com.example.data.local.NewsDao
import com.example.data.local.NewsDatabase
import com.example.data.local.NewsSeedData
import com.example.data.model.AdPlacement
import com.example.data.model.Category
import com.example.data.model.NewsArticle
import com.example.data.model.SiteSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class NewsRepository(private val newsDao: NewsDao) {

    suspend fun ensureInitialized() = withContext(Dispatchers.IO) {
        val count = newsDao.getTotalPostsCount().first()
        if (count == 0) {
            NewsDatabase.populateInitialDatabase(newsDao)
        }
    }

    // Article feeds
    fun getPublishedArticles(): Flow<List<NewsArticle>> = newsDao.getPublishedArticles()
    fun getAllArticles(): Flow<List<NewsArticle>> = newsDao.getAllArticles()
    fun getBreakingArticles(): Flow<List<NewsArticle>> = newsDao.getBreakingArticles()
    fun getFeaturedArticles(): Flow<List<NewsArticle>> = newsDao.getFeaturedArticles()
    fun getArticlesByCategory(category: String): Flow<List<NewsArticle>> = newsDao.getArticlesByCategory(category)
    fun getArticleBySlug(slug: String): Flow<NewsArticle?> = newsDao.getArticleBySlug(slug)
    fun getArticleById(id: String): Flow<NewsArticle?> = newsDao.getArticleById(id)
    fun searchArticles(query: String): Flow<List<NewsArticle>> = newsDao.searchArticles(query)
    fun getRelatedArticles(category: String, currentId: String): Flow<List<NewsArticle>> =
        newsDao.getRelatedArticles(category, currentId)

    // Admin Counts
    fun getTotalPostsCount(): Flow<Int> = newsDao.getTotalPostsCount()
    fun getPublishedCount(): Flow<Int> = newsDao.getPublishedCount()
    fun getDraftCount(): Flow<Int> = newsDao.getDraftCount()

    // Article mutations
    suspend fun insertArticle(article: NewsArticle) = withContext(Dispatchers.IO) {
        newsDao.insertArticle(article)
    }

    suspend fun updateArticle(article: NewsArticle) = withContext(Dispatchers.IO) {
        newsDao.updateArticle(article)
    }

    suspend fun deleteArticle(article: NewsArticle) = withContext(Dispatchers.IO) {
        newsDao.deleteArticle(article)
    }

    suspend fun deleteArticleById(id: String) = withContext(Dispatchers.IO) {
        newsDao.deleteArticleById(id)
    }

    // Categories
    fun getCategories(): Flow<List<Category>> = newsDao.getCategories()

    suspend fun insertCategory(category: Category) = withContext(Dispatchers.IO) {
        newsDao.insertCategory(category)
    }

    suspend fun updateCategory(category: Category) = withContext(Dispatchers.IO) {
        newsDao.updateCategory(category)
    }

    suspend fun deleteCategory(category: Category) = withContext(Dispatchers.IO) {
        newsDao.deleteCategory(category)
    }

    // Ads
    fun getAllAds(): Flow<List<AdPlacement>> = newsDao.getAllAds()
    fun getAdByPlacement(placement: String): Flow<AdPlacement?> = newsDao.getAdByPlacement(placement)

    suspend fun updateAd(ad: AdPlacement) = withContext(Dispatchers.IO) {
        newsDao.updateAd(ad)
    }

    suspend fun insertAd(ad: AdPlacement) = withContext(Dispatchers.IO) {
        newsDao.insertAd(ad)
    }

    // Settings
    fun getSettings(): Flow<SiteSettings?> = newsDao.getSettings()

    suspend fun updateSettings(settings: SiteSettings) = withContext(Dispatchers.IO) {
        newsDao.insertSettings(settings)
    }
}
