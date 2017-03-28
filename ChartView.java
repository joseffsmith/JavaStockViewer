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

public class ChartView {
	public static GridPane getChart() {

		GridPane gridpane = new GridPane();
		gridpane.setHgap(10);
		gridpane.setVgap(10);
		ColumnConstraints col1 = new ColumnConstraints();
		ColumnConstraints col2 = new ColumnConstraints();

		RowConstraints row1 = new RowConstraints();
		col1.setPercentWidth(25);
		col2.setPercentWidth(75);
		gridpane.getColumnConstraints().addAll(col1,col2);
		// gridpane.getRowConstraints().add(row1);

		ObservableList<Company> companies = StockTableView.getCompanies();  

		// ChartSetUp



		// lineChart.getData()

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
		hb.getChildren().addAll(btnOpen,btnClose,btnHigh,btnLow,btnVolume);

		btnOpen.setSelected(true);
		String colName = toggles.getSelectedToggle().getUserData().toString();
		yAxis.setLabel(colName);    
		LineChart lineChart = new LineChart(xAxis,yAxis);
		lineChart.setHorizontalGridLinesVisible(false);
		lineChart.setVerticalGridLinesVisible(false);
		lineChart.setData(getChartData(companyListView.getSelectionModel().getSelectedItems(),colName));



		toggles.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			public void changed(ObservableValue<? extends Toggle> ov,
				Toggle old_toggle, Toggle new_toggle){
				if (toggles.getSelectedToggle() != null) {
					String colName = toggles.getSelectedToggle().getUserData().toString();
					yAxis.setLabel(colName);  		
					ObservableList<Company> selectedCompanies = companyListView.getSelectionModel().getSelectedItems();

					lineChart.getData().clear();
					lineChart.setData(getChartData(selectedCompanies,colName));
				}
			}
		});



		
		//list of companies on the side, when one is cliced load
		// getCHartData for the company thats clicked with whatever checkboxes are clicked
		// when checkboxes are clicked change all the companys that are currently clicked their 

		// lineChart.setCreateSymbols(false);
		// bind these to a menu icon





















		// ObservableList<Company> selectedCompanies = FXCollections.<Company>observableArrayList();

		// //need to create a new list based upon what is selected
		// companyListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Company>() {
		//  @Override
		//  public void changed(ObservableValue<? extends Company> observable, Company oldValue, Company newValue) {
		//      System.out.println("ChangedFrom " + oldValue + " to: " + newValue);
		//      if (oldValue != null && !selectedCompanies.contains(oldValue)) {

		//          selectedCompanies.add(oldValue);
		//      }

		//      if (selectedCompanies.contains(newValue)) {
		//          System.out.println("Already got it");
		//          // Remove from list
		//          // selectedCompanies.remove(newValue);
		//          System.out.println(companyListView.getSelectionModel().getSelectedIndices());
		//          //deselect
		//      } else {
		//          selectedCompanies.add(newValue);
		//      }

		//      if (!companyListView.getSelectionModel().getSelectedItems().containsAll(selectedCompanies)){
		//          for (int i=0;i<selectedCompanies.size();i++){
		//              companyListView.getSelectionModel().select(selectedCompanies.get(i));
		//          } 
		//      } else {
		//          System.out.println("all ready");
		//      }
		//      // if companyListView.selected not equal to selectedcompanies, select all

		//      // System.out.println(selectedCompanies);
		//  }
		// });

		companyListView.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				ObservableList<Company> selectedCompanies = companyListView.getSelectionModel().getSelectedItems();
				// System.out.println(selectedCompanies);
				lineChart.getData().clear();
				lineChart.setData(getChartData(selectedCompanies,colName));
				// ObservableList<XYChart.Series<String,Double>> s = lineChart.getData();
				

				// for (XYChart.Series<String, Double> k : s) {
				// 	for (XYChart.Data<String, Double> d : k.getData()) {
				// 		Tooltip t = new Tooltip(d.getXValue().toString() +"\n" + d.getYValue());
				// 		System.out.println(t.getText());
				// 		t.minWidth(20);
				// 		t.minHeight(20);
				// 		d.getNode().minWidth(20);
				// 		d.getNode().minHeight(20);
				// 		Tooltip.install(d.getNode(), t);
						
						// System.out.println(d.getNode().minWidth());
					}
				});	

			// }
		// });
		// for (int i = 0; i < lineChart.getData().size(); i++){
		// 	System.out.println(lineChart.getData().get(i).getData());
		// 	// XYChart.Series<String,Double> s = lineChart.getData().get(i);
		// 	// System.out.println(s);
		// }
	
		


		//Chart Area


		gridpane.add(hb,1,1);
		gridpane.add(companyListView,0,0);
		gridpane.add(lineChart,1,0);
		return gridpane;
	}



	public static ObservableList<XYChart.Series<String,Double>> getChartData(Company comp, String[] colNames) {
		ObservableList<XYChart.Series<String, Double>> answer = FXCollections.observableArrayList();

		for (String colName : colNames) {
			XYChart.Series<String, Double> openL= new Series<String, Double>();
			openL.setName(colName);
			for (int i=comp.companyDataProperty().size()-1; i >= 0 ; i-- ){
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

	private static ObservableList<XYChart.Series<String,Double>> getChartData(ObservableList<Company> companies, String colName) {
		ObservableList<XYChart.Series<String, Double>> answer = FXCollections.observableArrayList();
		for (int j = 0; j <companies.size();j++) {
			XYChart.Series<String, Double> openL = new XYChart.Series<String, Double>();

			Company comp = companies.get(j);
			
			openL.setName(comp.getCompanyName());

			for (int i=comp.companyDataProperty().size()-1; i >= 0 ; i-- ){
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
			
			// for (Data<String,Double> data : openL.getData()){
			// 	// Node node = data.getNode();
			// 	// node.setCurso()
			// 	// data.setNode(new HoverNode(data.getXValue(),data.getYValue()));
			// 	Tooltip.install(data.getNode(), new Tooltip(
			// 		data.getXValue().toString() +"\n" + data.getYValue()));
			// 	// System.out.println(data.getNode());


			// }
			answer.add(openL);
		}
		return answer;
	}

	public static void setHGridLines(Boolean state) {

	}

	public static void setVGridLines(Boolean state) {

	}

}