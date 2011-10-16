package net.andersonvom.easyshare;

public class Service
{
	private long id;
	private String name;
	private String email;
	
	public Service()
	{
		this.name  = "";
		this.email = "";
	}
	
	public Service(String name, String email)
	{
		this.name  = name;
		this.email = email;
	}
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
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
		this.name = name;
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
		this.email = email;
	}
	

}
