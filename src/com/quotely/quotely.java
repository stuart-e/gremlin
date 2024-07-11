package com.quotely;

import com.quotely.config.Language;
import com.quotely.thirdparty.Forismatic;

class Quotely {
    public static void main(String[] args) {
        //Validate input
        if (args.length > 1) {
            throw new IllegalArgumentException("Too many arguments. Only provide the requested language (English or Russian) as input.");
        }

        String language= String.valueOf(Language.ENGLISH);;
        if (args[0].toUpperCase().equals(String.valueOf(Language.RUSSIAN))) {
            language = String.valueOf(Language.RUSSIAN);
        } else if (!args[0].toUpperCase().equals(String.valueOf(Language.ENGLISH))){
            System.out.println("Could not find valid language matching your request. Defaulting to English.");
        }

        //Initialize class
        Forismatic forismatic = new Forismatic();

        //Make call
        String quote = "";
        try {
            quote = forismatic.getQuote(language);
        } catch (Exception e) {
            System.out.println("Failed to get quote. Error: " + e);
        }

        //Print results
        System.out.println(quote);
    }
}