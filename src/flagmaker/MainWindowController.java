package flagmaker;

import flagmaker.Divisions.*;
import flagmaker.Overlays.Overlay;
import flagmaker.Overlays.OverlayControl;
import flagmaker.Overlays.OverlayFactory;
import flagmaker.Overlays.OverlayTypes.PathTypes.OverlayPath;
import flagmaker.Overlays.OverlayTypes.ShapeTypes.OverlayFlag;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.imageio.ImageIO;
import javafx.fxml.FXML;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.SnapshotParameters;
import javafx.scene.SubScene;
import javafx.scene.shape.Line;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainWindowController
{
	@FXML private TextField txtRatioHeight;
	@FXML private TextField txtRatioWidth;
	@FXML private ComboBox cmbRatio;

	@FXML private AnchorPane leftAnchor;
	@FXML private StackPane leftStack;

	@FXML private TextField txtName;
	@FXML private Slider divisionSlider1;
	@FXML private Slider divisionSlider2;
	@FXML private Slider divisionSlider3;
	@FXML private Label divisionLabel1;
	@FXML private Label divisionLabel2;
	@FXML private Label divisionLabel3;
	@FXML private ColorPicker divisionPicker1;
	@FXML private ColorPicker divisionPicker2;
	@FXML private ColorPicker divisionPicker3;
	@FXML private ComboBox cmbPresets;

	@FXML private VBox lstOverlays;

	private Stage _stage;
	private SubScene _subScene;
	private Pane _pane;

	private SubScene _gridSubScene;
	private Pane _gridPane;

	private Division _division;

	private boolean _isLoading;
	private boolean _showGrid;
	private int _texture;

	private String _headerText;
	private String _filename;
	private boolean _isUnsaved;

	private Ratio _ratio;

	@FXML
	protected void initialize()
	{
		AddWorkspace();

		_headerText = String.format(" - FlagMaker %s", getClass().getPackage().getImplementationVersion());

		SetColorsAndSliders();
		LoadPresets();
		OverlayFactory.SetUpTypeMap();
		New();
	}

	public void SetPrimaryStage(Stage stage)
	{
		_stage = stage;
	}

	private void AddWorkspace()
	{
		_pane = new Pane();
		_subScene = new SubScene(leftAnchor, 300, 200);
		_subScene.setRoot(_pane);
		leftStack.getChildren().add(_subScene);

		_gridPane = new Pane();
		_gridPane.setBackground(Background.EMPTY);
		_gridSubScene = new SubScene(leftAnchor, 300, 200);
		_gridSubScene.setRoot(_gridPane);
		leftStack.getChildren().add(_gridSubScene);

		// Draw whenever the left side changes size
		leftStack.widthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> Draw()); // lags
		cmbRatio.valueProperty().addListener(new ChangeListener<String>()
		{
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
			{
				GridSizeDropdownChanged();
			}
		});

		_ratio = new Ratio(2, 3);
		_subScene.widthProperty().bind(Bindings.createDoubleBinding(() -> leftStack.getWidth() - 10, leftStack.widthProperty(), leftStack.heightProperty()));
		_subScene.heightProperty().bind(Bindings.createDoubleBinding(() -> (leftStack.getWidth() - 10) * _ratio.Height / _ratio.Width,
				leftStack.widthProperty(), leftStack.heightProperty(), txtRatioHeight.textProperty(), txtRatioWidth.textProperty()));
		_gridSubScene.widthProperty().bind(Bindings.createDoubleBinding(() -> leftStack.getWidth() - 10, leftStack.widthProperty(), leftStack.heightProperty()));
		_gridSubScene.heightProperty().bind(Bindings.createDoubleBinding(() -> (leftStack.getWidth() - 10) * _ratio.Height / _ratio.Width,
				leftStack.widthProperty(), leftStack.heightProperty(), txtRatioHeight.textProperty(), txtRatioWidth.textProperty()));
	}

	private void SetLanguages()
	{
	}

	private void SetTitle()
	{
		String title = String.format("%s%s%s",
				StringExtensions.IsNullOrWhitespace(_filename)
						? "Untitled"
						: StringExtensions.GetFilenameWithoutExtension(_filename),
				_isUnsaved ? "*" : "",
				_headerText);
		_stage.setTitle(title);
	}

	// Division
	private void DivisionColorChanged()
	{
		if (_isLoading) return;

		_division.SetColors(new Color[]
		{
			divisionPicker1.getValue(),
			divisionPicker2.getValue(),
			divisionPicker3.getValue()
		});
		Draw();
		SetAsUnsaved();
	}

	private void DivisionSliderChanged()
	{
		divisionLabel1.setText(String.format("%d", divisionSlider1.valueProperty().intValue()));
		divisionLabel2.setText(String.format("%d", divisionSlider2.valueProperty().intValue()));
		divisionLabel3.setText(String.format("%d", divisionSlider3.valueProperty().intValue()));
		_division.SetValues(new int[]
		{
			divisionSlider1.valueProperty().intValue(), divisionSlider2.valueProperty().intValue(), divisionSlider3.valueProperty().intValue()
		});
		Draw();
		SetAsUnsaved();
	}

	private void SetDivisionVisibility()
	{
		divisionPicker1.setValue(_division.Colors[0]);
		divisionPicker2.setValue(_division.Colors[1]);

		if (_division.Colors.length > 2)
		{
			divisionPicker3.setValue(_division.Colors[2]);
			ControlExtensions.ShowControl(divisionPicker3);
		}
		else
		{
			ControlExtensions.HideControl(divisionPicker3);
		}

		ControlExtensions.HideControl(divisionSlider1);
		ControlExtensions.HideControl(divisionSlider2);
		ControlExtensions.HideControl(divisionSlider3);
		ControlExtensions.HideControl(divisionLabel1);
		ControlExtensions.HideControl(divisionLabel2);
		ControlExtensions.HideControl(divisionLabel3);

		if (_division.Values.length <= 0) return;
		divisionSlider1.setValue(_division.Values[0]);
		ControlExtensions.ShowControl(divisionSlider1);
		divisionLabel1.setText(String.format("%d", _division.Values[0]));
		ControlExtensions.ShowControl(divisionLabel1);

		if (_division.Values.length <= 1) return;
		divisionSlider2.setValue(_division.Values[1]);
		ControlExtensions.ShowControl(divisionSlider2);
		divisionLabel2.setText(String.format("%d", _division.Values[1]));
		ControlExtensions.ShowControl(divisionLabel2);

		if (_division.Values.length <= 2) return;
		divisionSlider3.setValue(_division.Values[2]);
		ControlExtensions.ShowControl(divisionSlider3);
		divisionLabel3.setText(String.format("%d", _division.Values[2]));
		ControlExtensions.ShowControl(divisionLabel3);
	}

	@FXML private void DivisionGridClick()
	{
		_division = new DivisionGrid(divisionPicker1.getValue(), divisionPicker2.getValue(), divisionSlider1.valueProperty().intValue(), divisionSlider2.valueProperty().intValue());
		SetDivisionVisibility();
		Draw();
		SetAsUnsaved();
	}

	@FXML private void DivisionFessesClick()
	{
		_division = new DivisionFesses(divisionPicker1.getValue(), divisionPicker2.getValue(), divisionPicker3.getValue(),
				divisionSlider1.valueProperty().intValue(), divisionSlider2.valueProperty().intValue(), divisionSlider3.valueProperty().intValue());
		SetDivisionVisibility();
		Draw();
		SetAsUnsaved();
	}

	@FXML private void DivisionPalesClick()
	{
		_division = new DivisionPales(divisionPicker1.getValue(), divisionPicker2.getValue(), divisionPicker3.getValue(),
				divisionSlider1.valueProperty().intValue(), divisionSlider2.valueProperty().intValue(), divisionSlider3.valueProperty().intValue());
		SetDivisionVisibility();
		Draw();
		SetAsUnsaved();
	}

	@FXML private void DivisionBendsForwardClick()
	{
		_division = new DivisionBendsForward(divisionPicker1.getValue(), divisionPicker2.getValue());
		SetDivisionVisibility();
		Draw();
		SetAsUnsaved();
	}

	@FXML private void DivisionBendsBackwardClick()
	{
		_division = new DivisionBendsBackward(divisionPicker1.getValue(), divisionPicker2.getValue());
		SetDivisionVisibility();
		Draw();
		SetAsUnsaved();
	}

	@FXML private void DivisionXClick()
	{
		_division = new DivisionX(divisionPicker1.getValue(), divisionPicker2.getValue());
		SetDivisionVisibility();
		Draw();
		SetAsUnsaved();
	}

	// Overlays
	@FXML private void OverlayAdd()
	{
		OverlayAdd(lstOverlays.getChildren().size(), null, false);
	}

	private void SetOverlayMargins()
	{
		for (int i = 0; i < lstOverlays.getChildren().size() - 1; i++)
		{
			((OverlayControl) lstOverlays.getChildren().get(i)).setPadding(new Insets(0, 0, 20, 0));
		}
	}

	public void Remove(OverlayControl overlayControl)
	{
		lstOverlays.getChildren().remove(overlayControl);
		Draw();
		SetAsUnsaved();
	}

	public void MoveUp(OverlayControl overlayControl)
	{
		int index = lstOverlays.getChildren().indexOf(overlayControl);
		if (index == 0) return;

		ArrayList<OverlayControl> controls = new ArrayList<>();
		for (int i = 0; i < lstOverlays.getChildren().size(); i++)
		{
			if (i + 1 == index)
			{
				controls.add((OverlayControl) lstOverlays.getChildren().get(i + 1));
				controls.add((OverlayControl) lstOverlays.getChildren().get(i));
				i++;
			}
			else
			{
				controls.add((OverlayControl) lstOverlays.getChildren().get(i));
			}
		}

		lstOverlays.getChildren().clear();
		for (OverlayControl control : controls)
		{
			lstOverlays.getChildren().add(control);
		}

		SetOverlayMargins();
		Draw();
		SetAsUnsaved();
	}

	public void MoveDown(OverlayControl overlayControl)
	{
		int index = lstOverlays.getChildren().indexOf(overlayControl);
		if (index == lstOverlays.getChildren().size() - 1) return;

		ArrayList<OverlayControl> controls = new ArrayList<>();
		for (int i = 0; i < lstOverlays.getChildren().size(); i++)
		{
			if (i == index)
			{
				controls.add((OverlayControl) lstOverlays.getChildren().get(i + 1));
				controls.add((OverlayControl) lstOverlays.getChildren().get(i));
				i++;
			}
			else
			{
				controls.add((OverlayControl) lstOverlays.getChildren().get(i));
			}
		}

		lstOverlays.getChildren().clear();
		for (OverlayControl control : controls)
		{
			lstOverlays.getChildren().add(control);
		}

		SetOverlayMargins();
		Draw();
		SetAsUnsaved();
	}

	public void Clone(OverlayControl controlToClone)
	{
		int index = lstOverlays.getChildren().indexOf(controlToClone);
		Overlay original = controlToClone.GetOverlay();
		Class type = original.getClass();
		Overlay copy = OverlayFactory.GetInstanceByLongName(type.getName(), 1, 1);

		for (int i = 0; i < original.Attributes.length; i++)
		{
			copy.Attributes[i].Value = original.Attributes[i].Value;
			copy.Attributes[i].IsDiscrete = original.Attributes[i].IsDiscrete;
		}

		copy.SetColor(original.Color);

		if (type.isAssignableFrom(OverlayPath.class))
		{
			((OverlayPath) copy).StrokeColor = ((OverlayPath) original).StrokeColor;
		}
		else if (type.isAssignableFrom(OverlayFlag.class))
		{
			((OverlayFlag) copy).Flag = ((OverlayFlag) original).Flag;
		}

		Ratio gridSize = SelectedGridSize();
		copy.SetMaximum(gridSize.Width, gridSize.Height);

		OverlayAdd(index + 1, copy, true);
	}

	private void OverlayAdd(int index, Overlay overlay, boolean isLoading)
	{
		Ratio gridSize = SelectedGridSize();
		OverlayControl control = new OverlayControl(_stage, this, gridSize.Width, gridSize.Height, isLoading);

		if (control.WasCanceled)
		{
			return;
		}

		if (overlay != null)
		{
			control.SetOverlay(overlay);
		}

		lstOverlays.getChildren().add(control);
		SetOverlayMargins();

		if (!isLoading)
		{
			Draw();
			SetAsUnsaved();
		}
	}

	// Colors
	private void SetColorsAndSliders()
	{
		SetDefaultColors();

		divisionPicker1.valueProperty().addListener((ObservableValue<? extends Color> ov, Color oldval, Color newval) -> DivisionColorChanged());
		divisionPicker2.valueProperty().addListener((ObservableValue<? extends Color> ov, Color oldval, Color newval) -> DivisionColorChanged());
		divisionPicker3.valueProperty().addListener((ObservableValue<? extends Color> ov, Color oldval, Color newval) -> DivisionColorChanged());

		divisionSlider1.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldval, Number newval) -> DivisionSliderChanged());
		divisionSlider2.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldval, Number newval) -> DivisionSliderChanged());
		divisionSlider3.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldval, Number newval) -> DivisionSliderChanged());
	}

	private void SetDefaultColors()
	{
		divisionPicker1.setValue(Color.rgb(198, 12, 48));
		divisionPicker2.setValue(Color.rgb(253, 200, 47));
		divisionPicker3.setValue(Color.rgb(0, 38, 100));
	}

	@FXML private void ShuffleColors()
	{
		boolean skip2 = (_division instanceof DivisionGrid)
				&& (divisionSlider1.getValue() == 1)
				&& (divisionSlider2.getValue() == 1);
		Color[] colors = Flag().ColorsUsed();

		divisionPicker1.setValue(GetNextColor(divisionPicker1.getValue(), colors));

		if (!skip2)
		{
			divisionPicker2.setValue(GetNextColor(divisionPicker2.getValue(), colors));
		}

		if (divisionPicker3.visibleProperty().get())
		{
			divisionPicker3.setValue(GetNextColor(divisionPicker3.getValue(), colors));
		}

		for (OverlayControl overlay : (List<OverlayControl>) (List<?>) lstOverlays.getChildren())
		{
			overlay.SetColor(GetNextColor(overlay.GetColor(), colors));
		}
	}

	private Color GetNextColor(Color c, Color[] colors)
	{
		int index = Arrays.asList(colors).indexOf(c);
		return colors[(index + 1) % colors.length];
	}

	// Grid
	private Ratio SelectedGridSize()
	{
		String value = (String) cmbRatio.getValue();

		if (value == null)
		{
			return new Ratio(2, 3);
		}

		String[] parts = value.split(":");
		int height = Integer.parseInt(parts[0]);
		int width = Integer.parseInt(parts[1]);
		return new Ratio(width, height);
	}

	private void SetRatio(int width, int height)
	{
		txtRatioHeight.setText(height + "");
		txtRatioWidth.setText(width + "");
		_ratio = new Ratio(width, height);
		FillGridCombobox();
	}

	@FXML
	private void GridOnChanged()
	{
		_showGrid = !_showGrid;
		DrawGrid();
	}

	private void DrawGrid()
	{
		_gridPane.getChildren().clear();
		if (!_showGrid)
		{
			return;
		}
		if (cmbRatio.getItems().size() == 0)
		{
			return;
		}

		Ratio gridSize = SelectedGridSize();
		double intervalX = _gridPane.getWidth() / gridSize.Width;
		double intervalY = _gridPane.getHeight() / gridSize.Height;

		for (int x = 0; x <= gridSize.Width; x++)
		{
			Line line = new Line(x * intervalX, 0, x * intervalX, _gridPane.getHeight());
			line.setStrokeWidth(5);
			line.setStroke(Color.SILVER);
			_gridPane.getChildren().add(line);
		}
		
		for (int y = 0; y <= gridSize.Height; y++)
		{
			Line line = new Line(0, y * intervalY, _gridPane.getWidth(), y * intervalX);
			line.setStrokeWidth(5);
			line.setStroke(Color.SILVER);
			_gridPane.getChildren().add(line);
		}
	}

	private void FillGridCombobox()
	{
		cmbRatio.getItems().clear();
		for (int i = 1; i <= 20; i++)
		{
			int h = i * _ratio.Height;
			int w = i * _ratio.Width;
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

		_ratio = new Ratio(width, height);
		leftStack.autosize();

		if (!_isLoading)
		{
			Draw();
			SetAsUnsaved();
		}

		FillGridCombobox();
	}

	@FXML
	private void GridSizeDropdownChanged()
	{
		if (cmbRatio.getItems().size() == 0) return;
		Ratio gridSize = SelectedGridSize();
		int sliderMax = Math.max(gridSize.Width, gridSize.Height);

		divisionSlider1.setMax(sliderMax);
		divisionSlider2.setMax(sliderMax);
		divisionSlider3.setMax(sliderMax);

		for (OverlayControl overlay : (List<OverlayControl>)(List<?>)lstOverlays.getChildren())
		{
			((OverlayControl)overlay).SetMaximum(gridSize.Width, gridSize.Height);
		}

		if (!_isLoading)
		{
			Draw();
			SetAsUnsaved();
		}
	}

	// Other	
	private void NameChanged()
	{
		SetAsUnsaved();
	}

	private void SetAsUnsaved()
	{
		_isUnsaved = true;
		SetTitle();
	}

	public void Draw()
	{
		Flag().Draw(_pane);
		//DrawTexture(_canvas);
		DrawGrid();
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
		WritableImage snapshot = _pane.snapshot(new SnapshotParameters(), null);

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

	@FXML private void MenuExportBulkPngClick()
	{
		boolean error = false;
		List<File> files = GetFlagFiles();
		if (files == null || files.isEmpty()) return;
		
		File directory = GetBulkSaveDirectory(files.get(0));
		if (!directory.exists() || !directory.canWrite()) return;
		
		for (File file : files)
		{
			try
			{
				Flag.LoadFromFile(file).ExportToSvg(String.format("%s\\%s.svg", directory.getParent(), file.getName()));
			}
			catch (Exception ex)
			{
				error = true;
			}
		}
		
		ExportFinished(error);
	}
	
	@FXML private void MenuExportBulkSvgClick(){}
	
	private List<File> GetFlagFiles()
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select files");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Flag files", "*.flag"));
		return fileChooser.showOpenMultipleDialog(_stage);
	}
	
	private File GetBulkSaveDirectory(File defaultDirectory)
	{
		DirectoryChooser dc = new DirectoryChooser();
		dc.setInitialDirectory(defaultDirectory);
		dc.setTitle("Select directory to save in");
		return dc.showDialog(_stage);
	}

	private void ExportFinished(boolean errorOccurred)
	{
		if (errorOccurred)
		{
			new Alert(AlertType.ERROR, "Export bulk error", ButtonType.OK).showAndWait();
		}
		else
		{
			new Alert(AlertType.INFORMATION, "Export bulk success", ButtonType.OK).showAndWait();
		}
	}

	// Load / save
	@FXML private void New()
	{
		if (CheckUnsaved())
		{
			return;
		}
		PlainPreset(2, 2);
		SetDefaultColors();
		lstOverlays.getChildren().clear();
		SetRatio(3, 2);
		RatioTextboxChanged();
		txtName.setText("Untitled");
		_filename = "";
		_isUnsaved = false;
		SetTitle();
	}

	@FXML private void Save()
	{
		if (StringExtensions.IsNullOrWhitespace(_filename))
		{
			SaveAs();
		}
		else
		{
			SaveFlag();
		}
		
		SetTitle();
	}
	
	private void SaveFlag()
	{
		try
		{
			Flag().Save(_filename);
		}
		catch (IOException ex)
		{
			//
		}
		
		_isUnsaved = false;
		LoadPresets();
	}
	
	@FXML private void SaveAs()
	{
		_filename = "export.flag";
		SaveFlag();
	}

	@FXML private void Open()
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open flag");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Flag files", "*.flag"));
		File file = fileChooser.showOpenDialog(_stage);
		LoadFlagFromFile(file);
	}

	private boolean CheckUnsaved()
	{
		if (!_isUnsaved) return false;

		Alert alert = new Alert(AlertType.CONFIRMATION);
		ButtonType buttonYes = new ButtonType("Yes");
		ButtonType buttonNo = new ButtonType("No");
		ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		alert.setTitle("FlagMaker 2.0");
		alert.setHeaderText("Not saved");
		alert.setContentText("Save changes?");
		alert.getButtonTypes().setAll(buttonYes, buttonNo, buttonCancel);

		Optional<ButtonType> result = alert.showAndWait();

		ButtonType b = result.get();
		if (b == buttonYes)
		{
			Save();
		}

		return b == buttonCancel;
	}

	private void LoadFlagFromFile(File filename)
	{
		try
		{
			LoadFlag(Flag.LoadFromFile(filename));
			_filename = filename.getPath();
		}
		catch (Exception e)
		{
			new Alert(AlertType.ERROR, "Could not load file.", ButtonType.OK).showAndWait();
		}
	}

	private void LoadFlag(Flag flag){}

	// Presets
	private void PresetBlank()
	{
		PlainPreset(1, 1);
	}

	private void PresetHorizontal()
	{
		PlainPreset(1, 2);
	}

	private void PresetVertical()
	{
		PlainPreset(2, 1);
	}

	private void PresetQuad()
	{
		PlainPreset(2, 2);
	}

	private void PresetStripes()
	{
		for (int i = 0; i < cmbRatio.getItems().size(); i++)
		{
			if (((Ratio)cmbRatio.getItems().get(i)).Width >= 7)
			{
				cmbRatio.getSelectionModel().select(i);
				break;
			}
		}

		PlainPreset(1, 7);
	}

	private void PlainPreset(int slider1, int slider2)
	{
		DivisionGridClick();
		divisionSlider1.setValue(slider1);
		divisionSlider2.setValue(slider2);
		divisionSlider3.setValue(1);
	}

	private void LoadPresets()
	{
		cmbPresets.getItems().add("Blank");
		cmbPresets.getItems().add("Horizontal");
		cmbPresets.getItems().add("Vertical");
		cmbPresets.getItems().add("Quad");
		cmbPresets.getItems().add("Stripes");

		cmbPresets.valueProperty().addListener(new ChangeListener<String>()
		{
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
			{
				String preset = (String)cmbPresets.getValue();
				cmbPresets.setValue(null);

				switch (preset)
				{
					case "Blank": PresetBlank(); return;
					case "Horizontal": PresetHorizontal(); return;
					case "Vertical": PresetVertical(); return;
					case "Quad": PresetQuad(); return;
					case "Stripes": PresetStripes(); return;
				}
			}
		});
	}

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
		String name = StringExtensions.IsNullOrWhitespace(txtName.getText())
				? StringExtensions.IsNullOrWhitespace(_filename)
					? ""
					: Paths.get(_filename).getFileName().toString()
				: txtName.getText();
		return new Flag(name, _ratio, SelectedGridSize(), _division, GetOverlays());
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

	private Overlay[] GetOverlays()
	{
		ArrayList<Overlay> list = new ArrayList<>();
		for (Object control : lstOverlays.getChildren())
		{
			OverlayControl oc = (OverlayControl)control;
			Overlay o = oc.GetOverlay();
			list.add(o);
		}

		Overlay[] returnValue = new Overlay[]{};
		return list.toArray(returnValue);
	}
}
