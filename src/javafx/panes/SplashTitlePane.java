package javafx.panes;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class SplashTitlePane extends Pane {

    private Rectangle BLACK;
    private Rectangle SPLASH_BACKGROUND;
    private Color SPLASH_SCREEN_COLOUR;

    public SplashTitlePane(double w, double h){
        //Initialize JFX objects
        BLACK = new Rectangle(w, h, Color.BLACK);

        SPLASH_SCREEN_COLOUR = Color.AQUA; // Change this to whatever, AQUA is just a placeholder.
        SPLASH_BACKGROUND = new Rectangle(w, h, SPLASH_SCREEN_COLOUR);
        SPLASH_BACKGROUND.setOpacity(0);

        this.getChildren().addAll(BLACK, SPLASH_BACKGROUND);
    }

    public void run(){
        // Begin transition
        SequentialTransition fade = new SequentialTransition();

        // Fade in (fi)
        FadeTransition fi = new FadeTransition(Duration.millis(2000), SPLASH_BACKGROUND);
        fi.setFromValue(0.0);
        fi.setToValue(1.0);

        FadeTransition rb = new FadeTransition(Duration.millis(1), BLACK);
        rb.setFromValue(1.0);
        rb.setToValue(0.0);

        FadeTransition fo = new FadeTransition(Duration.millis(600), SPLASH_BACKGROUND);
        fo.setFromValue(1.0);
        fo.setToValue(0.0);

        fade.setOnFinished(e -> {
            this.toBack();
        });

        fade.getChildren().addAll(fi, rb, fo);
        fade.play();


    }

}
