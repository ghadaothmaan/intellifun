import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Database {

    static String AccountsDBPath = "AccountsDB.txt";
    static String GamesDBPath = "GamesDB.txt";

    static ArrayList<Account> accounts = new ArrayList<>();
    static ArrayList<Game> games = new ArrayList<>();

    public static void loadAccounts() throws FileNotFoundException {
        Scanner in = new Scanner(new File(AccountsDBPath));
        accounts = new ArrayList<>();
        while(in.hasNext()){
            String s = in.nextLine();
            String[] data;
            data = s.split("\\|");
            Account account = new Account(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]));
            accounts.add(account);
        }
        in.close();
    }

    public static void saveAccounts() throws FileNotFoundException {
        PrintWriter out = new PrintWriter(new File(AccountsDBPath));
        for (Account i : accounts){
            out.println(i.userType + "|" + i.username + "|" + i.password + "|" + i.email + "|" + i.score);
        }
        out.close();
    }

    public static void loadGames() throws FileNotFoundException {
        Scanner in = new Scanner(new File(GamesDBPath));
        games = new ArrayList<>();
        while(in.hasNext()){
            String s = in.nextLine();
            String[] data;
            data = s.split("\\|");
            Game game = new Game(data[0], data[1], data[2], Integer.parseInt(data[3]), data[4], data[5]);
            games.add(game);
        }
        in.close();
    }

    public static void saveGames() throws FileNotFoundException {
        PrintWriter out = new PrintWriter(new File(GamesDBPath));
        for (Game i : games){
            out.println(i.name + "|" + i.type + "|" + i.subject + "|" + i.numOfQuestions + "|" + i.creatorName + "|" + i.path);
        }
        out.close();
    }

    public static void listGames() {
        int count = 1;
        for (Game i : games){
            System.out.println(count + ". " + i.name + "\nGenre: " + i.subject +
                               "\nType: " + i.type + "\nCreated by: " + i.creatorName);
            System.out.println();
            count++;
        }
    }

}
