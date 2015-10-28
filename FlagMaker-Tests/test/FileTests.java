import flagmaker.Data.Flag;
import flagmaker.Data.Ratio;
import flagmaker.Divisions.*;
import flagmaker.Files.FileHandler;
import flagmaker.Overlays.OverlayFactory;
import flagmaker.Overlays.OverlayTypes.PathTypes.OverlayStar;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.scene.paint.Color;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

public class FileTests
{
	private final Ratio Ratio = new Ratio(5, 3);
	
	@BeforeClass
	public static void SetUpClass()
	{
		OverlayFactory.SetUpTypeMap();
		OverlayFactory.FillCustomOverlays();
	}
	
	@Test
	public void LoadFlagFromStringTest()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("name=Test\n");
		sb.append("ratio=3:5\n");
		sb.append("gridsize=3:5\n\n");
		
		sb.append("type=grid\n");
		sb.append("color1=ff0000\n");
		sb.append("color2=ffffff\n");
		sb.append("size1=2\n");
		sb.append("size2=2\n\n");
		
		sb.append("type=star\n");
		sb.append("color=00ff00\n");
		sb.append("x=2.5\n");
		sb.append("y=1.5\n");
		sb.append("size=1\n");
		sb.append("rotation=0\n");
		sb.append("stroke=0\n");
		sb.append("strokecolor=000000\n");
		sb.append("strokecurved=false\n");
		
		File file = SaveFlagFile(sb.toString());
		Flag flag = null;
		
		try
		{
			flag = FileHandler.LoadFlagFromFile(file);
		}
		catch (Exception ex)
		{
			fail(ex.getMessage());
		}
		
		assertEquals(flag.Ratio.Width, Ratio.Width);
		assertEquals(flag.Ratio.Height, Ratio.Height);
		assertEquals(flag.GridSize.Width, Ratio.Width);
		assertEquals(flag.GridSize.Height, Ratio.Height);

		assertTrue(flag.Division instanceof DivisionGrid);
		assertEquals(Color.RED, flag.Division.Colors[0]);
		assertEquals(Color.WHITE, flag.Division.Colors[1]);
		assertEquals(2, flag.Division.Values[0]);
		assertEquals(2, flag.Division.Values[1]);

		assertEquals(1, flag.Overlays.length);
		assertTrue(flag.Overlays[0] instanceof OverlayStar);
		OverlayStar s = (OverlayStar)flag.Overlays[0];
		assertEquals(2.5, s.GetDoubleAttribute("X"), 0.001);
		assertEquals(1.5, s.GetDoubleAttribute("Y"), 0.001);
		assertEquals(1, s.GetDoubleAttribute("Size"), 0.001);
		assertEquals(0, s.GetDoubleAttribute("Rotation"), 0.001);
		assertEquals(0, s.GetDoubleAttribute("Stroke"), 0.001);
		assertEquals(Color.BLACK, s.GetColorAttribute("StrokeColor"));
		assertEquals(false, s.GetBooleanAttribute("StrokeCurved"));
	}
	
	private File SaveFlagFile(String data)
	{
		try
		{
			File f = File.createTempFile("test", ".flag");
			
			try (FileWriter writer = new FileWriter(f, false); PrintWriter printLine = new PrintWriter(writer))
			{
				printLine.printf(data);
			}
			
			return f;
		}
		catch (IOException ex)
		{
			fail();
		}
		
		return null;
	}
}
