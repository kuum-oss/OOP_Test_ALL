package Case_5.components.impl;

import Case_5.components.BettingService;
import Case_5.model.Bet;
import Case_5.model.Race;

public class StandardBettingService implements BettingService {
    @Override
    public boolean placeBet(Race race, Bet bet) {
        if (race.isFinished()) {
            System.out.println("Bets closed! Race finished.");
            return false;
        }
        System.out.printf("[Client] Bet placed: %.2f on %s (x%.2f)%n",
                bet.getAmount(),
                bet.getHorse().getName(),
                race.getOdds(bet.getHorse()));
        return true;
    }

    @Override
    public double calculatePayout(Race race, Bet bet) {
        if (!race.isFinished()) return 0.0;

        // Спрощена логіка: виграє тільки якщо точно вгадав переможця
        if (bet.getType() == Bet.Type.WIN && race.getWinner().equals(bet.getHorse())) {
            return bet.getAmount() * race.getOdds(bet.getHorse());
        }
        return 0.0;
    }
}