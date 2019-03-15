package typespeed_game;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import model.Word;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Simun
 */
public class FXMLGameController extends Application implements Initializable {

//    public FXMLGameController(UpdateScreen updateScreen) {
//        this.updateScreen = updateScreen;
//    }
    
    
    FXMLGameController gc;
    private static final String FILE_PATH = "C:\\Users\\Simun\\Desktop\\Typespeed\\Typespeed_Game\\words.ser";
    public static Map<String, Word> words = new HashMap<String, Word>();
    private static final String TITLE = "Game controller";
    public int currentScore = 0;

    @FXML
    private BorderPane borderPane;
    @FXML
    private Pane gamePane;
    @FXML
    private AnchorPane ap;
    @FXML
    private TextField inputBox;
    @FXML
    private Button back;
//    @FXML
//    private Image image;        
    //Pane parent;
    UpdateScreen updateScreen;
    Parent root = null;
    Stage myStage;
      private volatile boolean running = true;
      private volatile boolean paused = false;
      private final Object pauseLock = new Object();
      
    @FXML
    private void back(javafx.event.ActionEvent event)throws IOException{

//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLMainMenu.fxml"));
//        Parent root = (Parent) fxmlLoader.load();
//
//        FXMLMainMenuController menuController = fxmlLoader.<FXMLMainMenuController>getController();
//        //Parent root = FXMLLoader.load(getClass().getResource("FXMLGame.fxml"));
//        Scene scene = new Scene(root);
//
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.setResizable(false);
//        stage.show();

        //Stage thisStage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        //thisStage.close();
        //myStage.close();

    }
      
    @Override
    public void start(Stage stage) throws Exception {
        myStage = stage;
        inputBox.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                //System.out.println(userInput.getText());
                Word word = Word.words.get(inputBox.getText());
                inputBox.setText("");
                if (word != null) {
                    updateScreen.guessedWord(word);
                }
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        inputBox.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                if (updateScreen.gameState == GameState.PLAYING) {
                    updateScreen.gameState = GameState.PAUSED;
                } else if (updateScreen.gameState == GameState.PAUSED) {
                    updateScreen.gameState = GameState.PLAYING;
                }
            }
            
            if (event.getCode() == KeyCode.ENTER) {
                //System.out.println(userInput.getText());
                Word word = Word.words.get(inputBox.getText());
                inputBox.setText("");
                if (word != null) {
                    updateScreen.guessedWord(word);
                    //enter sound
                }
            }
        });

        updateScreen = new UpdateScreen(gamePane);
        String placeholder = "Type here";
        inputBox.setText(placeholder);
        updateScreen.setup();

        

     //Platform.runLater(updateScreen);
        
        //Implement multi-threading
       
        Thread mojThread = new Thread(new Runnable() {
            @Override
            public void run() {

                    while(running){
                        synchronized (pauseLock) {
                            
                            if (!running) {
                                break;
                            }
                            if (paused) {
                                try {
                                pauseLock.wait(); 
                            } catch (InterruptedException ex) {
                                break;
                            }
                            if (!running) { // running might have changed since we paused
                                break;
                            }
                            }
                  Platform.runLater(updateScreen);
                     try{
                          Thread.sleep(50);
                        } catch (InterruptedException ex) {
                        }
                    if (updateScreen.gameState == GameState.PAUSED) {
                     pause();                     
                    }
                        }         
                }

                //System.exit(0); 
            }
                    public void stop(){
                        running = false;
                        
                        resume();
                    }       
                    
                    public void pause(){
                        paused = true;
                                   Platform.runLater(()->{
                         
//                         try {
                    Alert alert = new Alert(AlertType.CONFIRMATION, 
                        "Game paused. Do you want to save?", 
                        ButtonType.YES, ButtonType.CLOSE);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.YES){
                        try {
                            saveGame();
                            alert.close();
                            updateScreen.gameState = GameState.PLAYING;
                            resume();
                        } catch (CloneNotSupportedException ex) {
                            Logger.getLogger(FXMLGameController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       resume();
                    } else {
                        
                        alert.close();
                        updateScreen.gameState = GameState.PLAYING;
                        resume();
                       
                    }  
                     });
                    }
                    public void resume(){
                        synchronized(pauseLock){
                            paused = false;
                            pauseLock.notifyAll();
                        }
                    }

            private void saveGame() throws CloneNotSupportedException {
                 Word.loadWords = false;
                 try (ObjectOutputStream stream = new ObjectOutputStream(
                new FileOutputStream(FILE_PATH))) {
                    words = Word.getWords();
                    stream.writeObject(words);
        }       catch (FileNotFoundException ex) { 
                    Logger.getLogger(FXMLGameController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLGameController.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
                           
                            });
        mojThread.setDaemon(true);
        mojThread.start();
        //myStage.close();

    }

}
//                while(ap.isVisible()){
//                     Platform.runLater(updateScreen);
//                     try{
//                          Thread.sleep(5000);
//                        } catch (InterruptedException ex) {
//                        }
//                    if (updateScreen.gameState == GameState.PAUSED) {
//                    Platform.runLater(()->{
//                         
////                         try {
//                    Alert alert = new Alert(AlertType.CONFIRMATION, 
//                        "File already exists. Do you want to override?", 
//                        ButtonType.YES, ButtonType.NO);
//
//                    Optional<ButtonType> result = alert.showAndWait();
//                    if (result.get() == ButtonType.YES){
//                        // ... user chose YES
//                    } else {
//                        
//                        alert.close();
//                        //updateScreen.gameState = GameState.PLAYING;
//                    }



                             
//                               Parent root;         
//                                    root = FXMLLoader.load(getClass().getResource("FXMLMainMenu.fxml"));
//                                    
//                                    Scene scene = new Scene(root);
//                                    //scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
//                                    Stage stage = new Stage();
//                                    stage.setScene(scene);
//                                    stage.setTitle("Typespeed Game");
//                                    stage.setScene(scene);
//                                    stage.setResizable(false);
//                                    stage.show();
//    
//                                } catch (IOException ex) {
//                                    Logger.getLogger(FXMLGameController.class.getName()).log(Level.SEVERE, null, ex);
//                                }