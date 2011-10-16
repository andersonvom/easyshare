package net.andersonvom.easyshare;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class EasyShareActivity extends ListActivity
{
	
	private ServiceDbAdapter dbHelper;
	public static final int MENU_ADD_ID = Menu.FIRST;
	public static final int MENU_DEL_ID = Menu.FIRST+1;
	
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
    
    private void createService()
    {
    	Service service = new Service();
    	service.setName("Teste");
    	service.setEmail("test@example.com");
    	dbHelper.create(service);
    	fillServiceList();
    }
    
    private void fillServiceList()
    {
    	Cursor cursor = dbHelper.fetch();
    	startManagingCursor(cursor);
    	
    	String[] from = new String[] { ServiceDbAdapter.KEY_NAME };
    	int[] to = new int[] { android.R.id.text1 };
    	
    	SimpleCursorAdapter services =
    		new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, from, to);
    	setListAdapter(services);
    }
}
