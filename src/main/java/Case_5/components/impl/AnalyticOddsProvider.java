package Case_5.components.impl;

import Case_5.components.OddsProvider;
import Case_5.model.Horse;
import Case_5.model.Race;

public class AnalyticOddsProvider implements OddsProvider {
    @Override
    public void updateOdds(Race race, Horse horse, double newOdds) {
        if (newOdds < 1.01) {
            System.out.println("Error: Odds cannot be less than 1.01");
            return;
        }
        race.setOdds(horse, newOdds);
        System.out.printf("[Bookmaker] Odds for %s updated to x%.2f%n", horse.getName(), newOdds);
    }

    @Override
    public String getProviderName() { return "Global Analytics Ltd."; }
}