public class User implements java.io.Serializable
{
	private String userName;
	public String hostname;
	public int isOnline;
	public boolean isConference=false;

	public User(String userName)
	{
		this.hostname=hostname;
		this.userName=userName;
		this.isOnline = isOnline;
	}

	public String toString() {
		return userName;
	}
}