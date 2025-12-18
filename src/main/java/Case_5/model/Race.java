package Case_5.model;

import java.util.HashMap;
import java.util.Map;

public class Race {
    private final Map<Horse, Double> participants = new HashMap<>();
    private Horse winner;
    private boolean isFinished = false;

    // Додавання коня з дефолтним коефіцієнтом
    public void addParticipant(Horse horse) {
        participants.put(horse, 1.0);
    }

    public void setOdds(Horse horse, double odds) {
        if (participants.containsKey(horse)) {
            participants.put(horse, odds);
        }
    }

    public double getOdds(Horse horse) {
        return participants.getOrDefault(horse, 1.0);
    }

    public void finish(Horse winner) {
        this.winner = winner;
        this.isFinished = true;
    }

    public boolean isFinished() { return isFinished; }
    public Horse getWinner() { return winner; }
    public Map<Horse, Double> getParticipants() { return participants; }
}