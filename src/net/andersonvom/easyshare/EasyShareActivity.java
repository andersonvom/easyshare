package net.andersonvom.easyshare;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class EasyShareActivity extends ListActivity
{
	
	private ServiceDbAdapter dbHelper;
	private Cursor servicesCursor;
	
	private static final int MENU_ADD_ID = Menu.FIRST;
	private static final int MENU_DEL_ID = Menu.FIRST+1;
	
	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_EDIT   = 1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        dbHelper = new ServiceDbAdapter(this);
        dbHelper.open();
        fillServiceList();
        
        registerForContextMenu(getListView());
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo)
    {
    	super.onCreateContextMenu(menu, view, menuInfo);
    	menu.add(0, MENU_DEL_ID, 0, R.string.delete);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
    	switch(item.getItemId())
    	{
    		case MENU_DEL_ID:
    			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    			dbHelper.delete(info.id);
    			fillServiceList();
    			return true;
    	}
    	return super.onContextItemSelected(item);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	boolean result = super.onCreateOptionsMenu(menu);
    	menu.add(0, MENU_ADD_ID, 0, R.string.add);
    	
    	return result;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch (item.getItemId())
    	{
    		case MENU_ADD_ID:
    			createService();
    			return true;
    	}
    	
    	return super.onOptionsItemSelected(item);
    }
    
	@Override
	protected void onListItemClick(ListView list, View view, int position, long id)
	{
		super.onListItemClick(list, view, position, id);
		
		Cursor c = servicesCursor;		// This is only for optimization purposes, since accessing a local
		c.moveToPosition(position);		// variable is much faster than accessing a field in Dalvik
		
		Intent editIntent = new Intent(this, ServiceEdit.class);
		editIntent.putExtra(ServiceDbAdapter.KEY_ID,    id);
		editIntent.putExtra(ServiceDbAdapter.KEY_NAME,  c.getString(c.getColumnIndexOrThrow(ServiceDbAdapter.KEY_NAME)));
		editIntent.putExtra(ServiceDbAdapter.KEY_EMAIL, c.getString(c.getColumnIndexOrThrow(ServiceDbAdapter.KEY_EMAIL)));
		
		startActivityForResult(editIntent, ACTIVITY_EDIT);		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		super.onActivityResult(requestCode, resultCode, intent);
		if (intent == null) return;
		
		Bundle extras = intent.getExtras();
		Service service = Service.fromBundle(extras);

		if (service != null)
		{
			switch (requestCode)
			{
				case ACTIVITY_CREATE:
					dbHelper.create(service);
					break;
				case ACTIVITY_EDIT:
					if (service.getId() != 0) dbHelper.update(service);
					break;
			}
			fillServiceList();
		}
	}

	private void createService()
    {
    	Intent createIntent = new Intent(this, ServiceEdit.class);
    	startActivityForResult(createIntent, ACTIVITY_CREATE);
    }
    
    private void fillServiceList()
    {
    	servicesCursor = dbHelper.fetch();
    	startManagingCursor(servicesCursor);
    	
    	String[] fieldNames = new String[] { ServiceDbAdapter.KEY_NAME };
    	int[] resourceIds = new int[] { android.R.id.text1 };
    	
    	SimpleCursorAdapter services =
    		new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, servicesCursor, fieldNames, resourceIds);
    	setListAdapter(services);
    }
}
