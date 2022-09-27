public class Buyer extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Buyer " + Thread.currentThread().getName() + " came to the salon");
            Main.locker.lock();
            if (!Main.cars.isEmpty()) {
                try {
                    System.out.println("Buyer " + Thread.currentThread().getName() + " get new car: " + Main.cars.get(0));
                    Main.cars.remove(0);
                    Thread.sleep(Main.PURCHASE_TIME);
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                System.out.println(Thread.currentThread().getName() + ": No cars, i'm leaving");
                try {
                    Thread.sleep(Main.PURCHASE_TIME);
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            Main.locker.unlock();
        }
    }
}
