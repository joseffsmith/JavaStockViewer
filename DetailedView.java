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

public class DetailedView {
	public static Stage detailedViewStage(Company company){
		GridPane gp = new GridPane();
		Scene scene = new Scene(gp,1200,600);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle(company.toString());

		ColumnConstraints col1 = new ColumnConstraints();	
		col1.setPercentWidth(50);
		gp.getColumnConstraints().add(col1);
		
		// Company name top left

		Label lblCompName = new Label(company.getCompanyName());
		Label lblMostRecentClose = new Label(company.getMostRecentClose().toString());

		gp.add(lblMostRecentClose,0,1);
		gp.add(lblCompName,0,0);

		
		//Stock Symbol Top Right
		
		Label lblStockSym = new Label(company.getStockSymbol());

		Double percChange = company.getTickerString(true);
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

		gp.add(lblStockSym,1,0);
		gp.add(textRef,1,1);


		//Chart on the left

		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		String[] colName = {"Open","Close"};
		

		yAxis.setLabel("Price");    
		LineChart lineChart = new LineChart(xAxis,yAxis);
		lineChart.setHorizontalGridLinesVisible(false);
		lineChart.setVerticalGridLinesVisible(false);
		lineChart.setData(ChartView.getChartData(company,colName));
		lineChart.setCreateSymbols(false);

		
		CheckBox btnOpen = new CheckBox("Open");
		CheckBox btnClose = new CheckBox("Close");
		CheckBox btnHigh = new CheckBox("High");
		CheckBox btnLow = new CheckBox("Low");
		CheckBox btnVolume = new CheckBox("Volume");



//  just need to copy from chartview to here to see multiple things for one company










		gp.add(lineChart,0,2);

		//Table on the right

        TableView<DayEntry> dataTableView = StockTableView.getIndTable(company);

        dataTableView.getItems().addAll(company.companyDataProperty());
        gp.add(dataTableView, 1, 2);

		return stage;

	}
}