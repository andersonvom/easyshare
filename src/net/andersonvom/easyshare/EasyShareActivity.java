package net.andersonvom.easyshare;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EasyShareActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ListView service_list = (ListView) findViewById(R.id.service_list);
        service_list.setAdapter(
        		new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, SERVICES)
        );
    }
    
    static final String[] SERVICES = new String[] {
    	"Facebook",
    	"Twitter",
    	"Imgur",
    	"Picasa",
    	"TwitPic"
    };
}