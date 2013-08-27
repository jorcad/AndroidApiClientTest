package ca.sukhni.net.android.api.client.test;

/**
 * A simplified alternative to OnClickListener's. Mainly used for interacting
 * with UIDialogs and APIClient procedures.
 * 
 * @author kckrinke
 * 
 */
public abstract class CallbackHandler
{
    /**
	 * A simple means of passing objects that must remain for the lifetime of the CallbackHandler.
	 */
    private Object base = null;

    /**
     * Constructor that accepts an arbitrary object called the "Base" object.
     * This object serves no internal purpose other than offering a means of
     * keeping an object available for the lifetime of the callback handler.
     * 
     * @param obj
     */
    public CallbackHandler(Object obj)
    {
        base = obj;
    }

    /**
     * Returns the base object (can be null).
     * 
     * @return
     */
    public Object getBaseObject()
    {
        return base;
    }

    /**
     * Override this method to act upon successful conditions. The arguments
     * given can be null and the data variable is an arbitrary object type that
     * differs depending on the use case of the callback handler. For example,
     * status and response are used with the APIClient for HTTP Status codes and
     * the actual response from the server. The data object can be null in this
     * case but if it's not null, it'll be the result of parsing the XML for the
     * given API procedure call. Remember to re-cast the data appropriately for
     * the different API procedure calls!
     * 
     * @param status
     * @param response
     * @param data
     */
    public void onSuccess(int status, String response, Object data)
    {
    }
    
    /**
     * Override this method to act upon failed conditions. The arguments given
     * can be null and the data variable is an arbitrary object type that
     * differs depending on the use case of the callback handler. For example,
     * status and response are used with the APIClient for HTTP Status codes and
     * the actual response from the server. More often then not, the data
     * argument will be null.
     * 
     * @param status
     * @param message
     * @param data
     */
    public void onFailure(int status, String message, Object data)
    {
    }
}
