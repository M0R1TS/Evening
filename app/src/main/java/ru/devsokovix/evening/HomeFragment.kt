package ru.devsokovix.evening

import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import drawable.FilmListRecyclerAdapter
import ru.devsokovix.evening.databinding.FragmentHomeBinding
import ru.devsokovix.evening.databinding.FragmentWatchLaterBinding
import ru.devsokovix.evening.databinding.MergeHomeScreenContextBinding
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    private val filmsDataBase: List<Film>
        get() = listOf(
            //Все рейтинги к фильмам указаны случайно и не отражают действительность и/или мнение автора.
            Film(
                "Jaws",
                R.drawable.jaws,
                "In the story, a giant man-eating shark attacks vacationers on Amity Island, " +
                        "a fictional New England resort town, prompting the local police chief to catch it with the help of an oceanographer and a professional shark hunter.",
                7.7f
            ),
            Film(
                "KILL BILL",
                R.drawable.killbill,
                "A pregnant assassin nicknamed Black Mamba is shot during her wedding by a man named Bill. But the woman’s " +
                        "head turned out to be strong - after lying in a coma for four years, the former bride comes to her senses. She is passionate about finding the traitors." +
                        " Now only ruthless revenge will calm the heart of the Black Mamba.",
                2.8f
            ),
            Film(
                "Rocky",
                R.drawable.rocky,
                "The film takes place from November 25, 1975 to January 1, 1976, in the town of Kensington. near Philadelphia. The main character Rocky Balboa is an amateur boxer." +
                        " During the day, Rocky extracts money from the debtors of his boss, local crime boss Tony Gazzo, and in the evenings he trains and performs in the ring.",
                5.6f
            ),
            Film(
                "Shogun",
                R.drawable.shogun,
                "When a mysterious European ship is found marooned in a nearby fishing village, Lord Yoshii Toranaga discovers secrets that could tip the scales of power and devastate his enemies.",
                8.6f
            ),
            Film(
                "Game of Thrones",
                R.drawable.gameofthrones,
                "Nine noble families fight for control over the lands of Westeros, while an ancient enemy returns after being dormant for millennia.",
                1.7f
            ),
            Film(
                "Breaking Bad",
                R.drawable.breakinbad,
                "A chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine with a former student in order to secure his family's future.",
                3.9f
            ),
            Film(
                "The Wolf of Wall Street",
                R.drawable.thewolfofwallstreet,
                "Based on the true story of Jordan Belfort, from his rise to a wealthy stock-broker living the high life to his fall involving crime, corruption and the federal government.",
                9.9f
            )
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimationHelper.performFragmentCircularRevealAnimation(binding.homeFragmentRoot, requireActivity(), 1)

        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }

        //Подключаем слушателя изменений введенного текста в поиска
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            //Этот метод отрабатывает при нажатии кнопки "поиск" на софт клавиатуре
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            //Этот метод отрабатывает на каждое изменения текста
            override fun onQueryTextChange(newText: String): Boolean {
                //Если ввод пуст то вставляем в адаптер всю БД
                if (newText.isEmpty()) {
                    filmsAdapter.addItems(filmsDataBase)
                    return true
                }
                //Фильтруем список на поиску подходящих сочетаний
                val result = filmsDataBase.filter {
                    //Чтобы все работало правильно, нужно и запроси и имя фильма приводить к нижнему регистру
                    it.title.lowercase(Locale.getDefault())
                        .contains(newText.lowercase(Locale.getDefault()))
                }
                //Добавляем в адаптер
                filmsAdapter.addItems(result)
                return true
            }
        })

        //находим наш RV
        initRecyckler()
        //Кладем нашу БД в RV
        filmsAdapter.addItems(filmsDataBase)
    }


    private fun initRecyckler() {
        binding.mainRecycler.apply {
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
    }
}