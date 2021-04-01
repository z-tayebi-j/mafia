
import java.util.Scanner;

public class Game {
    public static int numOfPlayers = 0;
    public static int numOfMafias = 0;
    public static int numOfVillagers = 0;
    public static Player[] allplayers = new Player[100];

    public static void assign_role(String[] playersnames, String name, String role) {
        for (int i = 1; i < playersnames.length; ++i)
            if (name.equals(playersnames[i])) {
                switch (role) {
                    case "Joker":
                        allplayers[numOfPlayers] = new Joker(name);
                        ++numOfPlayers;
                        break;
                    case "mafia":
                        allplayers[numOfPlayers] = new mafia(name);
                        ++numOfMafias;
                        ++numOfPlayers;
                        break;
                    case "godfather":
                        allplayers[numOfPlayers] = new godfather(name);
                        ++numOfMafias;
                        ++numOfPlayers;
                        break;
                    case "silencer":
                        allplayers[numOfPlayers] = new silencer(name);
                        ++numOfMafias;
                        ++numOfPlayers;
                        break;
                    case "villager":
                        allplayers[numOfPlayers] = new villager(name);
                        ++numOfVillagers;
                        ++numOfPlayers;
                        break;
                    case "doctor":
                        allplayers[numOfPlayers] = new doctor(name);
                        ++numOfVillagers;
                        ++numOfPlayers;
                        break;
                    case "detective":
                        allplayers[numOfPlayers] = new detective(name);
                        ++numOfVillagers;
                        ++numOfPlayers;
                        break;
                    case "bulletproof":
                        allplayers[numOfPlayers] = new bulletproof(name);
                        ++numOfVillagers;
                        ++numOfPlayers;
                        break;
                    default:
                        System.out.println("role not found");
                }
                return;
            }
        System.out.println("user not found");
    }

    public static void start_game() {
        for (int i = 0; i < numOfPlayers; ++i)
            System.out.println(allplayers[i].name + ": " + allplayers[i].role);
        System.out.println("\nReadi? Set! Go.");
    }

    public static void get_game_state() {
        System.out.println("Mafia = " + numOfMafias);
        System.out.println("Villager = " + numOfVillagers);
    }

    public static void main(String[] args) {
        String[] playersNames = null;
        boolean creat_game = false;
        boolean start_game = false;
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String s = scanner.next();
            switch (s) {
                case "creat_game":
                    creat_game = true;
                    String names = scanner.nextLine();
                    playersNames = names.split(" ");
                    break;
                case "assign_role":
                    if (creat_game == false)
                        System.out.println("no game created");
                    else {
                        String name = scanner.next();
                        String role = scanner.next();
                        assign_role(playersNames, name, role);
                    }
                    break;
                case "start_game":
                    if (creat_game == false)
                        System.out.println("no game created");
                    else if (start_game)
                        System.out.println("game has already started");
                    else if (numOfPlayers != playersNames.length - 1)
                        System.out.println("one or more players do not have a role");
                    else {
                        start_game = true;
                        start_game();
                    }
                    break;
                case "end_vote":
                    break;
                case "end_night":
                    break;
                case "get_game_state":
                    get_game_state();
                    break;
            }

        }
    }
}

class Player {
    public String name;
    public int NumOfDayVotes;
    public boolean isAlive = true;
    public String role;

    public Player(String name) {
        this.name = name;
    }
}

class mafia extends Player {
    public mafia(String name) {
        super(name);
        role = "mafia";
    }
}

class godfather extends mafia {
    public godfather(String name) {
        super(name);
        role = "godfather";
    }
}

class silencer extends mafia {
    public silencer(String name) {
        super(name);
        role = "silencer";
    }
}

class villager extends Player {
    public int NumOfNightVotes;

    public villager(String name) {
        super(name);
        role = "villager";
    }
}

class doctor extends villager {
    public doctor(String name) {
        super(name);
        role = "doctor";
    }
}

class detective extends villager {
    public detective(String name) {
        super(name);
        role = "detective";
    }
}

class bulletproof extends villager {
    public bulletproof(String name) {
        super(name);
        role = "bulletproof";
    }
}

class Joker extends Player {
    public int NumOfNightVotes;

    public Joker(String name) {
        super(name);
        role = "Joker";
    }
}
