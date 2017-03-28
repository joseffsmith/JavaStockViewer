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

class HoverNode extends StackPane {
	HoverNode(String date, Double value) {
		setPrefSize(5, 5);

		final Tooltip t = createDataThresholdLabel(date, value);

		setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent mouseEvent) {
				getChildren().setAll(t);
				setCursor(Cursor.NONE);
				toFront();
			}
		});
		setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override 
			public void handle(MouseEvent mouseEvent) {
				getChildren().clear();
				setCursor(Cursor.CROSSHAIR);
			}
		});
	}

	private Tooltip createDataThresholdLabel(String date, Double value) {
		Tooltip t = new Tooltip(date + "\n" + value);
						// System.out.println(t.getText());
						// t.minWidth(20);
						// t.minHeight(20);
						// d.getNode().minWidth(20);
						// d.getNode().minHeight(20);
						// Tooltip.install(d.getNode(), t);
		// final Label label = new Label(date + "\n" + value);
		// label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
		// label.setStyle("-fx-font-size: 10;");


		// label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
		return t;
		}
	}

