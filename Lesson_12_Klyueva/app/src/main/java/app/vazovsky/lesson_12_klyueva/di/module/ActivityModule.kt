package app.vazovsky.lesson_12_klyueva.di.module

import app.vazovsky.lesson_12_klyueva.presentation.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity
}