package ru.devsokovix.evening

import android.content.Intent
import android.health.connect.datatypes.units.Percentage
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import ru.devsokovix.evening.databinding.ActivityHomeBinding
import ru.devsokovix.evening.databinding.ActivityMainBinding
import ru.devsokovix.evening.databinding.FilmItemBinding


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private var backPressed = 0L

    private lateinit var binding: ActivityMainBinding
    private lateinit var animator: FilmItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        initNavigation()

        //Зупускаем фрагмент при старте
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_placeholder, HomeFragment())
            .addToBackStack(null)
            .commit()
    }

    fun launchDetailsFragment(film: Film) {
        //Создаем "посылку"
        val bundle = Bundle()
        //Кладем наш фильм в "посылку"
        bundle.putParcelable("film", film)
        //Кладем фрагмент с деталями в перменную
        val fragment = DetailsFragment()
        //Прикрепляем нашу "посылку" к фрагменту
        fragment.arguments = bundle

        //Запускаем фрагмент
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            if (backPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                super.onBackPressed()
                finish()
            } else {
                Toast.makeText(this, "Для выхода - нажмите еще раз", Toast.LENGTH_SHORT).show()
            }

            backPressed = System.currentTimeMillis()
        } else {
            super.onBackPressed()
        }
    }


    companion object {
        const val TIME_INTERVAL = 2000
    }


    private fun initNavigation() {

        binding.bottomNavigation.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.home -> {
                    val tag = "home"
                    val fragment = checkFragmentExistence(tag)
                    //В первом параметре, если фрагмент не найден и метод вернул null, то с помощью
                    //элвиса мы вызываем создание нового фрагмента
                    changeFragment( fragment?: HomeFragment(), tag)
                    true
                }
                R.id.favorites -> {
                    val tag = "favorites"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment( fragment?: FavoritesFragment(), tag)
                    true
                }

                R.id.watch_later -> {
                    val tag = "watch_later"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment( fragment?: WatchLaterFragment(), tag)
                    true
                }

                R.id.selections -> {
                    val tag = "selections"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment( fragment?: SelectionsFragment(), tag)
                    true
                }

                else -> false
            }
        }
    }
    //Ищем фрагмент по тэгу, если он есть то возвращаем его, если нет - то null
    private fun checkFragmentExistence(tag: String): Fragment? = supportFragmentManager.findFragmentByTag(tag)

    private fun changeFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment, tag)
            .addToBackStack(null)
            .commit()
    }

}