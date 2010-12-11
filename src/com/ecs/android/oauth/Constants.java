package com.ecs.android.oauth;


public class Constants {

	public static final String CONSUMER_KEY = "ecommitconsulting.be";
	
	public static final String SCOPE 			= "https://www.googleapis.com/auth/latitude";
	public static final String REQUEST_URL = "http://ecommitconsulting.be/docs.php";
	
	public static final String API_REQUEST 		= "https://www.googleapis.com/latitude/v1/location";
	
	public static final String ENCODING 		= "UTF-8";
	
	public static final String	OAUTH_CALLBACK_SCHEME	= "x-oauthflow-remote";
	public static final String	OAUTH_CALLBACK_HOST		= "callback";
	public static final String	OAUTH_CALLBACK_URL		= OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;

	public static final String CONSUMER_SECRET_KEY = "consumer_secret";

}
