import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.application.Application;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.File;
import java.io.FilenameFilter;

public class StockTableView {
	public static GridPane getTable() {


	// }

    // @Override
    // public void start(Stage primaryStage) {
    //     primaryStage.setTitle("TableViewExample");
    //     BorderPane root = new BorderPane();
    //     Scene scene = new Scene(root, 900, 500, Color.WHITE);

        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        // root.setCenter(gridpane);

        // candidates label
        Label candidatesLbl = new Label("Company");
        GridPane.setHalignment(candidatesLbl, HPos.CENTER);
        gridpane.add(candidatesLbl, 0, 0);

        ObservableList<Company> companies = getCompanies();
        final ListView<Company> companyListView = new ListView<>(companies);
        companyListView.setPrefWidth(250);
        companyListView.setMaxWidth(Double.MAX_VALUE);
        companyListView.setPrefHeight(400);

        companyListView.setCellFactory(new Callback<ListView<Company>, ListCell<Company>>() {

            @Override
            public ListCell<Company> call(ListView<Company> param) {
                final Label leadLbl = new Label();
                    final ListCell<Company> cell = new ListCell<Company>() {
                        @Override 
                        public void updateItem(Company item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item != null) {
                                    leadLbl.setText(item.getStockSymbol());
                                    setText(item.getCompanyName());

                                }
                        }
                    }; // ListCell
                    return cell;

            }
        });

        gridpane.add(companyListView, 0, 1);
        Label dataLbl = new Label("Data");
        gridpane.add(dataLbl, 2, 0);
        GridPane.setHalignment(dataLbl, HPos.CENTER);

        // companyListView.getFocusModel().focus(0);
        companyListView.getSelectionModel().selectFirst();

        Company selectedC = companyListView.getSelectionModel().getSelectedItem();
        TableView<DayEntry> dataTableView = getIndTable(selectedC);

        dataTableView.getItems().addAll(selectedC.companyDataProperty());
        gridpane.add(dataTableView, 2, 1);



    	companyListView.getSelectionModel().selectedItemProperty().addListener((
    		ObservableValue<? extends Company> observable, Company oldValue, Company newValue) -> {
    		if (observable != null && observable.getValue() != null) {
    			dataTableView.getItems().clear();
                // dataRows.clear();
                dataTableView.getItems().addAll(observable.getValue().companyDataProperty());
    			// dataRows.addAll(observable.getValue().companyDataProperty());
    		}
    	});
        

    	// primaryStage.setScene(scene);
    	// primaryStage.show();

    	return gridpane;
    }
    public static TableView<DayEntry> getIndTable(Company company){
        final TableView<DayEntry> dataTableView = new TableView<DayEntry>();
        dataTableView.setPrefWidth(600);

        final ObservableList<DayEntry> dataRows =  FXCollections.observableArrayList();
        dataTableView.setItems(dataRows);

        TableColumn<DayEntry, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory("date"));

        dateCol.setPrefWidth(dataTableView.getPrefWidth()/7);

        TableColumn<DayEntry, Double> openCol = new TableColumn<>("open");
        openCol.setCellValueFactory(new PropertyValueFactory("open"));

        dateCol.setPrefWidth(dataTableView.getPrefWidth()/7);

        TableColumn<DayEntry, Double> highCol = new TableColumn<>("high");
        highCol.setCellValueFactory(new PropertyValueFactory("high"));

        dateCol.setPrefWidth(dataTableView.getPrefWidth()/7);

        TableColumn<DayEntry, Double> lowCol = new TableColumn<>("low");
        lowCol.setCellValueFactory(new PropertyValueFactory("low"));

        dateCol.setPrefWidth(dataTableView.getPrefWidth()/7);

        TableColumn<DayEntry, Double> closeCol = new TableColumn<>("close");
        closeCol.setCellValueFactory(new PropertyValueFactory("close"));

        dateCol.setPrefWidth(dataTableView.getPrefWidth()/7);

        TableColumn<DayEntry, Integer> volumeCol = new TableColumn<>("volume");
        volumeCol.setCellValueFactory(new PropertyValueFactory("volume"));

        dateCol.setPrefWidth(dataTableView.getPrefWidth()/7);

        TableColumn<DayEntry, Double> adjCloseCol = new TableColumn<>("Adjusted Close");
        adjCloseCol.setCellValueFactory(new PropertyValueFactory("adjClose"));

        dateCol.setPrefWidth(dataTableView.getPrefWidth()/7);

        dataTableView.getColumns().setAll(dateCol,openCol,highCol,lowCol,closeCol,volumeCol,adjCloseCol);
        
        return dataTableView;
    }



    public static ObservableList<Company> getCompanies() {
    	ObservableList<Company> companies = FXCollections.<Company>observableArrayList();
    	File folder = new File("cmt205_cw_data");
    	File[] listOfFiles = folder.listFiles(new FilenameFilter() {
    		public boolean accept(File folder, String name) {
    			return name.endsWith(".csv");
    		}
    	});
    	for (int i = 0; i < listOfFiles.length; i++) {
    		Company compA = StockCSVReader.createObservableList(listOfFiles[i]);
            companies.add(compA);
    	}
    	return companies;
    }
    // public static void main(String[] args) {
    //     Application.launch(args);
    // }

}