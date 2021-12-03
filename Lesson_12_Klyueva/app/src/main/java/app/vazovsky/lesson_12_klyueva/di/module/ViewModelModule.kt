package app.vazovsky.lesson_12_klyueva.di.module

import androidx.lifecycle.ViewModel
import app.vazovsky.lesson_12_klyueva.di.util.ViewModelKey
import app.vazovsky.lesson_12_klyueva.presentation.detail.DetailViewModel
import app.vazovsky.lesson_12_klyueva.presentation.list.ListViewModel
import app.vazovsky.lesson_12_klyueva.presentation.map.MapViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelFactoryModule::class])
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun detailViewModel(viewModel: DetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel::class)
    abstract fun listViewModel(viewModel: ListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    abstract fun mapViewModel(viewModel: MapViewModel): ViewModel
}