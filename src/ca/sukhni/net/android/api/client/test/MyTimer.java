package ca.sukhni.net.android.api.client.test;

public class MyTimer 
{
	public final static String					TAG 				= MyTimer.class.getSimpleName();
	private long 								startTime 			= 0; 
	private long 								stopTime 			= 0;
	private long								procTime			= 0;
	private boolean								timerStarted		= false;
	
	private static long							sStartTime			= 0;
	private static long							sSTopTime			= 0;
	private static long							sProcTime			= 0;
	
	public static MyTimer						sTimer1				= new MyTimer();
	public static MyTimer						sTimer2				= new MyTimer();
	public static MyTimer						sTimer3				= new MyTimer();
	
	public MyTimer()
	{
		
	}
	public void start()
	{
		startTime = System.currentTimeMillis();
		timerStarted = true;
		Logger.verbose(TAG +": start(), Start Time= " + startTime);
	}
	public void stop()
	{
		if(timerStarted)
		{
			stopTime = System.currentTimeMillis();
			procTime = stopTime - startTime;
		}
		else
		{
			procTime = 0;
		}
		timerStarted = false;
		Logger.verbose(TAG +": stop(), Start Time= " + startTime);
		Logger.verbose(TAG +": stop(), Stop Time= " + stopTime);
		Logger.verbose(TAG + ": Difference = " + procTime);
	}
	
	public static void _start()
	{
		sStartTime = System.currentTimeMillis();
		Logger.verbose(TAG +": start(), Current Time= " + sStartTime);
	}
	
	public static void _stop()
	{
		sSTopTime = System.currentTimeMillis();
		sProcTime = sSTopTime - sStartTime;
		
		Logger.verbose(TAG +": _stop(), Start Time= " + sStartTime);
		Logger.verbose(TAG +": _stop(), Stop Time= " + sSTopTime);
		Logger.verbose(TAG + ": Difference = " + sProcTime);
	}
	
	public long getProcessTime()
	{
		return procTime;
	}
	
	public static long _getProcessTime()
	{
		return sProcTime;
	}
}
