package SongLib;

// Yash Patel and Om Shah 

import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class SongLibController implements Initializable {


	@FXML
	private Button buttonEdit;

	@FXML
	private Button deleteButton;

	@FXML
	private Button buttonAdd;

	@FXML
	private Button saveButton;

	@FXML
	private Button buttonCancel;

	@FXML
	private AnchorPane Song;

	@FXML
	private TextField Title;

	@FXML
	private TextField Year;

	@FXML
	private TextField Album;

	@FXML
	private TextField Artist;


	@FXML
	private Button SavebuttonEdit;

	@FXML
	private ListView<String> SongList;

	static ObservableList<Song> oL = FXCollections.observableArrayList();

	// The @Override statement 
	public void initialize(URL location, ResourceBundle resources) {
		onStart();
	}
	

	private void textReset() {
		textEnable();
		Title.setText("");
		Artist.setText("");
		Album.setText("");
		Year.setText("");
		textDisable();
	}

	private void goToMain() {
		textDisable();
		buttonAdd.setVisible(true);
		SavebuttonEdit.setVisible(false);
		SongList.setDisable(false);
		deleteButton.setVisible(true);
		buttonEdit.setVisible(true);
		saveButton.setVisible(false);
		buttonCancel.setVisible(false);

	}

	

	private void textDisable() {
		Title.setEditable(false);
		Year.setEditable(false);
		Album.setEditable(false);
		Artist.setEditable(false);
		Title.setDisable(true);
		Year.setDisable(true);
		Album.setDisable(true);
		Artist.setDisable(true);

	}

	private void textEnable() {
		Title.setDisable(false);
		Year.setDisable(false);
		Album.setDisable(false);
		Artist.setDisable(false);
		Title.setEditable(true);
		Year.setEditable(true);
		Album.setEditable(true);
		Artist.setEditable(true);
	}

	// Organizes the OL with the use of Comparators and then we add the list to the listView 
	private void makeNewList() {
		oL.sort(new Song());
		SongList.getItems().clear();
		oL.forEach((n) -> SongList.getItems().add(n.name + "\n" + n.artist));

	}

	private void getAlert(char a) {
		if (a == 'a') {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("This particular song is within the library!");
			alert.setHeaderText("This song already exists");
			alert.showAndWait();
		} else if (a == 'b') {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("There is some missing information");
			alert.setHeaderText("You cannot add a song!");
			alert.setContentText("You need both a title and artist to add to this song library");
			alert.showAndWait();
		}
	}

	private boolean check(Song song) {

		for (Song a : oL) {
			if (song.compare(song, a) == 0)
				return false;
		}
		return true;
	}

	// The file is getting changed into list of songs or List view upon the start command 
	private void onStart() {
		// sees if the file exists 
		String filepath = "filepath.txt";
		File f = new File(filepath);

		if (!f.isFile()) {

			// if the file is dne(does not exist), upon the first execution.
			// create a file that is empty 
			try {
				if (!f.createNewFile()) {
					System.out.println("file or directory created");
				}
				BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
				writer.write("***"); // no entries are available 
				writer.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// this make the ObservableArrayList which stores the songs
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filepath));
			reader.mark(3);
			String s = reader.readLine();
			if (s.equals("***")) {
				System.out.println("There are no songs in the file");
				reader.close();
				return;
			}
			reader.reset();
			String st;
			while ((st = reader.readLine()) != null) {
				String name = st;
				String artist = reader.readLine();
				String album = reader.readLine();
				String year = reader.readLine();
				Song stored = new Song(name, artist, album, year);
				oL.add(stored);
			}
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		makeNewList();

		if (!oL.isEmpty()) {
			SongList.getSelectionModel().selectFirst();
		}

		selectNode(null);

	}

	@FXML
	void selectNode(MouseEvent event) {

		if (oL.isEmpty()) {
			textReset();

		} else {

			int index = SongList.getSelectionModel().getSelectedIndex();
			// prints out the index
			textEnable();
			Title.setText(oL.get(index).name);
			Artist.setText(oL.get(index).artist);
			Album.setText(oL.get(index).album);
			Year.setText(oL.get(index).year);
			textDisable();
		}

	}


	@FXML
	void deleteButton(ActionEvent event) {
		if (oL.size() == 0)
			return;
		int index = SongList.getSelectionModel().getSelectedIndex();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Do you want to delete this song?");
		alert.setHeaderText("Are you sure you want to delete this song?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			oL.remove(index);
			makeNewList();
			// prints the index of the oL
			if (oL.size() == 0) {
				// does not do anything 
			} else if (index == oL.size()) {
				SongList.getSelectionModel().select(index - 1);
			} else {
				SongList.getSelectionModel().select(index);
			}
			selectNode(null);
		}

	}

	@FXML
	void saveButton(ActionEvent event) {
		String name = Title.getText();
		String artist = Artist.getText();
		String album = Album.getText();
		String year = Year.getText();

		if (name.equals("") || artist.equals("")) {
			getAlert('b');
			return;
		}

		Song add = new Song(name, artist, album, year);

		if (!oL.isEmpty()) {
			if (!check(add)) {
				getAlert('a');
				return;
			}
		}
		oL.add(add);
		makeNewList();
		SongList.getSelectionModel().select(oL.indexOf(add));
		goToMain();
		selectNode(null);

	}

	@FXML
	void buttonAdd(ActionEvent event) {
		deleteButton.setVisible(false);
		buttonEdit.setVisible(false);
		buttonAdd.setVisible(false);
		saveButton.setVisible(true);
		buttonCancel.setVisible(true);
		textReset();
		textEnable();
		SongList.setDisable(true);

	}

	@FXML
	void buttonCancel(ActionEvent event) {

		goToMain();

		if (!oL.isEmpty()) {
			SongList.getSelectionModel().selectFirst();
		}
		selectNode(null);

	}


	@FXML
	void SaveEdit(ActionEvent event) {
		int index = SongList.getSelectionModel().getSelectedIndex();
		String alb = Album.getText();
		String art = Artist.getText();
		String nme = Title.getText();
		String yr = Year.getText();

		if (nme.equals("") || art.equals("")) {
			getAlert('b');
			return;
		}

		Song edited = new Song(nme, art, alb, yr);
		Song old = oL.get(index);
		oL.remove(index);

		if (!check(edited)) {
			getAlert('a');
			oL.add(index, old);
			return;
		}

		oL.add(edited);
		makeNewList();
		SongList.getSelectionModel().select(oL.indexOf(edited));
		goToMain();
		selectNode(null);

	}

	@FXML
	void buttonEdit(ActionEvent event) {
		if (oL.size() == 0)
			return;
		buttonCancel.setVisible(true);
		buttonAdd.setVisible(false);
		SongList.setDisable(true);
		deleteButton.setVisible(false);
		buttonEdit.setVisible(false);
		SavebuttonEdit.setVisible(true);
		textEnable();
	}

	

	
}
