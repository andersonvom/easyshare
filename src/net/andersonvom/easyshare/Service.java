package net.andersonvom.easyshare;

import android.os.Bundle;

public class Service
{
	private Long id;
	private String name;
	private String email;
	
	public Service()
	{
		this.id = 0L;
		this.name  = "";
		this.email = "";
	}
	
	public Service(String name, String email)
	{
		this.name  = name;
		this.email = email;
	}
	
	public boolean isValid()
	{
		return ( this.name != "" && this.email != "" );
	}
	
	public Bundle toBundle()
	{
    	Bundle bundle = new Bundle();
    	bundle.putString(ServiceDbAdapter.KEY_NAME,  getName());
    	bundle.putString(ServiceDbAdapter.KEY_EMAIL, getEmail());
    	bundle.putLong(ServiceDbAdapter.KEY_ID, getId());
		
    	return bundle;
	}
	
	public static Service fromBundle(Bundle bundle)
	{
		Service service = null;
		
		if (bundle != null)
		{
			service = new Service();
			service.setId( bundle.getLong(ServiceDbAdapter.KEY_ID) );
			service.setName( bundle.getString(ServiceDbAdapter.KEY_NAME) );
			service.setEmail( bundle.getString(ServiceDbAdapter.KEY_EMAIL) );
		}
		
		return service;
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		if (id != null) this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		if (name != null) this.name = name;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		if (email != null) this.email = email;
	}
	

}
