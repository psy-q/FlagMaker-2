package flagmaker.About;

import flagmaker.Files.LocalizationHandler;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
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
	@FXML private Tab tabCredits;
	@FXML private Tab tabHistory;
	@FXML private VBox CreditBox;
	@FXML private VBox HistoryBox;

	public AboutController(Stage stage)
	{
		Load(stage);

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
			AddCredit(new URI("https://github.com/cubenity"), "Cubenity", "Polish translation");
			AddCredit(new URI("http://www.crwflags.com/fotw/flags/index.html"), "Flags of the World", "Flag construction specifications and colors");
			AddCredit(new URI("http://en.wikipedia.org/"), "Wikipedia", "Flag construction specifications and colors");
			AddCredit(new URI("https://flag-designer.appspot.com/"), "Lars Ruoff", "Inspiration; eagle, sun, and tree patterns");
			AddUnlinkedCredit("Various emblems created by: PepePateaTraseros, VainRobot, OakBlood3");
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
	
	private void AddUnlinkedCredit(String text)
	{
		Label l = new Label(text);
		HBox b = new HBox();
		b.setAlignment(Pos.CENTER_LEFT);
		b.getChildren().addAll(l);
		CreditBox.getChildren().add(b);
	}

	private void AddHistory()
	{
		AddVersion("2.0", "In progress");
		AddFeature("Linux version");
		AddFeature("Mac version");
		AddFeature("Installer");
		AddFeature("Brand-new random flag generation engine");
		AddFeature("Polish translation");
		AddFeature("Can rotate rays overlay");
		AddFeature("New emblem: Chicago star");
		AddFeature("Attribute handling overhaul");
		AddFeature("Minor bug fixes");

		AddVersion("1.7", "2014-08-25");
		AddFeature("Random flag generation");
		AddFeature("Bulk SVG / PNG export");
		AddFeature("Add strokes / outlines to path overlays");
		AddFeature("Toggle overlay visibility for editing purposes");
		AddFeature("New overlay types: Half-ellipse, line (arbitrary positioning), quadrilateral, ring");
		AddFeature("New emblems: Bolnisi cross (Georgia), fire, shield, snake, wave, yang");
		AddFeature("Toggleable cloth textures");
		AddFeature("Major bug fixes and under-the-hood optimization");

		AddVersion("1.6", "2014-02-15");
		AddFeature("Popup window for selecting overlays instead of cluttered dropdown");
		AddFeature("New overlay type: Transformer (arbitrary skewing, scaling, and rotation)");
		AddFeature("New overlay type: Image (insert any JPG or PNG)");
		AddFeature("New overlay type: Checkerboard");
		AddFeature("Triangle overlays can now be positioned anywhere (this WILL break some saved flags!)");
		AddFeature("Overlay sliders retain their values when switching types");
		AddFeature("Overlay sliders accept percentage and fractional inputs");
		AddFeature("Updating crescent overlay rendering (this WILL break some saved flags!)");
		AddFeature("Improved SVG output for smaller file sizes");
		AddFeature("Overlays no longer overflow the flag work area and cover other parts of the program");
		AddFeature("French language support");
		AddFeature("Fixed bug with multiple custom overlays of the same type not all being rendered");
		AddFeature("Fixed bug where exporting SVG with repeaters included the repeated overlay on its own");
		
		AddVersion("1.5.1", "2014-02-05");
		AddFeature("Fixed a few custom overlay bugs");
		AddFeature("Fixed a bug where pall overlays crashed on some cultures (the dreaded comma-as-decimal-separator bug)");
		AddFeature("Reducing decimal points to 3 on SVG output");
		AddFeature("Russian language support");
		
		AddVersion("1.5", "2014-02-02");
		AddFeature("Custom overlay functionality");
		AddFeature("Updated lines, border, cross, saltires, and fimbriations to line up with the grid (this WILL break some saved flags!)");
		AddFeature("All currently-used colors (custom or not) displayed in palette");
		AddFeature("Button to quickly shuffle around the colors used");
		AddFeature("New emblems: American eagle, anchor, angola, cedar (Lebanon), chakra (India), Communist party of the USA logo, coronet (Swedish crown), crown, Egyptian eagle, emblem of Iran, flash (lightning bolt), Forth International logo, hand, harp (Ireland), iron cross, kangaroo, kiwi, laurel wreath, Maltese cross, Mozambique hoe and rifle, olive branches (Cyprus), Papua New Guinea, parteiadler / reichsadler, shahadah, Sikh emblem, springbok, swastika, sword (Saudi Arabia), takbir, trident (Barbados), triskele, yin");
		AddFeature("Support for different languages");
		AddFeature("Spanish translation");
		AddFeature("Fixed major bug where the program would crash on computers using comma-decimal cultures");
		AddFeature("Fixed similar bug when reading .flag files");
		AddFeature("Fixed bug when loading a broken flag file");
		AddFeature("Fixed line rendering bugs when exporting SVG");
		
		AddVersion("1.4.1", "2013-12-04");
		AddFeature("Fixed fimbriation SVG export bug");
		
		AddVersion("1.4", "2013-12-04");
		AddFeature("Custom PNG dimensions on export");
		AddFeature("More robust file handling");
		AddFeature("Updated triangle overlay to have variable height");
		AddFeature("Shortcut keys for new, open, save, save as");
		AddFeature("New overlay type: Rays");
		AddFeature("New emblems: Fleur-de-lis, eagle, sun, tree, ermine");
		AddFeature("Box for editing the name for a .flag file");
		AddFeature("Fixed vertical alignment on star overlays");
		AddFeature("Fixed main icon");
		AddFeature("Fancy new icons for overlay controls");
		AddFeature("More world flag presets");
		
		AddVersion("1.3", "2013-11-09");
		AddFeature("Overlay sliders dealing with vertical alignment now use the grid's vertical size for its discrete values");
		AddFeature("Changed \"canton\" to \"box\"; now freely positionable");
		AddFeature("Changed the corner angles of saltires and fimbriations to have the same proportions as the flag itself");
		AddFeature("Click-to-edit slider values for overlays");
		AddFeature("Alignment grid (toggleable)");
		AddFeature("New overlay type: Repeaters (lateral and radial)");
		AddFeature("New overlay type: Flag (embed another FlagMaker flag into the editor)");
		AddFeature("New overlay type: Half saltire (as in the United Kingdom flag)");
		AddFeature("New overlay type: Border");
		AddFeature("New emblems: Four-pointed star, six-pointed star, eight-pointed star");
		AddFeature("Fixed a crashing bug that occurred on invalid ratio entry");
		AddFeature("Lots more world flag presets");
		AddFeature("Spruced up the Readme a bit");
		AddFeature("Behind-the-scenes refactoring and streamlining");
		
		AddVersion("1.2", "2013-10-07");
		AddFeature("Downgraded to .NET 4.0 for increased compatibility");
		AddFeature("Fixed star overlay");
		AddFeature("New overlay types: Horizontal line, vertical line");
		AddFeature("New emblems: Equitorial (Swiss) cross, maple leaf, pentagram, seven-pointed star, star of David");
		
		AddVersion("1.1", "2013-09-30");
		AddFeature("SVG export");
		AddFeature("Blank / basic presets");
		AddFeature("Grey background (for easily seeing white flags)");
		AddFeature("Additional color palette");
		AddFeature("Improved overlay engine");
		AddFeature("New emblems: Hammer and sickle, crescent");
		AddFeature("Overlay cloning");
		AddFeature("Overlay slider labels");
		AddFeature("Continuous overlay sliders");
		AddFeature("Adjustable workspace size");
		AddFeature("Icon");
		
		AddVersion("1.0", "2013-09-09");
		AddFeature("Initial release");
	}
	
	private void AddVersion(String number, String note)
	{
		Label l = new Label(String.format("%s — %s", number, note));
		l.getStyleClass().add("header");
		HistoryBox.getChildren().add(l);
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
		FXMLLoader loader = new FXMLLoader(getClass().getResource("About.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		stage.titleProperty().set(LocalizationHandler.Get("About"));
		stage.getIcons().add(new Image("flagmaker/Images/icon.png"));
		
		try
		{
			loader.load();
		}
		catch (Exception ex)
		{
		}
	}
}
