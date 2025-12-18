package Case_5;

import Case_5.components.*;
import Case_5.model.*;

// Фасад, що об'єднує роботу всіх компонентів
public class HippodromeSystem {
    private final OddsProvider oddsProvider;
    private final RaceArbiter arbiter;
    private final BettingService bettingService;

    // Dependency Injection
    public HippodromeSystem(OddsProvider oddsProvider,
                            RaceArbiter arbiter,
                            BettingService bettingService) {
        this.oddsProvider = oddsProvider;
        this.arbiter = arbiter;
        this.bettingService = bettingService;
    }

    // Делегування дій компонентам
    public void setBookmakerOdds(Race race, Horse horse, double odds) {
        oddsProvider.updateOdds(race, horse, odds);
    }

    public void processClientBet(Race race, Bet bet) {
        bettingService.placeBet(race, bet);
    }

    public void finishRace(Race race, Horse winner) {
        arbiter.registerResult(race, winner);
    }

    public void checkWin(Race race, Bet bet) {
        double win = bettingService.calculatePayout(race, bet);
        if (win > 0) {
            System.out.printf("$$$ CONGRATULATIONS! You won %.2f $$$%n", win);
        } else {
            System.out.println("... You lost. Try again next time.");
        }
    }
}