package Case_5.components;

import Case_5.model.Bet;
import Case_5.model.Race;

public interface BettingService {
    // Прийом ставки та валідація
    boolean placeBet(Race race, Bet bet);
    // Розрахунок виплати
    double calculatePayout(Race race, Bet bet);
}