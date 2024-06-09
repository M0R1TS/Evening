package ru.devsokovix.evening.data

import ru.devsokovix.evening.R
import ru.devsokovix.evening.domain.Film

class MainRepository {
    val filmsDataBase = listOf(
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
}