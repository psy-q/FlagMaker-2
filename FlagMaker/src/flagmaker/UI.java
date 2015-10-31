package flagmaker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UI extends Application
{
	@Override
	public void start(Stage stage) throws Exception
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
		loader.setControllerFactory(type ->
		{
			try
			{
				Object controller = type.newInstance();
				if (controller instanceof MainWindowController)
				{
					((MainWindowController) controller).SetPrimaryStage(stage);
				}
				return controller;
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		});

		VBox root = loader.load();
		Scene scene = new Scene(root);
		stage.getIcons().add(new Image("flagmaker/Images/icon.png"));
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
