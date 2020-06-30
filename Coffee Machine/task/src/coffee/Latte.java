package coffee;

public class Latte implements Coffee {
    final static int WATER = 350;
    final static int MILK = 75;
    final static int BEANS = 20;
    final static int PRICE = 7;

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
