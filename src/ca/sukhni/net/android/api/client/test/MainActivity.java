package ca.sukhni.net.android.api.client.test;

import ca.sukhni.net.android.api.client.ApiClientBuilder;
import ca.sukhni.net.android.api.client.ApiClientHandler;
import ca.sukhni.net.android.api.client.ExceptionStatus;
import ca.sukhni.net.android.api.client.Method;
import ca.sukhni.net.android.api.client.ResponseEntity;
import ca.sukhni.net.android.api.client.Status;
import ca.sukhni.net.android.api.client.test.InputDialogView.InputDialogListener;
import ca.sukhni.net.android.api.client.test.R;
import ca.sukhni.net.android.logger.Logger;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends BaseActivity
{

	public final static String TAG = MainActivity.class.getSimpleName();
	private ApiClientBuilder apiClientBuilder;
	private InputDialogView	inputDialogView;
	private TextView tvApiCall;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvApiCall = (TextView) findViewById(R.id.api_call_textView);
		apiClientBuilder = new ApiClientBuilder();
		inputDialogView = new InputDialogView(this);
		
		findViewById(R.id.execute_btton).setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				execute();
			}
			
		});
		findViewById(R.id.set_base_uri_bttn).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				inputDialogView.setTitle("Set base url");
				inputDialogView.showBaseUrliDialog();
				inputDialogView.setOnInputDialogListener(new InputDialogListener()
				{
					@Override
					public void onDoneClick(InputDialogView view)
					{
						if(view.getValue()!=null && view.getValue().trim().length()!=0)
						{
							apiClientBuilder.setBaseUri(view.getValue());
						}
						inputDialogView.dismissDialog();
						updateUI();
					}
					
					@Override
					public void onCancelClick(InputDialogView view)
					{
						inputDialogView.dismissDialog();
						updateUI();
					}
					
					@Override
					public void onAddClick(InputDialogView view)
					{
						inputDialogView.dismissDialog();
						updateUI();
					}
				});
			}
		});
		findViewById(R.id.add_path_bttn).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				inputDialogView.setTitle("Add Path");
				inputDialogView.showValueDialog();
				inputDialogView.setOnInputDialogListener(new InputDialogListener()
				{
					@Override
					public void onDoneClick(InputDialogView view)
					{
						if(view.getValue()!=null && view.getValue().trim().length()!=0)
						{
							apiClientBuilder.addPath(view.getValue());
						}
						inputDialogView.dismissDialog();
						updateUI();
					}
					
					@Override
					public void onCancelClick(InputDialogView view)
					{
						inputDialogView.dismissDialog();
						updateUI();
					}
					
					@Override
					public void onAddClick(InputDialogView view)
					{
						if(view.getValue()!=null && view.getValue().trim().length()!=0)
						{
							apiClientBuilder.addPath(view.getValue());
						}
						else
						{
							showAlertDialog("Path cannot be empty");
						}
						updateUI();
					}
				});
			}
		});
		
		findViewById(R.id.add_param_bttn).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				inputDialogView.setTitle("Add Param");
				inputDialogView.showNameValueDialog();
				inputDialogView.setOnInputDialogListener(new InputDialogListener()
				{
					@Override
					public void onDoneClick(InputDialogView view)
					{
						if(view.getName()!=null && view.getName().trim().length()!=0 && view.getValue()!=null && view.getValue().trim().length()!=0)
						{
							apiClientBuilder.addParam(view.getName(), view.getValue());
						}
						inputDialogView.dismissDialog();
						updateUI();
					}
					
					@Override
					public void onCancelClick(InputDialogView view)
					{
						inputDialogView.dismissDialog();
						updateUI();
					}
					
					@Override
					public void onAddClick(InputDialogView view)
					{
						if(view.getValue()!=null && view.getValue().trim().length()!=0)
						{
							apiClientBuilder.addParam(view.getName(), view.getValue());
						}
						else
						{
							showAlertDialog("name or value cannot be empty");
						}
						updateUI();
					}
				});
			}
		});
		
		findViewById(R.id.add_header_bttn).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				inputDialogView.setTitle("Add Header");
				inputDialogView.showNameValueDialog();
				inputDialogView.setOnInputDialogListener(new InputDialogListener()
				{
					@Override
					public void onDoneClick(InputDialogView view)
					{
						if(view.getName()!=null && view.getName().trim().length()!=0 && view.getValue()!=null && view.getValue().trim().length()!=0)
						{
							apiClientBuilder.addHeader(view.getName(), view.getValue());
						}
						inputDialogView.dismissDialog();
						updateUI();
					}
					
					@Override
					public void onCancelClick(InputDialogView view)
					{
						inputDialogView.dismissDialog();
						updateUI();
					}
					
					@Override
					public void onAddClick(InputDialogView view)
					{
						if(view.getValue()!=null && view.getValue().trim().length()!=0)
						{
							apiClientBuilder.addHeader(view.getName(), view.getValue());
						}
						else
						{
							showAlertDialog("name or value cannot be empty");
						}
						updateUI();
					}
				});
			}
		});
		
		findViewById(R.id.set_content_bttn).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				inputDialogView.setTitle("Set Content");
				inputDialogView.showContentDialog();
				inputDialogView.setOnInputDialogListener(new InputDialogListener()
				{
					@Override
					public void onDoneClick(InputDialogView view)
					{
						if(view.getContent()!=null)
						{
							apiClientBuilder.setTextContent(view.getContent());
						}
						inputDialogView.dismissDialog();
						updateUI();
					}
					
					@Override
					public void onCancelClick(InputDialogView view)
					{
						inputDialogView.dismissDialog();
						updateUI();
					}
					
					@Override
					public void onAddClick(InputDialogView view)
					{
						updateUI();
					}
				});
			}
		});
		
		findViewById(R.id.connection_timeout_bttn).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				promptNumericDialog("Timeout in ms",R.string.label_set_connection_timeout,new CallbackHandler(null)
				{
					@Override
					public void onSuccess(int status, String response, Object data)
					{
						try
						{
							apiClientBuilder.setConnectionTimeout(Integer.parseInt(response));
						}
						catch(Exception ex){}
						updateUI();
					}
				});
			}
		});
		
		findViewById(R.id.socket_timeout_bttn).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				promptNumericDialog("Timeout in ms",R.string.label_set_socket_timeout,new CallbackHandler(this)
				{
					@Override
					public void onSuccess(int status, String response, Object data)
					{
						try
						{
							apiClientBuilder.setSocketTimeout(Integer.parseInt(response));
						}
						catch(Exception ex){}
						updateUI();
					}
				});
			}
		});
		
		ToggleButton cT = (ToggleButton) findViewById(R.id.enable_connection_timeout_toggleBttn);
		cT.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				apiClientBuilder.setEnableConntectionTimeoutRetry(isChecked);
				updateUI();
			}
		});
		
		ToggleButton sT = (ToggleButton) findViewById(R.id.enable_socket_timeout_toggleBttn);
		sT.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				apiClientBuilder.setEnableSocketTimeoutRetry(isChecked);
				updateUI();
			}
		});
		
		Spinner spinner = (Spinner) findViewById(R.id.method_spinner);
		final String[] values = MainActivity.this.getResources().getStringArray(R.array.method);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item,values);
		spinner.setAdapter(adapter);
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				apiClientBuilder.setMethod(Method.fromMethod(values[position]));
				updateUI();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
				
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

	private void execute()
	{
		showProgressDialog("please wait");
		apiClientBuilder.build().executeOnAsyncTask(new ApiClientHandler()
		{
			@Override
			public void onInformational(Status status, String responseStatus, ResponseEntity entity)
			{
				Logger.info(TAG + ":Status: " + status.code() + " : responseStatus: " + responseStatus);
				String result = printResponseEntity(entity);
				String print = "Status= " + status.code() + "\nStatus line: " + responseStatus + "\nEntity String= " + result;
				showAlertDialog(print);
			}
	
			@Override
			public void onSuccessful(Status status, String responseStatus, ResponseEntity entity)
			{
				Logger.info(TAG + ": Status: " + status.code() + " : responseStatus: " + responseStatus);
				String result = printResponseEntity(entity);
				String print = "Status= " + status.code() + "\nStatus line: " + responseStatus + "\nEntity String= " + result;
				showAlertDialog(print);
			}
	
			@Override
			public void onClientError(Status status, String responseStatus, ResponseEntity entity)
			{
				Logger.info(TAG + ":Status: " + status.code() + " : responseStatus: " + responseStatus);
				String result = printResponseEntity(entity);
				String print = "Status= " + status.code() + "\nStatus line: " + responseStatus + "\nEntity String= " + result;
				showAlertDialog(print);
			}
	
			@Override
			public void onRedirection(Status status, String responseStatus, ResponseEntity entity)
			{
				Logger.info(TAG + ": Status: " + status.code() + " : responseStatus: " + responseStatus);
				String result = printResponseEntity(entity);
				String print = "Status= " + status.code() + "\nStatus line: " + responseStatus + "\nEntity String= " + result;
				showAlertDialog(print);
			}
	
			@Override
			public void onServerError(Status status, String responseStatus, ResponseEntity entity)
			{
				Logger.info(TAG + ": Status: " + status.code() + " : responseStatus: " + responseStatus);
				String result = printResponseEntity(entity);
				String print = "Status= " + status.code() + "\nStatus line: " + responseStatus + "\nEntity String= " + result;
				showAlertDialog(print);
			}
	
			@Override
			public void onException(ExceptionStatus exceptionStatus, Exception ex)
			{
				Logger.info(TAG + ": responseStatus: " + exceptionStatus.desc());
				showAlertDialog("Exception = " + exceptionStatus.desc() + ",Message= " + ex.getMessage());
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
	
	private void updateUI()
	{
		tvApiCall.setText(apiClientBuilder.toString());
	}
	
}
