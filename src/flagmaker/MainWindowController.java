package flagmaker;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import flagmaker.Divisions.*;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.SubScene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

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
	private Pane canvas;
	private Flag flag;
	private Ratio ratio;

	@FXML
	protected void initialize()
	{
		canvas = new Pane();
		subscene = new SubScene(leftAnchor, 300, 200);
		subscene.setRoot(canvas);
		leftStack.getChildren().add(subscene);

		// Draw whenever the left side changes size
		leftStack.widthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> Draw()); // lags
		cmbRatio.valueProperty().addListener(new ChangeListener<String>(){
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

		flag = new Flag("", new Ratio(2, 3), new Ratio(2, 3), new DivisionPales(new Color[] { Color.GRAY, Color.WHITE, Color.GRAY }, new Double[] { 1.0, 1.0, 1.0 }), null);
		RatioTextboxChanged();
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

		ratio = new Ratio(width, height);
		cmbRatio.getSelectionModel().select(0);
		
		Draw();
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

	private void Draw()
	{
		//_canvas.Width = _ratioWidth * 200;
		//_canvas.Height = _ratioHeight * 200;
		flag.Draw(canvas);
		//DrawTexture(_canvas);
		//DrawGrid();
		//SetUsedColorPalettes();
	}
}
