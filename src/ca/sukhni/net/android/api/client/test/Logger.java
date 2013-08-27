package ca.sukhni.net.android.api.client.test;

import java.text.DecimalFormat;
import android.annotation.SuppressLint;
import android.os.Debug;
import android.util.Log;

public class Logger 
{
	public static final String						TAG									= Logger.class.getSimpleName();
	/**
     * Used internally to log debug messages. Basically a shortcut.
     * 
     * @param msg
     */
    final public static void debug(String msg)
    {
        if (Constants.DEBUG) Log.d(Constants.LOG_TAG, msg);
    }

    /**
     * Same as debug(String) but for logging errors.
     * 
     * @param msg
     */
    final public static void error(String msg)
    {
    	if (Constants.DEBUG) Log.e(Constants.LOG_TAG, msg);
    }
    /**
     * Used internally to verbose debug messages. Basically a shortcut.
     * 
     * @param msg
     */
    final public static void verbose(String msg)
    {
        if (Constants.DEBUG) Log.v(Constants.LOG_TAG, msg);
    }
    /**
     * Used internally to information debug messages. Basically a shortcut.
     * 
     * @param msg
     */
    final public static void info(String msg)
    {
        if (Constants.DEBUG) Log.i(Constants.LOG_TAG, msg);
    }
    /**
     * 
     * @param msg
     * @param tr
     */
    final public static void error(String msg,Throwable tr)
    {
        if (Constants.DEBUG && tr!=null) 
        {
        	Log.e(Constants.LOG_TAG, msg,tr);
        }
    }
    /**
     * output the current used memory used by the app and native heap:
     * @param clazz
     */
    @SuppressLint("UseValueOf")
	public static void logHeap(Class<?> clazz) 
    {
    	if (Constants.ONLINE_VERSION)
    		return;
    	try
    	{
	        Double allocated = new Double(Debug.getNativeHeapAllocatedSize())/new Double((1048576));
	        Double available = new Double(Debug.getNativeHeapSize())/1048576.0;
	        Double free = new Double(Debug.getNativeHeapFreeSize())/1048576.0;
	        DecimalFormat df = new DecimalFormat();
	        df.setMaximumFractionDigits(2);
	        df.setMinimumFractionDigits(2);
	
	        info("<debug=====================================================================");
	        info("debug.heap native: allocated " + df.format(allocated) + "MB of " + df.format(available) + "MB (" + df.format(free) + "MB free) in [" + clazz.getName().replaceAll("com.myapp.android.","") + "]");
	        info("debug.memory: allocated: " + df.format(new Double(Runtime.getRuntime().totalMemory()/1048576)) + "MB of " + df.format(new Double(Runtime.getRuntime().maxMemory()/1048576))+ "MB (" + df.format(new Double(Runtime.getRuntime().freeMemory()/1048576)) +"MB free)");
	        info("=====================================================================debug>");
	        System.gc();
	        System.gc();
    	}
    	catch(Exception ex){}
    }
    
    public static void printStackTrace(Exception ex)
    {
    	if(Constants.DEBUG) ex.printStackTrace();
    }
    
}
