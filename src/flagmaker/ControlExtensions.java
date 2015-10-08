package flagmaker;

import javafx.scene.control.Control;

public class ControlExtensions
{
	public static void HideControl(Control object)
	{
		object.visibleProperty().set(false);
		object.managedProperty().set(false);
	}
	
	public static void ShowControl(Control object)
	{
		object.visibleProperty().set(true);
		object.managedProperty().set(true);
	}
}
