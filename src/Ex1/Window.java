package Ex1;

import java.io.FileInputStream;
import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

//Meshi Sanker, ID:205562747

public class Window extends Application {
	private FifaWorldCupData fifa = new FifaWorldCupFileManager(".\\teams.bin", ".\\games.bin");
	private Game game = new Game(null, null, 0, 0);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		BorderPane layout = new BorderPane();
		Scene scene = new Scene(layout, 1200, 570);

		// logo section
		Image image = new Image(new FileInputStream("logo.png"));
		ImageView logo = new ImageView(image);
		HBox logoHbox = new HBox(logo);
		Background logoBack = new Background(new BackgroundFill(Color.web("#8a000b"), CornerRadii.EMPTY, Insets.EMPTY));
		logoHbox.setBackground(logoBack);
		logoHbox.setAlignment(Pos.CENTER);

		// Teams section
		TeamPane rightTeam = new TeamPane();
		TeamPane leftTeam = new TeamPane();
		HBox teamsBox = new HBox(leftTeam, rightTeam);
		Background teamsBack = new Background(
				new BackgroundFill(Color.web("#3759E8"), CornerRadii.EMPTY, Insets.EMPTY));
		teamsBox.setBackground(teamsBack);
		teamsBox.setPadding(new Insets(5, 5, 5, 5));
		teamsBox.setSpacing(10);
		teamsBox.setAlignment(Pos.CENTER);

		// buttons section
		Button newMatch = new Button("New Match");
		newMatch.setOnAction(e -> newMatchClicked(leftTeam, rightTeam));
		Button matches = new Button("Matches");
		matches.setOnAction(e -> {
			try {
				matchesClicked();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		HBox butHbox = new HBox(10, newMatch, matches);
		butHbox.setPadding(new Insets(15, 12, 15, 12));
		butHbox.setAlignment(Pos.CENTER);

		layout.setCenter(teamsBox);
		layout.setTop(logoHbox);
		layout.setBottom(butHbox);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private void matchesClicked() throws IOException {
		Stage stage = new Stage();

		ObservableList<Integer> indexes = FXCollections.observableArrayList();
		ComboBox<Integer> comboBox = new ComboBox<>(indexes);
		comboBox.setMinWidth(200);
		comboBox.setPromptText("Choose game here");
		int count = fifa.getNumOfGames();
		for (int i = 1; i <= count; i++) {
			indexes.add(i);
		}

		game = fifa.getGameAt(1);
		Label str = new Label(game.gameToString());
		str.setFont(Font.font(25));
		str.setStyle("-fx-text-fill: WHITE");
		comboBox.setOnAction(e -> {
			int index = indexes.indexOf(comboBox.getValue());
			try {
				game = fifa.getGameAt(index + 1);
				str.setText(game.gameToString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		VBox box = new VBox(comboBox, str);
		Background teamsBack = new Background(
				new BackgroundFill(Color.web("#8a000b"), CornerRadii.EMPTY, Insets.EMPTY));
		box.setBackground(teamsBack);
		box.setSpacing(40);
		box.setAlignment(Pos.CENTER);

		stage.setScene(new Scene(box, 300, 200));
		stage.show();
	}

	private void newMatchClicked(TeamPane team1, TeamPane team2) {
		Stage stage = new Stage();

		Label home = new Label("HOME");
		home.setStyle("-fx-text-fill: WHITE");
		Label guest = new Label("GUEST");
		guest.setStyle("-fx-text-fill: WHITE");

		TextField hName = new TextField(team1.team.nameToString().toUpperCase());
		hName.setEditable(false);
		TextField gName = new TextField(team2.team.nameToString().toUpperCase());
		gName.setEditable(false);

		TextField hScore = new TextField("Enter Score Here");
		TextField gScore = new TextField("Enter Score Here");

		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.addRow(0, home, guest);
		pane.addRow(1, hName, gName);
		pane.addRow(2, hScore, gScore);
		pane.setHgap(15);
		pane.setVgap(15);

		Button save = new Button("Save");
		save.setOnAction(e -> {
			try {
				saveclicked(team1.team.getName(), hScore.getText(), team2.team.getName(), gScore.getText());
			} catch (NumberFormatException | IOException e1) {
				new Alert(Alert.AlertType.ERROR, "Unable to save the game:for input string:" + e1.getMessage())
						.showAndWait();
				return;
			}
		});

		VBox box = new VBox(40, pane, save);
		Background teamsBack = new Background(
				new BackgroundFill(Color.web("#8a000b"), CornerRadii.EMPTY, Insets.EMPTY));
		box.setBackground(teamsBack);
		box.setAlignment(Pos.CENTER);

		stage.setScene(new Scene(box, 420, 270));
		stage.show();
	}

	private void saveclicked(char[] hName, String hStr, char[] gName, String gStr) throws IOException {

		Alert alert = new Alert(AlertType.ERROR);
		
		if (Integer.parseInt(hStr) < 0 || Integer.parseInt(gStr) < 0) {
			if (Integer.parseInt(hStr) < 0 && Integer.parseInt(gStr) < 0)
				alert.setContentText("Unable to save the game: HOME and GUEST score must be a non-negetive integer!");
			else if (Integer.parseInt(hStr) < 0)
				alert.setContentText("Unable to save the game: HOME score must be a non-negetive integer!");
			else if (Integer.parseInt(gStr) < 0)
				alert.setContentText("Unable to save the game:GUEST score must be a non-negetive integer!");
			alert.showAndWait();
		}

		else if (Integer.parseInt(hStr) >= 0 && Integer.parseInt(gStr) >= 0) {
			game.setHostTeam(hName);
			game.setGuestTeam(gName);
			game.setHostScore(Integer.parseInt(hStr));
			game.setGuestScore(Integer.parseInt(gStr));
			fifa.saveGame(game);
		}

	}

}
