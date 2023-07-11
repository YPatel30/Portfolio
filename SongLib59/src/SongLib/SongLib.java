package SongLib;

// Yash Patel and Om Shah 

import java.io.File;
import java.io.PrintWriter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SongLib extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("SongLib.fxml"));
		Scene scene = new Scene(root, 600, 400);
		stage.setScene(scene);
        stage.show();
        
        stage.setOnCloseRequest(event -> {
			PrintWriter writer;
			try {
				File file = new File("filepath.txt");
				file.createNewFile();
				writer = new PrintWriter(file);

				if (SongLibController.oL.isEmpty() == true) {
					writer.println("***");
				} else {
					for (Song s : SongLibController.oL) {
						writer.println(s.name);
						writer.println(s.artist);
						writer.println(s.album);
						writer.println(s.year);
					}
				}
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
        
	}
	
	public static void main(String args[]) {
		launch(args);
	
	}

}
