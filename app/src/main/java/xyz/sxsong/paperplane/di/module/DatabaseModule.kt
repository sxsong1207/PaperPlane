package xyz.sxsong.paperplane.di.module

import android.content.Context
import xyz.sxsong.paperplane.ZoteroStorage.Database.ZoteroDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun getDatabase(context: Context): ZoteroDatabase {
        return ZoteroDatabase(context)
    }
}