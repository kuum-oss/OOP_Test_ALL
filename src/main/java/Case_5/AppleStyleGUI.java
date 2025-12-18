package Case_5;

import Case_5.components.impl.*;
import Case_5.model.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AppleStyleGUI extends Application {

    // --- СТИЛІ (Apple Design Language) ---
    private static final String STYLE_BG = "-fx-background-color: #F5F5F7;"; // Apple Light Gray
    private static final String STYLE_CARD = "-fx-background-color: white; -fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);";
    private static final String STYLE_BTN_PRIMARY = "-fx-background-color: #007AFF; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;";
    private static final String STYLE_BTN_DISABLED = "-fx-background-color: #B0B0B5; -fx-text-fill: white; -fx-background-radius: 8;";
    private static final String STYLE_LABEL_HEADER = "-fx-font-family: 'System'; -fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1D1D1F;";
    private static final String STYLE_PROGRESS = "-fx-accent: #34C759;"; // Apple Green

    // Логіка системи
    private HippodromeSystem system;
    private Race currentRace;
    private Horse selectedHorse;

    // UI Елементи для оновлення
    private Label statusLabel;
    private Button startButton;
    private Map<Horse, ProgressBar> horseProgressBars = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        // 1. Ініціалізація бекенду (IoC)
        initSystem();

        // 2. Побудова UI
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setStyle(STYLE_BG);
        root.setAlignment(Pos.TOP_CENTER);

        // -- Header --
        Label title = new Label("Hippodrome Pro");
        title.setStyle(STYLE_LABEL_HEADER);

        // -- Card: Race Info & Selection --
        VBox raceCard = createRaceCard();

        // -- Footer: Controls --
        statusLabel = new Label("Choose a horse and place your bet.");
        statusLabel.setStyle("-fx-text-fill: #86868b; -fx-font-size: 14px;");

        startButton = new Button("Place Bet & Start Race");
        startButton.setStyle(STYLE_BTN_PRIMARY);
        startButton.setPrefWidth(200);
        startButton.setPrefHeight(40);
        startButton.setOnAction(e -> startRaceAnimation());

        // Збірка сцени
        root.getChildren().addAll(title, raceCard, statusLabel, startButton);

        Scene scene = new Scene(root, 450, 600);
        primaryStage.setTitle("Hippodrome Case_5");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initSystem() {
        system = new HippodromeSystem(
                new AnalyticOddsProvider(),
                new OfficialRaceArbiter(),
                new StandardBettingService()
        );

        currentRace = new Race();
        Horse h1 = new Horse("1", "Spirit");
        Horse h2 = new Horse("2", "Thunder");
        Horse h3 = new Horse("3", "Flash");

        currentRace.addParticipant(h1);
        currentRace.addParticipant(h2);
        currentRace.addParticipant(h3);

        system.setBookmakerOdds(currentRace, h1, 1.5);
        system.setBookmakerOdds(currentRace, h2, 2.8);
        system.setBookmakerOdds(currentRace, h3, 4.5);

        // За замовчуванням обираємо першого
        selectedHorse = h1;
    }

    private VBox createRaceCard() {
        VBox card = new VBox(15);
        card.setStyle(STYLE_CARD);
        card.setPadding(new Insets(20));

        Label cardTitle = new Label("Live Race #101");
        cardTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        card.getChildren().add(cardTitle);

        ToggleGroup group = new ToggleGroup();

        for (Horse h : currentRace.getParticipants().keySet()) {
            HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER_LEFT);

            // Radio Button selection
            RadioButton rb = new RadioButton();
            rb.setToggleGroup(group);
            if (h == selectedHorse) rb.setSelected(true);
            rb.setOnAction(e -> selectedHorse = h);

            // Info
            Label nameLbl = new Label(h.getName());
            nameLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
            nameLbl.setMinWidth(60);

            Label oddsLbl = new Label("x" + currentRace.getOdds(h));
            oddsLbl.setStyle("-fx-text-fill: #86868b; -fx-font-size: 12px;");
            oddsLbl.setMinWidth(40);

            // Visualization (Progress Bar)
            ProgressBar pb = new ProgressBar(0);
            pb.setPrefWidth(180);
            pb.setStyle(STYLE_PROGRESS);
            horseProgressBars.put(h, pb);

            row.getChildren().addAll(rb, nameLbl, oddsLbl, pb);
            card.getChildren().add(row);
        }

        return card;
    }

    // --- ЛОГІКА АНІМАЦІЇ ---
    private void startRaceAnimation() {
        if (selectedHorse == null) return;

        // Блокуємо кнопку
        startButton.setDisable(true);
        startButton.setStyle(STYLE_BTN_DISABLED);
        startButton.setText("Race in progress...");
        statusLabel.setText("Your bet: $100 on " + selectedHorse.getName());

        // Реєструємо ставку в системі
        system.processClientBet(currentRace, new Bet(100, selectedHorse, Bet.Type.WIN));

        // Запускаємо окремий потік для анімації (щоб не завис інтерфейс)
        new Thread(() -> {
            Random rand = new Random();
            boolean finished = false;

            while (!finished) {
                try { Thread.sleep(50); } catch (InterruptedException e) {}

                for (Horse h : horseProgressBars.keySet()) {
                    double currentProgress = horseProgressBars.get(h).getProgress();
                    // Випадкова швидкість
                    double move = rand.nextDouble() * 0.02;
                    double newProgress = currentProgress + move;

                    // Оновлюємо UI (це треба робити в JavaFX потоці)
                    Platform.runLater(() -> horseProgressBars.get(h).setProgress(newProgress));

                    if (newProgress >= 1.0) {
                        finished = true;
                        final Horse winner = h;
                        // Фініш
                        Platform.runLater(() -> endRace(winner));
                        break;
                    }
                }
            }
        }).start();
    }

    private void endRace(Horse winner) {
        system.finishRace(currentRace, winner);

        startButton.setText("Race Finished");

        if (selectedHorse.equals(winner)) {
            statusLabel.setText("WINNER! You earned $" + (100 * currentRace.getOdds(winner)));
            statusLabel.setStyle("-fx-text-fill: #34C759; -fx-font-weight: bold; -fx-font-size: 16px;");
        } else {
            statusLabel.setText("You lost. Winner was " + winner.getName());
            statusLabel.setStyle("-fx-text-fill: #FF3B30; -fx-font-weight: bold; -fx-font-size: 16px;");
        }
    }
}