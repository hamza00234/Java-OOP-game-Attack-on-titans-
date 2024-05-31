package game.gui;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Difficulity {
	static Scene scene;
	
	public static Scene difficulity(Stage stage) {
    	
    	Image image1 = new Image("attack-on-titan-wallpaper-16.png");
		Image image2 = new Image("attack-on-titan-wallpaper-17.png");
		Image image3 = new Image("attack-on-titan-wallpaper-20.png");
		
		
		
		
		ImageView imageview = new ImageView(image1);
		imageview.fitWidthProperty().bind(stage.widthProperty());
		imageview.fitHeightProperty().bind(stage.heightProperty());
		
		ImageView imageview2 = new ImageView(image2);
		imageview2.fitWidthProperty().bind(stage.widthProperty());
		imageview2.fitHeightProperty().bind(stage.heightProperty());
		
		
		Image image = new Image("attack-on-titan-wallpaper-20.png");
    	ImageView imageview1 = new ImageView(image);
		imageview1.fitWidthProperty().bind(stage.widthProperty());
		imageview1.fitHeightProperty().bind(stage.heightProperty());
		
    	Label text = new Label();
    	text.setTranslateY(300);
    	text.setTranslateX(15);
    	text.setText("CHOOSE DIFFICULTY");
    	text.setFont(Font.font(50));
    	text.setTextFill(Color.WHITE);
    	text.setLayoutX(1.5);
    	
 //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    	
        Text hard = Main.make_label(30, 635 , "HARD");
    	hard.setFont(Font.font(37));
    	
    	Rectangle rectangle2 = Main.make_button(25, 600, hard);
    	rectangle2.setOnMouseClicked(event ->{try {
    		Hard.Hard(stage);
		} catch (IOException e1) {
			System.out.println("");
		}
		});
    		
    	
    	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
    	
    	Text easy = Main.make_label(30, 500, "EASY");
    	easy.setFont(Font.font(37));

    	Rectangle rectangle = Main.make_button(25, 465, easy);
    	rectangle.setOnMouseClicked(event ->{try {
    			Easy.Easy(stage);
		} catch (IOException e1) {
			System.out.println("");
		}
		 });
    	
 /////////////////////////////////////////////////////////////////////
    	Text back = Main.make_label(1850,950, "BACK");
        back.setFont(Font.font(25));
        back.setOnMouseClicked(event ->{
        		
				 stage.setScene(Main.getscene());
				 stage.setFullScreen(true);
				
        });
//////////////////////////////////////////////////////////////////////////////////////////////////////////////   
    	
		Group root = new Group();
		root.getChildren().addAll(imageview1,text,rectangle,easy,rectangle2,hard,back);
		
//////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		
		
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.25), imageview);
        fadeTransition.setFromValue(1.0); 
        fadeTransition.setToValue(0.0); 
        EventHandler<Event> e = event -> {root.getChildren().setAll(imageview2,text,rectangle,easy,rectangle2,hard,back);
		rectangle.setFill(Color.WHITE);
		easy.setFill(Color.BLACK);
	    fadeTransition.setNode(imageview2);
	    fadeTransition.setFromValue(0.0); 
        fadeTransition.setToValue(1.0); 
        fadeTransition.play();};
        
        rectangle.setOnMouseEntered(e);
        easy.setOnMouseEntered(e);
        
        
        
        EventHandler<Event> e2 = event -> {root.getChildren().setAll(imageview,text,rectangle,easy,rectangle2,hard,back);
		rectangle2.setFill(Color.WHITE);
		hard.setFill(Color.BLACK);
		fadeTransition.setNode(imageview);
		fadeTransition.setFromValue(0.0); 
		fadeTransition.setToValue(1.0); 
		fadeTransition.play();};
        
        rectangle2.setOnMouseEntered(e2);
        hard.setOnMouseEntered(e2);
       
	    scene = new Scene(root,1220,720);
	    scene.setFill(Color.BLACK);
	    
	    return scene;
	}
	
	public static Scene getscene() {
		 return scene;
	}
}
