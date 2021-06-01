package rssreader

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import rssreader.sync.RefreshWorker
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.android.AppReducer
import jetbrains.rssreader.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

@RequiresApi(Build.VERSION_CODES.O)
class BootstrapApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        initKoin()
        launchBackgroundSync()
    }

    private val appModule = module {
        single { RssReader.create(get(), BuildConfig.DEBUG) }
        single { FeedStore(get()) }
        single { Modo(AppReducer(this@BootstrapApplication)) }
    }

    private fun initKoin() {
        startKoin {
            if (BuildConfig.DEBUG) androidLogger(Level.ERROR)

            androidContext(this@BootstrapApplication)
            modules(appModule)
        }
    }

    private fun launchBackgroundSync() {
        RefreshWorker.enqueue(this)
    }

    companion object {
        internal lateinit var INSTANCE: BootstrapApplication
            private set
    }

}
