import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
            FileInputStream fileIn = new FileInputStream("res/bytes/raw.txt");
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

                FileOutputStream fileOut = new FileOutputStream("res/bytes/raw.txt");
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
        // Scanner class
        Scanner scanner = new Scanner(System.in);

        // HashMap
        HashMap<String, String> accountDictionary = new HashMap<>();

        // Load the local information
        try {
            FileInputStream fileIn = new FileInputStream("res/bytes/infoRaw.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            // load every saved element
            try {
                while (true) { 
                    Saved acc = (Saved) in.readObject();
                    accountDictionary.put(acc.getAccount(), acc.getPassword());
                }
            } catch (EOFException eofe) {}

        } catch (Exception e) {}

        // Introduce main system
        System.out.println("Welcome to CLI Password Manager\nChoose from the following options\n1. Check Accounts\n2. Add New Accounts\n3. Change local information\n3. Exit");

        // user input
        int input = scanner.nextInt();

        switch (input) {
            case 1: {
                if (accountDictionary.size() == 0) {
                    System.out.println("Theres nothing here!");
                    Program();
                }
                int i = 0;
                for (String acc : accountDictionary.keySet()) {
                    System.out.println(i + ". " + acc + ": " + accountDictionary.get(acc));
                }
            }
            case 2: {

            }
            case 3: System.exit(0);
        }
    }
}
