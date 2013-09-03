package ca.sukhni.net.android.api.client.test;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class InputDialogView extends InputDialog
{
	private View		rootView;
	private Button 		btnCancel,btnAdd,btnDone;
	private EditText 	etName,etValue,etContent;
	private Dialog		dialog;
	private String		title = "Input Dialog";
	
	public InputDialogView(Context context)
	{
		rootView = View.inflate(context, R.layout.input_dialog_layout, null);
		btnCancel = (Button) rootView.findViewById(R.id.cancel_button);
		btnAdd = (Button) rootView.findViewById(R.id.add_button);
		btnDone = (Button) rootView.findViewById(R.id.done_button);
		
		etName = (EditText) rootView.findViewById(R.id.name_edit_text);
		etValue = (EditText) rootView.findViewById(R.id.value_edit_text);
		etContent = (EditText) rootView.findViewById(R.id.content_edit_text);
		
		dialog = new Dialog(context);
		//dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setTitle(title);
		dialog.setCancelable(true);
		dialog.setContentView(rootView);
		
		btnCancel.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				setParameters();
				if(onInputDialogListener!=null) onInputDialogListener.onCancelClick(InputDialogView.this);
			}
		});
		
		btnAdd.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				setParameters();
				if(onInputDialogListener!=null) onInputDialogListener.onAddClick(InputDialogView.this);
			}
		});
		
		btnDone.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				setParameters();
				if(onInputDialogListener!=null) onInputDialogListener.onDoneClick(InputDialogView.this);
			}
		});
		
	}
	
	private void setParameters()
	{
		setName(etName.getText().toString());
		setValue(etValue.getText().toString());
		setContent(etContent.getText().toString());
	}
	
	public void showValueDialog()
	{
		etContent.setVisibility(View.GONE);
		etName.setVisibility(View.GONE);
		etValue.setVisibility(View.VISIBLE);
		btnAdd.setVisibility(View.VISIBLE);
		etName.setText("");
		etValue.setText("");
		etContent.setText("");
		dialog.setTitle(title);
		dialog.show();
	}
	
	public void showBaseUrliDialog()
	{
		etContent.setVisibility(View.GONE);
		etName.setVisibility(View.GONE);
		etValue.setVisibility(View.VISIBLE);
		btnAdd.setVisibility(View.VISIBLE);
		etName.setText("");
		etValue.setText("");
		etContent.setText("");
		dialog.setTitle(title);
		btnAdd.setVisibility(View.GONE);
		dialog.show();
	}
	
	public void showNameValueDialog()
	{
		etContent.setVisibility(View.GONE);
		etName.setVisibility(View.VISIBLE);
		etValue.setVisibility(View.VISIBLE);
		btnAdd.setVisibility(View.VISIBLE);
		etName.setText("");
		etValue.setText("");
		etContent.setText("");
		dialog.setTitle(title);
		dialog.show();
	}
	
	public void showContentDialog()
	{
		etContent.setVisibility(View.VISIBLE);
		etName.setVisibility(View.GONE);
		etValue.setVisibility(View.GONE);
		btnAdd.setVisibility(View.GONE);
		etName.setText("");
		etValue.setText("");
		etContent.setText("");
		dialog.setTitle(title);
		dialog.show();
	}
	
	public void dismissDialog()
	{
		if(dialog!=null) dialog.dismiss();
	}
	
	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}


	private InputDialogListener onInputDialogListener			= null;
	public void setOnInputDialogListener(InputDialogListener listener)
	{
		this.onInputDialogListener = listener;
	}
	public interface InputDialogListener
	{
		public abstract void onCancelClick(InputDialogView view);
		public abstract void onAddClick(InputDialogView view);
		public abstract void onDoneClick(InputDialogView view);
	}
}

abstract class InputDialog
{
	private String name;
	private String value;
	private String content;
	
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @return the value
	 */
	public String getValue()
	{
		return value;
	}
	/**
	 * @return the content
	 */
	public String getContent()
	{
		return content;
	}
	/**
	 * @param name the name to set
	 */
	protected void setName(String name)
	{
		this.name = name;
	}
	/**
	 * @param value the value to set
	 */
	protected void setValue(String value)
	{
		this.value = value;
	}
	/**
	 * @param content the content to set
	 */
	protected void setContent(String content)
	{
		this.content = content;
	}
	
	
}
