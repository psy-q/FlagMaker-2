package flagmaker.Color;

import java.util.Random;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColorButton extends VBox
{
	@FXML private Rectangle rect;
	private Color _value;
	private ColorButtonListener _listener;
	private Random _random;
	
	public ColorButton()
	{
		_random = new Random();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ColorButton.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try
		{
			loader.load();
		}
		catch (Exception ex)
		{
			String s = ex.getMessage();
		}
	}
	
	public void SetListener(ColorButtonListener listener)
	{
		_listener = listener;
	}
	
	public Color GetValue()
	{
		return _value;
	}
	
	public void SetValue(Color value)
	{
		_value = value;
		rect.setFill(value);
	}
	
	@FXML private void OnClicked()
	{
		double newR = _random.nextDouble();
		double newG = _random.nextDouble();
		double newB = _random.nextDouble();
		Color newColor = new Color(newR, newG, newB, 1.0);
		
		Color oldval = _value;
		SetValue(newColor);
		_listener.ColorChanged(oldval, _value);
	}
}
