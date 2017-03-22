import java.util.Collections;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.*;
import java.io.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class DayEntry {
	private StringProperty date;
	private DoubleProperty open;
	private DoubleProperty high;
	private DoubleProperty low;
	private DoubleProperty close;
	// Volume in the millions but int still sufficient
	private IntegerProperty volume;
	private DoubleProperty adjClose;
	
	public DayEntry(String indate,Double inopen,
		Double inhigh,Double inlow,Double inclose,
		int involume, Double inadjClose){
		setDate(indate);
		setOpen(inopen);
		setHigh(inhigh);
		setLow(inlow);
		setClose(inclose);
		setVolume(involume);
		setAdjClose(inadjClose);

	}

	public final void setDate(String value){
		dateProperty().set(value);
	}

	public final String getDate(){
		return dateProperty().get();

	}

	public StringProperty dateProperty() {
		if (date == null) {
			date = new SimpleStringProperty();
		}
		return date;
	}

	public final void setOpen(Double value){
		openProperty().set(value);
	}

	public final Double getOpen(){
		return openProperty().get();

	}

	public DoubleProperty openProperty() {
		if (open == null) {
			open = new SimpleDoubleProperty();
		}
		return open;
	}


	public final void setHigh(Double value){
		highProperty().set(value);
	}

	public final Double getHigh(){
		return highProperty().get();

	}

	public DoubleProperty highProperty() {
		if (high == null) {
			high = new SimpleDoubleProperty();
		}
		return high;
	}

	public final void setLow(Double value){
		lowProperty().set(value);
	}

	public final Double getLow(){
		return lowProperty().get();

	}

	public DoubleProperty lowProperty() {
		if (low == null) {
			low = new SimpleDoubleProperty();
		}
		return low;
	}

	public final void setClose(Double value){
		closeProperty().set(value);
	}

	public final Double getClose(){
		return closeProperty().get();

	}

	public DoubleProperty closeProperty() {
		if (close == null) {
			close = new SimpleDoubleProperty();
		}
		return close;
	}

	public final void setVolume(int value){
		volumeProperty().set(value);
	}

	public final int getVolume(){
		return volumeProperty().get();

	}

	public IntegerProperty volumeProperty() {
		if (volume == null) {
			volume = new SimpleIntegerProperty();
		}
		return volume;
	}

	public final void setAdjClose(Double value){
		adjCloseProperty().set(value);
	}

	public final Double getAdjClose(){
		return adjCloseProperty().get();

	}

	public DoubleProperty adjCloseProperty() {
		if (adjClose == null) {
			adjClose = new SimpleDoubleProperty();
		}
		return adjClose;
	}
	public String toString(){
		return getDate() + ", " + getOpen() + ", " + getHigh() + ", " + getLow() + ", " + getClose() + ", " + getVolume() + ", " + getAdjClose() + ",\n";
	}




}