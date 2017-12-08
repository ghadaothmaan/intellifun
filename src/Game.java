import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Game {
    String name;
    String type;
    String subject;
    String path;
    String creatorName;
    int numOfQuestions;

    Game () {
        type = "";
        subject = "";
        path = "";
        creatorName = "";
        name = "";
        numOfQuestions = 0;
    }

    Game (String name, String type, String subject, int numOfQuestions, String creatorName, String path) {
        this.type = type;
        this.name = name;
        this.subject = subject;
        this.path = path;
        this.creatorName = creatorName;
        this.numOfQuestions = numOfQuestions;
    }

    public static Game getGame(String name) {
        Database db = new Database();
        Game found = new Game();
        for (int i = 0; i < db.games.size(); i++) {
            if (db.games.get(i).name.equals(name)) {
                found = db.games.get(i); break;
            }
        }
        return found;
    }

    public static int playGame(String name, Scanner userInput) throws FileNotFoundException {
        Game toPlay = getGame(name);
        Scanner readQs = new Scanner(new File(toPlay.path));

        int score = 0;
        for (int j = 0; j < toPlay.numOfQuestions; j++){
            int n = readQs.nextInt();
            String ans = readQs.next();
            readQs.nextLine();
            for (int k = 0; k < n; k++){
                System.out.println(readQs.nextLine());
            }
            String answer = userInput.next();
            answer = answer.toUpperCase();
            if (answer.equals(ans)) {
                score++;
                System.out.println("Correct answer! :)");
            }
            else {
                System.out.println("Wrong answer! :(");
            }
            System.out.println();
        }

        System.out.println("Your score for this game is: " + score + " out of " + toPlay.numOfQuestions);
        //Account.currentAccount.score += score;
        return score;
    }

    public static void updateScore(int score) {
        Account.currentAccount.score += score;
    }

    public static void createGame(Scanner in) throws IOException {
        Game game = new Game();

        System.out.print("Game name: ");
        game.name = in.nextLine();

        while(true) {

            Path pathQ = Paths.get("/Users/gee/IdeaProjects/software_1/" + game.name + ".txt");
            Files.createDirectories(pathQ.getParent());
            try {
                Files.createFile(pathQ);
            } catch (FileAlreadyExistsException e) {
                System.out.print("This name already exists. Please choose a different name.\nGame name: ");
                game.name = in.nextLine();
                continue;
            }

            break;
        }

        game.path = game.name + ".txt" ;

        PrintWriter out = new PrintWriter(new File(game.path));

        System.out.print("Game subject: ");
        game.subject = in.nextLine();

        game.creatorName = Account.currentAccount.username;

        System.out.print("Number of questions: ");
        game.numOfQuestions = in.nextInt();
        in.nextLine();

        System.out.print("Game type: (TF or MCQ) ");
        game.type = in.nextLine();

        int numOfChoices = 2;
        if (game.type.compareTo("TF") != 0) {
            System.out.print("Number of choices: ");
            numOfChoices = in.nextInt();
            in.nextLine();
            System.out.println();
        }

        String abc = "ABCDEFG";
        for (int i = 1; i <= game.numOfQuestions; i++) {
            String ans, s;

            System.out.print("Answer: (letter) ");
            ans = in.nextLine();
            out.println(numOfChoices + " " + ans);

            System.out.println("Question number " + i + ": ");
            out.print(i+ ". ");

            s = in.nextLine();
            out.println(s);

            for (int j= 1; j <= numOfChoices; j++) {
                System.out.println("Choice " + abc.charAt(j-1) + ": ");
                out.print(abc.charAt(j-1) + ". ");
                s = in.nextLine();
                out.println(s);
            }
            System.out.println();
        }
        Database.games.add(game);
        System.out.println("Yahoo! Your game was created successfully man! Thumbs up ;)");
        out.close();
    }
     
}
