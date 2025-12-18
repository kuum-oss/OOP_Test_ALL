package Case_5.model;

public class Bet {
    public enum Type { WIN, PLACE } // WIN - 1 місце, PLACE - в трійці

    private final double amount;
    private final Horse horse;
    private final Type type;

    public Bet(double amount, Horse horse, Type type) {
        this.amount = amount;
        this.horse = horse;
        this.type = type;
    }

    public double getAmount() { return amount; }
    public Horse getHorse() { return horse; }
    public Type getType() { return type; }
}