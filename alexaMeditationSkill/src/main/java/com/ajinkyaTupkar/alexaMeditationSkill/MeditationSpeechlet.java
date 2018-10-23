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
import com.amazon.speech.ui.SsmlOutputSpeech;

public class MeditationSpeechlet implements SpeechletV2{

	
	private static final String BYE = "Thank you for your time today. Hope to hear soon from you. Dhanyawaad!";
	
	@Override
	public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {
		// TODO Auto-generated method stub
	}

	@Override
	public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
		requestEnvelope.getSession();
		return getWelcomeResponse();
	}

	@Override
	public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
		IntentRequest request = requestEnvelope.getRequest();

		Intent intent = request.getIntent();
		String intentName = (intent != null) ? intent.getName() : null;

		if ("AMAZON.HelpIntent".equals(intentName)) {
			return getHelpResponse();
		} else if ("StartIntent".equals(intentName)) {
			return getWelcomeResponse();
		} else if ("AMAZON.YesIntent".equals(intentName)) {
			return startMediationWithSSML();
		} else if ("AMAZON.CancelIntent".equals(intentName) || "AMAZON.StopIntent".equals(intentName) || "AMAZON.NoIntent".equals(intentName)) {
			return getAskResponse("End", BYE, true);
		} else {
			return getAskResponse("YogaInstructor", "This is unsupported.  Please try something else.", false);
		}
	}

	private SpeechletResponse startMediationWithSSML() {
		SsmlOutputSpeech speech = new SsmlOutputSpeech();
		speech.setSsml("<speak> "
				+ "Okay, Let's start. "
				+ "Take the first few seconds to get in any comfortable posture. You can try padmasana, ardha padmasana or sukhasana. Close your eyes, make sure your spine is straight and breathe normally."
				+ "<break time=\"5s\"/> "
				+ "<prosody volume=\"x-soft\" rate=\"slow\"> Take a deep breath in, and breathe out. <break time=\"2s\"/> Take a deep breath in again and as you breathe out, feel the relaxation in your body with every exhalation. Continue this for next few breaths."
				+ "<break time=\"10s\"/>Now, Scan your body and feel the different sensations arising in your body. Start from your head, and acknowledge these sensations while scanning your body, till your toe."
				+ "<break time=\"10s\"/>"
				+ "Acknowledge any thoughts that are coming to your mind, and just be aware of their existence without building up on them."
				+ "<break time=\"10s\"/>"
				+ "Bring attention back to your breaths. Feel your chest and abdomen move as you breathe in, and breathe out."
				+ "<break time=\"10s\"/>"
				+ "Gently bring your attention to your surroundings. Listen to the surrounding noise, and try to identify the source of various sounds that you are hearing."
				+ "<break time=\"10s\"/>"
				+ "Now, bring your attention back to your breathing. Keep the breathing normal and pay attention to your breaths."
				+ "<break time=\"10s\"/>"
				+ "With the next breath, gently chant Om as you breathe out."
				+ "<break time=\"10s\"/>"
				+ "Now, rub your palms against each other to produce heat. Place your palms over your eyes, and gently massage your face."
				+ "<break time=\"5s\"/>"
				+ "Slowly open your eyes in middle of your palms."
				+ "<break time=\"5s\"/>"
				+ "</prosody>"
				+ BYE
				+ "</speak>");
		String speechText = "Follow the instructions";
		String cardTitle = "Lets start";
		
		SimpleCard card = getSimpleCard(cardTitle, speechText);
		SpeechletResponse response = SpeechletResponse.newTellResponse(speech, card);
		response.setNullableShouldEndSession(true);
		return response;
	}

	@Override
	public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {
		// TODO Auto-generated method stub
		
	}

	
	private SpeechletResponse getWelcomeResponse() {
		String speechText = "Namaste! This is your yoga teacher. I will be your guide for a 2 minute meditation today. Are you ready to begin?";
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
		String speechText = "I am your Meditation instructor. I can guide you step by step to practice 2 minute meditation. Would you like to continue?";
		return getAskResponse("YogaInstructor", speechText, false);
	}
}
