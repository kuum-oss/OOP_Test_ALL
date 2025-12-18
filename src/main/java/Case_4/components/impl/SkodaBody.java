package Case_4.components.impl;

import Case_4.components.Body;

public class SkodaBody implements Body {
    @Override
    public double getWeight() { return 1500.0; }

    @Override
    public double getAerodynamicsFactor() { return 0.32; }

    @Override
    public String getType() { return "Skoda Octavia Body"; }
}