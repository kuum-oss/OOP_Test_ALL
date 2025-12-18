package Case_4;

import Case_4.components.impl.*;
import Case_4.model.Car;

public class Main {
    public static void main(String[] args) {

        System.out.println("Starting Assembly Line in package Case_4...\n");

        // 1. Створення компонентів (IoC Container logic)
        // Ми обираємо конкретні деталі тут
        var engine = new BMWEngine();
        var body = new SkodaBody();
        var suspension = new SeatSuspension();
        var electronics = new BoschElectronics();

        // 2. Ін'єкція залежностей (збірка авто)
        Car myHybridCar = new Car(engine, body, suspension, electronics);

        // 3. Тестування результату
        myHybridCar.printSpecs();
    }
}