//Project: Typespeed title
//Author: Šimun
//Starting code copied from tutorial for gamedevelopment in JavaFX: https://gamedevelopment.tutsplus.com/tutorials/introduction-to-javafx-for-game-development--cms-2383
//Koliko traje igra, timer thread za mjerenje vremena.
package typespeed_game;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Typespeed_Game extends Application {

    private static Scene mainScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLMainMenu.fxml"));

        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        mainScene = scene;
        stage.setTitle("Typespeed Game");
        stage.setScene(scene);
        stage.show();
    }

    public static Scene getMainScene() {
        return mainScene;
    }
}
