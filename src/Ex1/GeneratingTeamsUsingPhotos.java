package Ex1;

import java.io.File;
import java.io.IOException;

import javafx.scene.paint.Color;

public class GeneratingTeamsUsingPhotos {

    public static void main(String[] args) throws IOException {
        FifaWorldCupData fifa = new FifaWorldCupFileManager(".\\teams.bin",".\\games.bin");

        File dir = new File(".\\photos");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                fifa.saveTeam(new Team(child.getName().replaceAll(".png","").toCharArray(),Color.color(Math.random(), Math.random(),Math.random()),
                        Color.color(Math.random(),Math.random(),Math.random())));
            }
        }

        for(int i = 0 ; i <= fifa.getNumOfTeams() ; i++){
            System.out.println(fifa.nextTeam());
        }

        for(int i = 0 ; i <= fifa.getNumOfTeams() ; i++){
            System.out.println(fifa.previousTeam());
        }

    }
}
