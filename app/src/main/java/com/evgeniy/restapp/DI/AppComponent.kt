package com.evgeniy.restapp.DI

import android.app.Application
import com.evgeniy.restapp.DI.Modules.AndroidModule
import com.evgeniy.restapp.DI.Modules.NetModule
import com.evgeniy.restapp.DI.Modules.RepositoryModule
import com.evgeniy.restapp.UI.ConversationScreen.ConversationPresenter
import com.evgeniy.restapp.UI.MainActivity.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidModule::class, NetModule::class, RepositoryModule::class])
interface AppComponent {
    //Injects
    fun inject(mainActivity: MainActivity)
    fun inject(app: Application)
    fun inject(conversationPresenter: ConversationPresenter)
}