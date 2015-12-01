package flagmaker.About;

import flagmaker.Files.LocalizationHandler;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AboutController extends VBox
{
	private Stage _stage;
	@FXML private Tab tabCredits;
	@FXML private Tab tabHistory;
	@FXML private VBox CreditBox;
	@FXML private VBox HistoryBox;

	public AboutController(Stage stage)
	{
		Load(stage);

		stage.titleProperty().set(LocalizationHandler.Get("About"));
		stage.getIcons().add(new Image("flagmaker/Images/icon.png"));
		tabCredits.setText(LocalizationHandler.Get("Credits"));
		tabHistory.setText(LocalizationHandler.Get("History"));
		
		AddCredits();
		AddHistory();
	}
	
	private void AddCredits()
	{
		try
		{
			AddCredit(new URI("https://github.com/andrewsarnold"), "Andrew Arnold", "Development");
			AddCredit(new URI("http://www.reddit.com/user/Hellerick"), "Hellerick Ferlibay", "Russian translation");
			AddCredit(new URI("http://www.reddit.com/user/Tikchbila"), "Tikchbila", "French translation");
			AddCredit(new URI("http://www.reddit.com/user/Cubenity"), "Cubenity", "Polish translation");
			AddCredit(new URI("http://www.crwflags.com/fotw/flags/index.html"), "Flags of the World", "Flag construction specifications and colors");
			AddCredit(new URI("http://en.wikipedia.org/"), "Wikipedia", "Flag construction specifications and colors");
			AddCredit(new URI("https://flag-designer.appspot.com/"), "Lars Ruoff", "Inspiration; eagle, sun, and tree patterns");
		}
		catch (URISyntaxException ex)
		{
		}
	}
	
	private void AddCredit(URI link, String name, String role)
	{
		Hyperlink h = new Hyperlink(name);
		h.setOnAction(event ->
		{
			try
			{
				Desktop.getDesktop().browse(link);
			}
			catch (IOException ex)
			{
			}
		});
		h.setTooltip(new Tooltip(link.toString()));
		Label l = new Label("— " + role);
		HBox b = new HBox();
		b.setAlignment(Pos.CENTER_LEFT);
		b.getChildren().addAll(h, l);
		CreditBox.getChildren().add(b);
	}

	private void AddHistory()
	{
		try
		{
			AddVersion("2.0", "In progress");
			AddFeature("Linux version");
			AddFeature("Mac version");
			AddFeature("Brand-new random flag generation engine");
			AddFeature("Polish translation");
			AddFeature("Can rotate rays overlay");
			AddFeature("Attribute handling overhaul");
			AddFeature("Minor bug fixes");
			
			AddVersion("1.7", LocalDate.of(2014, 8, 25), new URI("http://www.reddit.com/r/vexillology/comments/2ei5wf/flagmaker_17_flagmaker_jr/"));
			AddFeature("Random flag generation");
			AddFeature("Bulk SVG / PNG export");
			AddFeature("Add strokes / outlines to path overlays");
			AddFeature("Toggle overlay visibility for editing purposes");
			AddFeature("New overlay types: Bolnisi cross (Georgia), fire, half-ellipse, line (arbitrary positioning), quadrilateral, ring, shield, snake, wave, yang");
			AddFeature("Toggleable cloth textures");
			AddFeature("Major bug fixes and under-the-hood optimization");
		}
		catch (URISyntaxException ex)
		{
		}
	}
	
	private void AddVersion(String number, String note)
	{
		Label l = new Label(String.format("%s — %s", number, note));
		l.getStyleClass().add("header");
		HistoryBox.getChildren().add(l);
	}
	
	private void AddVersion(String number, LocalDate date, URI link)
	{
		Hyperlink h = new Hyperlink(date.toString());
		h.setOnAction(event ->
		{
			try
			{
				Desktop.getDesktop().browse(link);
			}
			catch (IOException ex)
			{
			}
		});
		h.setTooltip(new Tooltip(link.toString()));
		Label l = new Label(String.format("%s —", number));
		l.getStyleClass().add("header");
		h.getStyleClass().add("header");
		HBox b = new HBox();
		b.setAlignment(Pos.CENTER_LEFT);
		b.getChildren().addAll(l, h);
		HistoryBox.getChildren().add(b);
	}
	
	private void AddFeature(String text)
	{
		Label l = new Label("— " + text);
		l.setWrapText(true);
		HBox b = new HBox();
		b.setAlignment(Pos.CENTER_LEFT);
		b.getChildren().addAll(l);
		HistoryBox.getChildren().add(b);
	}
	
	private void Load(Stage stage)
	{
		_stage = stage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("About.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try
		{
			loader.load();
		}
		catch (Exception ex)
		{
		}
	}
}
