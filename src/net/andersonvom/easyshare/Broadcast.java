package net.andersonvom.easyshare;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

public class Broadcast extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		String addresses = getAddresses();
		
		Intent i = new Intent(Intent.ACTION_SEND);
		i.putExtras(extras);
		i.putExtra("address", addresses);
		i.putExtra("sms_body", addresses);
		//i.putExtra(Intent.EXTRA_STREAM, audioUri);
		i.setType("text/plain");
		startActivity(i);		
	}
	
	private String getAddresses()
	{
		ServiceDbAdapter dbHelper = new ServiceDbAdapter(this);
        dbHelper.open();
    	Cursor services = dbHelper.fetch();
		int emailColumn = services.getColumnIndex(ServiceDbAdapter.KEY_EMAIL);
    	
    	String addresses = "";
    	if (services.moveToFirst())
    	{
    		do
    		{
    			addresses += services.getString(emailColumn) + ", ";
    		} while ( services.moveToNext() );
    	}
    	dbHelper.close();
    	
    	return addresses;
	}
	
}
