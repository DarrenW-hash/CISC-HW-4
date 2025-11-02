
public class Depositors {
	
	private Name name;
	private String SSnumber;
	
	public Depositors() {
		
	}
	
	public Depositors(Name name, String SSnumber) {
		this.name = name;
		this.SSnumber = SSnumber;
	}
	
	//copy constructor
	public Depositors(Depositors other) {
		this.name = other.name;
		this.SSnumber = other.SSnumber;
	}
	
	//getters
	public Name getNames() {
		return name;
	}
	public String getSSnumber() {
		return SSnumber;
	}
	
	//setters
	public void setSSnumber(String _SSnumber) {
		SSnumber = _SSnumber;
	}
	
	//toString()	
	@Override
	public String toString()	{
		return name.toString() + " " + SSnumber;
	}
	
	//eqauls method 
	@Override
	public boolean equals(Object obj)	{
		if(this == obj) {
			return true;
		}
		if(obj == null)	{
			return false;
		}
		
		Depositors other  = (Depositors) obj;
		
		return this.name.equals(other.name) && this.SSnumber.equals(other.SSnumber);
	}
}
