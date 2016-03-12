package flagmaker.Color;

import flagmaker.Files.LocalizationHandler;
import java.util.ArrayList;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
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
	private boolean _shouldTrigger;
	@FXML private Slider sldR;
	@FXML private Label lblR;
	@FXML private Slider sldG;
	@FXML private Label lblG;
	@FXML private Slider sldB;
	@FXML private Label lblB;
	@FXML private Slider sldA;
	@FXML private Label lblA;
	@FXML private Button btnSaveAdvanced;
	@FXML private Canvas cnvSatLight;
	@FXML private Canvas cnvHue;
	
	@FXML private Button btnCancel;
	
	private Image _colorCircle;
	private Color _color;
	
	public ColorSelector(Stage stage, Color currentColor, ArrayList<Color> usedColors, ArrayList<Color> recentColors)
	{
		Load(stage);
		SetWindowStrings();
		FillNamedColorList(paneFotw, ColorList.FlagsOfTheWorld());
		FillNamedColorList(paneFoan, ColorList.FlagsOfAllNations());
		FillColorList(paneUsed, usedColors);
		FillColorList(paneRecent, new ArrayList(new ArrayList(recentColors).subList(0, Math.min(recentColors.size(), 10))));
		
		SetAdvanced(currentColor);
		
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
		btnSaveAdvanced.setText(LocalizationHandler.Get("Save"));
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

	private void SetAdvanced(Color currentColor)
	{
		_color = currentColor;
		_colorCircle = new Image("flagmaker/Images/color-circle.png");
		FillHueCanvas();
		FillSatLightCanvas(_color);
		SetColorCircle(_color);
		
		lblR.setText(Integer.toString((int)(_color.getRed() * 255)));
		lblG.setText(Integer.toString((int)(_color.getGreen() * 255)));
		lblB.setText(Integer.toString((int)(_color.getBlue() * 255)));
		lblA.setText(Integer.toString((int)(_color.getOpacity() * 255)));
		
		sldR.setValue(_color.getRed() * 255);
		sldG.setValue(_color.getGreen() * 255);
		sldB.setValue(_color.getBlue() * 255);
		sldA.setValue(_color.getOpacity() * 255);
		
		sldR.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldval, Number newval) ->
		{
			if (_shouldTrigger && !oldval.equals(newval)) SliderChanged(sldR, lblR);
		});
		sldG.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldval, Number newval) ->
		{
			if (_shouldTrigger && !oldval.equals(newval)) SliderChanged(sldG, lblG);
		});
		sldB.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldval, Number newval) ->
		{
			if (_shouldTrigger && !oldval.equals(newval)) SliderChanged(sldB, lblB);
		});
		sldA.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldval, Number newval) ->
		{
			if (_shouldTrigger && !oldval.equals(newval)) SetNumberLabel(sldA, lblA);
		});
	}
	
	private void SliderChanged(Slider slider, Label label)
	{
		SetNumberLabel(slider, label);
		SetColorFromSliders();
		FillSatLightCanvas(_color);
		SetColorCircle(_color);
	}
	
	private void SetColorCircle(Color color)
	{
		double brightness = color.getBrightness();
		double y = (1 - brightness) * cnvSatLight.getHeight();
		
		double saturation = color.getSaturation();
		double x = saturation * cnvSatLight.getWidth();
		
		GraphicsContext gc = cnvSatLight.getGraphicsContext2D();
		gc.drawImage(_colorCircle, x - 5, y - 5);
	}
	
	@FXML private void CnvSatLightClick(MouseEvent e)
	{
		
	}
	
	@FXML private void CnvSatLightDrag(MouseEvent e)
	{
		_shouldTrigger = false;
		double x = e.getX() / cnvSatLight.getWidth();
		double y = 1 - (e.getY() / cnvSatLight.getHeight());
		_color = Color.hsb(_color.getHue(), Clamp(x), Clamp(y));
		FillSatLightCanvas(_color);
		SetColorCircle(_color);
		SetSlidersFromColor();
		_shouldTrigger = true;
	}

	private void FillHueCanvas()
	{
		int width = (int)cnvHue.getWidth();
		int height = (int)cnvHue.getHeight();
		WritableImage img = new WritableImage(width, height);
		PixelWriter pw = img.getPixelWriter();
		
		for (int y = 0; y < height; y++)
		{
			Color color = Color.hsb(360.0 * y / height, 1, 1);
			for (int x = 0; x < width; x++)
			{
				pw.setColor(x, y, color);
			}
		}
		
		GraphicsContext gc = cnvHue.getGraphicsContext2D();
		gc.drawImage(img, 0, 0);
	}
	
	private void FillSatLightCanvas(Color input)
	{
		int width = (int)cnvSatLight.getWidth();
		int height = (int)cnvSatLight.getHeight();
		WritableImage img = new WritableImage(width, height);
		PixelWriter pw = img.getPixelWriter();
		
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				Color color = Color.hsb(input.getHue(), x / (double)width, 1 - y / (double)height);
				pw.setColor(x, y, color);
			}
		}
		
		GraphicsContext gc = cnvSatLight.getGraphicsContext2D();
		gc.drawImage(img, 0, 0);
	}
	
	private void SetNumberLabel(Slider slider, Label label)
	{
		label.setText(Integer.toString((int)slider.getValue()));
	}
	
	public Color GetSelectedColor()
	{
		return _color;
	}
	
	@FXML
	private void SaveAdvanced()
	{
		SetColorFromSliders();
		_stage.close();
	}

	private void SetColorFromSliders()
	{
		_color = new Color(sldR.getValue() / 255, sldG.getValue() / 255, sldB.getValue() / 255, sldA.getValue() / 255);
	}
	
	private void SetSlidersFromColor()
	{
		sldR.setValue(_color.getRed() * 255);
		sldG.setValue(_color.getGreen() * 255);
		sldB.setValue(_color.getBlue() * 255);
		sldA.setValue(_color.getOpacity() * 255);
		
		SetNumberLabel(sldR, lblR);
		SetNumberLabel(sldG, lblG);
		SetNumberLabel(sldB, lblB);
		SetNumberLabel(sldA, lblA);
	}
	
	@FXML
	private void Cancel()
	{
		_stage.close();
	}
	
	private double Clamp(double input)
	{
		return Math.max(0, Math.min(1, input));
	}
}
