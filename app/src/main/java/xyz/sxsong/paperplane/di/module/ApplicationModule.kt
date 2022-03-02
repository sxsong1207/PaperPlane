package xyz.sxsong.paperplane.di.module

import android.content.Context
import xyz.sxsong.paperplane.PreferenceManager
import xyz.sxsong.paperplane.PaperPlaneApplication
import xyz.sxsong.paperplane.ZoteroStorage.AttachmentStorageManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(val app: PaperPlaneApplication) {

    @Provides
    fun provideContext(): Context {
        return app
    }

    @Singleton
    @Provides
    fun providesAttachmentStorageManager(
        context: Context,
        preferenceManager: PreferenceManager
    ): AttachmentStorageManager {
        return AttachmentStorageManager(context, preferenceManager)
    }

    @Singleton
    @Provides
    fun providePreferenceManager(context: Context): PreferenceManager {
        return PreferenceManager(context)
    }
}