package flagmaker.Overlays;

import flagmaker.FileHandler;
import flagmaker.LocalizationHandler;
import flagmaker.MainWindowController;
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
	private static HashMap<String, OverlayPath> _customTypes;
	
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
		_typeMap.put("eagle american", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayEagleAmerican");
		_typeMap.put("egypt", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayEgypt");
		_typeMap.put("equatorial cross", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayEquatorialCross");
		_typeMap.put("ermine", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayErmine");
		_typeMap.put("fire", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayFire");
		_typeMap.put("flash", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayFlash");
		_typeMap.put("fleur de lis", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayFleurDeLis");
		_typeMap.put("forth international", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayForthInternational");
		_typeMap.put("hammer sickle", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayHammerSickle");
		_typeMap.put("hand", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayHand");
		_typeMap.put("harp", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayHarp");
		_typeMap.put("iran", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayIran");
		_typeMap.put("iron cross", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayIronCross");
		_typeMap.put("kangaroo", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayKangaroo");
		_typeMap.put("kiwi", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayKiwi");
		_typeMap.put("laurel", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayLaurel");
		_typeMap.put("maltese cross", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayMalteseCross");
		_typeMap.put("maple leaf", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayMapleLeaf");
		_typeMap.put("mozambique", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayMozambique");
		_typeMap.put("papua new guinea", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayPapuaNewGuinea");
		_typeMap.put("parteiadler", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayParteiadler");
		_typeMap.put("pentagram", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayPentagram");
		_typeMap.put("reichsadler", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayReichsadler");
		_typeMap.put("shahadah", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayShahadah");
		_typeMap.put("shield", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayShield");
		_typeMap.put("sikh", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlaySikh");
		_typeMap.put("snake", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlaySnake");
		_typeMap.put("springbok", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlaySpringbok");
		_typeMap.put("star", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayStar");
		_typeMap.put("star eight", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayStarEight");
		_typeMap.put("star four", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayStarFour");
		_typeMap.put("star of david", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayStarOfDavid");
		_typeMap.put("star seven", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayStarSeven");
		_typeMap.put("star six", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayStarSix");
		_typeMap.put("sun", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlaySun");
		_typeMap.put("swastika", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlaySwastika");
		_typeMap.put("sword", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlaySword");
		_typeMap.put("takbir", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayTakbir");
		_typeMap.put("tree", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayTree");
		_typeMap.put("trident", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayTrident");
		_typeMap.put("triskele", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayTriskele");
		_typeMap.put("wave", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayWave");
		_typeMap.put("yang", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayYang");
		_typeMap.put("yin", "flagmaker.Overlays.OverlayTypes.PathTypes.OverlayYin");
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
			new OverlayEagleAmerican(0, 0),
			new OverlayEgypt(0, 0),
			new OverlayEquatorialCross(0, 0),
			new OverlayErmine(0, 0),
			new OverlayFire(0, 0),
			new OverlayFlash(0, 0),
			new OverlayFleurDeLis(0, 0),
			new OverlayForthInternational(0, 0),
			new OverlayHammerSickle(0, 0),
			new OverlayHand(0, 0),
			new OverlayHarp(0, 0),
			new OverlayIran(0, 0),
			new OverlayIronCross(0, 0),
			new OverlayKangaroo(0, 0),
			new OverlayKiwi(0, 0),
			new OverlayLaurel(0, 0),
			new OverlayMalteseCross(0, 0),
			new OverlayMapleLeaf(0, 0),
			new OverlayMozambique(0, 0),
			new OverlayPapuaNewGuinea(0, 0),
			new OverlayParteiadler(0, 0),
			new OverlayPentagram(0, 0),
			new OverlayReichsadler(0, 0),
			new OverlayShahadah(0, 0),
			new OverlayShield(0, 0),
			new OverlaySikh(0, 0),
			new OverlaySnake(0, 0),
			new OverlaySpringbok(0, 0),
			new OverlayStar(0, 0),
			new OverlayStarEight(0, 0),
			new OverlayStarFour(0, 0),
			new OverlayStarOfDavid(0, 0),
			new OverlayStarSeven(0, 0),
			new OverlayStarSix(0, 0),
			new OverlaySun(0, 0),
			new OverlaySwastika(0, 0),
			new OverlaySword(0, 0),
			new OverlayTakbir(0, 0),
			new OverlayTree(0, 0),
			new OverlayTrident(0, 0),
			new OverlayTriskele(0, 0),
			new OverlayWave(0, 0),
			new OverlayYang(0, 0),
			new OverlayYin(0, 0)
		};
	}
	
	public static Overlay[] GetCustom()
	{
		Overlay[] returnValue = new Overlay[]{};
		return _customTypes.values().toArray(returnValue);
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
	
	public static void FillCustomOverlays()
	{
		_customTypes = new HashMap<>();
		
		File directory;
		try
		{
			directory = new File(new File(MainWindowController.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent() + "\\Custom");
			File[] files = directory.listFiles();
			if (files == null) return;
			
			for (File file : files)
			{
				OverlayPath overlay = FileHandler.LoadOverlayFromFile(file);
				
				if (_customTypes.containsKey(overlay.Name) || _typeMap.containsKey(overlay.Name))
				{
					throw new Exception(String.format(LocalizationHandler.Get("OverlayNameExists"), overlay.Name));
				}
				
				_customTypes.put(overlay.Name, overlay);
			}
		}
		catch (Exception ex)
		{
		}
	}
	
	public static Overlay GetFlagInstance(File path, int maximumX, int maximumY) throws Exception
	{
		return new OverlayFlag(FileHandler.LoadFlagFromFile(path), path, maximumX, maximumY);
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
		if (_customTypes.containsKey(name))
		{
			OverlayPath overlayCopy = _customTypes.get(name).Copy();
			overlayCopy.SetMaximum(defaultMaximumX, defaultMaximumY);
			return overlayCopy;
		}
		
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
