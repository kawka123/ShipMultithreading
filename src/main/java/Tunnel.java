import java.util.ArrayList;
import java.util.List;

public class Tunnel {

    private List<Ship> store;
    private static final int maxShipsInTunnel = 5;
    private static final int minShipsInTunnel = 0;
    private int shipsCounter = 0;

    public Tunnel() {
        List<Ship> store = new ArrayList<>(); //это наш тунель
    }

    public synchronized boolean add(Ship element) {
        try {
            if (shipsCounter < maxShipsInTunnel) {
                notifyAll();
                store.add(element);
                System.out.printf("%s - Ships in the tunnel. The ship arrived in the tunnel: %s %s %s;", store.size(), element.getType(), element.getSize(), Thread.currentThread().getName());
                shipsCounter++;
            } else {
                System.out.println(store.size() + "Ships in the tunnel. There is no place for a ship in the tunnel.");
                wait();
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public synchronized Ship get(Type shipType) {
        try {
            if (shipsCounter > minShipsInTunnel) {
                notifyAll();
                for (Ship sh : store) {
                    if (sh.getType() == shipType) {
                        shipsCounter--;
                        System.out.printf("%s - Ships in the tunnel. The ship is taken from the tunnel: %s;", Thread.currentThread().getName());
                        store.remove(sh);
                        return sh;
                    }
                }
            }
            System.out.println("There are no ships in the tunnel;");
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
