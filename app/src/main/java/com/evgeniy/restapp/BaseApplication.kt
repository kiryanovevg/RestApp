package com.evgeniy.restapp

import android.app.Application
import com.evgeniy.restapp.DI.Modules.AndroidModule
import com.evgeniy.restapp.DI.AppComponent
import com.evgeniy.restapp.DI.DaggerAppComponent
import com.evgeniy.restapp.DI.Modules.NetModule
import com.evgeniy.restapp.DI.Modules.RepositoryModule
import com.vk.sdk.VKSdk


class BaseApplication : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = initDaggerComponent()
        component.inject(this)

        VKSdk.initialize(this)
    }

    protected fun initDaggerComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .netModule(NetModule(
                        getString(R.string.base_url),
                        getString(R.string.auth_url),
                        getString(R.string.apiV)))
                .androidModule(AndroidModule(this))
                .repositoryModule(RepositoryModule())
                .build()
    }
}