import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        Lock locker = new ReentrantLock(true);
        final int PREPARATION_TIME = 3000;
        final int PURCHASE_TIME = 2000;
        List<String> cars = new ArrayList<>();
        new Thread(() ->{
            for (int i = 0; i < 10; i++) {
                    cars.add("Toyota");
                    System.out.println("Seller make new car: " + cars.get(0));
                    try {
                        Thread.sleep(PREPARATION_TIME);
                    } catch (InterruptedException ex){
                        System.out.println(ex.getMessage());
                    }
            }
        }).start();

        Runnable buyer = () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("Buyer " + Thread.currentThread().getName() + " came to the salon");
                locker.lock();
                    if (!cars.isEmpty()) {
                        try {
                            System.out.println("Buyer " + Thread.currentThread().getName() + " get new car: " + cars.get(0));
                            cars.remove(0);
                            Thread.sleep(PURCHASE_TIME);
                        } catch (InterruptedException ex) {
                            System.out.println(ex.getMessage());
                        }
                    } else {
                        System.out.println(Thread.currentThread().getName() + ": No cars, i'm leaving");
                        try {
                            Thread.sleep(PURCHASE_TIME);
                        } catch (InterruptedException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                locker.unlock();
            }
        };
        new Thread(buyer).start();
        new Thread(buyer).start();
        new Thread(buyer).start();
    }
}
