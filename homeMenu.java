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
import javafx.stage.Screen;
import javafx.scene.control.Alert;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TableView;
public class homeMenu extends Application {

	@Override
	public void start(Stage stage){
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		stage.setTitle("Simple Stock Viewer");
		stage.setX(primaryScreenBounds.getMinX());
		stage.setY(primaryScreenBounds.getMinY());
		//Set up border pane for the main window

		BorderPane rootNode = new BorderPane();



		Scene myScene = new Scene(rootNode, 1000, 600);
		rootNode.prefWidthProperty().bind(myScene.widthProperty());
		stage.setScene(myScene);
		
		//TOP 
		Boolean percentage = true;
		TextFlow textRef = getTickerFlow(true);

		TranslateTransition transTransition = TranslateTransitionBuilder.create()
      		.duration(new Duration(200000))
      		.node(textRef)
      		.toX(-5050)
      		.interpolator(Interpolator.LINEAR)
      		.cycleCount(Timeline.INDEFINITE)
      		.autoReverse(true)
      		.rate(4.0)
      		.build();

      	ScrollPane sp = new ScrollPane();

      	Group gro = new Group();

      	gro.getChildren().add(sp);

		sp.setLayoutY(28);
		// sp.setFitToWidth(true);
		sp.setPrefWidth(1000);
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setVbarPolicy(ScrollBarPolicy.NEVER);
		sp.setPannable(true);
		sp.setContent(textRef);
		sp.setFitToHeight(true);
		sp.setStyle("-fx-background: black;");

      	Group grou = new Group();


      	// MENU


      	MenuBar menuBar = new MenuBar();

      	// File Menu 

      	Menu fMenu = new Menu("File");

      	MenuItem exitM = new MenuItem("Exit");

      	exitM.setOnAction(new EventHandler<ActionEvent>(){
      		public void handle(ActionEvent t) {
      			stage.hide();
      		}
      	});

      	fMenu.getItems().add(exitM);

      	// Ticker Tape Menu

      	Menu ttMenu = new Menu("Ticker Tape");
      	
      	// //percent or value
      	// RadioMenuItem percentRadio = new RadioMenuItem("Percentage");
      	// RadioMenuItem valueRadio = new RadioMenuItem("Value");

      	// ToggleGroup toggleGroup = new ToggleGroup();

      	// percentRadio.setOnAction(new EventHandler<ActionEvent>() {
      	// 	@Override public void handle(ActionEvent e) {
      	// 		if (! percentage) {
      	// 			transTransition.stop();
      	// 			final TextFlow textRef = getTickerFlow(true);
      				
      	// 		} 
      	// 	}
      	// });
      	// percentRadio.setToggleGroup(toggleGroup);
      	// valueRadio.setOnAction(new EventHandler<ActionEvent>() {
      	// 	@Override public void handle(ActionEvent e) {
      	// 		if (percentage) {
      	// 			transTransition.stop();
      	// 			final TextFlow textRef = getTickerFlow(false);
      	// 			transTransition.playFromStart();
      	// 		} 
      	// 	}
      	// });
      	// valueRadio.setToggleGroup(toggleGroup);


      	Slider tickerSpeed = new Slider();
      	tickerSpeed.setMin(0);
      	tickerSpeed.setMax(15);
      	tickerSpeed.setValue(1);


      	tickerSpeed.valueProperty().addListener(new ChangeListener<Number>() {
      		public void changed(ObservableValue<? extends Number> ov,
      			Number old_value, Number new_val) {
      			transTransition.setRate(new_val.doubleValue());

	      		}
      		
      	});

      	CustomMenuItem textSpeed = new CustomMenuItem(new Text("Speed"));
      	CustomMenuItem sliderSpeed= new CustomMenuItem(tickerSpeed);
      	sliderSpeed.setHideOnClick(false);
      	textSpeed.setDisable(true);



      	// ttMenu.getItems().add(percentRadio);
      	// ttMenu.getItems().add(valueRadio);
		ttMenu.getItems().add(textSpeed);
      	ttMenu.getItems().add(sliderSpeed);
      	

      	
      	menuBar.getMenus().addAll(fMenu,ttMenu);
      	menuBar.prefWidthProperty().bind(stage.widthProperty());
      	
      	grou.getChildren().add(gro);
      	grou.getChildren().add(menuBar);

		rootNode.setTop(grou);

		/// LEFT = VBOX


		//Top Left


		Button btnClose = new Button("Exit");
		btnClose.setPrefWidth(140);
		btnClose.setPrefHeight(40);

		btnClose.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e){
				stage.hide();
			}
		});


		VBox vbTopLeft = new VBox(5);
		vbTopLeft.getChildren().add(btnClose);
		vbTopLeft.setAlignment(Pos.CENTER);
		VBox.setMargin(vbTopLeft, new Insets(5,5,5,5));


		/// BOTTOM

		Button btnReport = new Button("Generate Report");

		Button btnViewDetails = new Button("View Details");

		btnReport.setPrefWidth(140);
		btnReport.setPrefHeight(40);

		btnViewDetails.setPrefWidth(140);
		btnViewDetails.setPrefHeight(40);


		btnReport.setOnAction(
			new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					final Stage dialog = Report.reportDialogStage();

					dialog.initModality(Modality.APPLICATION_MODAL);
                	dialog.initOwner(stage);

                	dialog.show();
				}
			});



		HBox hbBottom = new HBox(5);
		hbBottom.getChildren().addAll(btnViewDetails, btnReport);
		hbBottom.setAlignment(Pos.CENTER_RIGHT);
		rootNode.setBottom(hbBottom);
		BorderPane.setAlignment(hbBottom, Pos.CENTER);
		BorderPane.setMargin(hbBottom, new Insets(5,5,5,5));

		//Bottom Left
		//Possibly look at toggle buttons for these

		Button btnHome = new Button("Home");
		Button btnGraph = new Button("Graph");
		Button btnTable = new Button("Table");

		btnGraph.setOnAction(e -> btnGraphClick(rootNode,btnViewDetails));
		btnTable.setOnAction(e -> btnTableClick(rootNode,btnViewDetails));
		btnHome.setOnAction(e -> btnHomeClick(rootNode,btnViewDetails));

		btnHome.setPrefWidth(140);
		btnHome.setPrefHeight(40);
		btnGraph.setPrefWidth(140);
		btnGraph.setPrefHeight(40);
		btnTable.setPrefWidth(140);
		btnTable.setPrefHeight(40);



		VBox vbBotLeft = new VBox(5);
		vbBotLeft.getChildren().addAll(btnHome, btnGraph, btnTable);
		vbBotLeft.setAlignment(Pos.CENTER);
		VBox.setMargin(vbBotLeft, new Insets(5,5,5,5));


		// Group

		VBox vbLeft = new VBox(5);
		vbLeft.getChildren().addAll(vbTopLeft,vbBotLeft);
		vbLeft.setAlignment(Pos.TOP_CENTER);
		rootNode.setLeft(vbLeft);
		BorderPane.setAlignment(vbLeft, Pos.CENTER);
		BorderPane.setMargin(vbLeft, new Insets(5, 5, 5, 5));

		



		///RIGHTYTIGHTY

		

		// TableView table = SETableView.generateTable(true);
		// StockCSVReader.loadStockFile()

		HBox hbCenter = new HBox(5);
		// hbCenter.getChildren().add(table);
		// hbCenter.setAlignment(Pos.CENTER_LEFT);
		// HBox.setHgrow(table, Priority.ALWAYS);
		TableView<Company> tableView = HomeTableView.getTable();
		rootNode.setCenter(tableView);
		BorderPane.setAlignment(hbCenter, Pos.CENTER);
		BorderPane.setMargin(hbCenter, new Insets(5,5,5,5));

		
		btnViewDetails.setOnAction(
			new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// System.out.println();
					final Stage dialog = DetailedView.detailedViewStage(tableView.getSelectionModel().getSelectedItem());
					// dialog.initModality(Modality.APPLICATION_MODAL);
                	dialog.initOwner(stage);
					dialog.show();
				}
			});

		//SHOW

		stage.show();

		transTransition.play();

		
	}







	public static void btnHomeClick(BorderPane rootNode, Button btnViewDetails){
		if(btnViewDetails.isDisable()){
			btnViewDetails.setDisable(false);
		}
		rootNode.setCenter(null);
		TableView<Company> homeView = HomeTableView.getTable();
		rootNode.setCenter(homeView);
	}
	public static void btnGraphClick(BorderPane rootNode, Button btnViewDetails){
		if(!btnViewDetails.isDisable()){
			btnViewDetails.setDisable(true);
		}
		rootNode.setCenter(null);
		GridPane chartView = ChartView.getChart();
		rootNode.setCenter(chartView);
		
	}

	public static void btnTableClick(BorderPane rootNode, Button btnViewDetails){
		if(!btnViewDetails.isDisable()){
			btnViewDetails.setDisable(true);
		}
		rootNode.setCenter(null);
		GridPane tableView = StockTableView.getTable();
		rootNode.setCenter(tableView);
	}


	public static TextFlow getTickerFlow(Boolean percentage){

		ObservableList<Company> companies = StockTableView.getCompanies();
		TextFlow mFlow = new TextFlow();
		mFlow.getChildren().add(new Text("        "));

		for (int i = 0; i < companies.size(); i++){
			Double percChange = companies.get(i).getTickerString(percentage);
			String stockS = companies.get(i).getStockSymbol();
			Text stockName = TextBuilder.create()
			    .textAlignment(TextAlignment.JUSTIFY)
			    .text(stockS)
			    .fill(Color.rgb(218, 232, 92))
			    .font(Font.font("SansSerif", FontWeight.BOLD, 20))
			    .build();

			Text closingStatement = TextBuilder.create()
			    .textAlignment(TextAlignment.JUSTIFY)
			    .text(" |  ")
			    .fill(Color.rgb(218, 232, 92))
			    .font(Font.font("SansSerif", FontWeight.BOLD, 20))
			    .build();	

			
			Text textRef = new Text();
			textRef.setTextAlignment(TextAlignment.JUSTIFY);
			textRef.setFont(Font.font("SansSerif", FontWeight.BOLD, 20));



			if (percChange > 0) {
				//Make it green and add plus
				textRef.setText("+"+String.format("%.3f",percChange)+"% ");
				textRef.setFill(Color.rgb(0,255,0));

			} else if (percChange < 0) {
				//Make it red and keep negative sign
				textRef.setText(String.format("%.3f",percChange)+"% ");
				textRef.setFill(Color.rgb(255,0,0));
			} else {
				//Make it white
				textRef.setText(String.format("%.3f",percChange)+"% ");
				textRef.setFill(Color.rgb(255,255,255));
			}
			// System.out.println(stockName);
			// System.out.println(textRef);
			// System.out.println(closingStatement);
			mFlow.getChildren().addAll(stockName, textRef, closingStatement);

		}
		return mFlow;
	}



	public static void main(String[] args){
		launch(args);
		System.out.println("\u2606");
		ObservableList<Company> companies = StockTableView.getCompanies();

		System.out.println(companies.get(0).companyData.get(0).getOpen());
	}
}