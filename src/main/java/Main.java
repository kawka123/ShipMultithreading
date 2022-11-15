import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        System.out.println("Available number of processes: " + Runtime.getRuntime().availableProcessors());
        ExecutorService se = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        //закидываем в пул столько, сколько моджет обработатьь процессор

        Tunnel tunnel = new Tunnel();
        ShipGenerator shipGenerator = new ShipGenerator(tunnel, 10); //количесво создаваыемых кораблей

        //создание причалов
        PierLoader pl1 = new PierLoader(tunnel, Type.DRESS);
        PierLoader pl2 = new PierLoader(tunnel, Type.BANANA);
        PierLoader pl3 = new PierLoader(tunnel, Type.MEAL);


        se.execute(shipGenerator);
        se.execute(pl1); se.execute(pl2); se.execute(pl3);

        se.shutdown();
    }
}
