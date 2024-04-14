package drawable

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.devsokovix.evening.Film
import ru.devsokovix.evening.databinding.FilmItemBinding



class FilmViewHolder(private var binding: FilmItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val title =  binding.title
    private val poster = binding.poster
    private val description = binding.description

    fun bind(film: Film) {
        title.text = film.title
        Glide.with(itemView)
            .load(film.poster)
            .centerCrop()
            .into(poster)
        description.text = film.description
    }
}