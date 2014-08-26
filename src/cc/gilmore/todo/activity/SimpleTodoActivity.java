package cc.gilmore.todo.activity;

import java.util.List;
 

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
 
import cc.gilmore.todo.DraggableListView;
import cc.gilmore.todo.R;
import cc.gilmore.todo.TodoArrayAdapter;
import cc.gilmore.todo.Todo;
import cc.gilmore.todo.persistence.TodoProvider;
 
public class SimpleTodoActivity extends Activity {
	public static final String APP_TAG = "cc.gilmore.todo.simple-todos";
	private DraggableListView todoListView;
	private Button btNewTask;
	private EditText etNewTask;
	private TodoProvider provider;
 
	public SimpleTodoActivity() {
		
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_main);
		getWindow().getDecorView().setBackgroundColor(Color.BLACK);
		
		provider = new TodoProvider(this);
		todoListView = (DraggableListView) findViewById(R.id.todoListView);
		btNewTask = (Button) findViewById(R.id.btNewTask);
		etNewTask = (EditText) findViewById(R.id.etNewTask);
		btNewTask.setOnClickListener(handleNewTaskEvent);
		
		renderTodos();
	}
 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * renders the task list
	 */
	private void renderTodos() {
		
		List<Todo> todos = provider.findAll();
		if (!todos.isEmpty()) {
			Log.d(APP_TAG, String.format("%d beans found", todos.size()));

	        TodoArrayAdapter adapter = new TodoArrayAdapter(this, R.layout.text_view, R.id.tvViewRow, todos);
	        todoListView.setTodoList(todos);
	        todoListView.setAdapter(adapter);
	        todoListView.setChoiceMode(DraggableListView.CHOICE_MODE_SINGLE);
			todoListView.setBackgroundColor(Color.BLACK);
		} else {
			Log.d(APP_TAG, "no tasks found");
		}
	}
	
	private OnClickListener handleNewTaskEvent = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Log.d(APP_TAG, "add task click received");
			String todoText = etNewTask.getText().toString();
			provider.addTask(todoText);
			etNewTask.setText("");
			renderTodos();
			Intent i = new Intent ("cc.gilmore.todo.persistence.BackgroundConnectionService");
	        i.putExtra("todoText", todoText);
	        view.getContext().startService(i);

		}
	};
	
    public void doneClickHandler(View v) 
    {
    	RelativeLayout layout = (RelativeLayout)v.getParent();
    	TextView textView = (TextView)layout.getChildAt(0);
		Log.d(APP_TAG, "marking task done: " + textView.getText().toString());
		provider.deleteTask(textView.getText().toString());
		renderTodos();
    }
}
