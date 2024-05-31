package game.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import game.engine.Battle;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.weapons.WeaponRegistry;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WeaponShop {

	public static void WeaponShop() {
		Stage weaponView = new Stage();
		weaponView.initModality(Modality.APPLICATION_MODAL);
	    weaponView.setTitle("weapon shop");

		final Battle battle = Main.activeMode.equals("Easy") ? Easy.battle : Hard.battle; 
		
		Button closeButton = new Button("Close");
	    closeButton.setOnAction(e -> {
	    	weaponView.close();
	    });
	    

		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);

		grid.add(new Label("Resources: " + battle.getResourcesGathered()), 0, 0);
		grid.add(new Label("name"), 0, 1);
		grid.add(new Label("type"), 1, 1);
		grid.add(new Label("price"), 2, 1);
		grid.add(new Label(" damage"), 3, 1);
		
			
		if (battle != null && battle.getWeaponFactory().getWeaponShop() != null) {
			battle.getWeaponFactory().getWeaponShop().forEach((num, weapon) -> {
			Label name = new Label(weapon.getName() + " ");
			Label type = new Label(getWeaponType(weapon.getCode()));
		
			Label price = new Label(" " + weapon.getPrice() +" ");
			Label damage = new Label(" " + weapon.getDamage() + " ");
		
			List<Lane> lanes = new ArrayList<Lane>(battle.getLanes());
			
			ComboBox<String> comboBox = new ComboBox<>();
			ObservableList<String> comboBoxItems = FXCollections.observableArrayList();
		    
		    int i = 1;
		    for (Lane lane : lanes) {
		    	comboBoxItems.add("Lane " + i++);
		    }
		    comboBox.setItems(comboBoxItems);

		    comboBox.getSelectionModel().selectFirst();
			
			Button buy = new Button("buy");
		    buy.setOnAction(e -> {
		    	String val = comboBox.getValue();
		    	Integer index = Integer.parseInt(val.split(" ")[1]) - 1;
		    	
		    	Lane lane = battle.getOriginalLanes().get(index);
		    	
				try {
					battle.purchaseWeapon(weapon.getCode(), lane);
					
					weaponView.close();
				} catch (Exception error) {
						showError(error.getMessage());				
				}
			});
			
			
			grid.add(name, 0, num + 1);
			grid.add(type, 1, num + 1);
			grid.add(price, 2, num + 1);
			grid.add(damage, 3, num + 1);
			grid.add(comboBox, 4, num + 1);
			grid.add(buy, 5, num + 1);
			});
		}
		

		grid.add(closeButton, 0, 6);
		Scene scene1 = new Scene(grid,700,600);
		
		scene1.setFill(Color.DARKBLUE);
		
		weaponView.setScene(scene1);
		weaponView.showAndWait();
	}

	public static String getWeaponType(int code) {
		switch(code) {
		case 1: return "Piercing Cannon";
		case 2: return "Sniper Cannon";
		case 3: return "Volley Spread Cannon";
		case 4: return "Wall Trap";
		}
		return null;
	}
	public static void showError(String message) {
		 Stage errorView = new Stage();
		 errorView.initModality(Modality.APPLICATION_MODAL);
		 errorView.setTitle("Error");
	        
		 Label messageLabel = new Label(message);
		 messageLabel.setTextFill(Color.RED);
		 
	        Button closeButton = new Button("Close");
	        closeButton.setOnAction(e -> errorView.close());

	        GridPane grid = new GridPane();
	        grid.setAlignment(Pos.CENTER);
	        
	        grid.add(messageLabel, 0, 0);
	        grid.add(closeButton, 0, 1);

	        Scene popUpScene = new Scene(grid, 400, 400);
	        errorView.setScene(popUpScene);
	        errorView.showAndWait(); 
	}
}
