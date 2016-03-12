package flagmaker.Color;

import flagmaker.Files.LocalizationHandler;
import java.util.ArrayList;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
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
	@FXML private FlowPane paneFotw;
	@FXML private FlowPane paneFoan;
	@FXML private Tab tabAdvanced;
	@FXML private Button btnCancel;
	private Color _color;
	
	public ColorSelector(Stage stage)
	{
		Load(stage);
		FillColorList(paneFotw, ColorList.FlagsOfTheWorld());
		FillColorList(paneFoan, ColorList.FlagsOfAllNations());
		
		stage.titleProperty().set(LocalizationHandler.Get("Color"));
		stage.getIcons().add(new Image("flagmaker/Images/icon.png"));
		btnCancel.setText(LocalizationHandler.Get("Cancel"));
		tabStandard.setText(LocalizationHandler.Get("Standard"));
		tabAdvanced.setText(LocalizationHandler.Get("Advanced"));
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

	private void FillColorList(FlowPane pane, ArrayList<NamedColor> colors)
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
