package com.evgeniy.restapp.DI.Modules

import android.content.Context
import com.evgeniy.restapp.Api
import com.evgeniy.restapp.Data.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    internal fun provideRepository(api: Api, context: Context): Repository {
        return Repository(api, context)
    }
}