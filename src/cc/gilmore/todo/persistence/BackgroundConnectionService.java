package cc.gilmore.todo.persistence;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.IntentService;
import android.content.Intent;

public class BackgroundConnectionService extends IntentService {

    public BackgroundConnectionService() {
        // Need this to name the service
        super ("ConnectionServices");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
		String todoText = intent.getStringExtra("todoText");
		if(todoText == null || todoText.equals(""))
			return;
		
		List<NameValuePair> params = new LinkedList<NameValuePair>();
	    params.add(new BasicNameValuePair("text", todoText));
	    String paramString = URLEncodedUtils.format(params, "utf-8");
	    
		final HttpClient httpclient = new DefaultHttpClient();
		final HttpGet httpget = new HttpGet("http://www.gilmore.cc/2Du/insertTodo.php?"+paramString);
		
		HttpResponse response = null;
		try {
			response = httpclient.execute(httpget);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
//		finally {
//			System.out.println(response);
//		}
    }
}
