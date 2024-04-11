package drawable

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.devsokovix.evening.Film
import ru.devsokovix.evening.databinding.FilmItemBinding


class FilmViewHolder(
    private val binding: FilmItemBinding,
    private val clickListener: FilmListRecyclerAdapter.OnItemClickListener
) : ViewHolder(binding.root) {

    fun bind(film: Film) {
        with(binding) {
            root.setOnClickListener{
                clickListener.click(film)
            }
            title.text = film.title
            poster.setImageResource(film.poster)
            description.text = film.description
        }
    }
}