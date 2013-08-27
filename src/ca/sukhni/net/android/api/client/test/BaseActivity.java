package ca.sukhni.net.android.api.client.test;


import ca.sukhni.net.android.api.client.test.R;

import com.sukhni.xgen.analytics.MyGoogleAnalytics;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.widget.Toast;

public abstract class BaseActivity extends Activity
{

	public final static String					TAG							= BaseActivity.class.getSimpleName();
	private UIDialog							mUIDialog					= null;
	private MyTimer								mTimer						= null;
	private DetectConnectivity					mConnectivity 				= null;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Logger.debug(this.getClass().getSimpleName() + ": onCreate()");
		mUIDialog = new UIDialog(this);
	}
	/**
	 * 
	 * @param toastId
	 */
	public void showToast(final int toastId)
	{
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				Toast.makeText(getApplicationContext(), toastId, Toast.LENGTH_SHORT).show();
			}
		});
	}
	/**
	 * 
	 * @param toast
	 */
	public void showToast(final String toast)
	{
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		return super.onCreateOptionsMenu(menu);
	}
	/**
     * Access the connectivity detection system directly.
     * @return reference to DetectConnectivity instance
     */
    final public DetectConnectivity getConnectivity()
    {
		if (mConnectivity == null) mConnectivity = new DetectConnectivity();
        return mConnectivity;
    }
	/**
     * Simple test for actual network connectivity.
     * 
     * @return
     */
    final public boolean hasConnectivity()
    {
        return getConnectivity().checkNow(this);
    }


	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		Logger.debug(this.getClass().getSimpleName() + ": onDestroy()");
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		Logger.debug(this.getClass().getSimpleName() + ": onPause()");
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		Logger.debug(this.getClass().getSimpleName() + ": onStart()");
		MyGoogleAnalytics.getInstance(getApplicationContext()).activityStart(this);
	}
	
	@Override
	protected void onStop()
	{
		super.onStop();
		Logger.debug(this.getClass().getSimpleName() + ": onStop()");
		MyGoogleAnalytics.getInstance(getApplicationContext()).activityStop(this);
		if(mTimer!=null)
		{
			mTimer.stop();
			sendTimingReport(this.getClass().getSimpleName());
		}
	}

	@Override
	protected void onNewIntent(Intent intent)
	{
		super.onNewIntent(intent);
		Logger.debug(this.getClass().getSimpleName() + ": onNewIntent(" + intent + ")");
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		Logger.debug(this.getClass().getSimpleName() + ": onResume()");
		mTimer = new MyTimer();
		mTimer.start();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		Logger.debug(this.getClass().getSimpleName() + ": onConfigurationChanged( Orientatio= " + newConfig.orientation + ")");
		super.onConfigurationChanged(newConfig);
	}
	public int getOrientation()
	{
		return this.getResources().getConfiguration().orientation;
		//return this.orientation;
	}
	/**
	 * 
	 * @param category
	 * @param task
	 * @param code
	 */
	public void sendTimingReport(String task)
	{
		try
		{
			if(mTimer!=null )
			{
				long time = mTimer.getProcessTime();
				MyGoogleAnalytics.getInstance(this).sendActivityTiming(time,task, null);
				Logger.info(TAG + ": sendTimingReport(String task,int code): time= "+ time +", task= " + task);
				
			}
		}
		catch(Exception ex)
		{
			MyGoogleAnalytics.getInstance(getApplicationContext()).sendException(ex.getClass().getSimpleName(), ex);
		}
	}
	public void dismissDialog()
	{
		if(mUIDialog!=null) mUIDialog.dismiss();
	}
	/**
     * Display a ProgressDialog with a custom message.
     * 
     * @param message
     */
    public void showProgressDialog(Integer message)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,getString(R.string.app_name), getString(message), R.drawable.icon_small);
        mUIDialog.spinner();
    }
    /**
     * Display a ProgressDialog with a custom message.
     * 
     * @param message
     */
    public void showProgressDialog(String message)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,getString(R.string.app_name), message, R.drawable.icon_small);
        mUIDialog.spinner();
    }
    
    /**
     * Display a ProgressDialog with a custom message.
     * 
     * @param message
     * @param context
     */
    public void showProgressDialog(Integer message,Context context)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(context,context.getString(R.string.app_name), context.getString(message), R.drawable.icon_small);
        mUIDialog.spinner(context);
    }

    /**
     * Display a ProgressDialog with a custom message.
     * 
     * @param message
     * @param context
     */
    public void showProgressDialog(String message,Context context)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(context,context.getString(R.string.app_name), (message), R.drawable.icon_small);
        mUIDialog.spinner(context);
    }

    
    /**
     * Display a ProgressDialog with a custom title and message.
     * 
     * @param title
     * @param message
     */
    public void showProgressDialog(Integer title, Integer message)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,getString(title), getString(message), R.drawable.icon_small);
        mUIDialog.spinner();
    }
    
    /**
     * Display a ProgressDialog with a custom title and message.
     * 
     * @param title
     * @param message
     * @param context
     */
    public void showProgressDialog(Integer title, Integer message,Context context)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(context,getString(title), getString(message), R.drawable.icon_small);
        mUIDialog.spinner(context);
    }

    /**
     * Display a ProgressDialog with a custom title, message and icon.
     * 
     * @param title
     * @param message
     * @param icon
     */
    public void showProgressDialog(Integer title, Integer message, Integer icon)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,getString(title), getString(message), icon);
        mUIDialog.spinner();
    }

    /**
     * Display an AlertDialog with a custom message.
     * 
     * @param message
     */
    public void showAlertDialog(Integer message)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,getString(R.string.app_name), getString(message), R.drawable.icon_small);
        mUIDialog.setCanCancel(false);
        mUIDialog.popup();
    }

    /**
     * Display an AlertDialog with a custom message.
     * 
     * @param message
     */
    public void showAlertDialog(String message)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,getString(R.string.app_name), (message), R.drawable.icon_small);
        mUIDialog.setCanCancel(false);
        mUIDialog.popup();
    }
    
    /**
     * Display an AlertDialog with a custom message.
     * 
     * @param message
     * @param context
     */
    public void showAlertDialog(Integer message,Context context)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(context,context.getString(R.string.app_name), context.getString(message), R.drawable.icon_small);
        mUIDialog.setCanCancel(false);
        mUIDialog.popup(context);
    }
    /**
     * Display an AlertDialog with a custom message.
     * 
     * @param message
     * @param context
     */
    public void showAlertDialog(Integer message,String okBtnText,Context context, CallbackHandler handler)
    {
    	if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(context,getString(R.string.app_name), getString(message), R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(false);
        mUIDialog.popup(context);
    }
    /**
     * Display an AlertDialog with a custom message.
     * 
     * @param message
     * @param context
     */
    public void showAlertDialog(String message,Context context)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(context,context.getString(R.string.app_name), (message), R.drawable.icon_small);
        mUIDialog.setCanCancel(false);
        mUIDialog.popup(context);
    }
    /**
     * Display an AlertDialog with a custom message and a callback handler.
     * 
     * @param message
     * @param handler
     */
    public void showAlertDialog(Integer message, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,getString(R.string.app_name), getString(message), R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(false);
        mUIDialog.popup();
    }
    
    /**
     * Display an AlertDialog with a custom message and a callback handler.
     * 
     * @param message
     * @param handler
     */
    public void showAlertDialog(String message, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,getString(R.string.app_name), (message), R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(false);
        mUIDialog.popup();
    }
    
    /**
     * Display an AlertDialog with a custom message and a callback handler.
     * 
     * @param message
     * @param context
     * @param handler
     */
    public void showAlertDialog(Integer message,Context context ,CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(context,context.getString(R.string.app_name), context.getString(message), R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(false);
        mUIDialog.popup(context);
    }
    
    /**
     * Display an AlertDialog with a custom message and a callback handler.
     * 
     * @param message
     * @param context
     * @param handler
     */
    public void showAlertDialog(String message,Context context ,CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(context,context.getString(R.string.app_name), (message), R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(false);
        mUIDialog.popup(context);
    }
    /**
     * Display an AlertDialog with a custom title and message.
     * 
     * @param title
     * @param message
     */
    public void showAlertDialog(Integer title, Integer message)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,getString(title), getString(message), R.drawable.icon_small);
        mUIDialog.setCanCancel(false);
        mUIDialog.popup();
    }
    /**
     * Display an AlertDialog with a custom title and message.
     * 
     * @param title
     * @param message
     */
    public void showAlertDialog(String title, String message)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,(title), (message), R.drawable.icon_small);
        mUIDialog.setCanCancel(false);
        mUIDialog.popup();
    }
    /**
     * Display an AlertDialog with a custom title and message.
     * 
     * @param title
     * @param message
     * @param context
     */
    public void showAlertDialog(Integer title, Integer message,Context context)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(context,context.getString(title), context.getString(message), R.drawable.icon_small);
        mUIDialog.setCanCancel(false);
        mUIDialog.popup(context);
    }
    
    /**
     * Display an AlertDialog with a custom title and message.
     * 
     * @param title
     * @param message
     * @param context
     */
    public void showAlertDialog(String title, String message,Context context)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(context,(title), (message), R.drawable.icon_small);
        mUIDialog.setCanCancel(false);
        mUIDialog.popup(context);
    }
    /**
     * Display an AlertDialog with a custom title, message and callback handler.
     * 
     * @param title
     * @param message
     * @param handler
     */
    public void showAlertDialog(Integer title, Integer message, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,getString(title), getString(message), R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(false);
        mUIDialog.popup();
    }
    
    /**
     * Display an AlertDialog with a custom title, message and callback handler.
     * 
     * @param title
     * @param message
     * @param handler
     */
    public void showAlertDialog(String title, String message, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,(title), (message), R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(false);
        mUIDialog.popup();
    }
    
    /**
     * Display an AlertDialog with a custom title, message and callback handler.
     * @param title
     * @param message
     * @param context
     * @param handler
     */
    public void showAlertDialog(Integer title, Integer message,Context context, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(context,context.getString(title), context.getString(message), R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(false);
        mUIDialog.popup(context);
    }

    /**
     * Display an AlertDialog with a custom title, message,context and callback handler.
     * @param title
     * @param message
     * @param context
     * @param handler
     */
    public void showAlertDialog(String title, String message,Context context, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(context,(title), (message), R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(false);
        mUIDialog.popup(context);
    }
    
    /**
     * Display an AlertDialog with a custom title, message and icon.
     * 
     * @param title
     * @param message
     * @param icon
     */
    public void showAlertDialog(Integer title, Integer message, Integer icon)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,getString(title), getString(message), icon);
        mUIDialog.setCanCancel(false);
        mUIDialog.popup();
    }

    /**
     * Display an AlertDialog with a custom title, message and icon.
     * 
     * @param title
     * @param message
     * @param icon
     */
    public void showAlertDialog(String title, String message, Integer icon)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,(title), (message), icon);
        mUIDialog.setCanCancel(false);
        mUIDialog.popup();
    }
    
    /**
     * Display an AlertDialog with a custom title, message, icon and callback
     * handler.
     * 
     * @param title
     * @param message
     * @param icon
     * @param handler
     */
    public void showAlertDialog(Integer title, Integer message, Integer icon, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,getString(title), getString(message), icon, handler);
        mUIDialog.setCanCancel(false);
        mUIDialog.popup();
    }
    
    /**
     * Display an AlertDialog with a custom title, message, icon and callback
     * handler.
     * 
     * @param title
     * @param message
     * @param icon
     * @param handler
     */
    public void showAlertDialog(String title, String message, Integer icon, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,(title), (message), icon, handler);
        mUIDialog.setCanCancel(false);
        mUIDialog.popup();
    }

    /**
     * Display an AlertDialog containing a YES button and a NO button with a
     * custom message and a callback handler.
     * 
     * @param message
     * @param handler
     */
    public void promptYesNoDialog(Integer message, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,getString(R.string.app_name), getString(message), R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(true);
        mUIDialog.setLabelCancel("NO");
        mUIDialog.setLabelOK("YES");
        mUIDialog.popup();
    }
    /**
     * Display an AlertDialog containing a YES button and a NO button with a
     * custom message and a callback handler.
     * 
     * @param message
     * @param context
     * @param handler
     */
    public void promptYesNoDialog(Integer message,Context context,CallbackHandler handler)
    {
    	if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(context,context.getString(R.string.app_name), context.getString(message), R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(true);
        mUIDialog.setLabelCancel("NO");
        mUIDialog.setLabelOK("YES");
        mUIDialog.popup(context);
    }
    
    /**
     * Display an AlertDialog containing a YES button and a NO button with a
     * custom message and a callback handler. this method can be used from your own activity
     * 
     * @param message
     * @param activity
     * @param handler
     */
    public void promptYesNoDialog(Integer message, Activity activity,CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,activity.getString(R.string.app_name), activity.getString(message), R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(true);
        mUIDialog.setLabelCancel("NO");
        mUIDialog.setLabelOK("YES");
        mUIDialog.popup(activity);
    }
    
    /**
     * Display an AlertDialog containing a YES button and a NO button with a
     * custom message and a callback handler. this method can be used from your own activity
     * 
     * @param message
     * @param activity
     * @param handler
     */
    public void promptYesNoDialog(String message, Context context,CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(context,context.getString(R.string.app_name), (message), R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(true);
        mUIDialog.setLabelCancel("NO");
        mUIDialog.setLabelOK("YES");
        mUIDialog.popup(context);
    }
    
    /**
     * Display an AlertDialog containing a YES button and a NO button with a
     * custom title, message and callback handler.
     * 
     * @param title
     * @param message
     * @param handler
     */
    public void promptYesNoDialog(Integer title, Integer message, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,getString(title), getString(message), R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(true);
        mUIDialog.setLabelCancel("NO");
        mUIDialog.setLabelOK("YES");
        mUIDialog.popup();
    }
    
    /**
     * Display an AlertDialog containing a YES button and a NO button with a
     * custom title, message and callback handler.
     * 
     * @param title
     * @param message
     * @param handler
     */
    public void promptYesNoDialog(String title, String message, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,(title), (message), R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(true);
        mUIDialog.setLabelCancel("NO");
        mUIDialog.setLabelOK("YES");
        mUIDialog.popup();
    }

    /**
     * Display an AlertDialog containing a YES button and a NO button with a
     * custom title, message, icon and callback handler.
     * 
     * @param title
     * @param message
     * @param icon
     * @param handler
     */
    public void promptYesNoDialog(Integer title, Integer message, Integer icon, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,getString(title), getString(message), icon, handler);
        mUIDialog.setCanCancel(true);
        mUIDialog.setLabelCancel("NO");
        mUIDialog.setLabelOK("YES");
        mUIDialog.popup();
    }

    /**
     * Display an AlertDialog containing a YES button and a NO button with a
     * custom title, message, icon and callback handler.
     * 
     * @param title
     * @param message
     * @param icon
     * @param handler
     */
    public void promptYesNoDialog(String title, String message, Integer icon, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,(title), (message), icon, handler);
        mUIDialog.setCanCancel(true);
        mUIDialog.setLabelCancel("NO");
        mUIDialog.setLabelOK("YES");
        mUIDialog.popup();
    }

    /**
     *  Display an AlertDialog containing an OK button and a Cancel button with a
     * custom message and a callback handler
     * 
     * @param message
     * @param handler
     */
    public void promptOkCancelDialog(Integer message, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,getString(R.string.app_name), getString(message), R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(true);
        mUIDialog.popup();
    }
    
    /**
     * Display an AlertDialog containing an OK button and a Cancel button with a
     * custom message and a callback handler.
     * @param title
     * @param message
     * @param okBtnText
     * @param cancelBtnText
     * @param context
     * @param handler
     */
    public void promptOkCancelDialog(Integer title,Integer message,Integer okBtnText,Integer cancelBtnText,Context context,CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(context,getString(R.string.app_name), getString(message), R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(true);
        String strTitle = context.getResources().getString(title);
        String strMessage = context.getResources().getString(message);
        String strOkBtnText = context.getResources().getString(okBtnText);
        String strCancelBtnText = context.getResources().getString(cancelBtnText);

        mUIDialog.popup(strTitle,strMessage,strOkBtnText,strCancelBtnText,context,true);
    }
    
    public void promptOkCancelDialog(String title,String message,String okBtnText,String cancelBtnText,Context context,CallbackHandler handler)
    {
        if(mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(context,getString(R.string.app_name), message, R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(false);
        
        mUIDialog.popup(title,message,okBtnText,cancelBtnText,context,true);
    }

    /**
     * Display an AlertDialog containing an OK button and a Cancel button with a
     * custom title, message and callback handler.
     * 
     * @param title
     * @param message
     * @param handler
     */
    public void promptOkCancelDialog(Integer title, Integer message, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,getString(title), getString(message), R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(true);
        mUIDialog.popup();
    }

    /**
     * Display an AlertDialog containing an OK button and a Cancel button with a
     * custom title, message and callback handler.
     * 
     * @param title
     * @param message
     * @param handler
     */
    public void promptOkCancelDialog(String  title, String message, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,(title), (message), R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(true);
       
        mUIDialog.popup();
    }
    
    /**
     * Display an AlertDialog containing an OK button and a Cancel button with a
     * custom title, message, icon and callback handler.
     * 
     * @param title
     * @param message
     * @param icon
     * @param handler
     */
    public void promptOkCancelDialog(Integer title, Integer message, Integer icon, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,getString(title), getString(message), icon, handler);
        mUIDialog.setCanCancel(true);
        mUIDialog.popup();
    }

    /**
     * Display an AlertDialog containing an OK button and a Cancel button with a
     * custom title, message, icon and callback handler.
     * 
     * @param title
     * @param message
     * @param icon
     * @param handler
     */
    public void promptOkCancelDialog(String title, String message, Integer icon, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,(title), (message), icon, handler);
        mUIDialog.setCanCancel(true);
        mUIDialog.popup();
    }
    
    /**
     * Display an AlertDialog with an editable text field, custom message and
     * callback handler. The value from the EditText field is returned to the
     * onSuccess() handler via the response String.
     * 
     * @param hint
     * @param message
     * @param handler
     */
    public void promptTextDialog(String hint, Integer message, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,getString(R.string.app_name), getString(message), R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(true);
        mUIDialog.prompt_text(hint);
    }
    
    /**
     * Display an AlertDialog with an editable text field, custom message and
     * callback handler. The value from the EditText field is returned to the
     * onSuccess() handler via the response String.
     * 
     * @param hint
     * @param message
     * @param handler
     */
    public void promptTextDialog(String hint, String message, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,getString(R.string.app_name), (message), R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(true);
        mUIDialog.prompt_text(hint);
    }
    
    /**
     * Display an AlertDialog with an editable text field, custom title, message
     * and callback handler. The value from the EditText field is returned to
     * the onSuccess() handler via the response String.
     * 
     * @param hint
     * @param title
     * @param message
     * @param handler
     */
    public void promptTextDialog(String hint, Integer title, Integer message, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,getString(title), getString(message), R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(true);
        mUIDialog.prompt_text(hint);
    }

    /**
     * Display an AlertDialog with an editable text field, custom title, message
     * and callback handler. The value from the EditText field is returned to
     * the onSuccess() handler via the response String.
     * 
     * @param hint
     * @param title
     * @param message
     * @param handler
     */
    public void promptTextDialog(String hint, String title, String message, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,(title), (message), R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(true);
        mUIDialog.prompt_text(hint);
    }
    /**
     * Display an AlertDialog with an editable text field, custom title,
     * message, icon and callback handler. The value from the EditText field is
     * returned to the onSuccess() handler via the response String.
     * 
     * @param hint
     * @param title
     * @param message
     * @param icon
     * @param handler
     */
    public void promptTextDialog(String hint, Integer title, Integer message, Integer icon, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,getString(title), getString(message), icon, handler);
        mUIDialog.setCanCancel(true);
        mUIDialog.prompt_text(hint);
    }

    /**
     * Display an AlertDialog with an editable text field, custom title,
     * message, icon and callback handler. The value from the EditText field is
     * returned to the onSuccess() handler via the response String.
     * 
     * @param hint
     * @param title
     * @param message
     * @param icon
     * @param handler
     */
    public void promptTextDialog(String hint,String title, String message, Integer icon, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,(title), (message), icon, handler);
        mUIDialog.setCanCancel(true);
        mUIDialog.prompt_text(hint);
    }
    
    /**
     * Display an AlertDialog with an editable text field, custom title,
     * message, icon and callback handler. The value from the EditText field is
     * returned to the onSuccess() handler via the response String.
     * 
     * @param hint
     * @param title
     * @param message
     * @param icon
     * @param context
     * @param handler
     */
    public void promptShareDialog(String hint,Integer title,Integer message,Integer icon,Context context, CallbackHandler handler)
    {
    	 if (mUIDialog != null) mUIDialog.dismiss();
         mUIDialog = new UIDialog(context,context.getString(title), context.getString(message), icon, handler);
         mUIDialog.setCanCancel(true);
         mUIDialog.prompt_share_text_field(hint, InputType.TYPE_TEXT_FLAG_MULTI_LINE, context);
    }
    /**
     * Display an AlertDialog with an editable text field, custom title,
     * message, icon and callback handler. The value from the EditText field is
     * returned to the onSuccess() handler via the response String.
     * 
     * @param hint
     * @param title
     * @param message
     * @param icon
     * @param context
     * @param handler
     */
    public void promptShareDialog(String hint,String title,String message,Integer icon,Context context, CallbackHandler handler)
    {
    	 if (mUIDialog != null) mUIDialog.dismiss();
         mUIDialog = new UIDialog(context,(title), (message), icon, handler);
         mUIDialog.setCanCancel(true);
         mUIDialog.prompt_share_text_field(hint, InputType.TYPE_TEXT_FLAG_MULTI_LINE, context);
    }
    /**
     * Display an AlertDialog with an editable text field, custom message and
     * callback handler. The value from the EditText field is returned to the
     * onSuccess() handler via the response String. The input type for the
     * EditText field is preset to EMAIL ADDRESS.
     * 
     * @param hint
     * @param message
     * @param handler
     */
    public void promptEmailDialog(String hint, Integer message, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,getString(R.string.app_name), getString(message), R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(true);
        mUIDialog.prompt_email(hint);
       
    }
    
    /**
     * Display an AlertDialog with an editable text field, custom message and
     * callback handler. The value from the EditText field is returned to the
     * onSuccess() handler via the response String. The input type for the
     * EditText field is preset to EMAIL ADDRESS.
     * 
     * @param hint
     * @param message
     * @param context
     * @param handler
     */
    public void promptEmailDialog(String hint, Integer message,Context context, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(context,context.getString(R.string.app_name), context.getString(message), R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(true);
        mUIDialog.prompt_email(hint,context);
       
    }

    /**
     * Display an AlertDialog with an editable text field, custom title, message
     * and callback handler. The value from the EditText field is returned to
     * the onSuccess() handler via the response String. The input type for the
     * EditText field is preset to EMAIL ADDRESS.
     * 
     * @param hint
     * @param title
     * @param message
     * @param handler
     */
    public void promptEmailDialog(String hint, Integer title, Integer message, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,getString(title), getString(message), R.drawable.icon_small, handler);
        mUIDialog.setCanCancel(true);
        mUIDialog.prompt_text(hint);
    }

    /**
     * Display an AlertDialog with an editable text field, custom title,
     * message, icon and callback handler. The value from the EditText field is
     * returned to the onSuccess() handler via the response String. The input
     * type for the EditText field is preset to EMAIL ADDRESS.
     * 
     * @param hint
     * @param title
     * @param message
     * @param icon
     * @param handler
     */
    public void promptEmailDialog(String hint, Integer title, Integer message, Integer icon, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,getString(title), getString(message), icon, handler);
        mUIDialog.setCanCancel(true);
        mUIDialog.prompt_text(hint);
    }

    /**
     * Display an AlertDialog with an editable numeric-input field, custom hint,
     * message and callback handler. The value from the EditText field is
     * returned to the onSuccess() handler via the response String. The input
     * type for the EditText field is preset to TYPE_CLASS_NUMBER.
     * 
     * @param hint
     * @param message
     * @param handler
     */
    public void promptNumericDialog(String hint, Integer message, CallbackHandler handler)
    {
        promptNumericDialog(hint, R.string.app_name, message, R.drawable.icon_small, handler);
    }

    /**
     * Display an AlertDialog with an editable numeric-input field, custom hint,
     * title, message and callback handler. The value from the EditText field is
     * returned to the onSuccess() handler via the response String. The input
     * type for the EditText field is preset to TYPE_CLASS_NUMBER.
     * 
     * @param hint
     * @param title
     * @param message
     * @param handler
     */
    public void promptNumericDialog(String hint, Integer title, Integer message, CallbackHandler handler)
    {
        promptNumericDialog(hint, title, message, R.drawable.icon_small,handler);
    }
    
    public void promptNumericDialog(String hint, Integer title, Integer message,Context context,CallbackHandler handler)
    {
        promptNumericDialog(hint, title, message, R.drawable.icon_small,context ,handler);
    }

    /**
     * Display an AlertDialog with an editable numeric-input field, custom hint,
     * title, message, icon and callback handler. The value from the field is
     * returned to the onSuccess() handler via the response String. The input
     * type for the EditText field is preset to TYPE_CLASS_NUMBER.
     * 
     * @param hint
     * @param title
     * @param message
     * @param icon
     * @param handler
     */
    public void promptNumericDialog(String hint, Integer title, Integer message, Integer icon, CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(this,getString(title), getString(message), icon, handler);
        mUIDialog.setCanCancel(true);
        mUIDialog.prompt_text_field(hint, InputType.TYPE_CLASS_NUMBER);
    }
    /**
     * Display an AlertDialog with an editable numeric-input field, custom hint,
     * title, message, icon and callback handler. The value from the field is
     * returned to the onSuccess() handler via the response String. The input
     * type for the EditText field is preset to TYPE_CLASS_NUMBER.
     * 
     * @param hint
     * @param title
     * @param message
     * @param icon
     * @param context
     * @param handler
     */
    public void promptNumericDialog(String hint, Integer title, Integer message, Integer icon,Context context ,CallbackHandler handler)
    {
        if (mUIDialog != null) mUIDialog.dismiss();
        mUIDialog = new UIDialog(context,context.getString(title), context.getString(message), icon, handler);
        mUIDialog.setCanCancel(true);
        mUIDialog.prompt_text_field(hint, InputType.TYPE_CLASS_NUMBER,context);
    }
}
