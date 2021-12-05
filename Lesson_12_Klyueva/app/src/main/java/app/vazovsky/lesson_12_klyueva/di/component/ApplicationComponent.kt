package app.vazovsky.lesson_12_klyueva.di.component


import app.vazovsky.lesson_12_klyueva.BridgesApplication
import app.vazovsky.lesson_12_klyueva.di.module.ActivityModule
import app.vazovsky.lesson_12_klyueva.di.module.ApiServiceModule
import app.vazovsky.lesson_12_klyueva.di.module.FragmentModule
import app.vazovsky.lesson_12_klyueva.di.module.ViewModelFactoryModule
import app.vazovsky.lesson_12_klyueva.di.module.ViewModelModule
import dagger.android.support.AndroidSupportInjectionModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelFactoryModule::class,
        ActivityModule::class,
        FragmentModule::class,
        ViewModelModule::class,
        ApiServiceModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<BridgesApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: BridgesApplication): ApplicationComponent
    }
}