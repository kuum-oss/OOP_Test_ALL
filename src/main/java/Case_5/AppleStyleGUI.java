package Case_5;

import Case_5.components.impl.*;
import Case_5.model.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AppleStyleGUI extends Application {

    // --- СТИЛИ (Apple Design Language) ---
    private static final String STYLE_BG = "-fx-background-color: #F5F5F7;"; // Светло-серый фон
    private static final String STYLE_CARD = "-fx-background-color: white; -fx-background-radius: 16; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 15, 0, 0, 4);";
    
    // Кнопка (Синяя)
    private static final String STYLE_BTN_PRIMARY = "-fx-background-color: #007AFF; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 10; -fx-cursor: hand;";
    private static final String STYLE_BTN_DISABLED = "-fx-background-color: #B0B0B5; -fx-text-fill: white; -fx-background-radius: 10;";
    
    // Кнопка выхода (Красный текст, без фона)
    private static final String STYLE_BTN_EXIT = "-fx-background-color: transparent; -fx-text-fill: #FF3B30; -fx-font-size: 14px; -fx-font-weight: bold; -fx-cursor: hand;";

    // Поле ввода
    private static final String STYLE_INPUT = "-fx-background-color: #F2F2F7; -fx-background-radius: 8; -fx-padding: 8; -fx-font-size: 14px; -fx-text-fill: #1D1D1F;";

    private static final String STYLE_LABEL_HEADER = "-fx-font-family: 'System'; -fx-font-size: 24px; -fx-font-weight: 800; -fx-text-fill: #1D1D1F;";
    private static final String STYLE_PROGRESS = "-fx-accent: #34C759;"; // Зеленый прогресс-бар

    // Логика
    private HippodromeSystem system;
    private Race currentRace;
    private Horse selectedHorse;

    // UI Элементы
    private Label statusLabel;
    private Button startButton;
    private TextField betInput; // <-- Новое поле
    private Map<Horse, ProgressBar> horseProgressBars = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        initSystem();

        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setStyle(STYLE_BG);
        root.setAlignment(Pos.CENTER);

        // 1. Заголовок
        Label title = new Label("Hippodrome Pro");
        title.setStyle(STYLE_LABEL_HEADER);

        // 2. Карточка с гонкой
        VBox raceCard = createRaceCard();

        // 3. Блок ввода ставки (НОВОЕ)
        HBox betBox = new HBox(10);
        betBox.setAlignment(Pos.CENTER);
        
        Label betLabel = new Label("Your Bet ($):");
        betLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #86868b;");
        
        betInput = new TextField("100"); // Значение по умолчанию
        betInput.setStyle(STYLE_INPUT);
        betInput.setPrefWidth(100);
        
        betBox.getChildren().addAll(betLabel, betInput);

        // 4. Статус и Кнопки
        statusLabel = new Label("Choose a horse, set amount & start.");
        statusLabel.setStyle("-fx-text-fill: #86868b; -fx-font-size: 13px;");

        startButton = new Button("Place Bet & Start Race");
        startButton.setStyle(STYLE_BTN_PRIMARY);
        startButton.setPrefWidth(220);
        startButton.setPrefHeight(45);
        startButton.setOnAction(e -> startRaceAnimation());

        // Кнопка ВЫХОДА (НОВОЕ)
        Button exitButton = new Button("Exit Application");
        exitButton.setStyle(STYLE_BTN_EXIT);
        exitButton.setOnAction(e -> Platform.exit()); // Закрывает приложение

        // Сборка всего вместе
        root.getChildren().addAll(title, raceCard, betBox, statusLabel, startButton, exitButton);

        Scene scene = new Scene(root, 400, 650); // Чуть увеличил высоту
        primaryStage.setTitle("Hippodrome Case_5");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initSystem() {
        system = new HippodromeSystem(new AnalyticOddsProvider(), new OfficialRaceArbiter(), new StandardBettingService());
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
        
        selectedHorse = h1; // Выбор по умолчанию
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

            RadioButton rb = new RadioButton();
            rb.setToggleGroup(group);
            if (h == selectedHorse) rb.setSelected(true);
            rb.setOnAction(e -> selectedHorse = h);

            Label nameLbl = new Label(h.getName());
            nameLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
            nameLbl.setMinWidth(60);

            Label oddsLbl = new Label("x" + currentRace.getOdds(h));
            oddsLbl.setStyle("-fx-text-fill: #86868b; -fx-font-size: 12px;");
            oddsLbl.setMinWidth(35);

            ProgressBar pb = new ProgressBar(0);
            pb.setPrefWidth(140);
            pb.setStyle(STYLE_PROGRESS);
            horseProgressBars.put(h, pb);

            row.getChildren().addAll(rb, nameLbl, oddsLbl, pb);
            card.getChildren().add(row);
        }
        return card;
    }

    private void startRaceAnimation() {
        if (selectedHorse == null) return;

        // 1. Считываем ставку с поля ввода
        double betAmount;
        try {
            betAmount = Double.parseDouble(betInput.getText());
            if (betAmount <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            statusLabel.setText("Error: Invalid bet amount!");
            statusLabel.setStyle("-fx-text-fill: #FF3B30;");
            return;
        }

        // Блокируем интерфейс
        startButton.setDisable(true);
        betInput.setDisable(true); // Блокируем ввод во время гонки
        startButton.setStyle(STYLE_BTN_DISABLED);
        startButton.setText("Race in progress...");
        
        statusLabel.setText(String.format("Bet: $%.0f on %s", betAmount, selectedHorse.getName()));
        statusLabel.setStyle("-fx-text-fill: #86868b;");

        // Регистрация ставки
        system.processClientBet(currentRace, new Bet(betAmount, selectedHorse, Bet.Type.WIN));

        // Анимация (в отдельном потоке)
        new Thread(() -> {
            Random rand = new Random();
            boolean finished = false;

            // Сброс прогресса перед стартом
            horseProgressBars.values().forEach(pb -> Platform.runLater(() -> pb.setProgress(0)));

            while (!finished) {
                try { Thread.sleep(50); } catch (InterruptedException e) {}

                for (Horse h : horseProgressBars.keySet()) {
                    double current = horseProgressBars.get(h).getProgress();
                    double move = rand.nextDouble() * 0.02; // Скорость
                    double next = current + move;

                    Platform.runLater(() -> horseProgressBars.get(h).setProgress(next));

                    if (next >= 1.0) {
                        finished = true;
                        final Horse winner = h;
                        Platform.runLater(() -> endRace(winner, betAmount));
                        break;
                    }
                }
            }
        }).start();
    }

    private void endRace(Horse winner, double betAmount) {
        system.finishRace(currentRace, winner);
        
        startButton.setDisable(false);
        betInput.setDisable(false);
        startButton.setStyle(STYLE_BTN_PRIMARY);
        startButton.setText("Place Bet & Start Race");

        if (selectedHorse.equals(winner)) {
            double win = betAmount * currentRace.getOdds(winner);
            statusLabel.setText(String.format("WINNER! You won $%.2f", win));
            statusLabel.setStyle("-fx-text-fill: #34C759; -fx-font-weight: bold; -fx-font-size: 15px;");
        } else {
            statusLabel.setText("You lost. Winner: " + winner.getName());
            statusLabel.setStyle("-fx-text-fill: #FF3B30; -fx-font-weight: bold; -fx-font-size: 15px;");
        }
    }
}
