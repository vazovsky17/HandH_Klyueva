package app.vazovsky.lesson_12_klyueva.presentation.base

import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment(@LayoutRes layoutRes: Int) : DaggerFragment(layoutRes) {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    @MainThread
    inline fun <reified VM : ViewModel> Fragment.appViewModels() =
        createViewModelLazy(VM::class, { this.viewModelStore }, { viewModelFactory })

    @MainThread
    inline fun <reified VM : ViewModel> Fragment.appActivityViewModels() =
        createViewModelLazy(
            VM::class,
            { requireActivity().viewModelStore },
            {
                if (requireActivity() is BaseActivity) {
                    (requireActivity() as BaseActivity).viewModelFactory
                } else {
                    requireActivity().defaultViewModelProviderFactory
                }
            }
        )
}