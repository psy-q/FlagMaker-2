import flagmaker.Data.Flag;
import flagmaker.Data.Ratio;
import flagmaker.Divisions.*;
import flagmaker.Files.FileHandler;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.scene.paint.Color;
import org.junit.Test;
import static org.junit.Assert.*;

public class FileTests
{
	private final Ratio Ratio = new Ratio(5, 3);
	
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
		sb.append("size2=2\n");
		
		File file = SaveFlagFile(sb.toString());
		
		try
		{
			Flag flag = FileHandler.LoadFlagFromFile(file);
			
			assertEquals(flag.Ratio.Width, Ratio.Width);
			assertEquals(flag.Ratio.Height, Ratio.Height);
			assertEquals(flag.GridSize.Width, Ratio.Width);
			assertEquals(flag.GridSize.Height, Ratio.Height);
			
			assertTrue(flag.Division instanceof DivisionGrid);
			assertEquals(Color.RED, flag.Division.Colors[0]);
			assertEquals(Color.WHITE, flag.Division.Colors[1]);
			assertEquals(2, flag.Division.Values[0]);
			assertEquals(2, flag.Division.Values[1]);
		}
		catch (Exception ex)
		{
			fail();
		}
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
