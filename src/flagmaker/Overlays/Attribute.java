package flagmaker.Overlays;

public class Attribute
{
	public String Name;
	public boolean IsDiscrete;
	public double Value;
	public boolean UseMaxX;
	
	public Attribute(String name, boolean isDiscrete, double initialValue, boolean useMaxX)
	{
		Name = name;
		IsDiscrete = isDiscrete;
		Value = initialValue;
		UseMaxX = useMaxX;
	}
	
	public String ToString()
	{
		return Name + ": " + Value;
	}
}
