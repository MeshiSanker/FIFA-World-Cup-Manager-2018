package Ex1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

//Meshi Sanker, ID:205562747

public class TeamPane extends BorderPane {
	private FifaWorldCupData fifa = new FifaWorldCupFileManager(".\\teams.bin", ".\\games.bin");
	public Team team = new Team(null, null, null);

	public TeamPane() throws IOException {
		team = fifa.nextTeam();
		// buttons section
		Button right = new Button(">");
		right.setOnAction(e -> {
			try {
				rightClick();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		Button left = new Button("<");
		left.setOnAction(e -> {
			try {
				leftClick();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		setRight(right);
		setLeft(left);
		setAlignment(left, Pos.CENTER);
		setAlignment(right, Pos.CENTER);
		setPrefWidth(580);
		setTitle();
		setImage();
		setColors();

	}

	private void setTitle() {
		Text name = new Text(team.nameToString().toUpperCase());
		name.setFont(Font.font(30));
		setTop(name);
		setAlignment(name, Pos.CENTER);
	}

	private void setImage() throws FileNotFoundException {
		Image image = new Image(new FileInputStream(".\\photos\\" + team.nameToString() + ".png"));
		ImageView flag = new ImageView(image);
		setCenter(flag);
	}

	private void setColors() {
		Circle c1 = new Circle();
		c1.setFill(team.getMainColor());
		c1.setRadius(6);

		Circle c2 = new Circle();
		c2.setFill(team.getSecColor());
		c2.setRadius(6);

		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.addRow(0, new Label("First Color:"), c1);
		pane.addRow(1, new Label("Second Color:  "), c2);
		setBottom(pane);

	}

	private void rightClick() throws IOException {
		team = fifa.nextTeam();
		setTitle();
		setImage();
		setColors();
	}

	private void leftClick() throws IOException {
		team = fifa.previousTeam();
		setTitle();
		setImage();
		setColors();
	}

}
