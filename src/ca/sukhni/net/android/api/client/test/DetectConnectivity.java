package ca.sukhni.net.android.api.client.test;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class DetectConnectivity
{
    /**
     * Used internally to log debug messages. Basically a shortcut.
     * 
     * @param msg
     */
    @SuppressWarnings("unused")
	private void debug(String msg)
    {
        if (Constants.DEBUG) Log.d(Constants.LOG_TAG, msg);
    }

    /**
     * Same as debug(String) but for logging errors.
     * 
     * @param msg
     */
    private void error(String msg)
    {
        Log.e(Constants.LOG_TAG, msg);
    }

    ConnectivityManager connectivityManager;
    NetworkInfo         wifiInfo;
	NetworkInfo mobileInfo;

    /**
     * Check for <code>TYPE_WIFI</code> and <code>TYPE_MOBILE</code> connection
     * using <code>isConnected()</code> Checks for generic Exceptions and writes
     * them to LogCat as <code>CheckConnectivity Exception</code>. Make sure
     * AndroidManifest.xml has appropriate permissions.
     * 
     * @param con
     *            Application context
     * @return Boolean
     */
    public boolean checkNow(Context con)
    {
        try
        {
            connectivityManager = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
            
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            boolean serviceAvailable = (netInfo!=null && netInfo.isConnected())? true:false;
            
            wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiInfo.isConnected() || mobileInfo.isConnected() || serviceAvailable) 
            { 
            	Logger.info("DetectConnectivity : " + true);
            	return true; 
            }
        }
        catch (Exception e)
        {
        	Logger.info("DetectConnectivity Exception: " + e.getMessage());
        }
        error("DetectConnectivity : " + false);
        return false;
    }
    /**
     * 
     * @param con
     * @param handler
     * @return
     */
    public boolean checkNow(Context con,CallbackHandler handler)
    {
        try
        {
            connectivityManager = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
            
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            boolean serviceAvailable = (netInfo!=null && netInfo.isConnected())? true:false;
       
            wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiInfo.isConnected() || mobileInfo.isConnected() || serviceAvailable) 
            {
            	handler.onSuccess(0, null, null);
            	return true; 
            }
        }
        catch (Exception e)
        {
        	handler.onFailure(-1, null, null);
            error("DetectConnectivity Exception: " + e.getMessage());
        }
        handler.onFailure(-1, null, null);
        return false;
    }
    
    /**
     * get the connection type 
     * @param context
     * @return string value of the connection type, if connection not available, then return null
     */
    @SuppressWarnings("deprecation")
	public String getConnectionType(Context context)
    {
    	 try
         {
             connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
             NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
             if (netInfo == null || !connectivityManager.getBackgroundDataSetting()) 
             {
            	    return null;
             }
             else
             {
            	// Only update if WiFi or 3G is connected and not roaming
            	 int netType = netInfo.getType();
            	 if(netType == ConnectivityManager.TYPE_WIFI)
            	 {
            		 return "WiFi";
            	 }
            	 if(netType == ConnectivityManager.TYPE_MOBILE)
            	 {
            		 return "Mobile";
            	 }
            	 else
            	 {
            		 return null;
            	 }
             }

         }
    	 catch (Exception e)
         {
             error("DetectConnectivity Exception: " + e.getMessage());
             return null;
         }
    }
    
}

