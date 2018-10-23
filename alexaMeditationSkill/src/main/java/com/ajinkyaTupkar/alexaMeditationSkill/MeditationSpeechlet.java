package com.ajinkyaTupkar.alexaMeditationSkill;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.SpeechletV2;
import com.amazon.speech.ui.OutputSpeech;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

public class MeditationSpeechlet implements SpeechletV2{

	public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {
		// TODO Auto-generated method stub
		
	}

	public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
		// TODO Auto-generated method stub
		return getWelcomeResponse();
	}

	public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
		IntentRequest request = requestEnvelope.getRequest();

		Intent intent = request.getIntent();
		String intentName = (intent != null) ? intent.getName() : null;

		if ("AMAZON.HelpIntent".equals(intentName)) {
			return getHelpResponse();
		} else if ("AMAZON.YesIntent".equals(intentName)) {
			return getAskResponse("YogaInstructor", "This is unsupported.  Please try something else.", false);
		} else if ("AMAZON.NoIntent".equals(intentName) || "StopIntent".equals(intentName) || "AMAZON.CancelIntent".equals(intentName)) {
			return getAskResponse("YogaInstructor", "This is unsupported.  Please try something else.", false);
		} else {
			return getAskResponse("YogaInstructor", "This is unsupported.  Please try something else.", false);
		}
	}

	public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {
		// TODO Auto-generated method stub
		
	}

	
	private SpeechletResponse getWelcomeResponse() {
		String speechText = "Namaste! This is your yoga teacher. I will be your surya namaskar instructor for today. Are you ready to begin?";
		return getAskResponse("Welcome", speechText, false);
	}
	
	private SpeechletResponse getAskResponse(String cardTitle, String speechText, boolean shouldEndSession) {
		SimpleCard card = getSimpleCard(cardTitle, speechText);
		PlainTextOutputSpeech speech = getPlainTextOutputSpeech(speechText);
		Reprompt reprompt = getReprompt(speech);

		SpeechletResponse response = SpeechletResponse.newAskResponse(speech, reprompt, card);
		response.setNullableShouldEndSession(shouldEndSession);
		return response;
	}
	
	private SimpleCard getSimpleCard(String title, String content) {
		SimpleCard card = new SimpleCard();
		card.setTitle(title);
		card.setContent(content);

		return card;
	}
	
	private PlainTextOutputSpeech getPlainTextOutputSpeech(String speechText) {
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		return speech;
	}
	
	private Reprompt getReprompt(OutputSpeech outputSpeech) {
		Reprompt reprompt = new Reprompt();
		reprompt.setOutputSpeech(outputSpeech);

		return reprompt;
	}
	
	private SpeechletResponse getHelpResponse() {
		String speechText = "I am your Surya Namaskar instructor. I can guide you step by step to practice the Surya Namaskar. Would you like to continue?";
		return getAskResponse("YogaInstructor", speechText, false);
	}
}
