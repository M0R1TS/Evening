package ru.devsokovix.evening

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.devsokovix.evening.databinding.ActivityDetailsBinding


class DetailsActivity : AppCompatActivity() {

    private lateinit var bindingDetails: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setFilmsDetails()
    }

    private fun setFilmsDetails(){
        //Получаем наш фильм из переданного бандла
        val film = intent.extras?.get("film") as Film

        //Устанавливаем заголовок
        bindingDetails.detailsToolbar.title = film.title
        //Устанавливаем картинку
        bindingDetails.detailsPoster.setImageResource(film.poster)
        //Устанавливаем описание
        bindingDetails.detailsDescription.text = film.description
    }
}

