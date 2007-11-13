public class Message implements java.io.Serializable
{
	public int 	  			_header;
	public String 			_username;
	public String 			_destination;
	public String 			_message;
	public String 			_host;
	public User 			_user;
	public String 			_data;

	public Message()
	{
	}

	public Message(int header)
	{
		_header = header;
	}

	public Message(int header,String message)
	{
		_header = header;
		_message=message;
	}

	public String getMessage()
	{
		return _message;
	}
}