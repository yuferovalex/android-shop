package edu.yuferov.shop.app

import android.app.Application
import edu.yuferov.shop.app.di.AppComponent
import edu.yuferov.shop.app.di.DaggerAppComponent
import edu.yuferov.shop.app.di.MainModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
            .mainModule(MainModule(this))
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}