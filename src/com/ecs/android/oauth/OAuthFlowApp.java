package com.ecs.android.oauth;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Entry point in the application.
 * Launches the OAuth flow by starting the PrepareRequestTokenActivity
 *
 */
public class OAuthFlowApp extends Activity {

	final String TAG = getClass().getName();
	private SharedPreferences prefs;
	private TextView textView; 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        textView = (TextView) findViewById(R.id.response_code);
        
        Button launchOauth = (Button) findViewById(R.id.btn_launch_oauth);
        Button clearCredentials = (Button) findViewById(R.id.btn_clear_credentials);
        
        launchOauth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent().setClass(v.getContext(), PrepareRequestTokenActivity.class));
            }
        });

        clearCredentials.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	clearCredentials();
            	performApiCall();
            }
        });
        
        
        performApiCall();
    }

	private void performApiCall() {
		
		
		String jsonOutput = "";
        try {
        	doGet(Constants.API_REQUEST,getConsumer(this.prefs));
        	Log.i(TAG,jsonOutput);
		} catch (Exception e) {
			Log.e(TAG, "Error executing request",e);
			textView.setText("Error retrieving location : " + jsonOutput);
		}
	}
	
    private void clearCredentials() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		final Editor edit = prefs.edit();
		edit.remove(OAuth.OAUTH_TOKEN);
		edit.remove(OAuth.OAUTH_TOKEN_SECRET);
		edit.commit();
	}

	
	private OAuthConsumer getConsumer(SharedPreferences prefs) {
		String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
		String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
		String consumer_secret = prefs.getString(Constants.CONSUMER_SECRET_KEY, "");
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(Constants.CONSUMER_KEY, consumer_secret);
		consumer.setTokenWithSecret(token, secret);
		return consumer;
	}
	
	private String doGet(String url,OAuthConsumer consumer) throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient();
    	HttpGet request = new HttpGet(url);
    	Log.i(TAG,"Requesting URL : " + url);
    	consumer.sign(request);
    	HttpResponse response = httpclient.execute(request);
    	Log.i(TAG,"Statusline : " + response.getStatusLine());
    	textView.setText("Response status : " + response.getStatusLine());
    	InputStream data = response.getEntity().getContent();
    	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(data));
        String responeLine;
        StringBuilder responseBuilder = new StringBuilder();
        while ((responeLine = bufferedReader.readLine()) != null) {
        	responseBuilder.append(responeLine + "\n");
        }
        Log.i(TAG,"Response : " + responseBuilder.toString());
        return responseBuilder.toString();
	}	
}