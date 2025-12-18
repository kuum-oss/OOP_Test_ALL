package Case_5.components.impl;

import Case_5.components.RaceArbiter;
import Case_5.model.Horse;
import Case_5.model.Race;

public class OfficialRaceArbiter implements RaceArbiter {
    @Override
    public void registerResult(Race race, Horse winner) {
        if (race.getParticipants().containsKey(winner)) {
            race.finish(winner);
            System.out.printf("[Admin] Race finished! Winner is %s%n", winner.getName());
        } else {
            System.out.println("[Admin] Error: This horse is not in the race!");
        }
    }
}