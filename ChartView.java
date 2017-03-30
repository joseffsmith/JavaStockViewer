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
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Toggle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.control.Slider;
public class ChartView {
	public static GridPane getChart() {

		GridPane gridpane = new GridPane();
		gridpane.setHgap(10);
		gridpane.setVgap(10);
		ColumnConstraints col1 = new ColumnConstraints();
		ColumnConstraints col2 = new ColumnConstraints();

		RowConstraints row1 = new RowConstraints();
		RowConstraints row2 = new RowConstraints();

		col1.setPercentWidth(25);
		col2.setPercentWidth(75);
		row1.setPercentHeight(90);
		row2.setPercentHeight(10);

		gridpane.getColumnConstraints().addAll(col1,col2);
		gridpane.getRowConstraints().addAll(row1,row2);

		ObservableList<Company> companies = StockTableView.getCompanies();  

		// ChartSetUp

		// List of Companies

		//Should probably create a company ListView Class
		final ListView<Company> companyListView = new ListView<Company>(companies);
		companyListView.setItems(companies);
		companyListView.setPrefWidth(250);
		companyListView.setMaxWidth(Double.MAX_VALUE);
		companyListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		companyListView.getSelectionModel().selectIndices(0,1);

		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		
		// This needs changing to radio buttons


		ToggleGroup toggles = new ToggleGroup();
		RadioButton btnOpen = new RadioButton("Open");
		RadioButton btnClose = new RadioButton("Close");
		RadioButton btnHigh = new RadioButton("High");
		RadioButton btnLow = new RadioButton("Low");
		RadioButton btnVolume = new RadioButton("Volume");

		Slider timeFrame = new Slider();
		timeFrame.setMin(1);
		timeFrame.setMax(4);
		timeFrame.setValue(4);
		timeFrame.setMajorTickUnit(1);
		timeFrame.setShowTickMarks(true);
		timeFrame.setMinorTickCount(0);
		timeFrame.setSnapToTicks(true);

				
		btnOpen.setToggleGroup(toggles);
		btnOpen.setUserData("Open");
		btnClose.setToggleGroup(toggles);
		btnClose.setUserData("Close");
		btnHigh.setToggleGroup(toggles);
		btnHigh.setUserData("High");
		btnLow.setToggleGroup(toggles);
		btnLow.setUserData("Low");
		btnVolume.setToggleGroup(toggles);  
		btnVolume.setUserData("Volume");

		HBox hb = new HBox();
		hb.setAlignment(Pos.BOTTOM_CENTER);

		hb.getChildren().addAll(btnOpen,btnClose,btnHigh,btnLow,btnVolume,timeFrame);

		btnOpen.setSelected(true);
		String colName = toggles.getSelectedToggle().getUserData().toString();
		yAxis.setLabel(colName);    
		LineChart lineChart = new LineChart(xAxis,yAxis);
		lineChart.setHorizontalGridLinesVisible(false);
		lineChart.setVerticalGridLinesVisible(false);
		lineChart.setData(getChartData(companyListView.getSelectionModel().getSelectedItems(),colName,4));
		lineChart.setTitle("Time Frame: All Data");
		lineChart.setCreateSymbols(false);
		lineChart.setAnimated(false);


		toggles.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			public void changed(ObservableValue<? extends Toggle> ov,
				Toggle old_toggle, Toggle new_toggle){
				if (toggles.getSelectedToggle() != null) {
					String colName = toggles.getSelectedToggle().getUserData().toString();
					yAxis.setLabel(colName);  		
					ObservableList<Company> selectedCompanies = companyListView.getSelectionModel().getSelectedItems();
					Number new_val = timeFrame.getValue();
					lineChart.getData().clear();
					lineChart.setData(getChartData(selectedCompanies,colName,new_val));
					
				}
			}
		});




		companyListView.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				ObservableList<Company> selectedCompanies = companyListView.getSelectionModel().getSelectedItems();
				lineChart.getData().clear();
				Number new_val = timeFrame.getValue();

				lineChart.setData(getChartData(selectedCompanies,colName,new_val));

			}
		});	


		timeFrame.valueProperty().addListener(new ChangeListener<Number>(){
			public void changed(ObservableValue<? extends Number> ov,
				Number old_value, Number new_val) {
				String colName = toggles.getSelectedToggle().getUserData().toString();
				ObservableList<Company> selectedCompanies = companyListView.getSelectionModel().getSelectedItems();
				lineChart.getData().clear();
				lineChart.setData(getChartData(selectedCompanies,colName,new_val));
				int new_val_int = new_val.intValue();
				if (new_val_int == 1) {
					lineChart.setTitle("Time Frame: 1 Week");
				} else if (new_val_int == 2) {
					lineChart.setTitle("Time Frame: 1 Month");
				} else if (new_val_int == 3) {
					lineChart.setTitle("Time Frame: 3 Months");
				} else {
					lineChart.setTitle("Time Frame: All Data");
				}
			}
		});

		//Chart Area

		gridpane.add(hb,1,1,1,1);
		gridpane.add(companyListView,0,0,1,2);
		gridpane.add(lineChart,1,0,1,1);
		return gridpane;
	}



	public static ObservableList<XYChart.Series<String,Double>> getChartData(Company comp, String[] colNames, Number new_val) {
		ObservableList<XYChart.Series<String, Double>> answer = FXCollections.observableArrayList();
		int k=  comp.companyDataProperty().size()-1;
		int value = new_val.intValue();
		if (value == 1){
			k= 4;
		} else if (value == 2){
			k= 19;
		} else if (value == 3) {
			k= 59;
		}

		for (String colName : colNames) {
			XYChart.Series<String, Double> openL= new Series<String, Double>();
			openL.setName(colName);

			for (int i = k; i >= 0 ; i-- ){
				if (colName == "Open") {
					openL.getData().add(new XYChart.Data(comp.companyData.get(i).getDate(),comp.companyData.get(i).getOpen()));

				} else if (colName == "Close") {
					openL.getData().add(new XYChart.Data(comp.companyData.get(i).getDate(),comp.companyData.get(i).getClose()));

				} else if (colName == "High") {
					openL.getData().add(new XYChart.Data(comp.companyData.get(i).getDate(),comp.companyData.get(i).getHigh()));

				} else if (colName == "Low") {
					openL.getData().add(new XYChart.Data(comp.companyData.get(i).getDate(),comp.companyData.get(i).getLow()));

				} else if (colName == "Volume") {
					openL.getData().add(new XYChart.Data(comp.companyData.get(i).getDate(),comp.companyData.get(i).getVolume()));
				}
			}
			answer.add(openL);
		}

		return answer;
	}
	public static XYChart.Series<String,Double> getChartData(Company comp, String colName) {
		int k=  comp.companyDataProperty().size()-1;


		XYChart.Series<String, Double> openL= new Series<String, Double>();
		openL.setName(colName);

		for (int i = k; i >= 0 ; i-- ){
			if (colName == "Open") {
				openL.getData().add(new XYChart.Data(comp.companyData.get(i).getDate(),comp.companyData.get(i).getOpen()));

			} else if (colName == "Close") {
				openL.getData().add(new XYChart.Data(comp.companyData.get(i).getDate(),comp.companyData.get(i).getClose()));

			} else if (colName == "High") {
				openL.getData().add(new XYChart.Data(comp.companyData.get(i).getDate(),comp.companyData.get(i).getHigh()));

			} else if (colName == "Low") {
				openL.getData().add(new XYChart.Data(comp.companyData.get(i).getDate(),comp.companyData.get(i).getLow()));

			} else if (colName == "Volume") {
				openL.getData().add(new XYChart.Data(comp.companyData.get(i).getDate(),comp.companyData.get(i).getVolume()));
			}
		}
		// answer.add(openL);
		

		return openL;
	}

	private static ObservableList<XYChart.Series<String,Double>> getChartData(ObservableList<Company> companies, String colName, Number new_val) {
		ObservableList<XYChart.Series<String, Double>> answer = FXCollections.observableArrayList();



		for (int j = 0; j <companies.size();j++) {
			XYChart.Series<String, Double> openL = new XYChart.Series<String, Double>();

			Company comp = companies.get(j);

			int k= comp.companyDataProperty().size()-1;
			int value = new_val.intValue();
			if (value == 1){
				k=4;
			} else if (value == 2){
				k=19;
			} else if (value == 3) {
				k=59;
			}
			
			openL.setName(comp.getCompanyName());

			for (int i = k; i >= 0 ; i-- ){
				if (colName == "Open") {
					openL.getData().add(new XYChart.Data(comp.companyData.get(i).getDate(),comp.companyData.get(i).getOpen()));

				} else if (colName == "Close") {
					openL.getData().add(new XYChart.Data(comp.companyData.get(i).getDate(),comp.companyData.get(i).getClose()));

				} else if (colName == "High") {
					openL.getData().add(new XYChart.Data(comp.companyData.get(i).getDate(),comp.companyData.get(i).getHigh()));

				} else if (colName == "Low") {
					openL.getData().add(new XYChart.Data(comp.companyData.get(i).getDate(),comp.companyData.get(i).getLow()));

				} else if (colName == "Volume") {
					openL.getData().add(new XYChart.Data(comp.companyData.get(i).getDate(),comp.companyData.get(i).getVolume()));
				}
			}
			

			answer.add(openL);
		}
		return answer;
	}

	public static void setHGridLines(Boolean state) {

	}

	public static void setVGridLines(Boolean state) {

	}

}