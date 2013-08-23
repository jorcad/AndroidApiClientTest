package ca.sukhni.net.android.api.client.test;

import ca.sukhni.net.android.api.client.ApiClient;
import ca.sukhni.net.android.api.client.ApiClientBuilder;
import ca.sukhni.net.android.api.client.ApiClientHandler;
import ca.sukhni.net.android.api.client.ExceptionStatus;
import ca.sukhni.net.android.api.client.Method;
import ca.sukhni.net.android.api.client.ResponseEntity;
import ca.sukhni.net.android.api.client.Status;
import ca.sukhni.net.android.logger.Logger;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity
{

	public final static String TAG = MainActivity.class.getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViewById(R.id.button1).setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				doTest();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void doTest()
	{
		// http://192.168.2.146:7979/Ais/User/Session/Verify?
		// ApiKey={APIKEY}&SimID={SIMID}&AndroidID={ANDROIDID}&SessionID={SESSIONID}&Imei={IMEI}&Language={LANGUAGE}&MobirooAppVersion={MOBIROOAPPVERSION
		ApiClient apiClient = new ApiClientBuilder()
				.setBaseUri("http://api.mobaroo.com")
				.addPath("Ais")
				.addPath("User")
				.addPath("Session")
				.addPath("Verify")
				.addParam("ApiKey", "f01c3556-e03d-47d1-a6cc-73949fcfc181")
				.addParam("SimID", "123456782")
				.addParam("Imei", "78486541654564654")
				.addParam("AndroidID", "12345679")
				.addParam("SessionID", "")
				.addParam("Language", "en-US")
				.addParam("MobirooAppVersion", "1.0.0.0")
				.setMethod(Method.GET)
				.build();
		apiClient.executeOnAsyncTask(new ApiClientHandler()
		{

			@Override
			public void onInformational(Status status, String responseStatus, ResponseEntity entity)
			{
				Logger.info(TAG + ": Status: " + status.code() + " : responseStatus: " + responseStatus);
				printResponseEntity(entity);
			}

			@Override
			public void onSuccessful(Status status, String responseStatus, ResponseEntity entity)
			{
				Logger.info(TAG + ": Status: " + status.code() + " : responseStatus: " + responseStatus);
				printResponseEntity(entity);
			}

			@Override
			public void onClientError(Status status, String responseStatus, ResponseEntity entity)
			{
				Logger.info(TAG + ": Status: " + status.code() + " : responseStatus: " + responseStatus);
				printResponseEntity(entity);
			}

			@Override
			public void onRedirection(Status status, String responseStatus, ResponseEntity entity)
			{
				Logger.info(TAG + ": Status: " + status.code() + " : responseStatus: " + responseStatus);
				printResponseEntity(entity);
			}

			@Override
			public void onServerError(Status status, String responseStatus, ResponseEntity entity)
			{
				Logger.info(TAG + ": Status: " + status.code() + " : responseStatus: " + responseStatus);
				printResponseEntity(entity);
			}

			@Override
			public void onException(ExceptionStatus exceptionStatus, Exception ex)
			{
				Logger.info(TAG + ": responseStatus: " + exceptionStatus.desc());
			}

		});
	}
	
	private void printResponseEntity(ResponseEntity entity)
	{
		if(entity!=null)
		{
			Logger.info("======================ResponseEntity============================");
			Logger.info("Content Length= " + entity.getContentLength());
			Logger.info("Content String= " + entity.getResponseContentAsString());
			Logger.info("======================ResponseEntity============================");
		}
		else
		{
			Logger.error(TAG + ": printResponseEntity(ResponseEntity entity): Entity is NULL");
		}
	}
	
}
