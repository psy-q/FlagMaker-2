package flagmaker.Overlays;

import flagmaker.Overlays.OverlayTypes.SpecialTypes.OverlayImage;
import flagmaker.Overlays.OverlayTypes.SpecialTypes.OverlayFlag;
import flagmaker.Flag;
import flagmaker.Overlays.OverlayTypes.*;
import flagmaker.Overlays.OverlayTypes.PathTypes.*;
import flagmaker.Overlays.OverlayTypes.RepeaterTypes.*;
import flagmaker.Overlays.OverlayTypes.ShapeTypes.*;
import java.io.File;
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
		_typeMap.put("line", "flagmaker.Overlays.OverlayTypes.OverlayLine");
		_typeMap.put("line horizontal", "flagmaker.Overlays.OverlayTypes.OverlayLineHorizontal");
		_typeMap.put("line vertical", "flagmaker.Overlays.OverlayTypes.OverlayLineVertical");
		_typeMap.put("pall", "flagmaker.Overlays.OverlayTypes.OverlayPall");
		_typeMap.put("quadrilateral", "flagmaker.Overlays.OverlayTypes.OverlayQuadrilateral");
		_typeMap.put("rays", "flagmaker.Overlays.OverlayTypes.OverlayRays");
		_typeMap.put("ring", "flagmaker.Overlays.OverlayTypes.OverlayRing");
		_typeMap.put("saltire", "flagmaker.Overlays.OverlayTypes.OverlaySaltire");
		_typeMap.put("triangle", "flagmaker.Overlays.OverlayTypes.OverlayTriangle");
				
		_typeMap.put("flag", "flagmaker.Overlays.OverlayTypes.SpecialTypes.OverlayFlag");
		_typeMap.put("image", "flagmaker.Overlays.OverlayTypes.SpecialTypes.OverlayImage");
		
		_typeMap.put("repeater lateral", "flagmaker.Overlays.OverlayTypes.RepeaterTypes.OverlayRepeaterLateral");
		_typeMap.put("repeater radial", "flagmaker.Overlays.OverlayTypes.RepeaterTypes.OverlayRepeaterRadial");
		
		_typeMap.put("anchor", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayAnchor");
		_typeMap.put("angola", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayAngola");
		_typeMap.put("bolnisi cross", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayBolnisiCross");
		_typeMap.put("branches", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayBranches");
		_typeMap.put("cedar", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayCedar");
		_typeMap.put("chakra", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayChakra");
		_typeMap.put("coronet", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayCoronet");
		_typeMap.put("cpusa", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayCpusa");
		_typeMap.put("crescent", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayCrescent");
		_typeMap.put("crown", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayCrown");
		_typeMap.put("eagle", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayEagle");
		_typeMap.put("maple leaf", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayMapleLeaf");
		_typeMap.put("maltese cross", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayMalteseCross");
		_typeMap.put("laurel", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayLaurel");
		_typeMap.put("star", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayStar");
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
			new OverlayHalfSaltire(0, 0),
			new OverlayLine(0, 0),
			new OverlayLineHorizontal(0, 0),
			new OverlayLineVertical(0, 0),
			new OverlayPall(0, 0),
			new OverlayQuadrilateral(0, 0),
			new OverlayRays(0, 0),
			new OverlayRing(0, 0),
			new OverlaySaltire(0, 0),
			new OverlayTriangle(0, 0)
		};
	}
	
	public static Overlay[] GetEmblems()
	{
		return new Overlay[]
		{
			new OverlayAnchor(0, 0),
			new OverlayAngola(0, 0),
			new OverlayBolnisiCross(0, 0),
			new OverlayBranches(0, 0),
			new OverlayCedar(0, 0),
			new OverlayChakra(0, 0),
			new OverlayCoronet(0, 0),
			new OverlayCpusa(0, 0),
			new OverlayCrescent(0, 0),
			new OverlayCrown(0, 0),
			new OverlayEagle(0, 0),
			new OverlayMapleLeaf(0, 0),
			new OverlayMalteseCross(0, 0),
			new OverlayLaurel(0, 0),
			new OverlayStar(0, 0)
		};
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
			new OverlayImage(0, 0),
			new OverlayRepeaterLateral(0, 0),
			new OverlayRepeaterRadial(0, 0)
		};
	}
	
	public static Overlay GetFlagInstance(File path, int maximumX, int maximumY) throws Exception
	{
		return new OverlayFlag(Flag.LoadFromFile(path), path, maximumX, maximumY);
	}
	
	public static Overlay GetImageInstance(File path, int maximumX, int maximumY)
	{
		return new OverlayImage(path, maximumX, maximumY);
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
