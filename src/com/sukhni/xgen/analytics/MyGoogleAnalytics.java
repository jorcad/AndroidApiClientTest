package com.sukhni.xgen.analytics;

import ca.sukhni.net.android.api.client.test.Logger;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Transaction;

import android.app.Activity;
import android.content.Context;

/**
 * 
 * @author malsukhni
 *
 */
public class MyGoogleAnalytics
{
	public final static String 					TAG				= MyGoogleAnalytics.class.getSimpleName();
	private static MyGoogleAnalytics			pInstance		= null;
	
	/**
	 * NOTE: Always use the application context to initialise the tracker
	 * @param context
	 */
	private MyGoogleAnalytics(Context context)
	{
		this.setTrackerContext(context);
		pInstance = this;
	}
	/**
	 * Sets the context to use to the applicationContext of the input Context.
	 * @param context
	 */
	private void setTrackerContext(Context context)
	{
		try
		{
			EasyTracker.getInstance().setContext(context);
		}
		catch(Exception ex)
		{
			Logger.error(TAG + ": setTrackerContext(Context): "+ ex.getMessage());
		}
	}

	/**
	 * Set the campaign. The input string may be a set of URL parameters or a
	 * full URL. The URL parameters may or may not be encoded. Valid campaign
	 * parameters are:
	 * <li>dclid</li> <li>gclid</li> <li>utm_campaign</li> <li>utm_content</li> <li>utm_medium</li> <li>utm_source</li> <li>utm_term</li>
	 * 
	 * <p> Implementations should handle input of the form
	 * http://my.site.com/index.html?utm_campaign=wow&utm_source=source as well
	 * as input of the form utm_campaign=wow&utm_source=source.</P>
	 * 
	 * @param campaign
	 */
	public void setTrackerCampaign(String campaign)
	{
		try
		{
			EasyTracker.getTracker().setCampaign(campaign);
		}
		catch(Exception ex)
		{
			Logger.error(TAG + ": setTrackerCampaign(String ): "+ ex.getMessage());
		}
	}
	/**
	 * 
	 * @param context
	 * @return
	 */
	public static MyGoogleAnalytics getInstance(Context context)
	{
		if(pInstance==null)
		{
			pInstance = new MyGoogleAnalytics(context);
			pInstance.printTrackerParameters();
		}
		return pInstance;
	}
	/**
	 * get tracker instance
	 * @return
	 */
	public static MyGoogleAnalytics getInstance() throws NullPointerException
	{
		if(pInstance==null) throw new NullPointerException("Google tracker instance is NULL");
		return pInstance;
	}
	/**
	 * print the tracker id, app id, and sample rate
	 */
	public void printTrackerParameters()
	{
		try
		{
			Logger.info(TAG + ": Tracking Id= " +EasyTracker.getTracker().getTrackingId());
			Logger.info(TAG + ": App Id= " +EasyTracker.getTracker().getAppId());
			Logger.info(TAG + ": Sample Rate= " +EasyTracker.getTracker().getSampleRate());
		}
		catch(Exception ex)
		{
			Logger.error(TAG + ": printTrackerParameters(): "+ ex.getMessage());
		}
	}
	/**
	 * Track the start of an Activity, 
	 * but only if "ga_autoActivityTracking" is true. 
	 * This method will start a new session if necessary, 
	 * and will send an empty event to Google Analytics if "ga_autoActivityTracking" is false to ensure proper application-level tracking. 
	 * This method should be called from the onStart method in each Activity in your application.
	 * @param activity
	 */
	public void activityStart(Activity activity)
	{
		try
		{
			EasyTracker.getInstance().activityStart(activity);
		}
		catch(Exception ex)
		{
			Logger.error(TAG + ": sendException(Activity): "+ ex.getMessage());
		}
	}
	/**
	 * Track the end of an Activity and/or application. 
	 * This is done by sending an empty event to Google Analytics. 
	 * Note that this method should be called from the onStop method of each Activity in your application.
	 * @param activity
	 */
	public void activityStop(Activity activity)
	{
		try
		{
			EasyTracker.getInstance().activityStart(activity);
		}
		catch(Exception ex)
		{
			Logger.error(TAG + ": activityStop(Activity): "+ ex.getMessage());
		}
	}
	/**
	 * start tracking the application session
	 */
	public void startSession()
	{
		try
		{
			EasyTracker.getTracker().setStartSession(true);
		}
		catch(Exception ex)
		{
			Logger.error(TAG + ": startSession(Activity): "+ ex.getMessage());
		}
	}
	/**
	 * Tracks entering a view with the current app screen name.
	 */
	public void sendView()
	{
		try
		{
			EasyTracker.getTracker().sendView();
		}
		catch(Exception ex)
		{
			Logger.error(TAG + ": sendView(): "+ ex.getMessage());
		}
	}
	/**
	 * Tracks entering a view with a new app screen name.
	 * @param appScreen
	 */
	public void sendView(String appScreen)
	{
		try
		{
			EasyTracker.getTracker().sendView(appScreen);
		}
		catch(Exception ex)
		{
			Logger.error(TAG + ": sendView(String): "+ ex.getMessage());
		}
	}
	/**
	 * Tracks an event. 
	 * Events have a category, action, label and value. 
	 * This method can be used to track events such as button presses or other user interactions with your application.
	 * @param category
	 * @param action
	 * @param label
	 * @param value
	 */
	public void sendEvent(String category, String action, String label, Long value)
	{
		try
		{
			EasyTracker.getTracker().sendEvent(category, action, label, value);
		}
		catch(Exception ex)
		{
			Logger.error(TAG + ": sendEvent(String,String,String,Long): "+ ex.getMessage());
		}
	}
	/**
	 * Tracks an event. 
	 * Events have a category, action, label and value. 
	 * This method can be used to track events such as button presses or other user interactions with your application.
	 * @param category
	 * @param action
	 * @param label
	 * @param value
	 */
	public void sendEvent(String category, String action, String label, int value)
	{
		try
		{
			EasyTracker.getTracker().sendEvent(category, action, label, (long) value);
		}
		catch(Exception ex)
		{
			Logger.error(TAG + ": sendEvent(String,String,String,Long): "+ ex.getMessage());
		}
	}
	/**
	 * Tracks a user timing hit.
	 * Category and label are optional.
	 * @param category
	 * @param intervalInMilliseconds
	 * @param name
	 * @param label
	 */
	public void sendTiming(String category, long intervalInMilliseconds, String name, String label)
	{
		try
		{
			EasyTracker.getTracker().sendTiming(category, intervalInMilliseconds, name, label);
		}
		catch(Exception ex)
		{
			Logger.error(TAG + ": sendTiming(String, long, String,String): "+ ex.getMessage());
		}
	}
	/**
	 * Tracks a API timing hit.
	 * @param intervalInMilliseconds
	 * @param task
	 * @param code
	 */
	public void sendApiTiming(long intervalInMilliseconds, String task, int code)
	{
		try
		{
			EasyTracker.getTracker().sendTiming("API", intervalInMilliseconds, task, Integer.toString(code));
		}
		catch(Exception ex)
		{
			Logger.error(TAG + ": sendTiming(String, long, String,String): "+ ex.getMessage());
		}
	}
	/**
	 * Tracks a Activity timing hit.
	 * @param intervalInMilliseconds
	 * @param task
	 * @param code
	 */
	public void sendActivityTiming(long intervalInMilliseconds, String task,String label)
	{
		try
		{
			EasyTracker.getTracker().sendTiming("Activity", intervalInMilliseconds, task, label);
		}
		catch(Exception ex)
		{
			Logger.error(TAG + ": sendTiming(String, long, String,String): "+ ex.getMessage());
		}
	}
	/**
	 * Tracks a social hit. Target is optional.
	 * @param network
	 * @param action
	 * @param target
	 */
	public void sendSocial(String network,String action,String target)
	{
		try
		{
			EasyTracker.getTracker().sendSocial(network, action, target);
		}
		catch(Exception ex)
		{
			Logger.error(TAG + ": sendSocial(String,String,String): "+ ex.getMessage());
		}
	}
	/**
	 * Tracks an eCommerce transaction.
	 * @param transaction
	 */
	public void sendTransaction(Transaction transaction)
	{
		try
		{
			EasyTracker.getTracker().sendTransaction(transaction);
		}
		catch(Exception ex)
		{
			Logger.error(TAG + ": sendTransaction(Transaction): "+ ex.getMessage());
		}
	}
	/**
	 * Tracks that an exception occurred. 
	 * Note that the description is optional. 
	 * Note: the exception will be non fatal by default
	 * If you don't want to send a description simply set the input parameter to null.
	 * @param message
	 * */
	public void sendException(String message)
	{
		sendException(message, false);
	}
	/**
	 * Tracks that an exception occurred. 
	 * Note that the description is optional. 
	 * If you don't want to send a description simply set the input parameter to null.
	 * @param message
	 * @param fatal
	 */
	public void sendException(String message,boolean fatal)
	{
		try
		{
			EasyTracker.getTracker().sendException(message, fatal);
			Logger.error(TAG + ": sendException(String message,boolean fatal): message= " + message);
		}
		catch(Exception ex)
		{
			Logger.error(TAG + ": sendException(String,boolean): "+ ex.getMessage());
		}
	}
	/**
	 * Tracks that an exception occurred. 
	 * Note that the description is optional. 
	 * Note: the exception will be non fatal by default
	 * If you don't want to send a description simply set the input parameter to null.
	 * @param threadName the name of the @{link Thread} that got the exception, or null
	 * @param exception
	 */
	public void sendException(String threadName, Throwable exception)
	{
		sendException(threadName, exception, false);
	}
	/**
	 * Tracks that an exception occurred. 
	 * Note that the description is optional. 
	 * If you don't want to send a description simply set the input parameter to null.
	 * @param threadName the name of the @{link Thread} that got the exception, or null
	 * @param exception
	 * @param fatal
	 */
	public void sendException(String threadName, Throwable exception, boolean fatal)
	{
		try
		{
			EasyTracker.getTracker().sendException(threadName, exception, fatal);
			Logger.error(TAG + ": sendException(String threadName, Throwable exception, boolean fatal): Thread Name= " + threadName);
		}
		catch(Exception ex)
		{
			Logger.error(TAG + ": sendException(String,Throwable,boolean): "+ ex.getMessage());
		}
	}
	
}
