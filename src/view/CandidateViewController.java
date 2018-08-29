package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CandidateViewController {
    @FXML
    private TextField nameField;

    @FXML
    private TextField yearField;

    @FXML
    private TextField programField;

    @FXML
    private TextField contractField;

    @FXML
    private TextField sessionField;

    @FXML
    private TextField caField;

    @FXML
    private TextField statusField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmButtoin;
    
    private int index;
    
    private MainViewController mainView;
    
    private Stage popStage;
    
    public void start(Stage popStage, boolean AorE, MainViewController mainView,int index, String name, String year, String program, String contract, String num, String ca, String status) {
    	this.index = index;
    	this.popStage = popStage;
    	this.mainView = mainView;
    	if(! AorE) {
    		this.nameField.setText(name);
    		this.yearField.setText(year);
    		this.programField.setText(program);
    		this.contractField.setText(contract);
    		this.sessionField.setText(num);
    		this.caField.setText(ca);
    		this.statusField.setText(status);
    	}
    }
    
    public void confirm() {
    	this.mainView._editCandidate(this.index, this.nameField.getText(), this.yearField.getText(), this.programField.getText(), this.contractField.getText(), this.sessionField.getText(), this.caField.getText(), this.statusField.getText());
    	this.popStage.close();
    }
    
    public void cancel() {
    	this.popStage.close();
    }
}
