package ru.devsokovix.evening

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import drawable.FilmListRecyclerAdapter
import ru.devsokovix.evening.databinding.FilmItemBinding
import ru.devsokovix.evening.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private lateinit var filmsBinding: FragmentDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return filmsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFilmsDetails()
    }

    private fun setFilmsDetails() {
        val film = arguments?.get("film") as Film

        filmsBinding!!.detailsToolbar.title = film.title
        filmsBinding!!.detailsPoster.setImageResource(film.poster)
        filmsBinding!!.detailsDescription.text = film.description
    }
}