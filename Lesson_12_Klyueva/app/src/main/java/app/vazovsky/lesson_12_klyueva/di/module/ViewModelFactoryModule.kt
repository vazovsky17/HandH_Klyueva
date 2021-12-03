package app.vazovsky.lesson_12_klyueva.di.module

import androidx.lifecycle.ViewModelProvider
import app.vazovsky.lesson_12_klyueva.di.util.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory):ViewModelProvider.Factory

}