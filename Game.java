
import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        boolean creat_game = false;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String s = scanner.next();
            switch (s) {
                case "creat_game":
                    creat_game = true;
                    break;
                case "assign_role":
                    if (creat_game == false)
                        System.out.println("no game created");
                    break;
                case "start_game":
                    break;
                case "end_vote":
                    break;
                case "end_night":
                    break;
                case "get_game_state":
                    break;
            }

        }
    }
}

class Player {
    public String name;
    public int NumOfDayVotes;
    public boolean isAlive = true;

    public Player(String name) {
        this.name = name;
    }
}

class mafia extends Player {
    public mafia(String name) {
        super(name);
    }
}

class villager extends Player {
    public int NumOfNightVotes;

    public villager(String name) {
        super(name);
    }
}

class Joker extends Player {
    public int NumOfNightVotes;

    public Joker(String name) {
        super(name);
    }
}
