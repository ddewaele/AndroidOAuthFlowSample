package com.ecs.android.oauth;

import java.net.URLEncoder;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Prepares a OAuthConsumer and OAuthProvider 
 * 
 * OAuthConsumer is configured with the consumer key & consumer secret.
 * OAuthProvider is configured with the 3 OAuth endpoints.
 * 
 * Execute the OAuthRequestTokenTask to retrieve the request, and authorize the request.
 * 
 * After the request is authorized, a callback is made here.
 * 
 */
public class PrepareRequestTokenActivity extends Activity {

	final String TAG = getClass().getName();
	
    private OAuthConsumer consumer; 
    private OAuthProvider provider;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	try {
        	System.setProperty("debug", "true");
    	      this.consumer = new CommonsHttpOAuthConsumer(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
    	        this.provider = new CommonsHttpOAuthProvider(
    	        		Constants.REQUEST_URL  + "?scope=" + URLEncoder.encode(Constants.SCOPE, Constants.ENCODING),
    	        		Constants.ACCESS_URL,
    	        		Constants.AUTHORIZE_URL);
        	} catch (Exception e) {
        		Log.e(TAG, "Error creating consumer / provider",e);
    		}

        Log.i(TAG, "Starting task to retrieve request token.");
		new OAuthRequestTokenTask(this,consumer,provider).execute();
	}

	/**
	 * Called when the OAuthRequestTokenTask finishes (user has authorized the request token).
	 * The callback URL will be intercepted here.
	 */
	@Override
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent); 
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		final Uri uri = intent.getData();
		if (uri != null && uri.getScheme().equals(Constants.OAUTH_CALLBACK_SCHEME)) {
			Log.i(TAG, "Callback received : " + uri);
			Log.i(TAG, "Retrieving Access Token");
			new RetrieveAccessTokenTask(this,consumer,provider,prefs).execute(uri);
			finish();	
		}
	}
}
