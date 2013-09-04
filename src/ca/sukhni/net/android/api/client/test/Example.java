package ca.sukhni.net.android.api.client.test;

import android.util.Log;
import ca.sukhni.net.android.api.client.ApiClient;
import ca.sukhni.net.android.api.client.ApiClientBuilder;
import ca.sukhni.net.android.api.client.ApiClientHandler;
import ca.sukhni.net.android.api.client.ExceptionStatus;
import ca.sukhni.net.android.api.client.Method;
import ca.sukhni.net.android.api.client.ResponseEntity;
import ca.sukhni.net.android.api.client.Status;

public class Example
{

	public static void main(String[] args)
	{
		ApiClient apiClient = new ApiClientBuilder()
		.setBaseUri("http://localhost:8181")
		.addPath("WebServices")
		.addPath("rest")
		.addPath("accout")
		.addPath("create")
		.addParam("UserName", "user123")
		.addParam("FirstName", "Mike")
		.addParam("LastName", "Norm")
		.addParam("Password", "p@ssw0rd")
		.addParam("email", "mike@test.com")
		.addHeader("ContentType", "application/xml")
		.setTextContent("<Root><Test>this is test</Test></Root>")
		.setMethod(Method.POST)
		.setConnectionTimeout(6000)
		.setSocketTimeout(6000)
		.build();
		
		//execute and handle the result
		apiClient.executeOnAsyncTask(new ApiClientHandler()
		{
			
			@Override
			public void onInformational(Status status, String responseStatus, ResponseEntity entity)
			{
				Log.i("ApiClient", "Response Content code: " + status.code());
				Log.i("ApiClient", "Response Content Status: " + responseStatus);
				Log.i("ApiClient", "Response Content Lenght: " + entity.getContentLength());
				Log.i("ApiClient:", "Response Content as string: " + entity.getResponseContentAsString());
				
			}
			
			@Override
			public void onSuccessful(Status status, String responseStatus, ResponseEntity entity)
			{
				Log.v("ApiClient", "Response Content code: " + status.code());
				Log.v("ApiClient", "Response Content Status: " + responseStatus);
				Log.v("ApiClient", "Response Content Lenght: " + entity.getContentLength());
				Log.v("ApiClient:", "Response Content as string: " + entity.getResponseContentAsString());
				
			}
			
			@Override
			public void onClientError(Status status, String responseStatus, ResponseEntity entity)
			{
				Log.v("ApiClient", "Response Content code: " + status.code());
				Log.v("ApiClient", "Response Content Status: " + responseStatus);
				Log.v("ApiClient", "Response Content Lenght: " + entity.getContentLength());
				Log.v("ApiClient:", "Response Content as string: " + entity.getResponseContentAsString());
				
			}
			
			@Override
			public void onServerError(Status status, String responseStatus, ResponseEntity entity)
			{
				Log.e("ApiClient", "Response Content code: " + status.code());
				Log.e("ApiClient", "Response Content Status: " + responseStatus);
				Log.e("ApiClient", "Response Content Lenght: " + entity.getContentLength());
				Log.e("ApiClient:", "Response Content as string: " + entity.getResponseContentAsString());
				
			}
			
			@Override
			public void onRedirection(Status status, String responseStatus, ResponseEntity entity)
			{
				Log.i("ApiClient", "Response Content code: " + status.code());
				Log.i("ApiClient", "Response Content Status: " + responseStatus);
				Log.i("ApiClient", "Response Content Lenght: " + entity.getContentLength());
				Log.i("ApiClient:", "Response Content as string: " + entity.getResponseContentAsString());
				
			}
			
			@Override
			public void onException(ExceptionStatus exceptionStatus, Exception e)
			{
				Log.e("ApiClient", "Exception code: " + exceptionStatus.code());
				Log.e("ApiClient", "Exception: " + e.getMessage());
			}
			
		});
	}

}
