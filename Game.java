
import java.util.Scanner;

public class Game {
    public static int numOfPlayers = 0;
    public static int numOfMafia = 0;
    public static int numOfVillagers = 0;
    public static Player[] allplayers = new Player[100];
    public static int DayNum = 1;
    public static int NightNum = 1;

    public static int index(Player[] players, int size, String name) {
        for (int i = 0; i < size; ++i)
            if (players[i].name.equals(name))
                return i;
        return -1;
    }

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
                        ++numOfMafia;
                        ++numOfPlayers;
                        break;
                    case "godfather":
                        allplayers[numOfPlayers] = new godfather(name);
                        ++numOfMafia;
                        ++numOfPlayers;
                        break;
                    case "silencer":
                        allplayers[numOfPlayers] = new silencer(name);
                        ++numOfMafia;
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
        System.out.println("\nReady? Set! Go.");
    }

    public static void win() {
        if (numOfMafia == 0) {
            System.out.println("Villagers won!");
            System.exit(0);
        }
        if (numOfVillagers <= numOfMafia) {
            System.out.println("Mafia won!");
            System.exit(0);
        }
    }

    public static void Day() {
        Scanner scanner = new Scanner(System.in);
        while (!(scanner.hasNext("end_vote") || scanner.hasNext("get_game_state") || scanner.hasNext("start_game"))) {
            String voter_name = scanner.next();
            String votee_name = scanner.next();
            if (index(allplayers, numOfPlayers, voter_name) == -1 || index(allplayers, numOfPlayers, votee_name) == -1) {
                System.out.println("user not found");
                continue;
            }
            Player voter = allplayers[index(allplayers, numOfPlayers, voter_name)];
            Player votee = allplayers[index(allplayers, numOfPlayers, votee_name)];
            if (voter.silenced)
                System.out.println("voter is silenced");
            else if (votee.isAlive == false)
                System.out.println("votee already dead");
            else if (voter.isAlive == false)
                System.out.println("voter is dead");
            else
                ++votee.NumOfDayVotes;
        }
        if (scanner.hasNext("get_game_state")) {
            get_game_state();
            System.out.println("please enter votes:");
            Day();
        }
        if (scanner.hasNext("start_game")) {
            System.out.println("game has already started");
            System.out.println("please enter votes:");
            Day();
        }
        if (scanner.hasNext("end_vote"))
            System.out.println("please enter end_vote again to see the result!");
    }

    public static void DayResult() {
        //bubble sort of number of votes:
        for (int i = 0; i < numOfPlayers - 1; i++)
            for (int j = 0; j < numOfPlayers - i - 1; j++)
                if (allplayers[j].NumOfDayVotes > allplayers[j + 1].NumOfDayVotes) {
                    Player temp = allplayers[j];
                    allplayers[j] = allplayers[j + 1];
                    allplayers[j + 1] = temp;
                }
        if (allplayers[numOfPlayers - 1].NumOfDayVotes == allplayers[numOfPlayers - 2].NumOfDayVotes)
            System.out.println("nobody died");
        else if (allplayers[numOfPlayers - 1] instanceof Joker) {
            System.out.println("Joker won!");
            System.exit(0);
        } else {
            allplayers[numOfPlayers - 1].isAlive = false;
            System.out.println(allplayers[numOfPlayers - 1].name + " died");
            if (allplayers[numOfPlayers - 1] instanceof mafia)
                --numOfMafia;
            else
                --numOfVillagers;
        }
        for (int i = 0; i < numOfPlayers; ++i) {
            allplayers[i].NumOfDayVotes = 0;
            if (allplayers[i] instanceof villager && allplayers[i].silenced)
                allplayers[i].silenced = false;
        }

    }

    public static void Night() {
        System.out.println("Night " + NightNum);
        ++NightNum;
        for (int i = 0; i < numOfPlayers; ++i)
            if (allplayers[i].isAlive && (allplayers[i] instanceof mafia || allplayers[i] instanceof detective || allplayers[i] instanceof doctor))
                System.out.println(allplayers[i].name + ": " + allplayers[i].role);

        Scanner scanner = new Scanner(System.in);
        while (!(scanner.hasNext("end_night") || scanner.hasNext("get_game_state") || scanner.hasNext("start_game"))) {
            String first_name = scanner.next();
            String second_name = scanner.next();
            Player first_player = allplayers[index(allplayers, numOfPlayers, first_name)];
            if (!first_player.isAlive) {
                System.out.println("user is dead");
            } else if (!(first_player instanceof mafia || first_player instanceof detective || first_player instanceof doctor))
                System.out.println("user can not wake up during night");
            else if (first_player instanceof detective) {
                if (index(allplayers, numOfPlayers, second_name) == -1)
                    System.out.println("user not found");
                else if (((detective) first_player).has_asked)
                    System.out.println("detective has already asked");
                else {
                    Player second_player = allplayers[index(allplayers, numOfPlayers, second_name)];
                    ((detective) first_player).ask(second_player);
                }
            } else if (first_player instanceof doctor) {
                Player second_player = allplayers[index(allplayers, numOfPlayers, second_name)];
                ((doctor) first_player).save(second_player);
            } else {
                if (index(allplayers, numOfPlayers, second_name) == -1)
                    System.out.println("user not joined");
                else {
                    Player second_player = allplayers[index(allplayers, numOfPlayers, second_name)];
                    if (!second_player.isAlive)
                        System.out.println("votee already dead");
                    else if (first_player instanceof silencer) {
                        if (!((silencer) first_player).has_silenced_someone)
                            ((silencer) first_player).silence(second_player);
                        else
                            ((mafia) first_player).votee = second_player;
                    } else
                        ((mafia) first_player).votee = second_player;
                }
            }

        }
        if (scanner.hasNext("get_game_state")) {
            get_game_state();
            System.out.println("please enter your request :");
            Night();
        }
        if (scanner.hasNext("start_game")) {
            System.out.println("game has already started");
            System.out.println("please enter your request :");
            Night();
        }
        if (scanner.hasNext("end_night"))
            System.out.println("please enter end_night again to see the result!");
    }

    public static void NightResult() {
        for (int i = 0; i < numOfPlayers; ++i)
            for (int j = 0; j < numOfPlayers; ++j)
                if (allplayers[j] instanceof mafia && ((mafia) allplayers[j]).votee == allplayers[i])
                    ++allplayers[i].NumOfNightVotes;
        //bubble sort of number of votes:
        for (int i = 0; i < numOfPlayers - 1; i++)
            for (int j = 0; j < numOfPlayers - i - 1; j++)
                if (allplayers[j].NumOfNightVotes > allplayers[j + 1].NumOfNightVotes) {
                    Player temp = allplayers[j];
                    allplayers[j] = allplayers[j + 1];
                    allplayers[j + 1] = temp;
                }
        if (allplayers[numOfPlayers - 1].NumOfNightVotes == allplayers[numOfPlayers - 2].NumOfNightVotes && allplayers[numOfPlayers - 2].NumOfNightVotes == allplayers[numOfPlayers - 3].NumOfNightVotes)
            System.out.println("mafia tried to kill someone but no one was killed.");
        else if (allplayers[numOfPlayers - 1].NumOfNightVotes == allplayers[numOfPlayers - 2].NumOfNightVotes) {
            if (allplayers[numOfPlayers - 1].isSavedByDoctor) {
                System.out.println("mafia tried to kill " + allplayers[numOfPlayers - 2].name + "\n" + allplayers[numOfPlayers - 2].name + " was killed");
                allplayers[numOfPlayers - 2].isAlive = false;
                if (allplayers[numOfPlayers - 2] instanceof villager)
                    --numOfVillagers;
            } else if (allplayers[numOfPlayers - 2].isSavedByDoctor) {
                System.out.println("mafia tried to kill " + allplayers[numOfPlayers - 1].name + "\n" + allplayers[numOfPlayers - 1].name + " was killed");
                allplayers[numOfPlayers - 1].isAlive = false;
                if (allplayers[numOfPlayers - 1] instanceof villager)
                    --numOfVillagers;
            }
        } else {
            if (allplayers[numOfPlayers - 1] instanceof bulletproof && ((bulletproof) allplayers[numOfPlayers - 1]).additional_life) {
                System.out.println("mafia tried to kill someone but no one was killed.");
                ((bulletproof) allplayers[numOfPlayers - 1]).additional_life = false;
            } else if (allplayers[numOfPlayers - 1].isSavedByDoctor)
                System.out.println("mafia tried to kill " + allplayers[numOfPlayers - 1].name + " but doctor saved him/her");
            else {
                System.out.println("mafia tried to kill " + allplayers[numOfPlayers - 1].name + "\n" + allplayers[numOfPlayers - 1].name + " was killed");
                allplayers[numOfPlayers - 1].isAlive = false;
                if (allplayers[numOfPlayers - 1] instanceof villager)
                    --numOfVillagers;
            }
        }
        for (int i = 0; i < numOfPlayers; i++) {
            if (allplayers[i].silenced)
                System.out.println("Silenced " + allplayers[i].name);
            if (allplayers[i] instanceof detective)
                ((detective) allplayers[i]).has_asked = false;
            if (allplayers[i] instanceof mafia)
                ((mafia) allplayers[i]).votee = null;
            allplayers[i].NumOfNightVotes = 0;
            allplayers[i].isSavedByDoctor = false;
        }

    }

    public static void get_game_state() {
        System.out.println("Mafia = " + numOfMafia);
        System.out.println("Villager = " + numOfVillagers);
    }

    public static void main(String[] args) {
        String[] playersNames = null;
        boolean create_game = false;
        boolean start_game = false;
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String s = scanner.next();
            switch (s) {
                case "create_game":
                    create_game = true;
                    String names = scanner.nextLine();
                    playersNames = names.split(" ");
                    break;
                case "assign_role":
                    if (create_game == false)
                        System.out.println("no game created");
                    else {
                        String name = scanner.next();
                        String role = scanner.next();
                        assign_role(playersNames, name, role);
                    }
                    break;
                case "start_game":
                    if (create_game == false)
                        System.out.println("no game created");
                    else if (start_game)
                        System.out.println("game has already started");
                    else if (numOfPlayers != playersNames.length - 1)
                        System.out.println("one or more players do not have a role");
                    else {
                        start_game = true;
                        start_game();
                        System.out.println("Day " + DayNum);
                        Day();
                    }
                    break;
                case "end_vote":
                    DayResult();
                    win();
                    Night();
                    break;
                case "end_night":
                    ++DayNum;
                    System.out.println("Day " + DayNum);
                    NightResult();
                    win();
                    Day();
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
    public int NumOfNightVotes;
    public boolean isAlive = true;
    public boolean isSavedByDoctor = false;
    public boolean silenced = false;
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

    public Player votee;

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

    public boolean has_silenced_someone = false;

    public void silence(Player p) {
        p.silenced = true;
        has_silenced_someone = true;
    }
}

class villager extends Player {

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

    public void save(Player p) {
        p.isSavedByDoctor = true;
    }
}

class detective extends villager {
    public detective(String name) {
        super(name);
        role = "detective";
    }

    public boolean has_asked = false;

    public void ask(Player suspect) {
        if (!suspect.isAlive)
            System.out.println("suspect is dead");
        else {
            if (suspect instanceof mafia && !(suspect instanceof godfather))
                System.out.println("Yes");
            else
                System.out.println("No");
        }
        has_asked = true;
    }
}

class bulletproof extends villager {
    public bulletproof(String name) {
        super(name);
        role = "bulletproof";
    }

    public boolean additional_life = true;
}

class Joker extends Player {
    public Joker(String name) {
        super(name);
        role = "Joker";
    }
}
