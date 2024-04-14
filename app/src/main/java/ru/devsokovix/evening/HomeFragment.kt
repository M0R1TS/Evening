package ru.devsokovix.evening

import android.os.Bundle
import android.transition.Scene
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
import ru.devsokovix.evening.databinding.MergeHomeScreenContextBinding
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var binding: MergeHomeScreenContextBinding
    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    private val filmsDataBase: List<Film>
        get() = listOf(
        Film(
            "Jaws",
            R.drawable.jaws,
            "In the story, a giant man-eating shark attacks vacationers on Amity Island, " +
                    "a fictional New England resort town, prompting the local police chief to catch it with the help of an oceanographer and a professional shark hunter."
        ),
        Film(
            "KILL BILL",
            R.drawable.killbill,
            "A pregnant assassin nicknamed Black Mamba is shot during her wedding by a man named Bill. But the woman’s " +
                    "head turned out to be strong - after lying in a coma for four years, the former bride comes to her senses. She is passionate about finding the traitors." +
                    " Now only ruthless revenge will calm the heart of the Black Mamba."
        ),
        Film(
            "Pulp Fiction",
            R.drawable.pulpfiction,
            "Following Tarantino's previous film Reservoir Dogs, parts of the plot of Pulp Fiction were split up, " +
                    "mixed up and shown in the \"wrong\" order; a technique previously used by directors of the French New Wave, notably Jean-Luc Godard and François Truffaut, as well as Stanley Kubrick in The Killing."
        ),
        Film(
            "Rocky",
            R.drawable.rocky,
            "The film takes place from November 25, 1975 to January 1, 1976, in the town of Kensington. near Philadelphia. The main character Rocky Balboa is an amateur boxer." +
                    " During the day, Rocky extracts money from the debtors of his boss, local crime boss Tony Gazzo, and in the evenings he trains and performs in the ring."
        ),
        Film(
            "Shogun",
            R.drawable.shogun,
            "When a mysterious European ship is found marooned in a nearby fishing village, Lord Yoshii Toranaga discovers secrets that could tip the scales of power and devastate his enemies."
        ),
        Film(
            "Game of Thrones",
            R.drawable.gameofthrones,
            "Nine noble families fight for control over the lands of Westeros, while an ancient enemy returns after being dormant for millennia."
        ),
        Film(
            "Breaking Bad",
            R.drawable.breakinbad,
            "A chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine with a former student in order to secure his family's future."
        ),
        Film(
            "The Wolf of Wall Street",
            R.drawable.thewolfofwallstreet,
            "Based on the true story of Jordan Belfort, from his rise to a wealthy stock-broker living the high life to his fall involving crime, corruption and the federal government."
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MergeHomeScreenContextBinding.inflate(layoutInflater, container, false)


        initRecyckler()


        val scene = Scene.getSceneForLayout(container, R.layout.merge_home_screen_context, requireContext())
        val searchSlide = Slide(Gravity.TOP).addTarget(R.id.search_view)
        val recyclerSlide = Slide(Gravity.BOTTOM).addTarget(R.id.main_recycler)
        val customTransition = TransitionSet().apply {
            duration = 500
            addTransition(recyclerSlide)
            addTransition(searchSlide)
        }
        TransitionManager.go(scene, customTransition)
        return binding.root


    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    filmsAdapter.addItems(filmsDataBase)
                    return true
                }
                val result = filmsDataBase.filter {
                    it.title.lowercase(Locale.getDefault())
                        .contains(newText.lowercase(Locale.getDefault()))
                }
                filmsAdapter.addItems(result)
                return true
            }
        })

        initRecyckler()
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
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }
    }


}