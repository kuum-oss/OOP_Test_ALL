package Case_4.model;

import Case_4.components.*; // Імпорт усіх інтерфейсів

public class Car {
    // Поля - це інтерфейси (Абстракції), а не конкретні класи
    private final Engine engine;
    private final Body body;
    private final Suspension suspension;
    private final Electronics electronics;

    // Dependency Injection через конструктор
    public Car(Engine engine, Body body, Suspension suspension, Electronics electronics) {
        this.engine = engine;
        this.body = body;
        this.suspension = suspension;
        this.electronics = electronics;
    }

    // Розрахунок максимальної швидкості
    public double calculateMaxSpeed() {
        return (engine.getPower() / (body.getWeight() * body.getAerodynamicsFactor())) * 400;
    }

    // Розрахунок споживання палива
    public double calculateFuelConsumption() {
        double consumption = engine.getBaseFuelConsumption();
        if (electronics.hasStabilityControl()) {
            consumption += 0.2;
        }
        return consumption + (body.getWeight() / 2000);
    }

    // Вивід характеристик
    public void printSpecs() {
        System.out.println("====== AUTO BUILD SPECS ======");
        System.out.println("Parts used:");
        System.out.println(" -> Engine: " + engine.getManufacturer());
        System.out.println(" -> Body:   " + body.getType());
        System.out.println(" -> Susp.:  " + suspension.getManufacturer());
        System.out.println("------------------------------");
        System.out.printf("Max Speed:       %.1f km/h%n", calculateMaxSpeed());
        System.out.printf("Fuel Usage:      %.1f L/100km%n", calculateFuelConsumption());
        System.out.println("==============================\n");
    }
}