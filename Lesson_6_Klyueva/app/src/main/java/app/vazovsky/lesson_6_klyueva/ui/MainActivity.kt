package app.vazovsky.lesson_6_klyueva.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import app.vazovsky.lesson_6_klyueva.R
import app.vazovsky.lesson_6_klyueva.ui.ex3.ThirdFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private val bottomNavigation by lazy { findViewById<BottomNavigationView>(R.id.bottomNavigation) }
    private val fragmentContainerId by lazy { R.id.fragmentContainer }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(FirstFragment.newInstance())
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item1 -> {
                    replaceFragment(FirstFragment.newInstance())
                    true
                }
                R.id.item2 -> {
                    replaceFragment(SecondFragment.newInstance())
                    true
                }
                R.id.item3 -> {
                    replaceFragment(ThirdFragment.newInstance())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(fragmentContainerId, fragment)
            .addToBackStack(null)
            .commit()
    }
}