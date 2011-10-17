package net.andersonvom.easyshare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ServiceEdit extends Activity
{

	private EditText nameEdit;
	private EditText emailEdit;
	private Service service = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_service);
		setTitle(R.string.edit);
		
		nameEdit  = (EditText) findViewById(R.id.name);
		emailEdit = (EditText) findViewById(R.id.email);
		Button okButton = (Button) findViewById(R.id.ok);
		
		Bundle extras = getIntent().getExtras();
		service = createServiceFromExtras(extras);
		if (service != null)
		{
			nameEdit.setText(service.getName());
			emailEdit.setText(service.getEmail());
		}
		
		okButton.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View view)
		    {
		    	service.setName(nameEdit.getText().toString());
		    	service.setEmail(emailEdit.getText().toString());
		    	
		    	Intent editIntent = new Intent();
		    	editIntent.putExtras(service.toBundle());
		    	setResult(RESULT_OK, editIntent);
		    	finish();
		    }
		});
		
	}
	
	private Service createServiceFromExtras(Bundle extras)
	{
		Service service = new Service();
		
		if (extras != null)
		{
			service.setId( extras.getLong(ServiceDbAdapter.KEY_ID) );
			service.setName( extras.getString(ServiceDbAdapter.KEY_NAME) );
			service.setEmail( extras.getString(ServiceDbAdapter.KEY_EMAIL) );
		}
		
		return service;
	}
	
	
}
