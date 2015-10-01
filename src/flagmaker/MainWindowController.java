package flagmaker;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainWindowController
{
	@FXML
	private TextField txtRatioHeight;
	@FXML
	private TextField txtRatioWidth;
	@FXML
	private ComboBox cmbRatio;
	
	@FXML
	protected void initialize()
	{
		RatioTextboxChanged();
		cmbRatio.getSelectionModel().select(0);
	}
	
	@FXML
	public void RatioTextboxChanged()
	{
		String sheight = txtRatioHeight.getText();
		String swidth = txtRatioWidth.getText();		
		cmbRatio.getItems().clear();
		
		int height = CanParseInt(sheight)
				? Integer.parseInt(sheight)
				: 1;
		int width = CanParseInt(swidth)
				? Integer.parseInt(swidth)
				: 1;
		
		for (int i = 1; i <= 20; i++)
		{
			int h = i * height;
			int w = i * width;
			cmbRatio.getItems().add(h + ":" + w);
		}
	}
	
	private boolean CanParseInt(String value)
	{
		try
		{
			Integer.parseInt(value);
			return true;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
	}
}
