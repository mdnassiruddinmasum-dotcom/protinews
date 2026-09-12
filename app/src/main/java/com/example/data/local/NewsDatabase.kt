package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.AdPlacement
import com.example.data.model.Category
import com.example.data.model.NewsArticle
import com.example.data.model.SiteSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [NewsArticle::class, Category::class, AdPlacement::class, SiteSettings::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): NewsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    "protinews_database"
                )
                .addCallback(NewsDatabaseCallback(scope))
                .build()
                INSTANCE = instance
                instance
            }
        }

        private class NewsDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateInitialDatabase(database.newsDao())
                    }
                }
            }
        }

        suspend fun populateInitialDatabase(newsDao: NewsDao) {
            newsDao.insertCategories(NewsSeedData.initialCategories)
            newsDao.insertAds(NewsSeedData.initialAds)
            newsDao.insertSettings(NewsSeedData.initialSettings)
            newsDao.insertArticles(NewsSeedData.initialArticles)
        }
    }
}
