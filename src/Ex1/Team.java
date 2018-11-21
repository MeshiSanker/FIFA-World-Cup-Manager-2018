package Ex1;

import javafx.scene.paint.Color;

//Meshi Sanker, ID:205562747

public class Team {
	private char[] name;
	private Color mainColor;
	private Color secColor;

	public char[] getName() {
		return name;
	}

	public String nameToString() {
		return name[0] + "" + name[1] + "" + name[2];
	}

	public void setName(char[] name) {
		this.name = name;
	}

	public Color getMainColor() {
		return mainColor;
	}

	public void setMainColor(Color mainColor) {
		this.mainColor = mainColor;
	}

	public Color getSecColor() {
		return secColor;
	}

	public void setSecColor(Color secColor) {
		this.secColor = secColor;
	}

	public Team(char[] name, Color mainColor, Color secColor) {
		super();
		this.setName(name);
		this.mainColor = mainColor;
		this.secColor = secColor;
	}

}
