package edu.yuferov.shop.app

import android.app.Application
import edu.yuferov.shop.app.di.AppComponent
import edu.yuferov.shop.app.di.DaggerAppComponent

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
            // .context(this)
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}