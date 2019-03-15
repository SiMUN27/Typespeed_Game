
package typespeed_game;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import model.Word;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static model.Word.loadWords;

/**
 *  
 * @author Simun
 */
public class UpdateScreen implements Runnable {

    private transient Pane parent;
    private int currentScore = 1;
    GameState gameState;
    GameOverScreen gameOverScreen;
    UpdateScreen updateScreen;
    Word guessedWord;
    
    @FXML
    private TextField inputBox;

    private Label score;

    public UpdateScreen(Pane parent) {
        this.parent = parent;
        this.gameState = GameState.PLAYING;
        //this.gameOverScreen = new GameOverScreen(this);
    }

    @Override
    public void run() {

        parent.getChildren().clear();
//
//        if (gameState == GameState.GAMEOVER) {
//            gameOverScreen.show(this.parent);
//            return;
//        }

        //update targets
        Collection<Word> words = Word.getWords().values();
        if (gameState == GameState.PLAYING) {
            for (Word w : words) {
                // update positions and animation state
                w.update();
            }
        }

        //add back the elements 
        addWordsToParent(parent);

//      String currentScoreString = Integer.toString(currentScore);
//      score.setText(currentScoreString);
        if (gameState == GameState.SUCCESS) {
            if (guessedWord != null) {
                Word.words.remove(guessedWord.word);

                //guessedWord = null;
                gameState = GameState.PLAYING;
            }

            if (Word.words.isEmpty()) {
                gameState = GameState.GAMEOVER;
            }
        }
        
        if (gameState == GameState.PAUSED) {
            try {
                Thread.sleep(100);
                
            } catch (InterruptedException ex) {
                Logger.getLogger(UpdateScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setup() {
        setupWords();
    }

    private void setupWords() {
        

         Word.setup();
         if (!loadWords) {
             Word.setupWords(15);
        }
        
        
    }

    private void addWordsToParent(Pane parent) {

        Group g = new Group();

        for (Word w : Word.getWords().values()) {
            Text t = new Text(w.word);
            t.setId("wordStyle");

            double parentHeight = parent.getHeight();
            double parentWidth = parent.getWidth();

            g.getChildren().add(t);
            t.setTranslateX(w.xCor * parentWidth / 100);
            t.setTranslateY(w.yCor * parentHeight / 100);
            w.targetWidth = t.getLayoutBounds().getWidth() / parentWidth * 100;

        }

        g.setManaged(false);
        parent.getChildren().add(g);
    }

    public void guessedWord(Word word) {
        currentScore += 1;
        this.gameState = GameState.SUCCESS;
        guessedWord = word;

    }

}
