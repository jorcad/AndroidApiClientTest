package ca.sukhni.net.android.api.client.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import ca.sukhni.net.android.api.client.ApiClientBuilder;
import ca.sukhni.net.android.api.client.ApiClientHandler;
import ca.sukhni.net.android.api.client.ExceptionStatus;
import ca.sukhni.net.android.api.client.Method;
import ca.sukhni.net.android.api.client.ResponseEntity;
import ca.sukhni.net.android.api.client.Status;
import ca.sukhni.net.android.logger.Logger;

public class DebbugerActivity extends BaseActivity
{
	public final static String 				TAG 					= DebbugerActivity.class.getSimpleName();
	private ApiClientBuilder 				apiClientBuilder		= null;
	
	private EditText						urlEditText				= null;
	private EditText						bodyEditText			= null;
	private EditText						connectionEditText		= null;
	private EditText						socketEditText			= null;
	
	private int								intConnectionTimeout	= 6000;
	private int								intSocketTimeout		= 6000;
	private String 							url						= null;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.debugger_layout);
		apiClientBuilder = new ApiClientBuilder();
		
		urlEditText = (EditText) findViewById(R.id.url_edit_text);
		bodyEditText = (EditText) findViewById(R.id.body_edit_text);
		connectionEditText = (EditText) findViewById(R.id.connection_timeout_edit_text);
		socketEditText = (EditText) findViewById(R.id.socket_timeout_edit_text);
		
		ToggleButton cT = (ToggleButton) findViewById(R.id.enable_connection_timeout_toggleBttn);
		cT.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				apiClientBuilder.setEnableConntectionTimeoutRetry(isChecked);
			}
		});
		
		ToggleButton sT = (ToggleButton) findViewById(R.id.enable_socket_timeout_toggleBttn);
		sT.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				apiClientBuilder.setEnableSocketTimeoutRetry(isChecked);
			}
		});
		
		Spinner spinner = (Spinner) findViewById(R.id.method_spinner);
		final String[] values = getResources().getStringArray(R.array.method);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,values);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				apiClientBuilder.setMethod(Method.fromMethod(values[position]));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
				
			}
		});
		
		findViewById(R.id.exec_button).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(checkParameters())
				{
					apiClientBuilder.setBaseUri(url);
					apiClientBuilder.setConnectionTimeout(intConnectionTimeout);
					apiClientBuilder.setSocketTimeout(intSocketTimeout);
					apiClientBuilder.setTextContent(bodyEditText.getText().toString());
					execute();
				}
			}
		});
		
		TextView tvLink = (TextView) findViewById(R.id.link_text_view);
		Linkify.addLinks(tvLink, Linkify.ALL);
		
	}
	
	private boolean checkParameters()
	{
		url = urlEditText.getText().toString().trim();
		String connectionTimeout = connectionEditText.getText().toString();
		String socketTimeout = socketEditText.getText().toString();
		
		if(!isUrl(url))
		{
			showAlertDialog("The request URL is invalidate. Please check your request URL!");
			return false;
		}
		else if(connectionTimeout.length()==0)
		{
			intConnectionTimeout = 0;
		}
		if(socketTimeout.length()==0)
		{
			intSocketTimeout = 0;
		}
		return true;
	}
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}
	@Override
	protected void onPause()
	{
		super.onPause();
	}
	@Override
	protected void onStart()
	{
		super.onStart();
	}
	@Override
	protected void onStop()
	{
		super.onStop();
	}
	@Override
	protected void onResume()
	{
		super.onResume();
	}
	
	private void execute()
	{
		showProgressDialog("please wait");
		apiClientBuilder.build().executeOnAsyncTask(new ApiClientHandler()
		{
			@Override
			public void onInformational(Status status, String responseStatus, ResponseEntity entity)
			{
				Logger.info(TAG + ": Status: " + status.code() + " : responseStatus: " + responseStatus);
				String result = printResponseEntity(entity);
				String print = "Status= " + status.code() + "\n Status line: " + responseStatus + "\n Entity String= " + result;
				showAlertDialog(print);
			}
	
			@Override
			public void onSuccessful(Status status, String responseStatus, ResponseEntity entity)
			{
				Logger.info(TAG + ": Status: " + status.code() + " : responseStatus: " + responseStatus);
				String result = printResponseEntity(entity);
				String print = "Status= " + status.code() + "\n Status line: " + responseStatus + "\n Entity String= " + result;
				showAlertDialog(print);
			}
	
			@Override
			public void onClientError(Status status, String responseStatus, ResponseEntity entity)
			{
				Logger.info(TAG + ": Status: " + status.code() + " : responseStatus: " + responseStatus);
				String result = printResponseEntity(entity);
				String print = "Status= " + status.code() + "\n Status line: " + responseStatus + "\n Entity String= " + result;
				showAlertDialog(print);
			}
	
			@Override
			public void onRedirection(Status status, String responseStatus, ResponseEntity entity)
			{
				Logger.info(TAG + ": Status: " + status.code() + " : responseStatus: " + responseStatus);
				String result = printResponseEntity(entity);
				String print = "Status= " + status.code() + "\n Status line: " + responseStatus + "\n Entity String= " + result;
				showAlertDialog(print);
			}
	
			@Override
			public void onServerError(Status status, String responseStatus, ResponseEntity entity)
			{
				Logger.info(TAG + ": Status: " + status.code() + " : responseStatus: " + responseStatus);
				String result = printResponseEntity(entity);
				String print = "Status= " + status.code() + "\n Status line: " + responseStatus + "\n Entity String= " + result;
				showAlertDialog(print);
			}
	
			@Override
			public void onException(ExceptionStatus exceptionStatus, Exception ex)
			{
				Logger.info(TAG + ": responseStatus: " + exceptionStatus.desc());
				showAlertDialog("Exception = " + exceptionStatus.desc() + ", Message= " + ex.getMessage());
			}

		});
	}
	
	private String printResponseEntity(ResponseEntity entity)
	{
		String result = "";
		if(entity!=null)
		{
			result = entity.getResponseContentAsString();
			Logger.info("======================ResponseEntity============================");
			Logger.info("Content Length= " + entity.getContentLength());
			Logger.info("Content String= " + entity.getResponseContentAsString());
			Logger.info("======================ResponseEntity============================");
			return result;
		}
		else
		{
			Logger.error(TAG + ": printResponseEntity(ResponseEntity entity): Entity is NULL");
			return result;
		}
	}
	
	public static boolean isUrl(String str)
	{
		Pattern urlPattern = Pattern.compile("((https?|ftp|gopher|telnet|file):((//)|(\\\\\\\\))+[\\\\w\\\\d:#@%/;$()~_?\\\\+-=\\\\\\\\\\\\.&]*)",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = urlPattern.matcher(str);
		if (matcher.find())
			return true;
		else
			return false;
	}
}
