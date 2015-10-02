package flagmaker;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import flagmaker.Divisions.*;
import flagmaker.Overlays.*;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Insets;

public class MainWindowController
{
	@FXML
	private TextField txtRatioHeight;
	@FXML
	private TextField txtRatioWidth;
	@FXML
	private ComboBox cmbRatio;
	
	@FXML
	private AnchorPane leftPane;
	
	private SubScene subscene;
	private Pane canvas;
	private Flag flag;

	@FXML
	protected void initialize()
	{
		canvas = new Pane();
		canvas.styleProperty().set("-fx-background-color: #eeffee;");
		canvas.prefHeight(200);
		canvas.prefWidth(300);
		AnchorPane panel = new AnchorPane();
		panel.getChildren().add(canvas);
		panel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		panel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		subscene = new SubScene(leftPane, 300, 200);
		//Scene scene = new Scene(panel);
		subscene.setRoot(panel);
		leftPane.getChildren().add(subscene);
		
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
		
		cmbRatio.getSelectionModel().select(0);
		
		double ratio = height / (double)width;
		double width2 = leftPane.getWidth();
		double height2 = width2 * ratio;
		//subscene.prefHeight(height2);
		//subscene.prefWidth(width2);
		//subscene.setClip(new Rectangle(width, height2));
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
