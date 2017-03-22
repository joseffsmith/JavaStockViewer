import java.util.Scanner;
import java.io.File;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StockCSVReader {
	public static Company createObservableList( File file ) {    
		String fileNameAndExt = file.getName();
		String fileName = fileNameAndExt.substring(0, fileNameAndExt.lastIndexOf("."));
		String companyName = getCompName(fileName);
		Company compA = new Company(companyName,fileName);

		try {
			Scanner in = new Scanner( file );


			String headers = in.nextLine();

			while( in.hasNextLine() ) {
				String line  = in.nextLine();

				String[] parts = line.split(",");

				String date = parts[0];
				Double open = Double.parseDouble(parts[1]);
				Double high = Double.parseDouble(parts[2]);
				Double low =  Double.parseDouble(parts[3]);
				Double close = Double.parseDouble(parts[4]); 
				int volume = Integer.parseInt(parts[5]);
				Double adjClose = Double.parseDouble(parts[6]);

				compA.companyDataProperty().add(new DayEntry(date,open,high,low,close,volume,adjClose));
				
			}
			in.close();

			
		} catch(Exception e ){
			System.out.println( "Problem reading file: " + file.getName());
			e.printStackTrace();
		}
		return compA;
		
	}

	public static String getCompName(String stockSym){
		File file = new File("cmt205_cw_data/CompanyNames.txt");
		String result = null;

		try {
			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] parts = line.split(",");
				if (parts[0].contains(stockSym)) {
					result = parts[1];
				}

			}
			scanner.close();
		} catch(Exception e ){
			System.out.println( "Problem reading file: " + file.getName());
			e.printStackTrace();
			
		}
		return result;


	}
}