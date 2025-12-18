package Case_4.components.impl;

import Case_4.components.Engine;

public class BMWEngine implements Engine {
    @Override
    public double getPower() { return 300.0; }

    @Override
    public double getBaseFuelConsumption() { return 12.5; }

    @Override
    public String getManufacturer() { return "BMW M-Power"; }
}