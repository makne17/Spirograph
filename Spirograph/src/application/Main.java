package application;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


public class Main extends Application {
	
	private Orbit parentOrbit;
	private Orbit lastOrbit;
	private List<double[]> path = new ArrayList<>();
    
	@Override
	public void start(Stage primaryStage) {
        try {


            // Circle properties
            int radius = 200;  // Radius of the large circle

            // Create a Canvas
            Canvas canvas = new Canvas(Constants.canvasWidth, Constants.canvasHeight);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);

            // Center of the canvas (center of the large circle)
            int centerX = Constants.canvasWidth / 2;
            int centerY = Constants.canvasHeight / 2;

            parentOrbit = new Orbit(radius, centerX, centerY, 0);
            Orbit nextOrbit = parentOrbit;
            for(int i = 0; i < 11; i++) {
            	nextOrbit = nextOrbit.addChild();
            }
            lastOrbit = nextOrbit;
            
            AnimationTimer animationTimer = new AnimationTimer() {
				@Override
				public void handle(long arg0) {
					draw(gc);		
				}

			};
			animationTimer.start();
            
            // Set up the scene and stage
            StackPane root = new StackPane(canvas);
            Scene scene = new Scene(root, Constants.canvasWidth, Constants.canvasHeight);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Spirograph");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	protected void draw(GraphicsContext gc) {
        // Clear the canvas
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, Constants.canvasWidth, Constants.canvasHeight);
        
        gc.setStroke(Color.BLACK);
		Orbit nextOrbit = parentOrbit;
		for(int i = 0; i < Constants.resolution; i++) {
			while(nextOrbit != null) {
				nextOrbit.update();
				nextOrbit.show(gc);
				nextOrbit = nextOrbit.childOrbit;
			}
			path.add(new double[]{lastOrbit.posX, lastOrbit.posY});
		}
		
		gc.setStroke(Color.MAGENTA);
        gc.setLineWidth(1);
        gc.beginPath();
        for (double[] pos : path) {
            gc.lineTo(pos[0], pos[1]);
        }
        gc.stroke();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
