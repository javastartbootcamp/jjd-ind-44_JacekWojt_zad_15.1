package pl.javastart.task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TournamentStats {

    public static final int SORT_BY_NAME = 1;
    public static final int SORT_BY_SURNAME = 2;
    public static final int SORT_BY_RESULT = 3;
    public static final int SORT_DESCENDING = 2;

    void run(Scanner scanner) {
        List<Player> playerList = readPlayers(scanner);
        int sortingParameter = readSortingParameter(scanner);
        int sortingOrder = readSortingOrder(scanner);
        List<Player> sortedList = sortTheList(sortingParameter, sortingOrder, playerList);
        try {
            writingListToFile(sortedList);
        } catch (IOException e) {
            System.out.println("Nie udało się zapisać do pliku");
        }
    }

    private static int readSortingOrder(Scanner scanner) {
        System.out.println("Sortować rosnąco czy malejąco? (1 - rosnąco, 2 - malejąco)");
        return scanner.nextInt();
    }

    private static int readSortingParameter(Scanner scanner) {
        System.out.println("Po jakim parametrze posortować? (1 - imię, 2- nazwisko, 3- wynik)");
        return scanner.nextInt();
    }

    private static List<Player> readPlayers(Scanner scanner) {
        List<Player> playerList = new ArrayList<>();
        boolean exit = false;
        while (!exit) {
            System.out.println("Podaj wynik kolejnego gracza (lub stop): ");
            String competitorData = scanner.nextLine();
            if (!competitorData.equals("STOP")) {
                String[] playerArray = competitorData.split(" ");
                int result = Integer.parseInt(playerArray[2]);
                playerList.add(new Player(playerArray[0], playerArray[1], result));
            } else {
                exit = true;
            }
        }
        return playerList;
    }

    private void writingListToFile(List<Player> playersList) throws IOException {
        File playersFile = new File("stats.csv");
        try (FileWriter fileWriter = new FileWriter(playersFile)) {
            for (Player player : playersList) {
                fileWriter.write(player.getFirstName() + " " + player.getLastName() + ";" + player.getResult() + "\n");
            }
        }
    }

    private List<Player> sortTheList(int sortingParameter, int sortingOrder, List<Player> playerList) {
        Comparator<Player> comparator = switch (sortingParameter) {
            case SORT_BY_NAME -> new ComparatorFirstName();
            case SORT_BY_SURNAME -> new ComparatorLastName();
            case SORT_BY_RESULT -> new ComparatorResult();
            default -> new ComparatorFirstName();
        };
        if (sortingOrder == SORT_DESCENDING) {
            comparator = comparator.reversed();
        }
        playerList.sort(comparator);
        return playerList;
    }
}
