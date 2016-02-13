package systemtest.test.com.test.utility;

import android.util.Log;

public class DLog {
	
	private static boolean isValid(String str)
	{
		if(str==null)
			return false;
		return true;
	}
	
	public static void v(String Tag, String log)
	{		
		if(Constants.DEBUG && isValid(Tag) && isValid(log))
			Log.v(Tag, log);
	}
	
	public static void v(String Tag, String log, Throwable e)
	{		
		if(Constants.DEBUG && isValid(Tag) && isValid(log))
			Log.v(Tag, log, e);
	}
	
	public static void d(String Tag, String log)
	{		
		if(Constants.DEBUG && isValid(Tag) && isValid(log))
			Log.d(Tag, log);
	}
	
	public static void d(String Tag, String log, Throwable e)
	{		
		if(Constants.DEBUG && isValid(Tag) && isValid(log))
			Log.d(Tag, log, e);
	}
	
	public static void e(String Tag, String log)
	{		
		if(Constants.DEBUG && isValid(Tag) && isValid(log))
			Log.e(Tag, log);
	}
	
	public static void e(String Tag, String log, Throwable e)
	{		
		if(Constants.DEBUG && isValid(Tag) && isValid(log))
			Log.e(Tag, log, e);
	}
	
	public static void i(String Tag, String log)
	{		
		if(Constants.DEBUG && isValid(Tag) && isValid(log))
			Log.i(Tag, log);
	}
		
	public static void i(String Tag, String log, Throwable e)
	{		
		if(Constants.DEBUG && isValid(Tag) && isValid(log))
			Log.i(Tag, log, e);
	}
	
	public static void w(String Tag, String log)
	{		
		if(Constants.DEBUG && isValid(Tag) && isValid(log))
			Log.w(Tag, log);
	}
	
	public static void w(String Tag, String log, Throwable e)
	{		
		if(Constants.DEBUG && isValid(Tag) && isValid(log))
			Log.w(Tag, log, e);
	}
}
