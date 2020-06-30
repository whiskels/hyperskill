package coffee;

public class Espresso implements Coffee {
    private final static int WATER = 250;
    private final static int MILK = 0;
    private final static int BEANS = 16;
    private final static int PRICE = 4;

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
