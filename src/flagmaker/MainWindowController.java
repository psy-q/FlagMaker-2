package flagmaker;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import flagmaker.Divisions.*;
import flagmaker.Overlays.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

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
		canvas.styleProperty().set("-fx-background-color: #eeffee;");
		canvas.minHeight(200);
		canvas.minWidth(300);
		canvas.prefHeight(200);
		canvas.prefWidth(300);
		canvas.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
		canvas.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		subscene = new SubScene(leftAnchor, 300, 200);
		subscene.setRoot(canvas);
		subscene.setFill(Color.RED);
		
		leftStack.getChildren().add(subscene);
		
		subscene.widthProperty().bind(Bindings.createDoubleBinding(() -> leftStack.getWidth() - 10, leftStack.widthProperty(), leftStack.heightProperty()));
		subscene.heightProperty().bind(Bindings.createDoubleBinding(() -> (leftStack.getWidth() - 10) * ratio.Height / ratio.Width, leftStack.widthProperty(), leftStack.heightProperty()));
		
		RatioTextboxChanged();

		flag = new Flag("", new Ratio(2, 3), new Ratio(2, 3),
				new DivisionPales(new Color[]{ Color.RED, Color.WHITE, Color.BLUE }, new Double[]{ 1.0, 1.0, 1.0 }),
				null);
		Draw();
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
		
//		double ratio = height / (double)width;
//		double width2 = leftPane.getWidth();
//		double height2 = width2 * ratio;
//		subscene.prefHeight(height2);
//		subscene.prefWidth(width2);
//		subscene.setClip(new Rectangle(width2, height2));
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
