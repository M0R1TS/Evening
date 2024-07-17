package ru.devsokovix.evening.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ru.devsokovix.evening.view.rv_adapters.FilmListRecyclerAdapter
import ru.devsokovix.evening.view.MainActivity
import ru.devsokovix.evening.view.rv_adapters.TopSpacingItemDecoration
import ru.devsokovix.evening.databinding.FragmentFavoritesBinding
import ru.devsokovix.evening.domain.Film
import ru.devsokovix.evening.utils.AnimationHelper


class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Получаем список при транзакции фрагмента
        binding.favoritesRecycler
            .apply {
                filmsAdapter =
                    FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                        override fun click(film: Film) {
                            (requireActivity() as MainActivity).launchDetailsFragment(film)
                        }
                    })
                //Присваиваем адаптер
                adapter = filmsAdapter
                //Присвои layoutmanager
                layoutManager = LinearLayoutManager(requireContext())
                //Применяем декоратор для отступов
                val decorator = TopSpacingItemDecoration(8)
                addItemDecoration(decorator)
            }
        //Кладем нашу БД в RV
        val fileList = (requireActivity() as MainActivity).fileFavList
        var tempList = listOf<Film>()
        if (fileList != null) {
            fileList.forEach {
                if (it.isInFavorites)
                    tempList += it
            }
            if (!fileList.isEmpty())
                filmsAdapter.addItems(tempList)
        }

        AnimationHelper.performFragmentCircularRevealAnimation(binding.favoritesFragmentHome, requireActivity(), 2)
    }
}
