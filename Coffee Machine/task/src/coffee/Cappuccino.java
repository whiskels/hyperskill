package coffee;

public class Cappuccino implements Coffee {
    final static int WATER = 200;
    final static int MILK = 100;
    final static int BEANS = 12;
    final static int PRICE = 6;

    public int getWater() {
        return WATER;
    }

    public int getMilk() {
        return MILK;
    }

    public int getBeans() {
        return BEANS;
    }

    public int getPrice() {
        return PRICE;
    }
}
