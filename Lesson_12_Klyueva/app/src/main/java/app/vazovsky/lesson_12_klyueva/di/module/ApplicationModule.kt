package app.vazovsky.lesson_12_klyueva.di.module

import android.app.Application
import android.content.Context
import app.vazovsky.lesson_12_klyueva.BridgesApplication
import dagger.Module
import dagger.Provides

@Module
open class ApplicationModule {

    @Provides
    fun provideContext(app: BridgesApplication): Context {
        return app.applicationContext
    }

    @Provides
    fun provideApplication(app: BridgesApplication): Application {
        return app
    }

}