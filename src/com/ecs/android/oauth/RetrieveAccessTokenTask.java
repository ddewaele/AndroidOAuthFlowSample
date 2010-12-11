package com.ecs.android.oauth;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

public class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void> {

	final String TAG = getClass().getName();
	
	private Context	context;
	private SharedPreferences prefs;
	
	public RetrieveAccessTokenTask(Context context, SharedPreferences prefs) {
		this.context = context;
		this.prefs=prefs;
	}


	/**
	 * Retrieve the oauth_verifier, and store the oauth and oauth_token_secret 
	 * for future API calls.
	 */
	@Override
	protected Void doInBackground(Uri...params) {
		final Uri uri = params[0];
		
		
		String token = uri.getQueryParameter(OAuth.OAUTH_TOKEN);
		String secret = uri.getQueryParameter(OAuth.OAUTH_TOKEN_SECRET);
		String consumer_secret = uri.getQueryParameter(Constants.CONSUMER_SECRET_KEY);

		try {
			OAuthConsumer consumer =  new CommonsHttpOAuthConsumer(Constants.CONSUMER_KEY, consumer_secret);
			consumer.setTokenWithSecret(token, secret);
			final Editor edit = prefs.edit();
			edit.putString(OAuth.OAUTH_TOKEN, consumer.getToken());
			edit.putString(OAuth.OAUTH_TOKEN_SECRET, consumer.getTokenSecret());
			edit.putString(Constants.CONSUMER_SECRET_KEY, consumer.getConsumerSecret());
			edit.commit();
			
			context.startActivity(new Intent(context,OAuthFlowApp.class));

			Log.i(TAG, "OAuth - Access Token Retrieved");
			
		} catch (Exception e) {
			Log.e(TAG, "OAuth - Access Token Retrieval Error", e);
		}

		return null;
	}
}