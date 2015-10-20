package flagmaker;

import flagmaker.Divisions.*;
import flagmaker.Overlays.Overlay;
import flagmaker.Overlays.OverlayControl;
import flagmaker.Overlays.OverlayFactory;
import flagmaker.Overlays.OverlayTypes.PathTypes.OverlayPath;
import flagmaker.Overlays.OverlayTypes.ShapeTypes.OverlayFlag;
import flagmaker.RandomFlag.RandomFlagFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Line;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainWindowController
{
	@FXML private Menu mnuFile;
	@FXML private MenuItem mnuNew;
	@FXML private MenuItem mnuOpen;
	@FXML private MenuItem mnuSave;
	@FXML private MenuItem mnuSaveAs;
	@FXML private MenuItem mnuExportPng;
	@FXML private MenuItem mnuExportSvg;
	@FXML private MenuItem mnuBulkExportPng;
	@FXML private MenuItem mnuBulkExportSvg;
	@FXML private Menu mnuPresets;
	@FXML private Menu mnuLanguages;
	
	@FXML private Label lblRatio;
	@FXML private Label lblGridSize;
	@FXML private TextField txtRatioHeight;
	@FXML private TextField txtRatioWidth;
	@FXML private ComboBox cmbRatio;
	
	@FXML private Tooltip ttpShowGrid;
	@FXML private Tooltip ttpShuffleColors;
	@FXML private Tooltip ttpToggleTexture;
	@FXML private Tooltip ttpRandomFlag;
	
	@FXML private Tooltip ttpDivisionGrid;
	@FXML private Tooltip ttpDivisionFesses;
	@FXML private Tooltip ttpDivisionPales;
	@FXML private Tooltip ttpDivisionBendsForward;
	@FXML private Tooltip ttpDivisionBendsBackward;
	@FXML private Tooltip ttpDivisionBendsBoth;
	@FXML private Label lblPresets;
	@FXML private Label lblOverlays;

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
	@FXML private Tooltip ttpOverlayAddNew;
	
	@FXML private Label lblDivisions;
	
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
		LoadLocalization();
		AddWorkspace();

		_headerText = String.format(" - %s", GetNameWithVersion());

		SetColorsAndSliders();
		LoadBasicPresets();
		LoadFilePresets();
		HookUpEvents();
		OverlayFactory.SetUpTypeMap();
		New();
	}

	public void SetPrimaryStage(Stage stage)
	{
		_stage = stage;
	}
	
	private void LoadLocalization()
	{
		LoadLanguageMenu();
		
		LocalizationHandler.Initialize();
		SetWindowStrings();
	}
	
	private void LoadLanguageMenu()
	{
		Locale[] locales = new Locale[]
		{
			new Locale("en", "US"),
			new Locale("es", "MX"),
			new Locale("fr", "FR"),
			new Locale("pl", "PL"),
			new Locale("ru", "RU")
		};
		
		for (Locale locale : locales)
		{
			MenuItem item = new MenuItem(locale.getDisplayName());
			item.addEventHandler(EventType.ROOT, (Event event) ->
			{
				LocalizationHandler.SetLanguage(locale);
				SetWindowStrings();
			});
			mnuLanguages.getItems().add(item);
		}
	}

	private void SetWindowStrings()
	{
		mnuFile.setText(LocalizationHandler.Get("File"));
		mnuNew.setText(LocalizationHandler.Get("New"));
		mnuOpen.setText(LocalizationHandler.Get("OpenMenu"));
		mnuSave.setText(LocalizationHandler.Get("SaveMenu"));
		mnuSaveAs.setText(LocalizationHandler.Get("SaveAs"));
		mnuExportPng.setText(LocalizationHandler.Get("ExportAsPngMenu"));
		mnuExportSvg.setText(LocalizationHandler.Get("ExportAsSvgMenu"));
		mnuBulkExportPng.setText(LocalizationHandler.Get("ExportBulkAsPng"));
		mnuBulkExportSvg.setText(LocalizationHandler.Get("ExportBulkAsSvg"));
		mnuPresets.setText(LocalizationHandler.Get("WorldFlagPresets"));
		mnuLanguages.setText(LocalizationHandler.Get("Language"));
		
		lblRatio.setText(LocalizationHandler.Get("Ratio"));
		lblGridSize.setText(LocalizationHandler.Get("GridSize"));
		ttpShowGrid.setText(LocalizationHandler.Get("ShowGrid"));
		ttpShuffleColors.setText(LocalizationHandler.Get("ShuffleColors"));
		ttpToggleTexture.setText(LocalizationHandler.Get("ToggleTexture"));
		ttpRandomFlag.setText(LocalizationHandler.Get("GenerateRandomFlag"));
		
		lblDivisions.setText(LocalizationHandler.Get("Division"));
		ttpDivisionGrid.setText(LocalizationHandler.Get("DivisionGrid"));
		ttpDivisionFesses.setText(LocalizationHandler.Get("DivisionFesses"));
		ttpDivisionPales.setText(LocalizationHandler.Get("DivisionPales"));
		ttpDivisionBendsForward.setText(LocalizationHandler.Get("DivisionBendsForward"));
		ttpDivisionBendsBackward.setText(LocalizationHandler.Get("DivisionBendsBackward"));
		ttpDivisionBendsBoth.setText(LocalizationHandler.Get("DivisionBendsBoth"));
		lblPresets.setText(LocalizationHandler.Get("DivisionPresets"));
				
		lblOverlays.setText(LocalizationHandler.Get("Overlays"));
		ttpOverlayAddNew.setText(LocalizationHandler.Get("OverlayAdd"));
		LoadBasicPresets();
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
	
	private void HookUpEvents()
	{
		txtName.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.equals(oldValue))
			{
				SetAsUnsaved();
			}
		});
		
		_stage.setOnCloseRequest(event -> OnClosing(event));
	}

	private void SetTitle()
	{
		String title = String.format("%s%s%s",
				StringExtensions.IsNullOrWhitespace(_filename)
						? LocalizationHandler.Get("Untitled")
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
		if (_isLoading) return;
		
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

		if (!isLoading)
		{
			SetOverlayMargins();
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
		boolean skip2 = _division instanceof DivisionGrid &&
				divisionSlider1.getValue() == 1 &&
				divisionSlider2.getValue() == 1;
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

		return new Ratio(value);
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
	public void SetAsUnsaved()
	{
		_isUnsaved = true;
		SetTitle();
	}

	public void Draw()
	{
		if (!_isLoading)
		{
			Flag().Draw(_pane);
			DrawTexture(_pane);
			DrawGrid();
		}
	}

	private void DrawTexture(Pane canvas)
	{
		if (_texture == 0) return;
		
		double width = canvas.getWidth();
		double height = canvas.getHeight();
		InputStream is = MainWindowController.class.getResourceAsStream(String.format("Images/Texture/%d.png", _texture));
		Image i = new Image(is, width, height, false, true);
		Canvas c = new Canvas(width, height);
		GraphicsContext gc = c.getGraphicsContext2D();
		gc.drawImage(i, 0, 0);
		canvas.getChildren().add(c);
	}

	@FXML
	private void ToggleTexture()
	{
		_texture = (_texture + 1) % 6;
		Draw();
	}

	// Export
	public void MenuExportPngClick()
	{
		Size dimensions = GetPngDimensions(true);
		if (dimensions.X == 0 || dimensions.Y == 0) return;
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(LocalizationHandler.Get("ExportAsPng"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("PNG (*.png)", "*.png"));
		File file = fileChooser.showSaveDialog(_stage);
		
		if (file != null)
		{
			Flag().ExportToPng(dimensions, file);
		}
	}

	private Size GetPngDimensions(boolean constrain)
	{
		Dialog<Size> dialog = new Dialog<>();
		dialog.setTitle(LocalizationHandler.Get("ExportAsPng"));
		dialog.setHeaderText("Enter desired PNG size, in pixels"); // TODO
		ButtonType saveButtonType = new ButtonType(LocalizationHandler.Get("Save"), ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);
		
		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField width = new TextField();
		width.setPromptText(LocalizationHandler.Get("Width"));
		TextField height = new TextField();
		height.setPromptText(LocalizationHandler.Get("Height"));

		grid.add(new Label(LocalizationHandler.Get("Width")), 0, 0);
		grid.add(width, 1, 0);
		grid.add(new Label(LocalizationHandler.Get("Height")), 0, 1);
		grid.add(height, 1, 1);

		// Enable/Disable login button depending on whether a username was entered.
		Node saveButton = dialog.getDialogPane().lookupButton(saveButtonType);
		saveButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		width.textProperty().addListener((observable, oldValue, newValue) -> {
			saveButton.setDisable(newValue.trim().isEmpty() || height.getText().isEmpty() || !CanParseInt(newValue));
			
			if (CanParseInt(newValue) && constrain)
			{
				Integer w = Integer.parseInt(newValue);
				Ratio r = SelectedGridSize();
				height.setText(Integer.toString((int)(r.Height / (double)r.Width * w)));
			}
		});
		
		height.textProperty().addListener((observable, oldValue, newValue) -> {
			saveButton.setDisable(newValue.trim().isEmpty() || width.getText().isEmpty() || !CanParseInt(newValue));
			
			if (CanParseInt(newValue) && constrain)
			{
				Integer h = Integer.parseInt(newValue);
				Ratio r = SelectedGridSize();
				width.setText(Integer.toString((int)(r.Width / (double)r.Height * h)));
			}
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> width.requestFocus());

		// Convert the result to a username-password-pair when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == saveButtonType) {
				return new Size(Integer.parseInt(width.getText()), Integer.parseInt(height.getText()));
			}
			return null;
		});

		Optional<Size> result = dialog.showAndWait();
		return result.isPresent() ? result.get() : new Size(0, 0);
	}

	public void MenuExportSvgClick()
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(LocalizationHandler.Get("ExportAsSVG"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("SVG (*.svg)", "*.svg"));
		File file = fileChooser.showSaveDialog(_stage);
		
		if (file != null)
		{
			Flag().ExportToSvg(file);
		}
	}

	@FXML private void MenuExportBulkPngClick()
	{
		boolean error = false;
		List<File> files = GetFlagFiles();
		if (files == null || files.isEmpty()) return;
		
		Size dimensions = GetPngDimensions(false);
		if (dimensions.X == 0 || dimensions.Y == 0) return;
		
		File directory = GetBulkSaveDirectory(files.get(0).getParentFile());
		if (!directory.exists() || !directory.canWrite()) return;
		
		for (File file : files)
		{
			try
			{
				Flag flag = FileHandler.LoadFromFile(file);
				flag.ExportToPng(dimensions, new File(String.format("%s\\%s.png", directory, StringExtensions.GetFilenameWithoutExtension(file.getName()))));
			}
			catch (Exception ex)
			{
				error = true;
			}
		}
		
		ExportFinished(error);
	}
	
	@FXML private void MenuExportBulkSvgClick()
	{
		boolean error = false;
		List<File> files = GetFlagFiles();
		if (files == null || files.isEmpty()) return;
		
		File directory = GetBulkSaveDirectory(files.get(0).getParentFile());
		if (!directory.exists() || !directory.canWrite()) return;
		
		for (File file : files)
		{
			try
			{
				Flag flag = FileHandler.LoadFromFile(file);
				flag.ExportToSvg(new File(String.format("%s\\%s.svg", directory, StringExtensions.GetFilenameWithoutExtension(file.getName()))));
			}
			catch (Exception ex)
			{
				error = true;
			}
		}
		
		ExportFinished(error);
	}
	
	private List<File> GetFlagFiles()
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(LocalizationHandler.Get("SelectFiles"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter(LocalizationHandler.Get("FlagFileFilter"), "*.flag"));
		return fileChooser.showOpenMultipleDialog(_stage);
	}
	
	private File GetBulkSaveDirectory(File defaultDirectory)
	{
		DirectoryChooser dc = new DirectoryChooser();
		dc.setInitialDirectory(defaultDirectory);
		dc.setTitle(LocalizationHandler.Get("SelectDirectory"));
		return dc.showDialog(_stage);
	}

	private void ExportFinished(boolean errorOccurred)
	{
		if (errorOccurred)
		{
			new Alert(AlertType.ERROR, LocalizationHandler.Get("ExportBulkError"), ButtonType.OK).showAndWait();
		}
		else
		{
			new Alert(AlertType.INFORMATION, LocalizationHandler.Get("ExportBulkSuccess"), ButtonType.OK).showAndWait();
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
		txtName.setText(LocalizationHandler.Get("Untitled"));
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
		}
		
		_isUnsaved = false;
		LoadFilePresets();
	}
	
	@FXML private void SaveAs()
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save");
		fileChooser.getExtensionFilters().add(new ExtensionFilter(LocalizationHandler.Get("FlagFileFilter"), "*.flag"));
		File file = fileChooser.showSaveDialog(_stage);
		
		if (file != null)
		{
			_filename = file.getPath();
			SaveFlag();
		}	
	}

	@FXML private void Open()
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(LocalizationHandler.Get("Open"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter(LocalizationHandler.Get("FlagFileFilter"), "*.flag"));
		File file = fileChooser.showOpenDialog(_stage);
		LoadFlagFromFile(file);
	}

	private boolean CheckUnsaved()
	{
		if (!_isUnsaved) return false;

		Alert alert = new Alert(AlertType.CONFIRMATION);
		ButtonType buttonYes = new ButtonType(LocalizationHandler.Get("Yes"));
		ButtonType buttonNo = new ButtonType(LocalizationHandler.Get("No"));
		ButtonType buttonCancel = new ButtonType(LocalizationHandler.Get("Cancel"), ButtonData.CANCEL_CLOSE);
		alert.setTitle(GetNameWithVersion());
		alert.setHeaderText(LocalizationHandler.Get("NotSaved"));
		alert.setContentText(String.format(LocalizationHandler.Get("SaveChangesPrompt"), txtName.getText()));
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
			LoadFlag(FileHandler.LoadFromFile(filename));
			_filename = filename.getPath();
		}
		catch (Exception e)
		{
			new Alert(AlertType.ERROR, String.format(LocalizationHandler.Get("CouldNotOpenError"), e.getMessage()), ButtonType.OK).showAndWait();
		}
	}

	private void LoadFlag(Flag flag)
	{
		_isLoading = true;
		_ratio = flag.Ratio;
		txtRatioHeight.setText(Integer.toString(flag.Ratio.Height));
		txtRatioWidth.setText(Integer.toString(flag.Ratio.Width));
		FillGridCombobox();
		
		for (int i = 0; i < cmbRatio.getItems().size(); i++)
		{
			if (new Ratio(cmbRatio.getItems().get(i).toString()).Width != flag.GridSize.Width)
				continue;
			
			cmbRatio.getSelectionModel().select(i);
			break;
		}
		
		_division = flag.Division;
		SetDivisionVisibility();
		
		lstOverlays.getChildren().clear();
		for (Overlay overlay : flag.Overlays)
		{
			OverlayAdd(lstOverlays.getChildren().size(), overlay, true);
		}
		
		txtName.setText(flag.Name);
		_isUnsaved = false;
		_isLoading = false;
		
		Draw();
		
		for (Node n : lstOverlays.getChildren())
		{
			((OverlayControl)n).IsLoading = false;
		}
	}

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
			Object item = cmbRatio.getItems().get(i);
			Ratio ratio = new Ratio((String)item);
			if (ratio.Width >= 7)
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
		cmbPresets.getSelectionModel().clearSelection();
	}

	private void LoadBasicPresets()
	{
		cmbPresets.getItems().clear();
		cmbPresets.getItems().add(LocalizationHandler.Get("DivisionBlank"));
		cmbPresets.getItems().add(LocalizationHandler.Get("DivisionHorizontalHalves"));
		cmbPresets.getItems().add(LocalizationHandler.Get("DivisionVerticalHalves"));
		cmbPresets.getItems().add(LocalizationHandler.Get("DivisionQuartered"));
		cmbPresets.getItems().add(LocalizationHandler.Get("DivisionStripes"));

		cmbPresets.valueProperty().addListener(new ChangeListener<String>()
		{
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
			{
				if (StringExtensions.IsNullOrWhitespace(newValue)) return;
				else if (newValue.equals(LocalizationHandler.Get("DivisionBlank"))) PresetBlank();
				else if (newValue.equals(LocalizationHandler.Get("DivisionHorizontalHalves"))) PresetHorizontal();
				else if (newValue.equals(LocalizationHandler.Get("DivisionVerticalHalves"))) PresetVertical();
				else if (newValue.equals(LocalizationHandler.Get("DivisionQuartered"))) PresetQuad();
				else if (newValue.equals(LocalizationHandler.Get("DivisionStripes"))) PresetStripes();
			}
		});
	}
	
	private void LoadFilePresets()
	{
		mnuPresets.getItems().clear();
		
		try
		{
			File directory = new File(new File(MainWindowController.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent() + "\\Presets");
			File[] files = directory.listFiles();
			if (files == null) return;
						
			Map<String, File> presets = new HashMap<>();
			
			for (File fileEntry : files)
			{
				if (fileEntry.getName().endsWith(".flag"))
				{
					String name = GetPresetFlagName(fileEntry);
					
					presets.put(name, fileEntry);
				}
			}
			
			SortedSet<String> names = new TreeSet<>(presets.keySet());
						
			for (String name : names)
			{
				File fileEntry = presets.get(name);
				if (!StringExtensions.IsNullOrWhitespace(name))
				{
					MenuItem item = new MenuItem(name);
					item.addEventHandler(EventType.ROOT, (Event event) ->
					{
						LoadPreset(fileEntry);
					});
					mnuPresets.getItems().add(item);
				}
			}
		}
		catch (URISyntaxException ex)
		{
		}
	}

	private void LoadPreset(File file)
	{
		if (CheckUnsaved()) return;
		LoadFlagFromFile(file);
		SetTitle();
	}

	private String GetPresetFlagName(File file)
	{
		try (FileReader fr = new FileReader(file); BufferedReader sr = new BufferedReader(fr))
		{
			String line;
			while ((line = sr.readLine()) != null)
			{
				if (line.startsWith("name="))
				{
					return line.split("=")[1];
				}
			}
		}
		catch (Exception e)
		{
			return "";
		}
		
		return "";
	}

	@FXML
	private void GenerateRandomFlag()
	{
		if (CheckUnsaved()) return;
		Flag f = new RandomFlagFactory().GenerateFlag();
		LoadFlag(f);
		_filename = "";
		SetTitle();
	}

	private void OnClosing(WindowEvent event)
	{
		if (CheckUnsaved())
		{
			event.consume();
		}
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
	
	private String GetNameWithVersion()
	{
		return String.format("FlagMaker %s", getClass().getPackage().getImplementationVersion());
	}
}
