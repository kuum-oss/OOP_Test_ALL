package Case_5.ui;

import Case_5.model.Horse;
import Case_5.model.Race;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private final Scanner scanner;

    public ConsoleMenu() {
        this.scanner = new Scanner(System.in);
    }

    public int showMainMenu() {
        System.out.println("\n--- HIPPODROME MENU ---");
        System.out.println("1. Play Interactive Mode (User Input)");
        System.out.println("2. Run Demo (Auto Simulation)");
        System.out.println("0. Exit");
        System.out.print("Select option: ");
        return readInt();
    }

    public Horse selectHorse(Race race) {
        System.out.println("\n--- AVAILABLE HORSES ---");
        List<Horse> horses = new ArrayList<>(race.getParticipants().keySet());

        for (int i = 0; i < horses.size(); i++) {
            Horse h = horses.get(i);
            double odds = race.getOdds(h);
            System.out.printf("%d. %s (Odds: x%.2f)%n", i + 1, h.getName(), odds);
        }

        System.out.print("Choose your horse (number): ");
        int choice = readInt();

        if (choice > 0 && choice <= horses.size()) {
            return horses.get(choice - 1);
        }
        System.out.println("Invalid choice. Defaulting to horse #1.");
        return horses.get(0);
    }

    public double getBetAmount() {
        System.out.print("Enter bet amount ($): ");
        double amount = scanner.nextDouble();
        if (amount <= 0) {
            System.out.println("Amount must be positive! Setting to $10.");
            return 10.0;
        }
        return amount;
    }

    // Безпечне зчитування цілого числа
    private int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }
}