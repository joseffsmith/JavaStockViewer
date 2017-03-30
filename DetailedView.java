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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
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
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import java.util.List;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler; 
import javafx.scene.*;
import javafx.collections.*;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Tooltip;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class DetailedView {
	public static Stage detailedViewStage(Company company){
		GridPane gp = new GridPane();
		Scene scene = new Scene(gp,1200,600);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle(company.toString());

		ColumnConstraints col1 = new ColumnConstraints();
		RowConstraints row1 = new RowConstraints();
		RowConstraints row2 = new RowConstraints();
		RowConstraints row3 = new RowConstraints();

		row1.setPercentHeight(10);
		row2.setPercentHeight(10);	
		row3.setPercentHeight(80);	
		col1.setPercentWidth(50);

		gp.getColumnConstraints().add(col1);
		gp.getRowConstraints().addAll(row1);
		gp.getRowConstraints().addAll(row2);
		// Company name top left

		Label lblCompName = new Label(company.getCompanyName());
		Label lblMostRecentClose = new Label(company.getMostRecentClose().toString());
		lblCompName.setFont(Font.font(26));
		lblMostRecentClose.setFont(Font.font(26));

		gp.add(lblMostRecentClose,0,1);
		gp.add(lblCompName,0,0);

		
		//Stock Symbol Top Right
		
		Label lblStockSym = new Label(company.getStockSymbol());
		lblStockSym.setFont(Font.font(26));

		Double percChange = company.getTickerString(true);
		Text textRef = new Text();
		textRef.setTextAlignment(TextAlignment.JUSTIFY);
		textRef.setFont(Font.font("SansSerif", FontWeight.BOLD, 26));
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

		gp.add(lblStockSym,1,0);
		gp.add(textRef,1,1);


		//Chart on the left

		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		String[] colName = {"Open","Close","High","Low"};
		

		yAxis.setLabel("Price");    
		LineChart<String,Double> lineChart = new LineChart(xAxis,yAxis);
		lineChart.setHorizontalGridLinesVisible(false);
		lineChart.setVerticalGridLinesVisible(false);
		lineChart.setData(ChartView.getChartData(company,colName,4));
		lineChart.setCreateSymbols(false);
		lineChart.setAnimated(false);
		lineChart.setTitle("Time Frame: All Data");

		
		CheckBox btnOpen = new CheckBox("Open");
		CheckBox btnClose = new CheckBox("Close");
		CheckBox btnHigh = new CheckBox("High");
		CheckBox btnLow = new CheckBox("Low");
		CheckBox btnVolume = new CheckBox("Volume");

		btnOpen.setUserData("Open");
		btnClose.setUserData("Close");
		btnHigh.setUserData("High");
		btnLow.setUserData("Low");
		btnVolume.setUserData("Volume");

		btnOpen.setSelected(true);
		btnClose.setSelected(true);
		btnHigh.setSelected(true);
		btnLow.setSelected(true);

		HBox hb = new HBox();
		hb.setAlignment(Pos.BOTTOM_CENTER);
		


		hb.getChildren().addAll(btnOpen,btnClose,btnHigh,btnLow,btnVolume);

		gp.add(hb,0,3);
//  just need to copy from chartview to here to see multiple things for one company




		btnOpen.selectedProperty().addListener(new ChangeListener<Boolean>() {
           public void changed(ObservableValue<? extends Boolean> ov,
             Boolean old_val, Boolean new_val) {
             if (new_val) {
             	lineChart.getData().add(ChartView.getChartData(company,"Open"));           	
             }	else {
             	for (int i = 0; i < lineChart.getData().size(); i++){
             		if (lineChart.getData().get(i).getName() == "Open") {
             			lineChart.getData().remove(i);
             		}
             	}
             }

          }
        });

		btnClose.selectedProperty().addListener(new ChangeListener<Boolean>() {
           public void changed(ObservableValue<? extends Boolean> ov,
             Boolean old_val, Boolean new_val) {
             if (new_val) {
             	lineChart.getData().add(ChartView.getChartData(company,"Close"));           	
             }	else {
             	for (int i = 0; i < lineChart.getData().size(); i++){
             		if (lineChart.getData().get(i).getName() == "Close") {
             			lineChart.getData().remove(i);
             		}
             	}
             }

          }
        });

		btnLow.selectedProperty().addListener(new ChangeListener<Boolean>() {
           public void changed(ObservableValue<? extends Boolean> ov,
             Boolean old_val, Boolean new_val) {
             if (new_val) {
             	lineChart.getData().add(ChartView.getChartData(company,"Low"));           	
             }	else {
             	for (int i = 0; i < lineChart.getData().size(); i++){
             		if (lineChart.getData().get(i).getName() == "Low") {
             			lineChart.getData().remove(i);
             		}
             	}
             }

          }
        });

		btnHigh.selectedProperty().addListener(new ChangeListener<Boolean>() {
           public void changed(ObservableValue<? extends Boolean> ov,
             Boolean old_val, Boolean new_val) {
             if (new_val) {
             	lineChart.getData().add(ChartView.getChartData(company,"High"));           	
             }	else {
             	for (int i = 0; i < lineChart.getData().size(); i++){
             		if (lineChart.getData().get(i).getName() == "High") {
             			lineChart.getData().remove(i);
             		}
             	}
             }

          }
        });

		btnVolume.selectedProperty().addListener(new ChangeListener<Boolean>() {
           public void changed(ObservableValue<? extends Boolean> ov,
             Boolean old_val, Boolean new_val) {
             if (new_val) {
             	lineChart.getData().clear();
     			btnOpen.setSelected(false);
				btnClose.setSelected(false);
				btnHigh.setSelected(false);
				btnLow.setSelected(false);
             	lineChart.getData().add(ChartView.getChartData(company,"Volume"));           	
             }	else {
             	for (int i = 0; i < lineChart.getData().size(); i++){
             		if (lineChart.getData().get(i).getName() == "Volume") {
             			lineChart.getData().remove(i);
		     			btnOpen.setSelected(true);
						btnClose.setSelected(true);
						btnHigh.setSelected(true);
						btnLow.setSelected(true);
             		}
             	}
             }

          }
        });





		gp.add(lineChart,0,2);

		//Table on the right

        TableView<DayEntry> dataTableView = StockTableView.getIndTable(company);

        dataTableView.getItems().addAll(company.companyDataProperty());
        gp.add(dataTableView, 1, 2);

		return stage;

	}
}