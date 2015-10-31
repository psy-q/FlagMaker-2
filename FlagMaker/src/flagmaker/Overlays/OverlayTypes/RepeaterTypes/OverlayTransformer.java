package flagmaker.Overlays.OverlayTypes.RepeaterTypes;

import flagmaker.Overlays.Attributes.Attribute;
import flagmaker.Overlays.Attributes.DoubleAttribute;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Shear;
import javafx.scene.transform.Transform;

public class OverlayTransformer extends OverlayRepeater
{
    public OverlayTransformer(int maximumX, int maximumY)
    {
	super("transformer", new Attribute[]
	{
	    new DoubleAttribute("X", 1, maximumX, true),
	    new DoubleAttribute("Y", 1, maximumY, false),
	    new DoubleAttribute("SkewX", maximumX / 2.0, maximumX, true),
	    new DoubleAttribute("SkewY", maximumY / 2.0, maximumY, false),
	    new DoubleAttribute("Width", 1, maximumX, true),
	    new DoubleAttribute("Height", 1, maximumY, false),
	    new DoubleAttribute("Rotation", 0, maximumX, true),
	}, maximumX, maximumY);
    }

    @Override
    protected Shape[] Thumbnail()
    {
	return new Shape[]
	{
	    new Polygon(10, 10, 25, 10, 20, 20, 5, 20)
	};
    }

    @Override
    public void Draw(Pane canvas)
    {
		if (Overlay == null) return;
		if (!Overlay.IsEnabled) return;
		
		AnchorPane a = new AnchorPane();
		a.setBackground(Background.EMPTY);
		Scene s = new Scene(a, canvas.getWidth(), canvas.getHeight());
		Pane p = new Pane();
		p.setBackground(Background.EMPTY);
		s.setRoot(p);
		
		p.getTransforms().add(GetTransformation((int)canvas.getWidth(), (int)canvas.getHeight()));

		Overlay.Draw(p);
		canvas.getChildren().add(p);
    }

    @Override
    public String ExportSvg(int width, int height)
    {
		if (Overlay == null) return "";
		if (!Overlay.IsEnabled) return "";

		Transform matrix = GetTransformation(width, height);

		return String.format("<g transform=\"matrix(%.3f,%.3f,%.3f,%.3f,%.3f,%.3f)\">%s</g>",
			matrix.getMxx(),
			matrix.getMxy(),
			matrix.getMyx(),
			matrix.getMyy(),
			matrix.getTx(),
			matrix.getTy(),
			Overlay.ExportSvg(width, height));
    }
	
	private Transform GetTransformation(int width, int height)
	{
		double centerX = width * GetDoubleAttribute("X") / MaximumX;
		double centerY = height * GetDoubleAttribute("Y") / MaximumY;

		double skewX = 90 * (GetDoubleAttribute("SkewX") - MaximumX / 2.0) / MaximumX;
		double skewY = 90 * (GetDoubleAttribute("SkewY") - MaximumY / 2.0) / MaximumY;

		double scaleX = GetDoubleAttribute("Width");
		double scaleY = GetDoubleAttribute("Height");

		double rotation = (GetDoubleAttribute("Rotation") / MaximumX) * 360;
		
		Shear skewTransform = new Shear(skewX, skewY, centerX, centerY);
		Rotate rotateTransform = new Rotate(rotation, centerX, centerY);
		Scale scaleTransform = new Scale(scaleX, scaleY, centerX, centerY);

		return rotateTransform.createConcatenation(scaleTransform).createConcatenation(skewTransform);
	}
}
