package drawable

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.devsokovix.evening.Film
import ru.devsokovix.evening.R
import ru.devsokovix.evening.databinding.FilmItemBinding


class FilmViewHolder(private val binding: FilmItemBinding) : ViewHolder(binding.root) {

    val filmBinding = FilmItemBinding.bind(itemView)

    fun bind(film: Film) {
        with(binding) {
            filmBinding.title.text = film.title
            filmBinding.poster.setImageResource(film.poster)
            filmBinding.description.text = film.description
        }
    }
}