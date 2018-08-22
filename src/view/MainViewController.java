package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import beans.Candidate;
import beans.Fileloader;
import beans.Session;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MainViewController {
	
	private static final int CandidateStartIndex = 3;
	private static final int CoachStartIndex = 10;
	private static final int CoachNameRowIndex = 1;
	
	
	
	@FXML
	private ListView<Candidate> candidatelistview;
	
	@FXML
	private TableView<Session> sessionTableView;
	
    @FXML
    private TableColumn<Session, String> coachcolumn;

    @FXML
    private TableColumn<Session, String> sessionnumcolumn;

    @FXML
    private TableColumn<Session, String> datecolumn;

    @FXML
    private TableColumn<Session, String> formcolumn;
	
	
	private XSSFWorkbook dbworkbook;
	private XSSFSheet dbsheet;
	
	private ArrayList<Candidate> candlist;
	private ArrayList<Session> sesslist;
	private ObservableList<Candidate> candobslist;
	private ObservableList<Session> sessobslist;
	
	public void start(Stage primaryStage) throws IOException {
		candlist = new ArrayList<Candidate>();
		
		
		String xlsxpath = Fileloader.chooseFile(primaryStage);
		FileInputStream input_document = new FileInputStream(new File(xlsxpath));
        // convert it into a POI object
		dbworkbook = new XSSFWorkbook(input_document); 
        // Read excel sheet that needs to be updated
        dbsheet = dbworkbook.getSheetAt(0); 
        
        //read candidates
        Iterator rite = dbsheet.rowIterator();
        while(rite.hasNext()) {
        	Row r = (Row)rite.next();
        	if(r.getRowNum() > 2) {
        		String candname = r.getCell(2).getStringCellValue();
        		int candid = (int) r.getCell(0).getNumericCellValue();
        		Candidate cand = new Candidate(candid, candname);
        		candlist.add(cand);
        	}
        }
        
        candobslist = FXCollections.observableArrayList(candlist);
        candidatelistview.setItems(candobslist);
        
        
        this.candidatelistview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Candidate>() {
        	@Override
        	public void changed(ObservableValue<? extends Candidate> observable, Candidate oldc, Candidate newc) {
        		populateSession(newc.id);
        	}
        });
        
        
        
	}
	
	
	public void populateSession(int candid) {
		int rowIndex = candid + CandidateStartIndex -1;
		
		
		//read session from dbxlsx
		this.sesslist = new ArrayList<Session>();
		Row r = this.dbsheet.getRow(rowIndex);
		Iterator cite = r.cellIterator();
		while(cite.hasNext()) {
			Cell numCell = (Cell)cite.next();
			if(numCell.getColumnIndex() < this.CoachStartIndex || isCellEmpty(numCell)) {
				continue;
			}
			Cell dateCell = (Cell)cite.next();
			Cell formCell = (Cell)cite.next();
			Cell nameCell = this.dbsheet.getRow(CoachNameRowIndex).getCell(numCell.getColumnIndex());

			System.out.println(nameCell.toString() + dateCell.toString() +  formCell.toString() + numCell.getNumericCellValue());
			this.sesslist.add(new Session(nameCell.toString(), (int)numCell.getNumericCellValue(), dateCell.toString(), formCell.toString() ));
		}
		
		this.coachcolumn.setCellValueFactory(new Callback<CellDataFeatures<Session, String>, ObservableValue<String>>() {
	   	     public ObservableValue<String> call(CellDataFeatures<Session, String> p) {
	   	         return new SimpleStringProperty(p.getValue().getName());
	   	     }}
    	);
		this.datecolumn.setCellValueFactory(new Callback<CellDataFeatures<Session, String>, ObservableValue<String>>() {
	   	     public ObservableValue<String> call(CellDataFeatures<Session, String> p) {
	   	         return new SimpleStringProperty(p.getValue().getDate());
	   	     }}
    	);
		this.formcolumn.setCellValueFactory(new Callback<CellDataFeatures<Session, String>, ObservableValue<String>>() {
	   	     public ObservableValue<String> call(CellDataFeatures<Session, String> p) {
	   	         return new SimpleStringProperty(p.getValue().getForm());
	   	     }}
    	);
		this.sessionnumcolumn.setCellValueFactory(new Callback<CellDataFeatures<Session, String>, ObservableValue<String>>() {
	   	     public ObservableValue<String> call(CellDataFeatures<Session, String> p) {
	   	         return new SimpleStringProperty(p.getValue().num + "");
	   	     }}
    	);
		
		System.out.println(this.sesslist.size());
		this.sessobslist = FXCollections.observableArrayList(sesslist);
		this.sessionTableView.setItems(this.sessobslist);
		
	}
	
	
	public static boolean isCellEmpty(final Cell cell) {
	    if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
	        return true;
	    }

	    if (cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().isEmpty()) {
	        return true;
	    }

	    return false;
	}
}
