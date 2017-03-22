import java.util.Scanner;
import java.io.File;
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.event.ActionEvent;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.animation.TranslateTransition;
import javafx.application.Application;

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

import javafx.stage.Stage;

import javafx.util.Duration;

import javafx.event.EventHandler;
import javafx.event.Event;

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
public class homeMenu extends Application {

	@Override
	public void start(Stage stage){
		stage.setTitle("Welcome");

		
		
		//Set up border pane for the main window

		BorderPane rootNode = new BorderPane();


		Scene myScene = new Scene(rootNode, 1000, 500);
		stage.setScene(myScene);
		
		//TOP 


		
		// String message = "           " +getStockSymbols();

  //   	Text textRef = TextBuilder.create()
  //     		.layoutY(100)
  //     		.textOrigin(VPos.CENTER)
		//     .textAlignment(TextAlignment.JUSTIFY)
		//     .text(message)
		//     .fill(Color.rgb(218, 232, 92))
		//     .font(Font.font("SansSerif", FontWeight.BOLD, 20))
		//     .build();

		TextFlow textRef = getStockSymbols();

		TranslateTransition transTransition = TranslateTransitionBuilder.create()
      		.duration(new Duration(100000))
      		.node(textRef)
      		.toX(-5100)
      		.interpolator(Interpolator.LINEAR)
      		.cycleCount(Timeline.INDEFINITE)
      		.build();

      	Group gro = GroupBuilder.create()
      		.children(
      			ScrollPaneBuilder.create()
      			.prefWidth(1000)
      			.prefHeight(20)
              	.hbarPolicy(ScrollBarPolicy.NEVER)
              	.vbarPolicy(ScrollBarPolicy.NEVER)
              	.pannable(true)
              	.content(textRef)
             	.style("-fx-background: black;")
              	.build()
      			)
      		.build();



		rootNode.setTop(gro);

		/// LEFT = VBOX


		//Top Left


		Button btnClose = new Button("Exit");
		Button btnLoad = new Button("Load Stocks");
		btnLoad.setPrefWidth(140);
		btnLoad.setPrefHeight(40);
		btnClose.setPrefWidth(140);
		btnClose.setPrefHeight(40);



		VBox vbTopLeft = new VBox(5);
		vbTopLeft.getChildren().addAll(btnClose, btnLoad);
		vbTopLeft.setAlignment(Pos.CENTER);
		VBox.setMargin(vbTopLeft, new Insets(5,5,5,5));


		//Bottom Left
		//Possibly look at toggle buttons for these

		Button btnHome = new Button("Home");
		Button btnGraph = new Button("Graph");
		Button btnTable = new Button("Table");

		btnGraph.setOnAction(e -> btnGraphClick(rootNode));
		btnTable.setOnAction(e -> btnTableClick(rootNode));

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

		
		/// BOTTOM

		Button btnReport = new Button("Generate Report");
		Button btnAddFav = new Button("Add Favourite");
		Button btnResetView = new Button("Reset View");
		Button btnDelStocks = new Button("Delete Stocks");

		btnReport.setPrefWidth(140);
		btnReport.setPrefHeight(40);
		btnAddFav.setPrefWidth(140);
		btnAddFav.setPrefHeight(40);
		btnResetView.setPrefWidth(140);
		btnResetView.setPrefHeight(40);
		btnDelStocks.setPrefWidth(140);
		btnDelStocks.setPrefHeight(40);


		btnReport.setOnAction(
			new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					final Stage dialog = new Stage();
					dialog.initModality(Modality.APPLICATION_MODAL);
                	dialog.initOwner(stage);
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
			       		}
			       	});

			       	btnAddAll.setOnAction(new EventHandler<ActionEvent>(){
			       		@Override
			       		public void handle(ActionEvent event) {
			       			companyListView.getSelectionModel().selectAll();
			       		}
			       	});


                	dialogRoot.getChildren().addAll(companyListView,vbButtonsRight,lblExplain);



                	Scene dialogScene = new Scene(dialogRoot, 600, 500);
               	 	dialog.setScene(dialogScene);
                	dialog.show();
				}
			});













		HBox hbBottom = new HBox(5);
		hbBottom.getChildren().addAll(btnDelStocks, btnResetView, btnAddFav, btnReport);
		hbBottom.setAlignment(Pos.CENTER_RIGHT);
		rootNode.setBottom(hbBottom);
		BorderPane.setAlignment(hbBottom, Pos.CENTER);
		BorderPane.setMargin(hbBottom, new Insets(5,5,5,5));


		///RIGHTYTIGHTY

		

		// TableView table = SETableView.generateTable(true);
		// StockCSVReader.loadStockFile()

		HBox hbCenter = new HBox(5);
		// hbCenter.getChildren().add(table);
		// hbCenter.setAlignment(Pos.CENTER_LEFT);
		// HBox.setHgrow(table, Priority.ALWAYS);
		GridPane tableView = StockTableView.getTable();
		rootNode.setCenter(tableView);
		BorderPane.setAlignment(hbCenter, Pos.CENTER);
		BorderPane.setMargin(hbCenter, new Insets(5,5,5,5));

		//SHOW

		stage.show();

		transTransition.play();

		
	}

	public static void btnGraphClick(BorderPane rootNode){
		rootNode.setCenter(null);
		
	}

	public static void btnTableClick(BorderPane rootNode){
		rootNode.setCenter(null);
		GridPane tableView = StockTableView.getTable();
		rootNode.setCenter(tableView);
	}

	public static void generateReport(ObservableList<Company> companies){
		for (int i = 0; i <companies.size(); i++){
			System.out.println(companies.get(i));
		}
		

	}

	public static TextFlow getStockSymbols(){

		ObservableList<Company> companies = StockTableView.getCompanies();
		TextFlow mFlow = new TextFlow();
		mFlow.getChildren().add(new Text("        "));

		for (int i = 0; i < companies.size(); i++){
			Double percChange = companies.get(i).getTickerString();
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
	}
}