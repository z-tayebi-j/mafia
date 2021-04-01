
import java.util.Scanner;

public class Game {
    public static int numOfMafias = 0;
    public static int numOfVillagers = 0;
    mafia[] mafias = new mafia[50];
    villager[] villagers = new villager[50];

    public static void assign_role(String[] players, String name, String role) {
        for (int i = 0; i < players.length; ++i)
            if (name.equals(players[i])) {
                switch (role) {
                    case "Joker":
                        Joker j = new Joker(name);
                        break;
                    case "mafia":
                        mafia m = new mafia(name);
                        ++numOfMafias;
                        break;
                    case "godfather":
                        godfather g = new godfather(name);
                        ++numOfMafias;
                        break;
                    case "silencer":
                        silencer s = new silencer(name);
                        ++numOfMafias;
                        break;
                    case "villager":
                        villager v = new villager(name);
                        ++numOfVillagers;
                        break;
                    case "doctor":
                        doctor d = new doctor(name);
                        ++numOfVillagers;
                        break;
                    case "detective":
                        detective de = new detective(name);
                        ++numOfVillagers;
                        break;
                    case "bulletproof":
                        bulletproof b = new bulletproof(name);
                        ++numOfVillagers;
                        break;
                    default:
                        System.out.println("role not found");
                }
                return;
            }
        System.out.println("user not found");
    }

    public static void main(String[] args) {
        String[] playersNames = null;
        boolean creat_game = false;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String s = scanner.next();
            switch (s) {
                case "creat_game":
                    creat_game = true;
                    String names = scanner.next();
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

class godfather extends mafia {
    public godfather(String name) {
        super(name);
    }
}

class silencer extends mafia {
    public silencer(String name) {
        super(name);
    }
}

class villager extends Player {
    public int NumOfNightVotes;

    public villager(String name) {
        super(name);
    }
}

class doctor extends villager {
    public doctor(String name) {
        super(name);
    }
}

class detective extends villager {
    public detective(String name) {
        super(name);
    }
}

class bulletproof extends villager {
    public bulletproof(String name) {
        super(name);
    }
}

class Joker extends Player {
    public int NumOfNightVotes;

    public Joker(String name) {
        super(name);
    }
}
