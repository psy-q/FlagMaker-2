package flagmaker.Overlays;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OverlaySelector extends VBox
{
	private Stage _stage;

	@FXML
	private TabPane tabs;

	private int _defaultMaximumX;
	private int _defaultMaximumY;
	private Overlay _selectedOverlay;
	
	public OverlaySelector(Stage stage, int defaultMaximumX, int defaultMaximumY)
	{
		Load(stage);

		_defaultMaximumX = defaultMaximumX;
		_defaultMaximumY = defaultMaximumY;
		//Title = strings.Overlays;
		FillOverlays();
	}

	public Overlay GetSelectedOverlay()
	{
		return _selectedOverlay;
	}

	private void SetSelectedOverlay(Overlay value)
	{
		_selectedOverlay = value;
		_stage.close();
	}

	private void FillOverlays()
	{
		AddTab(new Overlay[]
		{
		}, "Overlays");
		//...
	}

	private void AddTab(Overlay[] overlays, String tabName)
	{
		tabs.getTabs().add(new Tab(tabName));
		//...
	}

	@FXML
	private void Cancel()
	{
		_stage.close();
	}

	private void Load(Stage stage)
	{
		_stage = stage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("OverlaySelector.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try
		{
			loader.load();
		}
		catch (Exception ex)
		{
			String s = ex.getMessage();
			s = s;
		}
	}
}
