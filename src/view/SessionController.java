package view;

import java.util.ArrayList;

import beans.Candidate;
import beans.Coach;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SessionController {
    @FXML
    private ChoiceBox<Coach> coachBox;

    @FXML
    private TextArea dateField;

    @FXML
    private TextField numField;

    @FXML
    private TextField formField;

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;
    

	private ArrayList<Coach> coachlist;
	private ObservableList<Coach> coachobslist;
	private MainViewController mainview;
	private Stage popStage;

	public void start(Stage popStage, ArrayList<Coach> coachList , MainViewController m) {
		this.popStage = popStage;
		this.coachlist = coachList;
		this.mainview = m;
		this.coachobslist = FXCollections.observableArrayList(this.coachlist);
		this.coachBox.setItems(this.coachobslist);
	}
	
	public void confirm() {
		//dosomething  
		this.mainview._newSession(this.coachBox.getSelectionModel().getSelectedItem(), this.numField.getText(), this.formField.getText(), this.dateField.getText());
		this.popStage.close();
	}
	
	public void cancel() {
		this.popStage.close();
	}

}
