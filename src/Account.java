import java.util.Scanner;

public class Account {
    static int option = -1;
    static Account currentAccount;
    String username;
    String password;
    String email;
    String userType;
    int score;

    Account() {
        this.email = "";
        this.password = "";
        this.username = "";
        this.userType = "";
        this.score = 0;
    }

    Account(String usertype, String username, String password, String email, int score) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.userType = usertype;
        this.score = score;
    }

    public static void validateChoice(Scanner in, Account account) {
        System.out.println("So tell me now, wanna register as a teacher or as a student?");

        while(option != 1 && option != 2) {
            System.out.println("1. Student\n2. Teacher");
            option = in.nextInt(); in.nextLine();
            if (option == 1)
                account.userType = "Student";
            else if (option == 2)
                account.userType = "Teacher";
            else
                System.out.println("Sigh~ I'm starting to question your IQ." +
                        "Please choose a valid number.");
        }
    }

    public static void validateUsername(Scanner in, Account account) {
        System.out.print("Username: ");
        account.username = in.nextLine();

        // validating a unique username
        boolean exists = true;
        while (exists) {
            boolean temp = false;
            for (Account i : Database.accounts){
                if (i.username.equals(account.username))
                    temp = true;
            }
            if (temp) {
                System.out.print("This username already exists. Please choose a different one.\nUsername: ");
                account.username = in.nextLine();
            }
            exists = temp;
        }

        // validating a valid username
        while (account.username.length() < 4) {
            System.out.print("Must be more than 4 characters. Please try again.\nUsername: ");
            account.username = in.nextLine();
        }
        while (!account.username.matches("[A-Za-z0-9 ]*")) {
            System.out.print("Must not contain any special characters. Please try again.\nUsername: ");
            account.username = in.nextLine();
        }
    }

    public static void validateEmail(Scanner in, Account account) {
        System.out.print("Email: ");
        account.email = in.next();

        // if the user is a teacher, validate their email
        if (option == 2) while (!account.email.contains(".edu")) {
            System.out.print("Invalid email format. Accepted format is \"abc@example.edu.co\". Please try again.\nEmail: ");
            account.email = in.next();
        }

        // validating a valid email
        while (!account.email.contains("@") || !account.email.contains(".")) {
            System.out.print("Invalid email format. Accepted format is \"abc@example.com\". Please try again.\nEmail: ");
            account.email = in.next();
        }

        // validating a unique email
        boolean exists = true;
        while (exists) {
            boolean temp = false;
            for (Account i : Database.accounts){
                if (i.email.equals(account.email))
                    temp = true;
            }
            if (temp) {
                System.out.print("This email already exists. Please choose a different one.\nEmail: ");
                account.email = in.nextLine();
            }
            exists = temp;
        }
    }

    public static void validatePassword(Scanner in, Account account) {
        System.out.print("Password: ");
        account.password = in.next();

        // validating a strong and valid password
        while (account.password.length() < 8) { // checks for less than 8 characters
            System.out.print("Must be more than 8 characters. Please try again.\nPassword: ");
            account.password = in.next();
            while (account.password.matches("[0-9 ]*")) { // checks at least one character is a letter
                System.out.print("Must contain at least 1 letter. Please try again.\nPassword: ");
                account.password = in.next();
            }
            // checks if the whole string is equal to its equivalent lowercase or uppercase
            while (account.password.equals(account.password.toUpperCase())) {
                System.out.print("Must have a lowercase character. Please try again.\nPassword: ");
                account.password = in.next();
            }
            while (account.password.equals(account.password.toLowerCase())) {
                System.out.print("Must have an uppercase character. Please try again.\nPassword: ");
                account.password = in.next();
            }
        }
    }

    public static void submitAccount(Account account) {
        account.currentAccount = account;
        Database.accounts.add(account);
    }

    public static void signup (Scanner in) {
        Account account = new Account();
        validateChoice(in, account);
        validateUsername(in, account);
        validateEmail(in, account);
        validatePassword(in, account);
        submitAccount(account);
        System.out.println("Congrats dude! You're now a member of this awesome website. Great ha?\n");
    }

    public static boolean validateLogin(String name, String password){
        boolean found = false;
        for (Account i : Database.accounts){
            if (i.username.equals(name) && i.password.equals(password)){
                found = true; currentAccount = i;
                break;
            }
        }
        return found;
    }

    public static boolean login(Scanner in) {
        String username, password;
        boolean logged = false;

        // validating their username and password
        while (!logged) {
            System.out.print("Username: ");
            username = in.nextLine();

            System.out.print("Password: ");
            password = in.nextLine();

            logged = validateLogin(username, password);
            if (!logged)
                System.out.println("Invalid username/password. Please try again.");

        }
        if (logged) {
            System.out.println("Woohoo! You logged in successfully fella.\n");
        }
        return logged;
    }
}
