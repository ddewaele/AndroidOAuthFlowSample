package com.ecs.android.oauth;


public class Constants {

	public static final String CONSUMER_KEY = "anonymous";
	public static final String CONSUMER_SECRET = "anonymous";
	
	public static final String SCOPE 			= "https://www.googleapis.com/auth/latitude";
	public static final String REQUEST_URL = "https://www.google.com/accounts/OAuthGetRequestToken";
	public static final String ACCESS_URL = "https://www.google.com/accounts/OAuthGetAccessToken";  
	public static final String AUTHORIZE_URL = "https://www.google.com/latitude/apps/OAuthAuthorizeToken?domain=anonymous&location=all&granularity=best&hd=default";
	
	public static final String API_REQUEST 		= "https://www.googleapis.com/latitude/v1/location";
	
	public static final String ENCODING 		= "UTF-8";
	
	public static final String	OAUTH_CALLBACK_SCHEME	= "x-oauthflow";
	public static final String	OAUTH_CALLBACK_HOST		= "callback";
	public static final String	OAUTH_CALLBACK_URL		= OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;

}
