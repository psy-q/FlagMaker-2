package flagmaker.Divisions;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Rectangle;

public class DivisionX extends Division 
{
	public DivisionX(Color color1, Color color2)
	{
		super(new Color[] { color1, color2 }, new int[] { });
	}

	@Override
	public String Name()
	{
		return "bends both";
	}

	@Override
	public void Draw(Pane canvas)
	{
		double height = canvas.getHeight();
		double width = canvas.getWidth();
		
		canvas.getChildren().add(new Rectangle(width, height, Colors[0]));
		
		Path p = new Path(new PathElement[]
		{
			new MoveTo(0, 0),
			new LineTo(width, height),
			new LineTo(0, height),
			new LineTo(width, 0)
		});
		p.fillProperty().set(Colors[1]);
		p.strokeWidthProperty().set(0);
		canvas.getChildren().add(p);
	}

	@Override
	public void SetColors(Color[] colors)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void SetValues(int[] values)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
