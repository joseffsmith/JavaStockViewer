import java.util.Scanner;
import java.io.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.event.ActionEvent;
import java.awt.Desktop;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;

import javafx.scene.paint.Color;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import java.util.Formatter;
import javafx.stage.Stage;

import javafx.util.Duration;

import javafx.event.EventHandler;
import javafx.event.Event;
import javafx.beans.value.ChangeListener;
import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.GroupBuilder;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.ScrollPaneBuilder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBuilder;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.collections.ObservableList;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;

import java.time.LocalDateTime;

import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.Group;

import javafx.scene.control.Alert;


public class Report {
	public static Stage reportDialogStage(){
		Stage dialog = new Stage();
		VBox dialogRoot = new VBox(5);

		HBox vbButtonsRight = new HBox(5);
	    
		// Buttons

		Button btnAddAll = new Button("Add All");
		btnAddAll.setPrefWidth(140);
		btnAddAll.setPrefHeight(40);

		Button btnGenRep = new Button("Report on selected companies");
		btnGenRep.setPrefWidth(280);
		btnGenRep.setPrefHeight(40);

		vbButtonsRight.getChildren().addAll(btnAddAll,btnGenRep);

		// Explanation

		Label lblExplain = new Label("Use Ctrl to select singly and use Shift to select multiple");
	    
		//List Left 

	    ObservableList<Company> companies = StockTableView.getCompanies();
	    final ListView<Company> companyListView = new ListView<>(companies);
	    companyListView.setItems(companies);
	    companyListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	   	btnGenRep.setOnAction(new EventHandler<ActionEvent>(){
	   		@Override
	   		public void handle(ActionEvent event) {
	   			ObservableList<Company> selectedCompanies = companyListView.getSelectionModel().getSelectedItems();
	   			generateReport(selectedCompanies);

	   			dialog.hide();
	   		}
	   	});

	   	btnAddAll.setOnAction(new EventHandler<ActionEvent>(){
	   		@Override
	   		public void handle(ActionEvent event) {
	   			companyListView.getSelectionModel().selectAll();
	   		}
	   	});


		dialogRoot.getChildren().addAll(companyListView,vbButtonsRight,lblExplain);



		Scene dialogScene = new Scene(dialogRoot, 400, 480);
		dialog.setScene(dialogScene);
		return dialog;
	}

	public static void generateReport(ObservableList<Company> companies){

		String filename = "Reports/" + LocalDateTime.now() + ".txt";

		try {
			FileWriter writer = new FileWriter(filename);
			PrintWriter out = new PrintWriter( writer );
			for (int i = 0; i <companies.size(); i++){
				Company company = companies.get(i);
				out.println(String.format("%14s: %d","Number", (i +1)));
				out.println(String.format("%14s: %s","Stock Symbol", company.getStockSymbol()));
				out.println(String.format("%14s: %s","Company Name", company));
				out.println(String.format("%14s: %s","Highest", company.getHighest()));
				out.println(String.format("%14s: %s","Lowest", company.getLowest()));
				out.println(String.format("%14s: %.1f","Average Close", company.getAverageClose()));
				out.println(String.format("%14s: %.1f ","Close", company.getMostRecentClose())+"\n");

			}
			out.close();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Report Created");
			alert.setHeaderText(null);
			alert.setContentText("Report generated and saved:\n" + filename);
			alert.showAndWait();

		} catch ( Exception e ) {
			e.printStackTrace();
			System.out.println( e );
		}

		


	}


}
