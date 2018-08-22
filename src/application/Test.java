package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Test {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		FileInputStream input_document = new FileInputStream(new File("res/sample.xlsx"));
        // convert it into a POI object
        XSSFWorkbook my_xlsx_workbook = new XSSFWorkbook(input_document); 
        // Read excel sheet that needs to be updated
        XSSFSheet my_worksheet = my_xlsx_workbook.getSheetAt(0); 
        
        Cell cell = null; 
        // Access the cell first to update the value
        cell = my_worksheet.getRow(1).getCell(13);
        // Get current value and reduce 5 from it
        System.out.println(cell.getStringCellValue());
        
	}

}
