package game.gui;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class New_stages {
	public static Stage Howtoplay() {
		
    	Text title = new Text();
    	title.setFill(Color.BLACK);
    	title.setFont(Font.font("Arial",25));
    	title.setText("How to play");
    	title.setTranslateY(-225);
    	title.setStroke(Color.BLACK);
    	title.setStrokeType(StrokeType.OUTSIDE);
    	title.setStrokeWidth(1);
    	
		
    	Text body = new Text();
    	body.setFill(Color.BLACK);
    	body.setFont(Font.font("verdana",18));
    	body.setText("Description :  \r\n"
    			+"Attack on Titan:\r\n"
    			+"Utopia is a one-player, endless , tower defense game inspired by the hit\r\n"
    			+"anime Attack on Titan. The story of the anime revolves around how ,\r\n"
    			+"titans, gigantic humanoid creatures, emerged one day and\r\n"
    			+"wiped out most of humanity. The few surviving humans fled \r\n"
    			+"and hid behind 3 great walls that provided safe haven\r\n"
    			+"from the titan threats. Wall Maria is the outer wall, \r\n"
    			+"Wall Rose is the middle wall and Wall Sina is the inside wall.\r\n"
    			+"\n"
    			+"How to play :  \r\n"
    			+"Each turn the player can choose to either Purchase and\r\n"
    			+"Deploy a Weapon or pass their turn without any actions \r\n"
    			+"after that a wave of titans will approach if the titans destroy\r\n"
    			+"all the walls in all lanes it will be game over your goal is to survive\r\n"
    			+"for the longest time.");
    	body.setTranslateX(5);
    	
    	
		StackPane root = new StackPane();
		root.getChildren().addAll(title,body);
		Scene scene1 = new Scene(root,700,600);
		Color darkestRed = Color.rgb(39, 61, 53);
		Stage stage = new Stage();
		stage.setScene(scene1);
		stage.setWidth(700);
		stage.setHeight(600);
    	
		
		
		
		scene1.setFill(darkestRed);
		
		
    	return stage;
    	
    	
    	
    	
    }
}
