package dev.olog.msc.app

import android.content.BroadcastReceiver
import android.content.Context
import androidx.preference.PreferenceManager
import com.google.android.play.core.splitcompat.SplitCompat
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasBroadcastReceiverInjector
import dev.olog.appshortcuts.AppShortcuts
import dev.olog.core.interactor.SleepTimerUseCase
import dev.olog.injection.CoreComponent
import dev.olog.msc.BuildConfig
import dev.olog.msc.R
import io.alterac.blurkit.BlurKit
import javax.inject.Inject

class App : ThemedApp(), HasBroadcastReceiverInjector {

    @Inject
    internal lateinit var broadcastInjector: DispatchingAndroidInjector<BroadcastReceiver>

    private lateinit var appShortcuts: AppShortcuts

    @Inject
    lateinit var sleepTimerUseCase: SleepTimerUseCase

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        inject()
        initializeComponents()
        initializeConstants()
        resetSleepTimer()

        registerActivityLifecycleCallbacks(CustomTabsActivityLifecycleCallback())
    }

    private fun initializeComponents() {
        appShortcuts = AppShortcuts.instance(this)

        BlurKit.init(this)
        if (BuildConfig.DEBUG) {
//            LeakCanary.install(this)
//            Stetho.initializeWithDefaults(this)
        }
    }

    private fun initializeConstants() {
        PreferenceManager.setDefaultValues(this, R.xml.prefs, false)
    }

    private fun resetSleepTimer() {
        sleepTimerUseCase.reset()
    }

    private fun inject() {
        DaggerAppComponent.factory()
            .create(CoreComponent.coreComponent(this))
            .inject(this)
    }

    // enables dynamic module
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }

    override fun broadcastReceiverInjector(): AndroidInjector<BroadcastReceiver> = broadcastInjector
}
