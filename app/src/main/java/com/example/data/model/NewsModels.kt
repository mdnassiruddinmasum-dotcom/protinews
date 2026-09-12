package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class NewsArticle(
    @PrimaryKey val id: String,
    val title: String,
    val slug: String,
    val category: String,
    val imageUrl: String,
    val excerpt: String,
    val content: String,
    val author: String,
    val tags: String,
    val publishedAt: String,
    val updatedAt: String,
    val status: String = "PUBLISHED", // "PUBLISHED" or "DRAFT"
    val isFeatured: Boolean = false,
    val isBreaking: Boolean = false,
    val seoTitle: String = "",
    val seoDescription: String = "",
    val readTimeMinutes: Int = 3,
    val views: Int = 0
)

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey val id: String,
    val name: String,
    val slug: String,
    val description: String,
    val postCount: Int = 0
)

@Entity(tableName = "ads")
data class AdPlacement(
    @PrimaryKey val id: String,
    val name: String,
    val type: String, // "social_bar", "banner", "header", "sidebar", "article", "footer"
    val code: String,
    val placement: String,
    val isEnabled: Boolean = true,
    val updatedAt: String = ""
)

@Entity(tableName = "settings")
data class SiteSettings(
    @PrimaryKey val id: String = "default_settings",
    val siteName: String = "protinews",
    val tagline: String = "Truth in Journalism • Real-time News Portal",
    val logo: String = "",
    val contactEmail: String = "contact@protinews.com",
    val socialLinks: String = "Facebook: @protinews, Twitter: @protinews, YouTube: ProtiNewsLive",
    val phone: String = "+880 1700-000000",
    val address: String = "Protinews Media Tower, Motijheel C/A, Dhaka-1000, Bangladesh"
)
