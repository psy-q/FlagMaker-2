import flagmaker.Data.Flag;
import flagmaker.Data.Ratio;
import flagmaker.Divisions.*;
import flagmaker.Overlays.Overlay;
import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ShuffleTests
{
	public ShuffleTests()
	{
	}
	
	@BeforeClass
	public static void setUpClass()
	{
	}
	
	@AfterClass
	public static void tearDownClass()
	{
	}
	
	@Before
	public void setUp()
	{
	}
	
	@After
	public void tearDown()
	{
	}

	@Test
	public void GridTest()
	{
		Flag f = new Flag("Basic", new Ratio(5, 3), new Ratio(5, 3), new DivisionGrid(Color.RED, Color.WHITE, 2, 2), new Overlay[]{});
		f.ShuffleColors();
		
		assertEquals(Color.WHITE, f.Division.Colors[0]);
		assertEquals(Color.RED, f.Division.Colors[1]);
	}

	@Test
	public void BlankTest()
	{
		Flag f = new Flag("Basic", new Ratio(5, 3), new Ratio(5, 3), new DivisionGrid(Color.RED, Color.WHITE, 1, 1), new Overlay[]{});
		f.ShuffleColors();
		
		assertEquals(Color.RED, f.Division.Colors[0]);
	}

	@Test
	public void PalesTest()
	{
		Flag f = new Flag("Basic", new Ratio(5, 3), new Ratio(5, 3), new DivisionPales(Color.RED, Color.WHITE, Color.BLUE, 1, 1, 1), new Overlay[]{});
		f.ShuffleColors();
		
		assertEquals(Color.BLUE, f.Division.Colors[0]);
		assertEquals(Color.RED, f.Division.Colors[1]);
		assertEquals(Color.WHITE, f.Division.Colors[2]);
	}
}
