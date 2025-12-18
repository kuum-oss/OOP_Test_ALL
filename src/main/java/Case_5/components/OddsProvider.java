package Case_5.components;

import Case_5.model.Horse;
import Case_5.model.Race;

public interface OddsProvider {
    // Логіка розрахунку та встановлення коефіцієнтів
    void updateOdds(Race race, Horse horse, double newOdds);
    String getProviderName();
}