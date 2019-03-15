package typespeed_game;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Word;

/**
 * FXML Controller class
 *
 * @author Simun
 */
public class FXMLMainMenuController implements Initializable {

    private static final String TITLE = "Typespeed Game";
    UpdateScreen updateScreen;
    @FXML
    private Button btnPlayGame;
    @FXML
    private Button btnExit;
    @FXML
    private Button btnLeaderboard;
    @FXML
    private Button btnLoadGame;
    @FXML
    FXMLGameController gc;
    private static final String FILE_PATH = "C:\\Users\\Simun\\Desktop\\Typespeed\\Typespeed_Game\\words.ser";
    
    @FXML
    private void loadGame(javafx.event.ActionEvent event)throws IOException{
        Word.loadWords = true; 
        play(event);
    }
    
    @FXML
    private void exitGame() {
        System.exit(0);
    }

    @FXML
    private void playGame(javafx.event.ActionEvent event) throws IOException {
        Word.loadWords = false;
        //updateScreen.gameState = GameState.PLAYING;
        play(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private void play(javafx.event.ActionEvent event)throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLGame.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        FXMLGameController gameController = fxmlLoader.<FXMLGameController>getController();
        //Parent root = FXMLLoader.load(getClass().getResource("FXMLGame.fxml"));
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        Stage thisStage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        thisStage.close();
    }

}
