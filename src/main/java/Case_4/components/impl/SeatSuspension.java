package Case_4.components.impl;

import Case_4.components.Suspension;

public class SeatSuspension implements Suspension {
    @Override
    public double getBrakingEfficiency() { return 1.2; }

    @Override
    public String getManufacturer() { return "Seat Sport Suspension"; }
}