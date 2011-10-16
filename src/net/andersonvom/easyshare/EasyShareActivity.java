package net.andersonvom.easyshare;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;

public class EasyShareActivity extends ListActivity
{
	
	private ServiceDbAdapter dbHelper;
	public static final int MENU_ADD_ID = Menu.FIRST;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        dbHelper = new ServiceDbAdapter(this);
        dbHelper.open();
        fillServiceList();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	boolean result = super.onCreateOptionsMenu(menu);
    	menu.add(0, MENU_ADD_ID, 0, R.string.add_service);
    	
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
