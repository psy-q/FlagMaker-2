package flagmaker.Color;

import flagmaker.Files.LocalizationHandler;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ColorSelector extends VBox
{
	private Stage _stage;

	@FXML private TabPane tabs;
	@FXML private Tab tabStandard;
	
	@FXML private Label labelFotw;
	@FXML private FlowPane paneFotw;
	@FXML private Label labelFoan;
	@FXML private FlowPane paneFoan;
	@FXML private Label labelRecent;
	@FXML private FlowPane paneRecent;
	@FXML private Label labelUsed;
	@FXML private FlowPane paneUsed;
	
	@FXML private Tab tabAdvanced;
	
	@FXML private Button btnCancel;
	private Color _color;
	
	public ColorSelector(Stage stage, ArrayList<Color> usedColors, ArrayList<Color> recentColors)
	{
		Load(stage);
		SetWindowStrings();
		FillNamedColorList(paneFotw, ColorList.FlagsOfTheWorld());
		FillNamedColorList(paneFoan, ColorList.FlagsOfAllNations());
		FillColorList(paneUsed, usedColors);
		FillColorList(paneRecent, new ArrayList(new ArrayList(recentColors).subList(0, Math.min(recentColors.size(), 10))));
		
		stage.titleProperty().set(LocalizationHandler.Get("Color"));
		stage.getIcons().add(new Image("flagmaker/Images/icon.png"));
	}

	private void SetWindowStrings()
	{
		btnCancel.setText(LocalizationHandler.Get("Cancel"));
		tabStandard.setText(LocalizationHandler.Get("Standard"));
		tabAdvanced.setText(LocalizationHandler.Get("Advanced"));
		labelFotw.setText(LocalizationHandler.Get("LargePaletteName"));
		labelFoan.setText(LocalizationHandler.Get("SmallPaletteName"));
		labelRecent.setText(LocalizationHandler.Get("RecentPaletteName"));
		labelUsed.setText(LocalizationHandler.Get("UsedPaletteName"));
	}
	
	private void Load(Stage stage)
	{
		_stage = stage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ColorSelector.fxml"));
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

	private void FillNamedColorList(FlowPane pane, ArrayList<NamedColor> colors)
	{
		for (NamedColor c : colors)
		{
			Button b = new Button();
			pane.getChildren().add(b);
			Rectangle r = new Rectangle(20, 20);
			r.setFill(c.Color);
			r.setStroke(Color.SILVER);
			Pane p = new Pane();
			p.getChildren().add(r);
			b.setGraphic(p);
			b.setTooltip(new Tooltip(c.Name));
			b.setOnAction(o -> { _color = c.Color; _stage.close(); });
		}
	}
	
	private void FillColorList(FlowPane pane, ArrayList<Color> colors)
	{
		for (Color c : colors)
		{
			Button b = new Button();
			pane.getChildren().add(b);
			Rectangle r = new Rectangle(20, 20);
			r.setFill(c);
			r.setStroke(Color.SILVER);
			Pane p = new Pane();
			p.getChildren().add(r);
			b.setGraphic(p);
			b.setOnAction(o -> { _color = c; _stage.close(); });
		}
	}
	
	Color GetSelectedColor()
	{
		return _color;
	}
	
	@FXML
	private void Cancel()
	{
		_stage.close();
	}
}
