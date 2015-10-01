/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flagmaker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UI extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
	Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));

	Scene scene = new Scene(root);

	stage.setScene(scene);
	stage.titleProperty().setValue("FlagMaker 2.0");
	stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
	launch(args);
    }
}
