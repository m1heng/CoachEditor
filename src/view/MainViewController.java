package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import beans.Fileloader;
import javafx.stage.Stage;

public class MainViewController {
	
	
	
	
	
	public void start(Stage primaryStage) throws IOException {
		
		
		String xlsxpath = Fileloader.chooseFile(primaryStage);
		FileInputStream input_document = new FileInputStream(new File(xlsxpath));
        // convert it into a POI object
        XSSFWorkbook my_xlsx_workbook = new XSSFWorkbook(input_document); 
        // Read excel sheet that needs to be updated
        XSSFSheet my_worksheet = my_xlsx_workbook.getSheetAt(0); 
        
        
        
	}
}
