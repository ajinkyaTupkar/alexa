package com.tupkarajinkya.alexa_odds_evens;

import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

public class OddsEvensSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedApplicationIds;
    static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
        supportedApplicationIds = new HashSet<String>();
        
        supportedApplicationIds.add("amzn1.ask.skill.1849cd26-7b96-49a7-a855-0c9e9986c95e");
    }

    public OddsEvensSpeechletRequestStreamHandler() {
        super(new OddsEvensSpeechlet(), supportedApplicationIds);
    }
}
