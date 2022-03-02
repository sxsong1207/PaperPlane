package xyz.sxsong.paperplane

import android.app.Application
//import com.facebook.flipper.android.AndroidFlipperClient
//import com.facebook.flipper.android.utils.FlipperUtils
//import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
//import com.facebook.flipper.plugins.inspector.DescriptorMapping
//import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
//import com.facebook.soloader.SoLoader
import xyz.sxsong.paperplane.di.component.ApplicationComponent
import xyz.sxsong.paperplane.di.component.DaggerApplicationComponent
import xyz.sxsong.paperplane.di.module.ApplicationModule

class PaperPlaneApplication : Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

//        SoLoader.init(this, false)
//
//        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
//            val client = AndroidFlipperClient.getInstance(this)
//            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
//            client.addPlugin(DatabasesFlipperPlugin(this));
//            client.start()
//        }


        component = DaggerApplicationComponent.builder().applicationModule(
            ApplicationModule(this)
        ).build()
    }

}
