package com.tupkarajinkya.alexa_odds_evens;

import java.util.Random;

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
import com.amazon.speech.ui.SsmlOutputSpeech;

public class OddsEvensSpeechlet implements SpeechletV2{

	
	private static final String BYE = "Thank you for your time today. Let's play this again sometime soon!";
	
	@Override
	public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {
		// TODO Auto-generated method stub
	}

	@Override
	public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
		return getHelpResponse();
	}

	@Override
	public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
		IntentRequest request = requestEnvelope.getRequest();

		Intent intent = request.getIntent();
		String intentName = (intent != null) ? intent.getName() : null;

		if ("AMAZON.HelpIntent".equals(intentName)) {
			return getHelpResponse();
		} else if ("AMAZON.YesIntent".equals(intentName)) {
			return getWelcomeResponse();
		} else if ("AMAZON.CancelIntent".equals(intentName) || "AMAZON.StopIntent".equals(intentName) || "AMAZON.NoIntent".equals(intentName)) {
			return getAskResponse("End", BYE, true);
		} else {
			return getAskResponse("OddsAndEvens", "This is unsupported.  Please try something else.", false);
		}
	}

	@Override
	public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {
		// TODO Auto-generated method stub
		
	}

	
	private SpeechletResponse getWelcomeResponse() {
		Random random = new Random();
		int num1 = random.nextInt();
		int num2 = random.nextInt();
		int num3 = random.nextInt();
		if(num1 % 2 == 0) {
			num1 = 2;
		} else {
			num1 = 1;
		}
		if(num2 % 2 == 0) {
			num2 = 2;
		} else {
			num2 = 1;
		}
		if(num3 % 2 == 0) {
			num3 = 2;
		} else {
			num3 = 1;
		}
		SsmlOutputSpeech speech = new SsmlOutputSpeech();
		speech.setSsml("<speak> "
				+ "Okay, Let's start. "
				+ "Choose one of odds or evens."
				+ "<break time=\"1s\"/> "
				+ "Let's go for the first round."
				+ "<prosody rate=\"fast\"> 1 2 3.</prosody>"
				+ " I chose "+ num1+"."
				+ "<break time=\"1s\"/>"
				+ "One more time."
				+ "<prosody rate=\"fast\"> 1 2 3. </prosody>"
				+ " I chose "+ num2+"."
				+ "<break time=\"1s\"/>"
				+ "Last time."
				+ "<prosody rate=\"fast\"> 1 2 3. </prosody>"
				+ " I chose "+ num3 +"."
				+ "<break time=\"1s\"/>"
				+ " I hope you won. "
				+ BYE
				+ "</speak>");
		String speechText = "Follow the instructions";
		String cardTitle = "Lets start";
		
		SimpleCard card = getSimpleCard(cardTitle, speechText);
		SpeechletResponse response = SpeechletResponse.newTellResponse(speech, card);
		response.setNullableShouldEndSession(true);
		return response;
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
		String speechText = "Hi there! This is a hand game of odds and, evens. You choose your side between odd, and even. Then we go for three rounds of selecting either one, or two. We then sum the selected numbers from both the players and note if it is odd, or even. Whoever gets the selected sum two or more out of three wins! So, do you want to play?";
		return getAskResponse("OddsAndEvens", speechText, false);
	}
}
