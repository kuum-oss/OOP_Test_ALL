package Case_4.components.impl;

import Case_4.components.Electronics;

public class BoschElectronics implements Electronics {
    @Override
    public boolean hasStabilityControl() { return true; }

    @Override
    public double getEnergyConsumption() { return 0.5; }
}