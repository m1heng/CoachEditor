package beans;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class Fileloader {
	public static String chooseFile(Stage s) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter(".xlsx"));
		
		File f = fc.showOpenDialog(s);
		if(f != null) {
			return f.getPath();
		}else {
			return null;
		}
	}
}
