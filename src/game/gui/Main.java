package game.gui;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application{
	static Scene scene;
	static String activeMode = "";
	@Override
	public void start(Stage stage) throws Exception {
		
		
		
		String videopath= "Untitled video - Made with Clipchamp (3).mp4";
		Media media = new Media (new File(videopath).toURI().toString());
		MediaPlayer mediaplayer= new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaplayer);
        
       
        mediaplayer.setAutoPlay(true);
        mediaplayer.setCycleCount(MediaPlayer.INDEFINITE);
        
      
        mediaView.fitWidthProperty().bind(stage.widthProperty());
        mediaView.fitHeightProperty().bind(stage.heightProperty());
      
		/////////////////////////////////////////
        
        
        // Start
        Label start= new Label();
        start.setText("START");
        start.setTextFill(Color.WHITE);
        start.setStyle("-fx-font-size: 30pt;");
        start.setTranslateY(450);
        start.setTranslateX(10);
        
        Rectangle rectangle = new Rectangle(300, 55, Color.BLACK);
        rectangle.setOpacity(0.4);
        rectangle.setTranslateY(450);
        rectangle.setTranslateX(10);
        rectangle.setOnMouseClicked(event -> {stage.setScene(Difficulity.difficulity(stage));
		 stage.setFullScreen(true);});
        
        
        // how to play
        Label how= new Label();
        how.setText("HOW TO PLAY");
        how.setTextFill(Color.WHITE);
        how.setStyle("-fx-font-size: 30pt;");
        how.setTranslateY(520);
        how.setTranslateX(10);
        
        Rectangle rectangle1 = new Rectangle(300, 55, Color.BLACK);
        rectangle1.setOpacity(0.4);
        rectangle1.setTranslateY(520);
        rectangle1.setTranslateX(10);
        rectangle1.setOnMouseClicked(event -> {Stage stage_2 = New_stages.Howtoplay();
 		stage_2.show();});
        
        
        // Exit
        Label Exit= new Label();
        Exit.setText("EXIT");
        Exit.setTextFill(Color.WHITE);
        Exit.setStyle("-fx-font-size: 30pt;");
        Exit.setTranslateY(590);
        Exit.setTranslateX(10);
        
        Rectangle rectangle2 = new Rectangle(300, 55, Color.BLACK);
        rectangle2.setOpacity(0.4);
        rectangle2.setTranslateY(590);
        rectangle2.setTranslateX(10);
        
        rectangle2.setOnMouseClicked(event -> {
            stage.close();
        });
        
        Group root= new Group();
        root.getChildren().addAll(mediaView,start,how,Exit,rectangle,rectangle1,rectangle2);
        
        scene = new Scene(root);
        
		stage.setWidth(1220);
		stage.setHeight(720);
		stage.setFullScreen(true);
		
		stage.setScene(scene);
		stage.show();
		
		////////////////	
	 TextField textf = new TextField();
	 Button b = new Button();
	 b.setOnAction(event->  s = textf.getText());
		
	}
	String s;
	 public static Text make_label(double x,double y, String name) {
	    	Text text = new Text();
	    	text.setText(name);
	    	text.setFill(Color.WHITE);
	        text.setTranslateX(x);
	        text.setTranslateY(y);
	        text.setFont(Font.font(30));
	        text.setCursor(Cursor.HAND);
	        
	        
	        return text;
	    }
	 public static Rectangle make_button(double x , double y , Text t) {
    	 Rectangle rectangle = new Rectangle(250 , 50);
    	 rectangle.setX(x);
         rectangle.setY(y);
         rectangle.setOpacity(0.6);
         rectangle.setFill(Color.BLACK);
         rectangle.setEffect(new GaussianBlur(2.5));
         rectangle.setCursor(Cursor.HAND);
         rectangle.setOnMouseEntered(event -> {t.setFill(Color.BLACK);
		rectangle.setFill(Color.WHITE);
			});
         

           rectangle.setOnMouseExited(event -> {t.setFill(Color.WHITE);
		   rectangle.setFill(Color.BLACK);
		   });
    	 return rectangle;

	 
	 }
	public static void main (String [] args) {
		launch(args);
	}
	public static Scene getscene() {
		 return scene;
	}

}
