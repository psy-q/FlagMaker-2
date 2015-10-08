package flagmaker.Overlays;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OverlaySelector extends VBox
{
	private Stage _stage;

	@FXML
	private TabPane tabs;

	private final int _defaultMaximumX;
	private final int _defaultMaximumY;
	private Overlay _selectedOverlay;
	
	public OverlaySelector(Stage stage, int defaultMaximumX, int defaultMaximumY)
	{
		Load(stage);

		_defaultMaximumX = defaultMaximumX;
		_defaultMaximumY = defaultMaximumY;
		stage.titleProperty().set("Overlays");
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
		AddTab(OverlayFactory.GetShapes(), "Shapes");
		AddTab(OverlayFactory.GetEmblems(), "Emblems");
		AddTab(OverlayFactory.GetCustom(), "Custom");
		AddTab(OverlayFactory.GetSpecial(), "Special");
	}

	private void AddTab(Overlay[] overlays, String tabName)
	{
		Tab tab = new Tab(tabName);
		FlowPane panel = new FlowPane(Orientation.HORIZONTAL);
		panel.hgapProperty().set(5);
		panel.vgapProperty().set(5);
		
		for (Overlay overlay : overlays)
		{
			Button b = new Button();
			b.setPrefHeight(30);
			b.setPrefWidth(30);
			b.graphicProperty().set(overlay.PaneThumbnail());
			b.tooltipProperty().set(new Tooltip(overlay.Name()));
			b.addEventHandler(ActionEvent.ACTION, event ->
			{
				String name = b.getTooltip().getText();
				SetSelectedOverlay(OverlayFactory.GetInstance(name, _defaultMaximumX, _defaultMaximumY));
			});
			panel.getChildren().add(b);
		}
		
		tab.setContent(panel);
		tabs.getTabs().add(tab);
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
		}
	}
}
