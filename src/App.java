import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Global HashMap
        HashMap<String, String> dictionary = new HashMap<>();

        // Load data and transfer to hashmap on first load
        try {
            FileInputStream fileIn = new FileInputStream("res/bytes/raw.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            User info = (User) in.readObject();

            in.close();
            fileIn.close();

            // Insert information inside hashmap
            dictionary.put(info.getUser(), info.getPassword());

            System.out.println("Successfully retrieved information");

            System.out.println("Enter username");
            String username = scanner.nextLine();

            if (dictionary.containsKey(username)) {
                System.out.println("Enter password: ");
                String password = scanner.nextLine();

                if (password.equals(dictionary.get(username))) {
                    Program();
                }

            }
        } catch (Exception e) {
            // If no data is found
            System.out.println("Enter username: ");
            String username = scanner.nextLine();

            System.out.println("What's your new password? (Has to be 12 characters long, including numbers and symbols): ");
            String password = scanner.nextLine();

            // Check if password has numbers
            if (Pattern.matches("(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).+", password) && password.length() >= 12) {
                // write raw class bytes to file
                User current = new User(username, password);

                FileOutputStream fileOut = new FileOutputStream("res/bytes/raw.ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);

                out.writeObject(current);

                out.close();
                fileOut.close();

                System.out.println("Press Enter and restart the program.");
                String enter = scanner.nextLine();

                System.exit(0);
                
            } else {
                System.out.println(false);
            }
        }
    }

    public static void Program() {
        // clear console
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // Scanner class
        Scanner scanner = new Scanner(System.in);
        
        // ArrayList (first initialised save of every class byte)
        ArrayList<User> init = new ArrayList<>();
        
        // HashMap
        HashMap<String, String> accountDictionary = new HashMap<>();

        // load the local information
        try {
            FileInputStream fileIn = new FileInputStream("res/bytes/infoRaw.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            // load every saved element
            try {
                while (true) { 
                    User acc = (User) in.readObject();
                    // initialise inside init (arraylist)
                }
            } catch (EOFException eofe) {}

        } catch (Exception e) {e.printStackTrace();}

        // introduce main system
        System.out.println("Welcome to CLI Password Manager\nChoose from the following options\n1. Check Accounts\n2. Add New Accounts\n3. Change local information\n3. Exit");

        // user input
        int input = Integer.valueOf(scanner.nextLine());

        // switch case with user input
        switch (input) {
            case 1: {
                // if there is no accounts saved then clear terminal and print message
                if (accountDictionary.size() == 0) {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("Theres nothing here!");
                    Program();
                } else {
                    int i = 0;
                    for (String acc : accountDictionary.keySet()) {
                        System.out.println(i + ". " + acc + ": " + accountDictionary.get(acc));
                    }
                }
            }
            case 2: {
                // allow the user to create new information
                System.out.println("Enter the email/username: ");
                String newAccount = scanner.nextLine();

                System.out.println("Enter the password: ");
                String newPassword = scanner.nextLine();

                Save(newAccount, newPassword);
            }
            case 3: System.exit(0);
            default: System.out.println("Please choose between 1-3.");
        }
    }

    static void Save(String username, String password) {
        // save raw bytes
        try {
            FileOutputStream fileOut = new FileOutputStream("res/bytes/infoRaw.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            User user = new User(username, password);
            out.writeObject(user);

            out.close();
            fileOut.close();

            // automatically return to the program once saved
            Program();
        } catch (Exception e) {e.printStackTrace();}
    }
}
