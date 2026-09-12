package com.example.data.local

import androidx.room.*
import com.example.data.model.AdPlacement
import com.example.data.model.Category
import com.example.data.model.NewsArticle
import com.example.data.model.SiteSettings
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    // Articles
    @Query("SELECT * FROM articles WHERE status = 'PUBLISHED' ORDER BY publishedAt DESC")
    fun getPublishedArticles(): Flow<List<NewsArticle>>

    @Query("SELECT * FROM articles ORDER BY publishedAt DESC")
    fun getAllArticles(): Flow<List<NewsArticle>>

    @Query("SELECT * FROM articles WHERE status = 'PUBLISHED' AND isBreaking = 1 ORDER BY publishedAt DESC LIMIT 10")
    fun getBreakingArticles(): Flow<List<NewsArticle>>

    @Query("SELECT * FROM articles WHERE status = 'PUBLISHED' AND isFeatured = 1 ORDER BY publishedAt DESC LIMIT 5")
    fun getFeaturedArticles(): Flow<List<NewsArticle>>

    @Query("SELECT * FROM articles WHERE status = 'PUBLISHED' AND category = :categoryName ORDER BY publishedAt DESC")
    fun getArticlesByCategory(categoryName: String): Flow<List<NewsArticle>>

    @Query("SELECT * FROM articles WHERE slug = :slug LIMIT 1")
    fun getArticleBySlug(slug: String): Flow<NewsArticle?>

    @Query("SELECT * FROM articles WHERE id = :id LIMIT 1")
    fun getArticleById(id: String): Flow<NewsArticle?>

    @Query("SELECT * FROM articles WHERE status = 'PUBLISHED' AND (title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%' OR tags LIKE '%' || :query || '%' OR category LIKE '%' || :query || '%') ORDER BY publishedAt DESC")
    fun searchArticles(query: String): Flow<List<NewsArticle>>

    @Query("SELECT * FROM articles WHERE status = 'PUBLISHED' AND category = :category AND id != :currentId ORDER BY publishedAt DESC LIMIT :limit")
    fun getRelatedArticles(category: String, currentId: String, limit: Int = 4): Flow<List<NewsArticle>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: NewsArticle)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<NewsArticle>)

    @Update
    suspend fun updateArticle(article: NewsArticle)

    @Delete
    suspend fun deleteArticle(article: NewsArticle)

    @Query("DELETE FROM articles WHERE id = :id")
    suspend fun deleteArticleById(id: String)

    @Query("SELECT COUNT(*) FROM articles")
    fun getTotalPostsCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM articles WHERE status = 'PUBLISHED'")
    fun getPublishedCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM articles WHERE status = 'DRAFT'")
    fun getDraftCount(): Flow<Int>

    // Categories
    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun getCategories(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<Category>)

    @Update
    suspend fun updateCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("DELETE FROM categories WHERE id = :id")
    suspend fun deleteCategoryById(id: String)

    // Ads
    @Query("SELECT * FROM ads ORDER BY id ASC")
    fun getAllAds(): Flow<List<AdPlacement>>

    @Query("SELECT * FROM ads WHERE placement = :placement AND isEnabled = 1 LIMIT 1")
    fun getAdByPlacement(placement: String): Flow<AdPlacement?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAd(ad: AdPlacement)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAds(ads: List<AdPlacement>)

    @Update
    suspend fun updateAd(ad: AdPlacement)

    // Settings
    @Query("SELECT * FROM settings WHERE id = 'default_settings' LIMIT 1")
    fun getSettings(): Flow<SiteSettings?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: SiteSettings)
}
