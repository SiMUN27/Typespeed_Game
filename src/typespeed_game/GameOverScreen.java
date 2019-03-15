
package typespeed_game;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Simun
 */
class GameOverScreen implements Serializable{

    private Button restartButton;

    //Build the game over screen
    public GameOverScreen(UpdateScreen updateGame) {
        restartButton = new Button("PLAY AGAIN");
        restartButton.setId("buttonStyleRestart");
        restartButton.setOnMousePressed(new EventHandler<MouseEvent>() //restart the game when pressed
        {
            @Override
            public void handle(MouseEvent me) {
                updateGame.setup(); //create the main canvas 

                updateGame.gameState = GameState.PLAYING; //change state of the game 

            }
        });
    }

    //Make the image and button show on the ending screen
    public void show(Pane parent) {
        StackPane stackPane = new StackPane();
        Group gButton = new Group();
        gButton.getChildren().add(restartButton);
        stackPane.getChildren().addAll(gButton);
        parent.getChildren().add(stackPane);
        gButton.setTranslateY(parent.getHeight() / 2 - restartButton.getLayoutBounds().getHeight() - 50);
    }
}
