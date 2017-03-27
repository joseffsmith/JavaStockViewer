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

public class ChartView {
	public static GridPane getChart() {

		GridPane gridpane = new GridPane();
		gridpane.setHgap(10);
		gridpane.setVgap(10);
		ColumnConstraints col1 = new ColumnConstraints();
		RowConstraints row1 = new RowConstraints();
		col1.setPercentWidth(100);
		row1.setPercentHeight(100);
		gridpane.getColumnConstraints().add(col1);
		gridpane.getRowConstraints().add(row1);

		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();


		yAxis.setLabel("Price (GBX)");

		ObservableList<Company> companies = StockTableView.getCompanies();


		
		LineChart lineChart = new LineChart(xAxis,yAxis);
		String colName = "Volume"
		//list of companies on the side, when one is cliced load
		// getCHartData for the company thats clicked with whatever checkboxes are clicked
		// when checkboxes are clicked change all the companys that are currently clicked their 
		lineChart.setData(getChartData(companies.get(0),colName));
		yAxis.setLabel(colName);
		lineChart.setCreateSymbols(false);


		gridpane.add(lineChart,0,0);



		return gridpane;
	}



	private static ObservableList<XYChart.Series<String,Double>> getChartData(Company comp, String colName) {
		ObservableList<XYChart.Series<String, Double>> answer = FXCollections.observableArrayList();
		Series<String, Double> openL= new Series<String, Double>();

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
		return answer;
	}

	private static ObservableList<XYChart.Series<String,Double>> getChartData(ObservableList<Company> companies, String colName) {
		ObservableList<XYChart.Series<String, Double>> answer = FXCollections.observableArrayList();
		for (int j = 0; j <companies.size();j++) {
			Series<String, Double> openL = new Series<String, Double>();

			Company comp = companies.get(j);
			openL.setName(comp.getCompanyName());

			for (int i=comp.companyDataProperty().size()-1; i >= 0 ; i-- ){
				openL.getData().add(new XYChart.Data(comp.companyData.get(i).getDate(),comp.companyData.get(i).getOpen()));
			}
			answer.add(openL);
		}
		return answer;
	}


}