package Ex1;


import java.io.IOException;

public interface FifaWorldCupData {

    void saveTeam(Team team) throws IOException;

    void saveTeam(Team team, int index) throws IOException;

    Team nextTeam() throws IOException;

    Team previousTeam() throws IOException;

    void saveGame(Game game) throws IOException;

    void saveGame(Game game, int index) throws IOException;

	Game getGameAt(int index) throws IOException;

    int getNumOfTeams();

    int getNumOfGames();
}
