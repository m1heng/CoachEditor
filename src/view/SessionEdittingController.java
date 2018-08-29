package view;

import beans.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SessionEdittingController {
    @FXML
    private Label nameLabel;

    @FXML
    private TextField numField;

    @FXML
    private TextArea dateField;
    
    private Stage popStage;
    private MainViewController mainView;
    private Session s;
    
    
    public void start(Stage popStage, MainViewController mainview, Session s) {
    	this.popStage = popStage;
    	this.mainView = mainview;
    	this.s = s;
    	this.nameLabel.setText(s.coach);
    	this.numField.setText(s.num);
    	this.dateField.setText(s.dates);
    }
    
    public void confirm() {
    	this.mainView._editSession(s.rowIndex, s.columnIndex, this.numField.getText(), this.dateField.getText());
    	this.popStage.close();
    }
    public void cancel() {
    	this.popStage.close();
    }
}
