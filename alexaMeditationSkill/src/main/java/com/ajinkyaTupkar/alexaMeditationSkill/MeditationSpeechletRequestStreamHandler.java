package com.ajinkyaTupkar.alexaMeditationSkill;

import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

public class MeditationSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedApplicationIds;
    static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
        supportedApplicationIds = new HashSet<String>();
        
        supportedApplicationIds.add("amzn1.ask.skill.5166af91-2f6a-42b1-964f-0a70085ca217");
    }

    public MeditationSpeechletRequestStreamHandler() {
        super(new MeditationSpeechlet(), supportedApplicationIds);
    }
}
