package cc.gilmore.todo;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.HashMap;
import java.util.List;

public class TodoArrayAdapter extends ArrayAdapter<Todo> {

    final int INVALID_ID = -1;

    HashMap<Todo, Integer> mIdMap = new HashMap<Todo, Integer>();

    public TodoArrayAdapter(Context context, int resource, int textViewResourceId, List<Todo> objects) {
        super(context, resource, textViewResourceId, objects);
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
    }

    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        Todo item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
