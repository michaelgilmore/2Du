package cc.gilmore.todo.persistence;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
 












import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
import cc.gilmore.todo.Todo;
import cc.gilmore.todo.activity.SimpleTodoActivity;
 
public class TodoProvider {
	private static final String DB_NAME = "tasks";
	private static final String TABLE_NAME = "tasks";
	private static final int DB_VERSION = 2;
	private static final String DB_CREATE_QUERY = "CREATE TABLE " + TABLE_NAME
			+ " ("
			+ "id integer primary key autoincrement,"
			+ "title text not null,"
			+ "created_date date"
			+ ");";
 
	private SQLiteDatabase storage;
	private SQLiteOpenHelper helper;
 
	public TodoProvider(Context ctx) {
		helper = new SQLiteOpenHelper(ctx, DB_NAME, null, DB_VERSION) {
			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion,
					int newVersion) {
				db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
				onCreate(db);
			}
 
			@Override
			public void onCreate(SQLiteDatabase db) {
				db.execSQL(DB_CREATE_QUERY);
			}
		};
		storage = helper.getWritableDatabase();
	}
 
	public List<Todo> findAll() {
		Log.d(SimpleTodoActivity.APP_TAG, "findAll triggered");
		List<Todo> tasks = new ArrayList<Todo>();
		Cursor c = storage.query(TABLE_NAME, new String[] { "title", "created_date" }, null,
				null, null, null, null);
		if (c != null) {
			c.moveToFirst();
			String title;
			String created_date;
			while (c.isAfterLast() == false) {
				title = c.getString(0);
				created_date = c.getString(1);
				Todo todo = new Todo(title, created_date);
				tasks.add(todo);
				c.moveToNext();
			}
			c.close();
		}
		return tasks;
	}
 
	public void addTask(String title) {
		ContentValues data = new ContentValues();
		data.put("title", title);
		storage.insert(TABLE_NAME, null, data);
		
		//addTaskOnline(title);
	}
 
	private void addTaskOnline(String title) {
		final HttpClient httpclient = new DefaultHttpClient();
		final HttpPost httppost = new HttpPost("www.gilmore.cc/2Du/insertTodo.php");
		final ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("text", title));

		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}
		
		HttpResponse response = null;
		try {
			response = httpclient.execute(httppost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println(httppost);
		}
	}

	public void deleteTask(String title) {
		storage.delete(TABLE_NAME, "title='" + title + "'", null);
	}
 
	public void deleteTask(long id) {
		storage.delete(TABLE_NAME, "id=" + id, null);
	}
}
