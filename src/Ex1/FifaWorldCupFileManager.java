package Ex1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import javafx.scene.paint.Color;

//Meshi Sanker, ID:205562747

public class FifaWorldCupFileManager implements FifaWorldCupData {

	private RandomAccessFile rafTeam;
	private RandomAccessFile rafGame;
	// TEAM_LENGTH= team name + 2 colors
	public static final int TEAM_LENGTH = (3 * Character.BYTES) + (6 * Double.BYTES);
	// GAME_LENGTH= 2 names + 2 goal count
	public static final int GAME_LENGTH = (6 * Character.BYTES) + (2 * Integer.BYTES);
	// need to remember to skip the first integer in both files.

	public FifaWorldCupFileManager(String teamFile, String gameFile) {
		super();
		try {
			this.rafTeam = new RandomAccessFile(teamFile, "rw");
			this.rafGame = new RandomAccessFile(gameFile, "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void saveTeam(Team team) throws IOException {

		char[] name = team.getName();
		rafTeam.seek(0);
		int count = rafTeam.readInt();
		rafTeam.seek(0);
		rafTeam.writeInt(count + 1);

		rafTeam.seek(rafTeam.length());
		for (int i = 0; i < 3; i++)
			rafTeam.writeChar(name[i]);

		rafTeam.writeDouble(team.getMainColor().getRed());
		rafTeam.writeDouble(team.getMainColor().getGreen());
		rafTeam.writeDouble(team.getMainColor().getBlue());
		rafTeam.writeDouble(team.getSecColor().getRed());
		rafTeam.writeDouble(team.getSecColor().getGreen());
		rafTeam.writeDouble(team.getSecColor().getBlue());

	}

	@Override
	public void saveTeam(Team team, int index) throws IOException {

		Team tempTeam = new Team(null, null, null);

		rafTeam.seek(0);
		int count = rafTeam.readInt();
		if (count < index) {
			System.out.println("null");
		} else {
			for (int i = count; i >= index; i--) {
				tempTeam = readTeam(i);
				writeTeam(i + 1, tempTeam);
			}

			writeTeam(index, team);

			rafTeam.seek(0);
			rafTeam.writeInt(count + 1);
		}

	}

	public void writeTeam(int index, Team team) throws IOException {
		char[] name = team.getName();
		rafTeam.seek(Integer.BYTES + ((index - 1) * TEAM_LENGTH));

		for (int i = 0; i < 3; i++)
			rafTeam.writeChar(name[i]);
		rafTeam.writeDouble(team.getMainColor().getRed());
		rafTeam.writeDouble(team.getMainColor().getGreen());
		rafTeam.writeDouble(team.getMainColor().getBlue());
		rafTeam.writeDouble(team.getSecColor().getRed());
		rafTeam.writeDouble(team.getSecColor().getGreen());
		rafTeam.writeDouble(team.getSecColor().getBlue());

	}

	public Team readTeam(int index) throws IOException {
		char[] name = new char[3];
		Team team = new Team(null, null, null);
		rafTeam.seek(0);
		int count = rafTeam.readInt();
		// checking if the index exist
		if (count < index) {
			return null;
		} else if (count >= index) {
			rafTeam.seek(Integer.BYTES + ((index - 1) * TEAM_LENGTH));

			for (int i = 0; i < 3; i++)
				name[i] = rafTeam.readChar();
			team.setName(name);
			Color mainColor = new Color(rafTeam.readDouble(), rafTeam.readDouble(), rafTeam.readDouble(), 1.0);
			Color secColor = new Color(rafTeam.readDouble(), rafTeam.readDouble(), rafTeam.readDouble(), 1.0);
			team.setMainColor(mainColor);
			team.setSecColor(secColor);
			return team;
		}
		return team;
	}

	@Override
	public Team nextTeam() throws IOException {
		Team team = new Team(null, null, null);

		if (rafTeam.getFilePointer() >= rafTeam.length() || rafTeam.getFilePointer() <= 0) {
			team = readTeam(1);
		} else {
			int index = (int) ((rafTeam.getFilePointer() - Integer.BYTES) / TEAM_LENGTH);
			team = readTeam(index + 1);
		}

		return team;
	}

	@Override
	public Team previousTeam() throws IOException {
		Team team;

		if (rafTeam.getFilePointer() > Integer.BYTES + TEAM_LENGTH) {
			int index = (int) ((rafTeam.getFilePointer() - Integer.BYTES) / TEAM_LENGTH);
			team = readTeam(index - 1);
		} else {
			rafTeam.seek(0);
			int count = rafTeam.readInt();
			team = readTeam(count);
		}
		return team;
	}

	@Override
	public void saveGame(Game game) throws IOException {
		char[] hName = game.getHostTeam();
		char[] gName = game.getGuestTeam();

		rafGame.seek(0);
		int count = rafGame.readInt();
		rafGame.seek(0);
		rafGame.writeInt(count + 1);
		rafGame.seek(rafGame.length());

		for (int i = 0; i < 3; i++)
			rafGame.writeChar(hName[i]);
		for (int i = 0; i < 3; i++)
			rafGame.writeChar(gName[i]);
		rafGame.writeInt(game.getHostScore());
		rafGame.writeInt(game.getGuestScore());

	}

	@Override
	public void saveGame(Game game, int index) throws IOException {

		Game tempGame = new Game(null, null, 0, 0);

		rafGame.seek(0);
		int count = rafGame.readInt();
		if (count < index) {
			System.out.println("null");
		} else {
			for (int i = count; i >= index; i--) {
				tempGame = getGameAt(i);
				writeGame(i + 1, tempGame);
			}

			writeGame(index, game);

			rafGame.seek(0);
			rafGame.writeInt(count + 1);
		}
	}

	public void writeGame(int index, Game game) throws IOException {
		rafGame.seek(Integer.BYTES + ((index - 1) * GAME_LENGTH));

		char[] hName = game.getHostTeam();
		char[] gName = game.getGuestTeam();

		for (int i = 0; i < 3; i++)
			rafGame.writeChar(hName[i]);
		for (int i = 0; i < 3; i++)
			rafGame.writeChar(gName[i]);
		rafGame.writeInt(game.getHostScore());
		rafGame.writeInt(game.getGuestScore());

	}

	@Override
	public Game getGameAt(int index) throws IOException {
		rafGame.seek(0);
		int count = rafGame.readInt();
		if (count < index) {
			return null;
		} else {
			rafGame.seek(Integer.BYTES + ((index - 1) * GAME_LENGTH));

			Game game = new Game(null, null, 0, 0);
			char[] hName = new char[] { rafGame.readChar(), rafGame.readChar(), rafGame.readChar() };
			char[] gName = new char[] { rafGame.readChar(), rafGame.readChar(), rafGame.readChar() };
			game.setHostTeam(hName);
			game.setGuestTeam(gName);
			game.setHostScore(rafGame.readInt());
			game.setGuestScore(rafGame.readInt());
			return game;
		}
	}

	@Override
	public int getNumOfTeams() {

		try {
			rafTeam.seek(0);
			int count = rafTeam.readInt();
			return count;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return 0;

	}

	@Override
	public int getNumOfGames() {

		try {
			rafGame.seek(0);
			int count = rafGame.readInt();
			return count;
		} catch (IOException e) {

			e.printStackTrace();
		}

		return 0;
	}

}
