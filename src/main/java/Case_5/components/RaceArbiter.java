package Case_5.components;

import Case_5.model.Horse;
import Case_5.model.Race;

public interface RaceArbiter {
    // Фіксація результату
    void registerResult(Race race, Horse winner);
}