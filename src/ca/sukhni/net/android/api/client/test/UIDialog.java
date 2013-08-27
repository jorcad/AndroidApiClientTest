package ca.sukhni.net.android.api.client.test;


import ca.sukhni.net.android.api.client.test.R;

import com.sukhni.xgen.analytics.MyGoogleAnalytics;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

public class UIDialog 
{
	/**
     * Private member variables.
     */
    protected String             	mMessage           	= null;    	//< @string id for the actual message of the dialog
    protected Integer             	mIcon           	= null;    	//< @drawable id for the icon to use (default is icon_small)
    protected String             	mTitle             	= null;    	//< @string id for the title of the dialog (default is app_name)
    
    protected CallbackHandler      	mHandler          	 = null;    //< Enabled success/fail reporting to higher-level code
    protected AlertDialog          	mAlertDialog      	 = null;    //< Track if we've got an AlertDialog for manual GC
    protected ProgressDialog       	mProgressDialog    	= null;    //< Same as mAlertDialog but for progress
    protected EditText              mEditText         	 = null;    //< Instance of an EditText field, used with prompt_*() methods
    protected Boolean               mCanCancel        	 = true;    //< Default to can cancel, set to false to prevent cancel
    protected String                mBttnTextOK       	 = "OK";    //< String used to set the label of the "OK" button.
    protected String               	mBttnTextCancel   	 = "CANCEL"; //< String used to set the label of the "Cancel" button.

    protected Context				  mContext			 = null;
    
    private EventReport			  	mEventReport		 	= null;
  
    /**
     * private "OK" click handler, calls the onSuccess() method of the
     * CallbackHandler
     */
    private final OnClickListener mOkHandler         = new OnClickListener()
                                                     {
                                                         
                                                         public void onClick(DialogInterface dialog, int which)
                                                         {
                                                             if (mHandler != null) mHandler.onSuccess(0, null, null);
                                                             if(mEventReport!=null)
                                                             {
                                                            	 mEventReport.setLabel(mBttnTextOK);
                                                            	 mEventReport.setValue(1);
                                                            	 mEventReport.sendReport();
                                                             }
                                                             dismiss();
                                                         }
                                                     };

    /**
     * private "Cancel" click handler, calls the onFailure() method of the
     * CallbackHandler
     */
    private final OnClickListener mCancelHandler     = new OnClickListener()
                                                     {
                                                         
                                                         public void onClick(DialogInterface dialog, int which)
                                                         {
                                                             if (mHandler != null) mHandler.onFailure(0, null, null);
                                                             if(mEventReport!=null)
                                                             {
                                                            	 mEventReport.setLabel(mBttnTextCancel);
                                                            	 mEventReport.setValue(-1);
                                                            	 mEventReport.sendReport();
                                                             }
                                                             dismiss();
                                                         }
                                                     };

    /**
     * private "OK" click handler for the prompt_*() methods. Calls the
     * onSuccess() method of the CallbackHandler.
     */
    private final OnClickListener mOkEditTextHandler = new OnClickListener()
                                                     {
                                                         
                                                         public void onClick(DialogInterface dialog, int which)
                                                         {
                                                             if (mHandler != null) mHandler.onSuccess(0, mEditText.getText().toString(), null);
                                                             dismiss();
                                                         }
                                                     };

    /**
     * Accessory method for the "Can Cancel" flag.
     * 
     * @param flag
     */
    public void setCanCancel(Boolean flag)
    {
        mCanCancel = flag;
    }

    /**
     * Accessory method for changing the label of the "OK" button.
     * 
     * @param label
     */
    public void setLabelOK(String label)
    {
        mBttnTextOK = label;
    }

    /**
     * Accessory method for changing the label of the "Cancel" button.
     * 
     * @param label
     */
    public void setLabelCancel(String label)
    {
        mBttnTextCancel = label;
    }

    /**
     * Create a new dialog with a custom message.
     * 
     * @param message
     */
    public UIDialog(Context context)
    {
    	mContext = context;
        mMessage = mContext.getString(R.string.app_name);
        mTitle = mContext.getString(R.string.app_name);
        mIcon = R.drawable.icon;
        mHandler = null;
    }
    /**
     * Create a new dialog with a custom message.
     * 
     * @param message
     */
    public UIDialog(Context context,String message)
    {
    	mContext = context;
        mMessage = message;
        mTitle = mContext.getString(R.string.app_name);
        mIcon = R.drawable.icon;
        mHandler = null;
    }

    /**
     * Create a new dialog with a custom message and callback handler.
     * 
     * @param message
     * @param handler
     */
    public UIDialog(Context context, String message, CallbackHandler handler)
    {
    	mContext = context;
        mMessage = message;
        mTitle = mContext.getString(R.string.app_name);
        mIcon = R.drawable.icon;
        mHandler = handler;
    }

	/**
     * Create a new dialog with a custom title and message.
     * 
     * @param title
     * @param message
     * @param handler
     */
    public UIDialog(Context context,String title, String message)
    {
    	mContext = context;
        mMessage = message;
        mTitle = title;
        mIcon = R.drawable.icon;
        mHandler = null;
    }

    /**
     * Create a new dialog with a custom title, message and callback
     * handler.
     * 
     * @param title
     * @param message
     * @param handler
     */
    public UIDialog(Context context,String title, String message, CallbackHandler handler)
    {
    	mContext = context;
        mMessage = message;
        mTitle = title;
        mIcon = R.drawable.icon;
        mHandler = handler;
    }

    /**
     * Create a new dialog with a custom title, message and icon.
     * 
     * @param title
     * @param message
     * @param icon
     */
    public UIDialog(Context context,String title, String message, Integer icon)
    {
    	mContext = context;
        mMessage = message;
        mTitle = title;
        mIcon = icon;
        mHandler = null;
    }

    /**
     * Create a new dialog with a custom title, message, icon and callback
     * handler.
     * 
     * @param title
     * @param message
     * @param icon
     * @param handler
     */
    public UIDialog(Context context,String title, String message, Integer icon, CallbackHandler handler)
    {
    	mContext = context;
        mMessage = message;
        mTitle = title;
        mIcon = icon;
        mHandler = handler;
    }

    /**
     * Display a dialog message box. If a cancel handler has been provided,
     * display the cancel button; otherwise hide the cancel button and only
     * show the "OK" button.
     */
    final public void popup()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(mMessage);
        builder.setTitle(mTitle);
        builder.setIcon(mIcon);
        builder.setCancelable(mCanCancel);
        if (mCanCancel) builder.setNegativeButton(mBttnTextCancel, mCancelHandler);
        builder.setPositiveButton(mBttnTextOK, mOkHandler);
        mEventReport = new EventReport("Popup", "Click");
        mEventReport.setMessage(mMessage);
        mAlertDialog = builder.create();
        mAlertDialog.show();
        
    }
    
    final public void popup(String title,String message,String okBtnText,String cancelBtnText,Context context)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setIcon(mIcon);
        builder.setCancelable(mCanCancel);
        builder.setPositiveButton(okBtnText, mOkHandler);
        if (mCanCancel) builder.setNegativeButton(cancelBtnText, mCancelHandler);
        mEventReport.setMessage(mMessage);
        mAlertDialog = builder.create();
        mAlertDialog = builder.create();
        mAlertDialog.show();
        
    }
    /**
     * pop up message using your own activity
     * @param activity
     */
    final public void popup(Context context)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(mMessage);
        builder.setTitle(mTitle);
        builder.setIcon(mIcon);
        builder.setCancelable(false);
        if (mCanCancel) builder.setNegativeButton(mBttnTextCancel, mCancelHandler);
        builder.setPositiveButton(mBttnTextOK, mOkHandler);
        mEventReport.setMessage(mMessage);
        mAlertDialog = builder.create();
        mAlertDialog = builder.create();
        mAlertDialog.show();
    }
    /**
     * pop up message using your own activity
     * @param activity
     */
    final public void popup(String okBtnTextButton,Context context)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(mMessage);
        builder.setTitle(mTitle);
        builder.setIcon(mIcon);
        builder.setCancelable(mCanCancel);
        if (mCanCancel) builder.setNegativeButton(mBttnTextCancel, mCancelHandler);
        builder.setPositiveButton(okBtnTextButton, mOkHandler);
        mEventReport.setMessage(mMessage);
        mAlertDialog = builder.create();
        mAlertDialog = builder.create();
        mAlertDialog.show();
    }
    /**
     * 
     * @param title
     * @param message
     * @param okBtnText
     * @param cancelBtnText
     * @param context
     * @param showCancelButton
     */
    final public void popup(String title,String message,String okBtnText,String cancelBtnText,Context context, boolean showCancelButton)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setIcon(mIcon);
        builder.setCancelable(mCanCancel);
        builder.setPositiveButton(okBtnText, mOkHandler);
        if (showCancelButton) builder.setNegativeButton(cancelBtnText, mCancelHandler);
        mEventReport.setMessage(mMessage);
        mAlertDialog = builder.create();
        mAlertDialog = builder.create();
        mAlertDialog.show();
    }

    /**
     * Display a ProgressDialog box. User cannot cancel, only system.
     */
    final public void spinner()
    {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setTitle(mTitle);
        mProgressDialog.setMessage(mMessage);
        mProgressDialog.setIcon(mIcon);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        MyGoogleAnalytics.getInstance(mContext.getApplicationContext()).sendView(mMessage);
    }
    /**
     * Display a ProgressDialog box. User cannot cancel, only system.
     * @param context
     */
    final public void spinner(Context context)
    {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setTitle(mTitle);
        mProgressDialog.setMessage(mMessage);
        mProgressDialog.setIcon(mIcon);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        MyGoogleAnalytics.getInstance(mContext.getApplicationContext()).sendView(mMessage);
        ;
    }
    /**
     * Prompt for a line of text in an AlertDialog box. The input type
     * specified will modify the EditText field appropriately. Use
     * InputType.TYPE_TEXT_* constants for the inputType argument. The
     * onSuccess() handler receives the user-input via the response String.
     * 
     * @param hint
     * @param inputType
     */
    final public void prompt_text_field(String hint, int inputType)
    {
        prompt_text_field( hint,  inputType,mContext);
    }
    
    /**
     * Prompt for a line of text in an AlertDialog box. The input type
     * specified will modify the EditText field appropriately. Use
     * InputType.TYPE_TEXT_* constants for the inputType argument. The
     * onSuccess() handler receives the user-input via the response String.
     * 
     * @param hint
     * @param inputType
     * @param context
     */
    final public void prompt_text_field(String hint, int inputType,Context context)
    {
        if (hint == null) hint = "";
        if (mEditText == null) mEditText = new EditText(context);
        mEditText.setSingleLine(true);
        mEditText.setImeActionLabel("Done", EditorInfo.IME_ACTION_DONE);
        mEditText.setInputType(inputType);
        mEditText.setText(hint);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(this.mMessage);
        builder.setTitle(mContext.getString(R.string.app_name));
        builder.setCancelable(mCanCancel);
        if (mCanCancel) builder.setNegativeButton(mBttnTextCancel, mCancelHandler);
        builder.setPositiveButton(mBttnTextOK, mOkEditTextHandler);
        builder.setIcon(R.drawable.icon);
        builder.setView(mEditText);
        mAlertDialog = builder.create();
        mAlertDialog.show();
        
//        Button neg = mAlertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
//        Button pos = mAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
//        if(neg!=null)
//        {
//        	neg.setTextColor(StatefulActivity.this.getResources().getColor(R.color.general_text_color));
//        	neg.setBackgroundResource(R.drawable.button_selector);
//        }
//        if(pos!=null)
//        {
//        	pos.setTextColor(StatefulActivity.this.getResources().getColor(R.color.general_text_color));
//        	pos.setBackgroundResource(R.drawable.button_selector);
//        }
    }

    /**
     * 
     * @param hint
     * @param inputType
     * @param context
     */
    final public void prompt_share_text_field(String hint, int inputType,Context context)
    {
        if (hint == null) hint = "Hi...";
        if (mEditText == null) mEditText = new EditText(context);
        mEditText.setSingleLine(false);
        mEditText.setLines(5);
        mEditText.setMinLines(5);
        mEditText.setImeActionLabel("Enter", EditorInfo.IME_ACTION_NEXT);
        mEditText.setInputType(inputType);
        mEditText.setText(hint);
        mEditText.setGravity(Gravity.TOP|Gravity.LEFT);
        mEditText.setEms(10);
        @SuppressWarnings("deprecation")
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
        mEditText.setLayoutParams(params);
        
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(this.mMessage);
        builder.setTitle(this.mTitle);
        builder.setCancelable(mCanCancel);
        if (mCanCancel) builder.setNegativeButton(mBttnTextCancel, mCancelHandler);
        
        builder.setPositiveButton(mBttnTextOK, mOkEditTextHandler);
        builder.setIcon(R.drawable.icon);
        
        builder.setView(mEditText);
        
        mAlertDialog = builder.create();
        
        mAlertDialog.show();
        
//        Button neg = mAlertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
//        Button pos = mAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
//        if(neg!=null)
//        {
//        	neg.setTextColor(StatefulActivity.this.getResources().getColor(R.color.general_text_color));
//        	neg.setBackgroundResource(R.drawable.button_selector);
//        }
//        if(pos!=null)
//        {
//        	pos.setTextColor(StatefulActivity.this.getResources().getColor(R.color.general_text_color));
//        	pos.setBackgroundResource(R.drawable.button_selector);
//        }
    }
    
    /**
     * Prompt for a line of text in an AlertDialog box. The onSuccess()
     * handler receives the user-input via the response String.
     * 
     * @param hint
     */
    final public void prompt_text(String hint)
    {
        prompt_text_field(hint, InputType.TYPE_TEXT_VARIATION_NORMAL);
    }

    /**
     * Prompt for an email address in an AlertDialog box. The onSuccess()
     * handler receives the user-input via the response String.
     * 
     * @param hint
     */
    final public void prompt_email(String hint)
    {
        prompt_text_field(hint, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
    }
    
    /**
     * Prompt for an email address in an AlertDialog box. The onSuccess()
     * handler receives the user-input via the response String.
     * 
     * @param hint
     * @param context
     */
    final public void prompt_email(String hint,Context context)
    {
        prompt_text_field(hint, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,context);
    }

    /**
     * Removes all dialogs and cleans up the object references.
     */
    final public void dismiss()
    {
        if (mAlertDialog != null) 
        		mAlertDialog.dismiss();
        if (mProgressDialog != null) 
        		mProgressDialog.dismiss();
        
        mAlertDialog = null;
        mProgressDialog = null;
    }
    
    public class EventReport
    {
    	private String category;
    	private String action;
    	private String label;
    	private String message="";
    	private long value;
		/**
		 * @param category
		 * @param action
		 * @param label
		 * @param value
		 */
		public EventReport(String category, String action, String label, long value)
		{
			super();
			this.category = category;
			this.action = action;
			this.label = label;
			this.value = value;
		}
		/**
		 * @param category
		 * @param action
		 * @param label
		 */
		public EventReport(String category, String action, String label)
		{
			super();
			this.category = category;
			this.action = action;
			this.label = label;
		}
		/**
		 * @param category
		 * @param action
		 */
		public EventReport(String category, String action)
		{
			super();
			this.category = category;
			this.action = action;
		}
		public void sendReport()
		{
			if(message==null || message.trim().length()==0)
				MyGoogleAnalytics.getInstance(mContext.getApplicationContext()).sendEvent(category, action, label, value);
			else
				MyGoogleAnalytics.getInstance(mContext.getApplicationContext()).sendEvent(category, action, message + "-" + label, value);
		}
		
		public String getCategory()
		{
			return category;
		}
		public void setCategory(String category)
		{
			this.category = category;
		}
		public String getAction()
		{
			return action;
		}
		public void setAction(String action)
		{
			this.action = action;
		}
		public String getLabel()
		{
			return label;
		}
		public void setLabel(String label)
		{
			this.label = label;
		}
		public long getValue()
		{
			return value;
		}
		public void setValue(long value)
		{
			this.value = value;
		}
		public String getMessage()
		{
			return message;
		}
		public void setMessage(String message)
		{
			this.message = message;
		}
    }
}
