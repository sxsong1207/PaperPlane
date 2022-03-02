package xyz.sxsong.paperplane.di.component

import android.content.Context
import xyz.sxsong.paperplane.AttachmentManager.AttachmentManagerModel
import xyz.sxsong.paperplane.LibraryActivity.Fragments.LibraryListFragment
import xyz.sxsong.paperplane.LibraryActivity.LibraryActivityModel
import xyz.sxsong.paperplane.SettingsActivity
import xyz.sxsong.paperplane.ZoteroAPI.Syncing.SyncManager
import xyz.sxsong.paperplane.ZoteroStorage.AttachmentStorageManager
import xyz.sxsong.paperplane.ZoteroStorage.ZoteroDB.ZoteroDB
import xyz.sxsong.paperplane.di.module.ApplicationModule
import xyz.sxsong.paperplane.di.module.DatabaseModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, DatabaseModule::class))
interface ApplicationComponent {
    val context: Context
    fun inject(libraryActivityModel: LibraryActivityModel)
    fun inject(settingsActivity: SettingsActivity)
    fun inject(attachmentManagerModel: AttachmentManagerModel)
    fun inject(attachmentStorageManager: AttachmentStorageManager)
    fun inject(syncManager: SyncManager)
    fun inject(zoteroDB: ZoteroDB)
    fun inject(libraryListFragment: LibraryListFragment)
}