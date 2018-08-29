package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import beans.Candidate;
import beans.Coach;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MainViewController {
	
	private static final int CandidateStartIndex = 3;
	private static final int CoachStartIndex = 10;
	private static final int CoachNameRowIndex = 1;
	private static final int CandIDIndex = 0;
	private static final int CandYearIndex = 1;
	private static final int CandNameIndex = 2;
	private static final int CandProIndex = 3;
	private static final int CandContractIndex = 4;
	private static final int CandNumIndex = 5;
	private static final int CandCAIndex = 8;
	private static final int CandStatusIndex = 9;
	
	@FXML
	private TextField searchtext;
	
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
    
    @FXML
    private Label candIDLabel;
    
    @FXML
    private Label candYearLabel;

    @FXML
    private Label candProLabel;

    @FXML
    private Label candNameLabel;

    @FXML
    private Label candContractLabel;

    @FXML
    private Label candNumLabel;

    @FXML
    private Label candProvidedLabel;

    @FXML
    private Label candRemainLabel;

    @FXML
    private Label candCALabel;

    @FXML
    private Label candStatusLabel;
    
    @FXML
    private ListView<Coach> coachlistview;

    @FXML
    private TextField coachsearchtext;

    @FXML
    private TableView<Session> coachsessionTableView;

    @FXML
    private TableColumn<Session, String> CoachCandColumn;

    @FXML
    private TableColumn<Session, String> CoachNumColumn;

    @FXML
    private TableColumn<Session, String> CoachdateColumn;

    @FXML
    private TableColumn<Session, String> CoachFormColumn;

    @FXML
    private Label coachnameLabel;
	
	
	private XSSFWorkbook dbworkbook;
	private XSSFSheet dbsheet;
	private String path;
	
	private ArrayList<Candidate> candlist;
	private ArrayList<Session> sesslist;
	private ArrayList<Coach> coachlist;
	private ObservableList<Coach> coachobslist;
	private ObservableList<Candidate> candobslist;
	private ObservableList<Session> sessobslist;
	
	public void start(Stage primaryStage) throws IOException {
		
		
		
		
		String xlsxpath = Fileloader.chooseFile(primaryStage);
		this.path = xlsxpath;
		FileInputStream input_document = new FileInputStream(new File(xlsxpath));
        // convert it into a POI object
		dbworkbook = new XSSFWorkbook(input_document); 
		input_document.close();
        // Read excel sheet that needs to be updated
        dbsheet = dbworkbook.getSheetAt(0); 
        
        //read candidates
        this.readCandidates();
        
        //read all coach
        this.readCoach();
        
        
        
        this.candidatelistview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Candidate>() {
        	@Override
        	public void changed(ObservableValue<? extends Candidate> observable, Candidate oldc, Candidate newc) {
        		populateSession(newc.id);
        	}
        });
        
        this.coachlistview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Coach>() {
        	@Override
        	public void changed(ObservableValue<? extends Coach> observable, Coach oldc, Coach newc) {
        		populateCoachSession(newc);
        	}
        });
        
        
        
        this.refreshCandidateListView(this.candlist);
        this.refreshCoachListView(coachlist);
	}
	
	
	public void populateCoachSession(Coach co) {
		this.coachnameLabel.setText(co.name);
		ArrayList<Session> tempsessionlist = new ArrayList<Session>();
		
		
		
		Iterator<Row> rite = dbsheet.rowIterator();
        while(rite.hasNext()) {
        	Row r = (Row)rite.next();
        	if(r.getRowNum() > 2) {
        		if(r.getCell(co.getIndex()) != null    &&     r.getCell(co.getIndex()).toString() != "") {
        			Session newSession = new Session(r.getRowNum(), co.getIndex(), co.name, r.getCell(co.getIndex()).toString(), r.getCell(co.getIndex()+1).toString(), r.getCell(co.getIndex()+2).toString());
        			newSession.setCand(r.getCell(CandNameIndex).toString());
        			tempsessionlist.add(newSession);
        			
        		}
        	}
        }
        
        this.CoachCandColumn.setCellValueFactory(new Callback<CellDataFeatures<Session, String>, ObservableValue<String>>() {
	   	     public ObservableValue<String> call(CellDataFeatures<Session, String> p) {
	   	         return new SimpleStringProperty(p.getValue().getCand());
		   	 }}
	   	);
		this.CoachdateColumn.setCellValueFactory(new Callback<CellDataFeatures<Session, String>, ObservableValue<String>>() {
		   	     public ObservableValue<String> call(CellDataFeatures<Session, String> p) {
		   	         return new SimpleStringProperty(p.getValue().getDate());
		   	     }}
	   	);
		this.CoachFormColumn.setCellValueFactory(new Callback<CellDataFeatures<Session, String>, ObservableValue<String>>() {
		   	     public ObservableValue<String> call(CellDataFeatures<Session, String> p) {
		   	         return new SimpleStringProperty(p.getValue().getForm());
		   	     }}
	   	);
		this.CoachNumColumn.setCellValueFactory(new Callback<CellDataFeatures<Session, String>, ObservableValue<String>>() {
		   	     public ObservableValue<String> call(CellDataFeatures<Session, String> p) {
		   	         return new SimpleStringProperty(p.getValue().num + "");
		   	     }}
	   	);
        
		ObservableList<Session> tempobs = FXCollections.observableArrayList(tempsessionlist);
		this.coachsessionTableView.setItems(tempobs);
		
	}


	public void coachreasech() {
		String target = this.coachsearchtext.getText();
		ArrayList<Coach> resultlist = new ArrayList<Coach>();
		for(int i = 0; i < this.coachlist.size(); i++) {
			Coach o = this.coachlist.get(i);
			if(o.name.contains(target)) {
				resultlist.add(o);
			}
		}
		this.refreshCoachListView(resultlist);
	}
	
	public void coachclear() {
		this.refreshCoachListView(this.coachlist);
	}
	
	public void readCandidates() {
		candlist = new ArrayList<Candidate>();
        Iterator<Row> rite = dbsheet.rowIterator();
        while(rite.hasNext()) {
        	Row r = (Row)rite.next();
        	if(r.getRowNum() > 2) {
        		String candname = r.getCell(2).toString();
        		int candid = (int) r.getCell(0).getNumericCellValue();
        		Candidate cand = new Candidate(candid, candname);
        		candlist.add(cand);
        	}
        }
	}
	
	public void readCoach() {
		coachlist = new ArrayList<Coach>();
		Row coachRow = dbsheet.getRow(CoachNameRowIndex);
        Iterator<Cell> cite = coachRow.cellIterator();
        while(cite.hasNext()) {
        	Cell c = cite.next();
        	if(c.getColumnIndex() >= CoachStartIndex ) {
        		String coachname = c.getStringCellValue();
        		this.coachlist.add(new Coach(c.getColumnIndex(), coachname));
        		System.out.println(coachname); 
        		if(cite.hasNext()) {
        			cite.next();
        		}
        		if(cite.hasNext()) {
        			cite.next();
        		}
        	}
        }
	}
	
	public void refreshCoachListView(ArrayList<Coach> clist) {
		coachobslist = FXCollections.observableArrayList(clist);
		this.coachlistview.setItems(coachobslist);
		this.coachlistview.getSelectionModel().selectFirst();
	}
	
	public void refreshCandidateListView(ArrayList<Candidate> clist) {
		candobslist = FXCollections.observableArrayList(clist);
        candidatelistview.setItems(candobslist);
        candidatelistview.getSelectionModel().selectFirst();
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
			this.sesslist.add(new Session(rowIndex, numCell.getColumnIndex(), nameCell.toString(), numCell.toString(), dateCell.toString(), formCell.toString() ));
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
		this.sessobslist = FXCollections.observableArrayList(sesslist);
		this.sessionTableView.setItems(this.sessobslist);
		
		
		
		this.candIDLabel.setText(candid + "");
		this.candYearLabel.setText(r.getCell(CandYearIndex).toString());
		this.candNameLabel.setText(r.getCell(CandNameIndex).toString());
		this.candContractLabel.setText(r.getCell(CandContractIndex).toString());
		this.candCALabel.setText(r.getCell(CandCAIndex).toString());
		this.candNumLabel.setText(r.getCell(CandNumIndex).toString());
		this.candProLabel.setText(r.getCell(CandProIndex).toString());
		this.candStatusLabel.setText(r.getCell(CandStatusIndex).toString());
		
		
	}
	
	
	
	public void addSession() throws IOException {
		FXMLLoader Popfl = new FXMLLoader(getClass().getResource("/view/SessionView.fxml"));
    	AnchorPane root = (AnchorPane)Popfl.load();
    	SessionController popc = Popfl.getController();
    	
    	Stage PopStage = new Stage();
    	popc.start(PopStage, this.coachlist, this);
    	Scene PopSence = new Scene(root);
    	PopStage.setScene(PopSence);
    	PopStage.show();
	}
	
	public void editSession() throws IOException {
		FXMLLoader Popfl = new FXMLLoader(getClass().getResource("/view/SessionEdittingView.fxml"));
    	AnchorPane root = (AnchorPane)Popfl.load();
    	SessionEdittingController popc = Popfl.getController();
    	
    	Stage PopStage = new Stage();
    	popc.start(PopStage, this, this.sessionTableView.getSelectionModel().getSelectedItem());
    	Scene PopSence = new Scene(root);
    	PopStage.setScene(PopSence);
    	PopStage.show();
	}
	
	public void _newSession(Coach c, String num, String form, String dates ) {
		int rowIndex = this.candidatelistview.getSelectionModel().getSelectedItem().id + CandidateStartIndex -1;
		int columnIndex = c.getIndex();
		Row r = this.dbsheet.getRow(rowIndex);
		Cell numCell = r.getCell(columnIndex);
		if(numCell == null) {
			r.createCell(columnIndex).setCellValue(num);
		}else {
			numCell.setCellValue(num);
		}
		
		
		Cell dateCell = r.getCell(columnIndex + 1);
		if(dateCell == null) {
			r.createCell(columnIndex + 1).setCellValue(dates);
		}else {
			dateCell.setCellValue(dates);
		}
		
		Cell formCell = r.getCell(columnIndex + 2);
		if(formCell == null) {
			r.createCell(columnIndex + 2).setCellValue(form);
		}else {
			formCell.setCellValue(form);
		}
	
		this.populateSession(this.candidatelistview.getSelectionModel().getSelectedItem().id);
	
	}
	
	public void _editSession(int rowIndex, int columnIndex, String num, String dates) {
		this.dbsheet.getRow(rowIndex).getCell(columnIndex).setCellValue(num);
		this.dbsheet.getRow(rowIndex).getCell(columnIndex+1).setCellValue(dates);
		this.populateSession(this.candidatelistview.getSelectionModel().getSelectedItem().id);
	}

	public void addCandidate() throws IOException {
		this.CandidateEdition(true);
	}
	
	public void editCandidate() throws IOException {
		this.CandidateEdition(false);
	}
	
	public void CandidateEdition(boolean AorE) throws IOException {
		FXMLLoader Popfl = new FXMLLoader(getClass().getResource("/view/CandidateView.fxml"));
    	AnchorPane root = (AnchorPane)Popfl.load();
    	CandidateViewController popc = Popfl.getController();
    	Stage PopStage = new Stage();
    	if(AorE) {
    		popc.start(PopStage,AorE, this, -1, null,null,null,null,null,null,null );
    	}else {
    		popc.start(PopStage, AorE, this,
    				this.candidatelistview.getSelectionModel().getSelectedItem().id + CandidateStartIndex -1,
    				this.candNameLabel.getText(),
    				this.candYearLabel.getText(),
    				this.candProLabel.getText(),
    				this.candContractLabel.getText(),
    				this.candNumLabel.getText(),
    				this.candCALabel.getText(),
    				this.candStatusLabel.getText());
    	}
    	
    	Scene PopSence = new Scene(root);
    	PopStage.setScene(PopSence);
    	PopStage.show();
	}
	
	public void _editCandidate(int index, String name, String year, String program, String contract, String num, String ca, String status) {
		int newindex = 0;
		Row newRow = null;
		
		if(index == -1) {
			newindex = this.dbsheet.getLastRowNum() + 1;
			int newid    = this.dbsheet.getLastRowNum() - 1;
			newRow = this.dbsheet.createRow(newindex);
			newRow.createCell(0).setCellValue(newid);
			newRow.createCell(CandYearIndex).setCellValue(year);
			newRow.createCell(CandNameIndex).setCellValue(name);
			newRow.createCell(CandProIndex).setCellValue(program);
			newRow.createCell(CandContractIndex).setCellValue(contract);
			newRow.createCell(CandNumIndex).setCellValue(num);
			newRow.createCell(CandCAIndex).setCellValue(ca);
			newRow.createCell(CandStatusIndex).setCellValue(status);
		}else {
			newindex = index;
			newRow = this.dbsheet.getRow(index);
			newRow.getCell(CandYearIndex).setCellValue(year);
			newRow.getCell(CandNameIndex).setCellValue(name);
			newRow.getCell(CandProIndex).setCellValue(program);
			newRow.getCell(CandContractIndex).setCellValue(contract);
			newRow.getCell(CandNumIndex).setCellValue(num);
			newRow.getCell(CandCAIndex).setCellValue(ca);
			newRow.getCell(CandStatusIndex).setCellValue(status);
		}
		
		this.readCandidates();
		this.refreshCandidateListView(this.candlist);
	}
	
	public void search() {
		String target = this.searchtext.getText();
		ArrayList<Candidate> resultlist = new ArrayList<Candidate>();
		for(int i = 0; i < this.candlist.size(); i++) {
			Candidate o = this.candlist.get(i);
			if(o.name.contains(target)) {
				resultlist.add(o);
			}
		}
		this.refreshCandidateListView(resultlist);
	}
	
	public void cancelsearch() {
		this.refreshCandidateListView(candlist);
	}
	
	public void saveChanges() throws IOException {
		FileOutputStream outFile =new FileOutputStream(new File(this.path));
        this.dbworkbook.write(outFile);
        outFile.close();
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
