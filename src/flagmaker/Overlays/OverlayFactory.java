package flagmaker.Overlays;

import flagmaker.Overlays.OverlayTypes.*;
import flagmaker.Overlays.OverlayTypes.ShapeTypes.*;
import java.lang.reflect.Constructor;
import java.util.HashMap;

public class OverlayFactory
{
	private static HashMap<String, String> _typeMap;
	
	public static void SetUpTypeMap()
	{
		_typeMap = new HashMap<>();
		_typeMap.put("border", "flagmaker.Overlays.OverlayTypes.OverlayBorder");
		_typeMap.put("box", "flagmaker.Overlays.OverlayTypes.ShapeTypes.OverlayBox");
		_typeMap.put("checkerboard", "flagmaker.Overlays.OverlayTypes.OverlayCheckerboard");;
		_typeMap.put("diamond", "flagmaker.Overlays.OverlayTypes.ShapeTypes.OverlayDiamond");
		_typeMap.put("ellipse", "flagmaker.Overlays.OverlayTypes.ShapeTypes.OverlayEllipse");
	}
	
	public static Overlay[] GetShapes()
	{
		return new Overlay[]
		{
			new OverlayBorder(0, 0),
			new OverlayBox(0, 0),
			new OverlayCheckerboard(0, 0),
			new OverlayDiamond(0, 0),
			new OverlayEllipse(0, 0)
		};
	}
	
	public static Overlay[] GetEmblems()
	{
		return new Overlay[]{};
	}
	
	public static Overlay[] GetCustom()
	{
		return new Overlay[]{};
	}
	
	public static Overlay[] GetSpecial()
	{
		return new Overlay[]{};
	}

	public static Overlay GetInstance(String name, int _defaultMaximumX, int _defaultMaximumY)
	{
		try
		{
			Class c = Class.forName(_typeMap.get(name));
			Constructor<Overlay> constructor = c.getDeclaredConstructor(new Class[] { int.class, int.class });
			Overlay o = constructor.newInstance(_defaultMaximumX, _defaultMaximumY);
			return o;
		}
		catch (Exception ex)
		{
			return null;
		}
	}
}
