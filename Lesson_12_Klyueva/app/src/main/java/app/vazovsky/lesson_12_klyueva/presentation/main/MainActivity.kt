package app.vazovsky.lesson_12_klyueva.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.bind
import androidx.navigation.fragment.NavHostFragment
import app.vazovsky.lesson_12_klyueva.R
import app.vazovsky.lesson_12_klyueva.databinding.ActivityMainBinding
import app.vazovsky.lesson_12_klyueva.presentation.list.ListFragment
import by.kirich1409.viewbindingdelegate.viewBinding

class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::bind)
    private val navController by lazy { (supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment).navController }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}