package com.udacity.gradle.javalibs;

import java.util.Random;

public class JokeTeller {

    public static String getRandomJoke() {

        /*
           Jokes from :
           http://thoughtcatalog.com
         */

        String[] jokes = {"You kill vegetarian vampires with a steak to the heart.",
                "Dry erase boards are remarkable.",
                "You want to hear a pizza joke? Never mind, it’s pretty cheesy.",
                "Why can’t a bike stand on its own? It’s two tired.",
                "Time flies like an arrow, fruit flies like banana.",
                "How does NASA organize their company parties? They planet.",
                "If you want to catch a squirrel just climb a tree and act like a nut."
        };

        Random rand = new Random();

        return jokes[rand.nextInt(jokes.length)];
    }

}
