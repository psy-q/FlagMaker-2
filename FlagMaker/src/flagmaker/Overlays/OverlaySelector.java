package flagmaker.Overlays;

import flagmaker.Files.FileHandler;
import flagmaker.Data.Flag;
import flagmaker.Files.LocalizationHandler;
import flagmaker.Overlays.OverlayTypes.SpecialTypes.OverlayFlag;
import flagmaker.Overlays.OverlayTypes.SpecialTypes.OverlayImage;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class OverlaySelector extends VBox
{
	private Stage _stage;

	@FXML private TabPane tabs;
	@FXML private Button btnCancel;

	private final int _defaultMaximumX;
	private final int _defaultMaximumY;
	private Overlay _selectedOverlay;
	
	public OverlaySelector(Stage stage, int defaultMaximumX, int defaultMaximumY)
	{
		Load(stage);

		_defaultMaximumX = defaultMaximumX;
		_defaultMaximumY = defaultMaximumY;
		stage.titleProperty().set(LocalizationHandler.Get("Overlays"));
		stage.getIcons().add(new Image("flagmaker/Images/icon.png"));
		btnCancel.setText(LocalizationHandler.Get("Cancel"));
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
		AddTab(OverlayFactory.GetShapes(), LocalizationHandler.Get("Shapes"));
		AddTab(OverlayFactory.GetEmblems(), LocalizationHandler.Get("Emblems"));
		AddTab(OverlayFactory.GetCustom(), LocalizationHandler.Get("Custom"));
		AddTab(OverlayFactory.GetSpecial(), LocalizationHandler.Get("Special"));
	}

	private void AddTab(Overlay[] overlays, String tabName)
	{
		Tab tab = new Tab(tabName);
		FlowPane panel = new FlowPane(Orientation.HORIZONTAL);
		ScrollPane scrollPane = new ScrollPane(panel);
		scrollPane.setFitToWidth(true);
		
		panel.setPadding(new Insets(5));
		
		for (Overlay overlay : overlays)
		{
			Button b = new Button();
			b.setPrefHeight(30);
			b.setPrefWidth(30);
			b.graphicProperty().set(overlay.PaneThumbnail());
			b.tooltipProperty().set(new Tooltip(overlay.Name));
			b.addEventHandler(ActionEvent.ACTION, event ->
			{
				String name = b.getTooltip().getText();
				switch (name)
				{
					case "flag":
						LoadFlag();
						break;
					case "image":
						LoadImage();
						break;
					default:
						SetSelectedOverlay(OverlayFactory.GetInstanceByShortName(name, _defaultMaximumX, _defaultMaximumY));
						break;
				}
			});
			panel.getChildren().add(b);
		}
		
		tab.setContent(scrollPane);
		tabs.getTabs().add(tab);
	}

	private void LoadFlag()
	{
		FileChooser fileChooserF = new FileChooser();
		fileChooserF.setTitle(LocalizationHandler.Get("Open"));
		fileChooserF.getExtensionFilters().add(new FileChooser.ExtensionFilter(LocalizationHandler.Get("FlagFileFilter"), "*.flag"));
		File flagFile = fileChooserF.showOpenDialog(_stage);
		if (flagFile != null)
		{
			Flag flag;
			try
			{
				flag = FileHandler.LoadFlagFromFile(flagFile);
				SetSelectedOverlay(new OverlayFlag(flag, flagFile, _defaultMaximumX, _defaultMaximumY));
			}
			catch (Exception ex)
			{
			}
		}
	}

	private void LoadImage()
	{
		FileChooser fileChooserI = new FileChooser();
		fileChooserI.setTitle(LocalizationHandler.Get("OpenImage"));
		fileChooserI.getExtensionFilters().add(new FileChooser.ExtensionFilter(LocalizationHandler.Get("ImageFileFilter"), "*.png;*.jpg"));
		File imageFile = fileChooserI.showOpenDialog(_stage);
		if (imageFile != null)
		{
			SetSelectedOverlay(new OverlayImage(imageFile, _defaultMaximumX, _defaultMaximumY));
		}
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
