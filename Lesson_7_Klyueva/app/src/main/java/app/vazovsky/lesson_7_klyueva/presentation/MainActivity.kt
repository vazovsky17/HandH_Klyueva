package app.vazovsky.lesson_7_klyueva.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import app.vazovsky.lesson_7_klyueva.R
import app.vazovsky.lesson_7_klyueva.presentation.list.ListFragment

class MainActivity : AppCompatActivity(), FragmentListener {

    private val fragmentContainerID by lazy { R.id.fragmentContainer }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .replace(fragmentContainerID, ListFragment.newInstance())
            .commit()
    }

    override fun openFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(fragmentContainerID, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun goBack() {
        supportFragmentManager
            .popBackStack()
    }
}