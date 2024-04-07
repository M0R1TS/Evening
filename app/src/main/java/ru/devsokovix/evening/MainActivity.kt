package ru.devsokovix.evening

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import drawable.FilmListRecyclerAdapter
import ru.devsokovix.evening.databinding.ActivityMainBinding
import ru.devsokovix.evening.databinding.FilmItemBinding

class MainActivity : AppCompatActivity() {

    private lateinit var filmsAdapter: FilmItemBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filmsAdapter = FilmItemBinding.inflate(layoutInflater)
        setContentView(filmsAdapter.root)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    val filmsDataBase = listOf(
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
}