package flagmaker;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import flagmaker.Divisions.*;
import flagmaker.Overlays.Overlay;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.SubScene;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javax.imageio.ImageIO;

public class MainWindowController
{
	@FXML
	private TextField txtRatioHeight;
	@FXML
	private TextField txtRatioWidth;
	@FXML
	private ComboBox cmbRatio;

	@FXML
	private AnchorPane leftAnchor;
	@FXML
	private StackPane leftStack;

	private SubScene subscene;
	private Pane pane;

	private Division division;
	// private ColorItem[] colors;

	private boolean isLoading;
	private boolean showGrid;
	private int texture;

	private String headerText;
	private String filename;
	private boolean isUnsaved;

	private Ratio ratio;
	private Ratio gridSize;

	@FXML
	protected void initialize()
	{
		AddWorkspace();
		New();
	}

	private void AddWorkspace()
	{
		pane = new Pane();
		subscene = new SubScene(leftAnchor, 300, 200);
		subscene.setRoot(pane);
		leftStack.getChildren().add(subscene);

		// Draw whenever the left side changes size
		leftStack.widthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> Draw()); // lags
		cmbRatio.valueProperty().addListener(new ChangeListener<String>()
		{
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
			{
				Draw();
			}
		});

		ratio = new Ratio(2, 3);
		subscene.widthProperty().bind(Bindings.createDoubleBinding(() -> leftStack.getWidth() - 10, leftStack.widthProperty(), leftStack.heightProperty()));
		subscene.heightProperty().bind(Bindings.createDoubleBinding(() -> (leftStack.getWidth() - 10) * ratio.Height / ratio.Width,
				leftStack.widthProperty(), leftStack.heightProperty(), txtRatioHeight.textProperty(), txtRatioWidth.textProperty()));
	}

		private void SetLanguages()
	{
	}

	private void SetTitle()
	{
	}
	
	// Division
	private void DivisionColorChanged(){}
	private void DivisionSliderChanged(){}
	private void SetDivisionVisibility(){}
	
	@FXML private void DivisionGridClick()
	{
		division = new DivisionGrid(Color.WHITE, Color.GRAY, 2, 2);
		SetDivisionVisibility();
		Draw();
		SetAsUnsaved();
	}
	
	@FXML private void DivisionFessesClick()
	{
		division = new DivisionFesses(Color.GRAY, Color.WHITE, Color.GRAY, 1, 2, 1);
		SetDivisionVisibility();
		Draw();
		SetAsUnsaved();
	}
	
	@FXML private void DivisionPalesClick()
	{
		division = new DivisionPales(Color.GRAY, Color.WHITE, Color.GRAY, 1, 2, 1);
		SetDivisionVisibility();
		Draw();
		SetAsUnsaved();
	}
	
	@FXML private void DivisionBendsForwardClick()
	{
		division = new DivisionBendsForward(Color.GRAY, Color.WHITE);
		SetDivisionVisibility();
		Draw();
		SetAsUnsaved();
	}
	
	@FXML private void DivisionBendsBackwardClick()
	{
		division = new DivisionBendsBackward(Color.GRAY, Color.WHITE);
		SetDivisionVisibility();
		Draw();
		SetAsUnsaved();
	}
	
	@FXML private void DivisionXClick()
	{
		division = new DivisionX(Color.GRAY, Color.WHITE);
		SetDivisionVisibility();
		Draw();
		SetAsUnsaved();
	}
	
	// Overlays
	private void OverlayAdd(){}
	private void SetOverlayMargins(){}
	//private void Draw(){}
	private void Remove(){}
	private void MoveUp(){}
	private void MoveDown(){}
	private void Clone(){}
	private void OverlayAdd(int index, Overlay overlay, boolean isLoading){}
		
	// Colors
	private void SetColorsAndSliders(){}
	private void SetUsedColorPalettes(){}
	private void ShuffleColors(){}
	private Color GetNextColor(Color c, List<Color> colors)
	{
		int index = colors.indexOf(c);
		return colors.get(((index + 1) % colors.size()));
	}
	
	// Grid
	private void SetRatio(int width, int height)
	{
		txtRatioHeight.setText(height + "");
		txtRatioWidth.setText(width + "");
		ratio = new Ratio(width, height);
		FillGridCombobox();
	}
	
	private void GridOnChanged(){}
	private void DrawGrid(){}
	
	private void FillGridCombobox()
	{
		cmbRatio.getItems().clear();
		for (int i = 1; i <= 20; i++)
		{
			int h = i * ratio.Height;
			int w = i * ratio.Width;
			cmbRatio.getItems().add(h + ":" + w);
		}
		cmbRatio.getSelectionModel().select(0);
	}
	
	@FXML
	public void RatioTextboxChanged()
	{
		String sheight = txtRatioHeight.getText();
		String swidth = txtRatioWidth.getText();

		int height = CanParseInt(sheight)
				? Integer.parseInt(sheight)
				: 1;
		int width = CanParseInt(swidth)
				? Integer.parseInt(swidth)
				: 1;

		ratio = new Ratio(width, height);
		leftStack.autosize();
		
		if(!isLoading)
		{
			Draw();
			SetAsUnsaved();
		}
		
		FillGridCombobox();
	}
	
	private void GridSizeDropdownChanged(){}
	
		// Other	
	private void NameChanged()
	{
	}

	private void SetAsUnsaved()
	{
	}

	private void Draw()
	{
		//_canvas.Width = _ratioWidth * 200;
		//_canvas.Height = _ratioHeight * 200;
		Flag().Draw(pane);
		//DrawTexture(_canvas);
		//DrawGrid();
		//SetUsedColorPalettes();
	}

	private void DrawTexture(Pane canvas)
	{
	}
	
	private void ToggleTexture()
	{	
	}
	
	// Export
	public void MenuExportPngClick()
	{
		WritableImage snapshot = pane.snapshot(new SnapshotParameters(), null);
		
		File fileA = new File("export.png");
		try
		{
			ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", fileA);
		}
		catch (Exception ex)
		{
		}
	}
	
	private Size GetPngDimensions(){return null;}
	
	public void MenuExportSvgClick()
	{
		try
		{
			Flag().ExportToSvg("export.svg");
		}
		catch (IOException ex)
		{
		}
	}
	
	private void MenuExportBulkPngClick(){}
	private void MenuExportBulkSvgClick(){}
	private void GetFlagFiles(){}
	private void GetBulkSaveDirectory(){}
	private void ExportFinished(){}
	
	// Load / save
	public void New()
	{
		//if (CheckUnsaved())
		//{
		//	return;
		//}
		PlainPreset(2, 2);
		//_divisionPicker1.SelectedColor = _divisionPicker1.StandardColors[1].Color;
		//_divisionPicker2.SelectedColor = _divisionPicker2.StandardColors[5].Color;
		//_lstOverlays.Children.Clear();
		SetRatio(3, 2);
		RatioTextboxChanged();
		//_txtName.Text = strings.Untitled;
		filename = "";
		isUnsaved = false;
		SetTitle();
	}
	
	// Presets
	private void PresetChanged(){}
	
	private void PresetBlank(){}
	
	private void PresetHorizontal(){}
	
	private void PresetVertical(){}
	
	private void PresetQuad(){}
	
	private void PresetStripes(){}
	
	private void PlainPreset(int slider1, int slider2)
	{
		DivisionGridClick();
	}
	
	private void LoadPresets(){}
	
	private void LoadPreset(){}
		
	private void GetPresetFlagName(){}
	
	private void GenerateRandomFlag(){}
	
	private void OnClosing()
	{
	}
	
	private void LanguageChange()
	{
	}
	
	public Flag Flag()
	{
		return new Flag("", ratio, gridSize, division, new Overlay[]{});
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
