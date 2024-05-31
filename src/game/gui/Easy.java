package game.gui;
import javax.swing.*;
import java.awt.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import game.engine.Battle;
import game.engine.lanes.Lane;
import game.engine.titans.AbnormalTitan;
import game.engine.titans.ArmoredTitan;
import game.engine.titans.PureTitan;
import game.engine.titans.Titan;
import game.engine.weapons.PiercingCannon;
import game.engine.weapons.SniperCannon;
import game.engine.weapons.VolleySpreadCannon;
import game.engine.weapons.WallTrap;
import game.engine.weapons.Weapon;
import game.engine.weapons.WeaponRegistry;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Easy {
	static Battle battle;
	
public static void Easy(Stage stage)  throws IOException {		
		Main.activeMode = "Easy";
	
		battle = new Battle(0,0,100,3,250);	
		List<Lane> lanes = new ArrayList<Lane>(battle.getLanes());

		Image image= new Image("easy.jpg");
		ImageView imageview = new ImageView(image);
		imageview.fitWidthProperty().bind(stage.widthProperty());
		imageview.fitHeightProperty().bind(stage.heightProperty());
		
		StackPane root= new StackPane(imageview);
		GridPane mainGrid = new GridPane();
		mainGrid.setPadding(new Insets(10, 10, 10, 10));
		GridPane innerGrid = updateInnerGrid(lanes);
		
		Label score = createLabel("SCORE: " + battle.getScore());
		Label turns = createLabel("TURN: " + (battle.getNumberOfTurns()));
		Label resources = createLabel("RESOURCES: " + battle.getResourcesGathered());
		Label phase = createLabel("PHASE: " + battle.getBattlePhase());
		Button passTurn = new Button("pass turn");
		Button purchaseWeapon = new Button("purchase weapon");
		Button endGame = new Button("end game");
		Button ai = new Button("AUTOPLAY AI");
		
		AtomicBoolean f = new AtomicBoolean(false);
		ai.setOnAction(e -> {
            f.set(true);
            try {
            	if (battle.getNumberOfTurns() % 5 != 0) throw new Exception();
            	List<Lane> sortedLanes = new ArrayList<>(lanes);

                // Sort the new list
                Collections.sort(sortedLanes);
                int x= sortedLanes.size();
                Lane mostDangerousLane = sortedLanes.get(x-1);
                
                battle.purchaseWeapon(new Random().nextInt(2) + 1,  mostDangerousLane);
			} catch (Exception ex) {
	        	battle.passTurn();
			}

            root.getChildren().clear();
            updateMainGrid(score, turns, resources, phase);
            root.getChildren().addAll(imageview, updateInnerGrid(lanes), mainGrid); // Adjust as per actual implementation

        });

		  Timeline timeline = new Timeline();

	        // Initialize the timeline with the keyframe
	        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
	            if (f.get() && !battle.isGameOver()) {
	                ai.fire();
	            } else if (battle.isGameOver()) {
	                timeline.stop();
	                Platform.runLater(() -> {
	                    showEndScreen();
		   		        stage.setScene(Main.getscene());
		   		        stage.setFullScreen(true);
	                });
	            }
	        }));

	        timeline.setCycleCount(Timeline.INDEFINITE); // Loop indefinitely until stopped
	        timeline.play(); // Start the timeline


		
		passTurn.setOnAction((e) -> {
			battle.passTurn();
			
			root.getChildren().clear();
			updateMainGrid(score, turns, resources, phase);
			root.getChildren().addAll(imageview,updateInnerGrid(lanes), mainGrid);
			if (battle.isGameOver()) {
				showEndScreen();
				stage.setScene(Main.getscene());
				stage.setFullScreen(true);
				
				
			}
		});
		
		endGame.setOnAction((e) -> {
			showEndScreen();
			stage.setScene(Main.getscene());
			stage.setFullScreen(true);
		});
		
		purchaseWeapon.setOnAction((e) -> {
			WeaponShop.WeaponShop();
			
			root.getChildren().clear();
			updateMainGrid(score, turns, resources, phase);
			
			root.getChildren().remove(innerGrid);
			root.getChildren().addAll(imageview,updateInnerGrid(lanes), mainGrid);
		
			if (battle.isGameOver()) {
				showEndScreen();
				stage.setScene(Main.getscene());
				stage.setFullScreen(true);
			}
		});  
		    
			
			
	        
		
		  GridPane.setConstraints(score, 0, 0);
	      GridPane.setConstraints(turns, 0, 1);
	      GridPane.setConstraints(resources, 0, 2); 
	      GridPane.setConstraints(phase, 0, 3);  
	      GridPane.setConstraints(passTurn, 0, 4); 
	      GridPane.setConstraints(purchaseWeapon, 0, 5);  
	      GridPane.setConstraints(endGame, 0, 6); 
	      GridPane.setConstraints(ai, 0, 7); 
	        
	      mainGrid.getChildren().addAll(score, turns, resources, phase, passTurn, purchaseWeapon, endGame,ai);

	      root.getChildren().addAll(mainGrid, innerGrid);

	      StackPane.setAlignment(mainGrid, Pos.CENTER);
	        
	      Scene scene = new Scene(root,1220,720);
	      stage.setScene(scene);
	      stage.setFullScreen(true);
	}

	private static GridPane updateInnerGrid(List<Lane> lanes) {
	  
	  
        GridPane innerGrid = new GridPane();
        innerGrid.setVgap(100);
        
        innerGrid.setTranslateX(200);
        innerGrid.setTranslateY(350);

		int i = 0;
		for (Lane lane: lanes) {
			if (lane.isLaneLost()) {
				GridPane laneGrid = new GridPane();
				Rectangle wall = new Rectangle(60, 110);
				Label message = new Label("Lane lost ");
				Label health = new Label("Health: " + lane.getLaneWall().getCurrentHealth());
				
				message.setFont(new Font(10)); 
				message.setTextFill(Color.WHITE);
				health.setFont(new Font(10)); 
				health.setTextFill(Color.WHITE);
				wall.setFill(Color.RED);
				
				

		        GridPane.setConstraints(message, 0, 1);
		        GridPane.setConstraints(health, 1, 0);
		        GridPane.setConstraints(wall, 1, 1);
		        
		        GridPane.setConstraints(laneGrid, 0, i);
		        
		        laneGrid.getChildren().addAll(message, wall, health);
		        innerGrid.getChildren().addAll(laneGrid);
		        
		        i++;
				continue;
			}
			GridPane laneGrid = new GridPane();
			
			// wall info
			Rectangle wall = new Rectangle(60, 120);
			Label health = new Label("Health: " + lane.getLaneWall().getCurrentHealth());
			Label dangerLevel = new Label("Danger level: " + lane.getDangerLevel());
			
			health.setFont(new Font(10)); 
			health.setTextFill(Color.WHITE);
			dangerLevel.setFont(new Font(10)); 
			dangerLevel.setTextFill(Color.WHITE);
			wall.setFill(Color.WHITE);

	        GridPane.setConstraints(dangerLevel, 0, 1);
	        GridPane.setConstraints(health, 1, 0);
	        GridPane.setConstraints(wall, 1, 1);
	        
	        // weapons////////////////////////////
	        GridPane weaponGrid = new GridPane();
	        int j = 0;
	        for (Weapon weapon: lane.getWeapons()) {
	        	//Rectangle weaponItem = new Rectangle(50, 50);
	        	
	        	Label type = null;
	        	if (weapon instanceof PiercingCannon) {
	        		type = createLabel(WeaponShop.getWeaponType(PiercingCannon.WEAPON_CODE) );
	        		Image Piercing= new Image("000000-removebg-preview.png");
	        		ImageView PiercingCannon= new ImageView(Piercing);
	        		PiercingCannon.setFitWidth(50);
	        		PiercingCannon.setFitHeight(50);
	        		GridPane.setConstraints(PiercingCannon, j+1, 1);
	        		weaponGrid.getChildren().addAll(type, PiercingCannon);
	        	}
	        	else if (weapon instanceof SniperCannon) {
	        		type = createLabel(WeaponShop.getWeaponType(SniperCannon.WEAPON_CODE));
	        		Image Sniper= new Image("1111-removebg-preview.png");
	        		ImageView SniperCannon= new ImageView(Sniper);
	        		SniperCannon.setFitWidth(50);
	        		SniperCannon.setFitHeight(50);
	        		GridPane.setConstraints(SniperCannon, j+1, 1);
	        		weaponGrid.getChildren().addAll(type, SniperCannon);
	        	}
	        	else if (weapon instanceof VolleySpreadCannon) {
	        		type = createLabel(WeaponShop.getWeaponType(VolleySpreadCannon.WEAPON_CODE));
	        		Image VolleySpread= new Image("WhatsApp_Image_2024-05-19_at_14.42.41_a4375999-removebg-preview.png");
	        		ImageView VolleySpreadCannon= new ImageView(VolleySpread);
	        		VolleySpreadCannon.setFitWidth(50);
	        		VolleySpreadCannon.setFitHeight(50);
	        		GridPane.setConstraints(VolleySpreadCannon, j+1, 1);
	        		weaponGrid.getChildren().addAll(type, VolleySpreadCannon);
	        	}
	        	else {
	        		type = createLabel(WeaponShop.getWeaponType(WallTrap.WEAPON_CODE));
	        		Image Wall= new Image("WhatsApp_Image_2024-05-19_at_15.01.35_3db6535b-removebg-preview.png");
	        		ImageView WallTrap= new ImageView(Wall);
	        		WallTrap.setFitWidth(50);
	        		WallTrap.setFitHeight(50);
	        		GridPane.setConstraints(WallTrap, j+1, 1);
	        		weaponGrid.getChildren().addAll(type, WallTrap);
	        	}
	        		type.setFont(new Font(10));
	        	j++;
	        	GridPane.setConstraints(type, j, 0);
	        	
	        	//weaponGrid.getChildren().addAll(type, weaponItem);
	        }
	        
	        // titans
	        List<Titan> titans = new ArrayList<>(lane.getTitans());
	        GridPane titanGrid = new GridPane();
	        
	        for (int k = 0; k < 10; k++) {
	        	Rectangle rec = new Rectangle(50, 50);
	        	rec.setFill(Color.TRANSPARENT);
	        	
	        	GridPane.setConstraints(rec, k, 0);
	        	titanGrid.getChildren().addAll(rec);
	        }
	        
	        for (Titan titan: titans) {
	        	Label tHealth = new Label("Health: " + titan.getCurrentHealth());
	        	Label name = new Label(titan.getClass().getSimpleName());
	        	//name.setBackground(Background.fill(Color.WHITE));
	        	name.setMinSize(50, titan.getHeightInMeters());
	        	
	        	tHealth.setFont(new Font(10)); 
	        	tHealth.setTextFill(Color.RED);
	        	name.setFont(new Font(10)); 
	        	name.setTextFill(Color.RED);
	        	
	        	int spawnDistance = titan.getDistance() / 10;
	        	
	        	GridPane.setConstraints(tHealth, spawnDistance, 0);
	        	GridPane.setConstraints(name, spawnDistance, 1);
	        	titanGrid.getChildren().addAll( tHealth, name);
	        }
	        
	        
	        weaponGrid.setTranslateY(-30);
	        weaponGrid.setTranslateX(-1300);
	        
	        GridPane.setConstraints(laneGrid, 0, i);
	        GridPane.setConstraints(weaponGrid, 1, i);
	        GridPane.setConstraints(titanGrid, 2, 1);
	        
	        titanGrid.setTranslateX(100);
	        titanGrid.setTranslateY(50);
	        titanGrid.setHgap(80);
	        
	        laneGrid.getChildren().addAll(health, dangerLevel, wall, titanGrid);
	        innerGrid.getChildren().addAll(laneGrid, weaponGrid);
	        
	        i++;
		}
        
		return innerGrid;
	}

	private static void updateMainGrid(Label score, Label turns, Label resources, Label phase) {
		   score.setText("SCORE: " + battle.getScore());
		   turns.setText("TURN: " + (battle.getNumberOfTurns()));
		   resources.setText("RESOURCES: " + battle.getResourcesGathered());
		   phase.setText("PHASE: " + battle.getBattlePhase());
	}
	
	private static Label createLabel(String text) {
    Label label = new Label(text);
    label.setTextFill(Color.WHITE);
    label.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
    return label;
}
	
	public static HashMap<Integer, WeaponRegistry> getWeaponShop() {
		return battle.getWeaponFactory().getWeaponShop();
	}
	
	public static void showEndScreen() {
		 Stage endView = new Stage();
		 endView.initModality(Modality.APPLICATION_MODAL);
		 endView.setTitle("GAME OVER");
	        
		 Label messageLabel = new Label("Game over");
		 messageLabel.setTextFill(Color.RED);
		 
	        Button closeButton = new Button("Close");
	        closeButton.setOnAction(e -> endView.close());

	        Label resouces = createLabel("Resources: " + battle.getResourcesGathered());
	        resouces.setTextFill(Color.BLACK);
	        Label turns = createLabel("Turns: " + battle.getNumberOfTurns());
	        turns.setTextFill(Color.BLACK);
	        Label score = createLabel("Score: " + battle.getScore());
	        score.setTextFill(Color.BLACK);
	        
	        GridPane grid = new GridPane();
	        grid.setAlignment(Pos.CENTER);
	        
	        grid.add(messageLabel, 0, 0);
	        grid.add(resouces, 0, 1);
	        grid.add(turns, 0, 2);
	        grid.add(score, 0, 3);
	        grid.add(closeButton, 0, 4);

	        Scene popUpScene = new Scene(grid, 400, 400);
	        endView.setScene(popUpScene);
	        endView.showAndWait(); 
	}
}
