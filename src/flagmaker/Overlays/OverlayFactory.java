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
		_typeMap.put("checkerboard", "flagmaker.Overlays.OverlayTypes.OverlayCheckerboard");
		_typeMap.put("cross", "flagmaker.Overlays.OverlayTypes.OverlayCross");
		_typeMap.put("diamond", "flagmaker.Overlays.OverlayTypes.ShapeTypes.OverlayDiamond");
		_typeMap.put("ellipse", "flagmaker.Overlays.OverlayTypes.ShapeTypes.OverlayEllipse");
		_typeMap.put("fimbriation backward", "flagmaker.Overlays.OverlayTypes.OverlayFimbriationBackward");
		_typeMap.put("fimbriation forward", "flagmaker.Overlays.OverlayTypes.OverlayFimbriationForward");
		_typeMap.put("half ellipse", "flagmaker.Overlays.OverlayTypes.OverlayHalfEllipse");
		_typeMap.put("half saltire", "flagmaker.Overlays.OverlayTypes.OverlayHalfSaltire");
	}
	
	public static Overlay[] GetShapes()
	{
		return new Overlay[]
		{
			new OverlayBorder(0, 0),
			new OverlayBox(0, 0),
			new OverlayCheckerboard(0, 0),
			new OverlayCross(0, 0),
			new OverlayDiamond(0, 0),
			new OverlayEllipse(0, 0),
			new OverlayFimbriationBackward(0, 0),
			new OverlayFimbriationForward(0, 0),
			new OverlayHalfEllipse(0, 0),
			new OverlayHalfSaltire(0, 0)
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
		return new Overlay[]
		{
			new OverlayFlag(0, 0),
			new OverlayImage(0, 0)
		};
	}
	
	public static Overlay GetInstanceByLongName(String name, int defaultMaximumX, int defaultMaximumY)
	{
		try
		{
			Class c = Class.forName(name);
			return GetInstance(c, defaultMaximumX, defaultMaximumY);
		}
		catch (Exception e)
		{
			return null;
		}		
	}

	public static Overlay GetInstanceByShortName(String name, int defaultMaximumX, int defaultMaximumY)
	{
		try
		{
			Class c = Class.forName(_typeMap.get(name));
			return GetInstance(c, defaultMaximumX, defaultMaximumY);
		}
		catch (Exception e)
		{
			return null;
		}		
	}
	
	private static Overlay GetInstance(Class c, int defaultMaximumX, int defaultMaximumY)
	{
		try
		{
			Constructor<Overlay> constructor = c.getDeclaredConstructor(new Class[] { int.class, int.class });
			Overlay o = constructor.newInstance(defaultMaximumX, defaultMaximumY);
			return o;
		}
		catch (Exception ex)
		{
			return null;
		}
	}
}
