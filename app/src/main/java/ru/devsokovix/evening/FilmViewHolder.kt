package drawable

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.devsokovix.evening.Film
import ru.devsokovix.evening.R
import ru.devsokovix.evening.databinding.FilmItemBinding


//В конструктор класс передается layout, который мы создали(film_item.xml)
class FilmViewHolder(private val binding: View) : ViewHolder(binding.root) {

    val filmBinding = FilmItemBinding.bind(itemView)



    fun bind(film: Film) {
        with(binding) {
            title.text = film.(R.id.title)
            poster.setImageResource(film.poster)
            filmBinding.description.text = film.description
        }
    }
}