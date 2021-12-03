package app.vazovsky.lesson_12_klyueva.di.module

import app.vazovsky.lesson_12_klyueva.presentation.detail.DetailFragment
import app.vazovsky.lesson_12_klyueva.presentation.list.ListFragment
import app.vazovsky.lesson_12_klyueva.presentation.map.MapFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun detailFragment(): DetailFragment

    @ContributesAndroidInjector
    abstract fun listFragment(): ListFragment

    @ContributesAndroidInjector
    abstract fun mapFragment(): MapFragment
}