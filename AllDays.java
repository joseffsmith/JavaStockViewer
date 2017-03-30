import javafx.collections.ObservableList;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.io.*;
import java.util.*;

public class AllDays {
	private ObservableList<DayEntry> entries = FXCollections.observableArrayList();
	private String stockName;
	private String companyName;

	public AllDays(String inStockName, String inCompanyName) {
		stockName = inStockName;
		companyName = inCompanyName;
	}

	public void add(String date,Double open,
		Double high,Double low,Double close,
		int volume, Double adjClose){

		entries.add(new DayEntry(date, open, high, low, close,
		 volume, adjClose));

	}


	public String getStockName() {
		return stockName;
	}

	public void setCompanyName(String inCompanyName) {
		companyName = inCompanyName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public double getAdjValDay() {
		return entries.get(0).getAdjClose();
	}

	public double getAdjValDay(LocalDate date) {
		return 3.0;
	}

	public String toString() {
        StringBuffer temp = new StringBuffer();
        temp.append(stockName+"\t" + companyName+"\n");
        for (int i = 0; i < entries.size(); ++i) {
            temp.append( entries.get(i).toString() + "\n" );
        }
        return temp.toString();	
	}

}