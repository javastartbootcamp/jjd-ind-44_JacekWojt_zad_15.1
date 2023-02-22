package pl.javastart.task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TournamentStats {

    void run(Scanner scanner) {
        List<Player> playerList = new ArrayList<>();
        String[] playerArray;
        boolean exit = true;
        while (exit) {
            System.out.println("Podaj wynik kolejnego gracza (lub stop): ");
            String competitorData = scanner.nextLine();
            if (!competitorData.equals("stop")) {
                playerArray = competitorData.split(" ");
                int result = Integer.parseInt(playerArray[2]);
                playerList.add(new Player(playerArray[0], playerArray[1], result));
            } else {
                exit = false;
            }
        }
        System.out.println("Po jakim parametrze posortować? (1 - imię, 2- nazwisko, 3- wynik)");
        int sortingParameter = scanner.nextInt();
        System.out.println("Sortować rosnąco czy malejąco? (1 - rosnąco, 2 - malejąco)");
        int sortingType = scanner.nextInt();
        List<Player> sortedList = sortingList(sortingParameter, sortingType, playerList);
        try {
            writingListToFile(sortedList);
        } catch (IOException e) {
            System.out.println("Nie udało się zapisać do pliku");
        }
    }

    private void writingListToFile(List<Player> playersList) throws IOException {
        File playersFile = new File("stats.csv");
        FileWriter fileWriter = new FileWriter(playersFile);
        for (Player player : playersList) {
            fileWriter.write(player.getFirstName() + " " + player.getLastName() + ", " + player.getResult() + "\n");
        }
        fileWriter.close();
    }

    private List<Player> sortingList(int sortingParameter, int sortingType, List<Player> playerList) {
        if (sortingParameter == 1 && sortingType == 1) {
            Collections.sort(playerList, new ComparatorFirstName());
            return playerList;
        } else if (sortingParameter == 1 && sortingType == 2) {
            Collections.sort(playerList, new ComparatorFirstName().reversed());
            return playerList;
        } else if (sortingParameter == 2 && sortingType == 1) {
            Collections.sort(playerList, new ComparatorLastName());
            return playerList;
        } else if (sortingParameter == 2 && sortingType == 2) {
            Collections.sort(playerList, new ComparatorLastName().reversed());
            return playerList;
        } else if (sortingParameter == 3 && sortingType == 1) {
            Collections.sort(playerList);
            return playerList;
        } else {
            Collections.sort(playerList);
            Collections.reverse(playerList);
            return playerList;
        }
    }
}
