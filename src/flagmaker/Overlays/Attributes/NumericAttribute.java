package flagmaker.Overlays.Attributes;

public abstract class NumericAttribute<T> extends Attribute<T>
{
	public int Maximum;
	public boolean UseMaxX;
	
	public NumericAttribute(String name, int maximum, boolean useMaxX)
	{
		super(name);
		Maximum = maximum;
		UseMaxX = useMaxX;
	}
}
