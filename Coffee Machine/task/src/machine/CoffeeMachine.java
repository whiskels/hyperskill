package machine;

import coffee.*;

import java.util.Scanner;

public class CoffeeMachine {
    private int water, milk, beans, cups, money;
    private status currentStatus;
    private Scanner sc;

    private enum status {
        WAITING, MAKING_COFFEE, FILL, EXIT
    }

    public CoffeeMachine(int water, int milk, int beans, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.cups = cups;
        this.money = money;
        this.currentStatus = status.WAITING;
        this.sc = new Scanner(System.in);
    }

    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine(400, 540, 120, 9, 550);
        while (coffeeMachine.currentStatus != status.EXIT) {
            coffeeMachine.work();
        }
    }

    private void work() {
        if (currentStatus == status.WAITING) {
            printMsg("Write action (buy, fill, take, remaining, exit):");
            switch (sc.next()) {
                case "buy":
                    currentStatus = status.MAKING_COFFEE;
                    chooseCoffee();
                    break;
                case "fill":
                    currentStatus = status.FILL;
                    fill();
                    break;
                case "take":
                    giveMoney();
                    break;
                case "remaining":
                    printStatus();
                    break;
                case "exit":
                    currentStatus = status.EXIT;
                    break;
            }
        }
    }

    private void chooseCoffee() {
        printMsg("What do you want to buy? 1 - espresso, " +
                "2 - latte, 3 - cappuccino, back - to main menu:");
        switch (sc.next()) {
            case "1":
                makeCoffee(new Espresso());
                break;
            case "2":
                makeCoffee(new Latte());
                break;
            case "3":
                makeCoffee(new Cappuccino());
                break;
            default:
                break;
        }
        currentStatus = status.WAITING;
    }

    private void printStatus() {
        printMsg("The coffee machine has:\n" +
                water + " of water\n" +
                milk + " of milk\n" +
                beans + " of coffee beans\n" +
                cups + " of disposable cups\n" +
                money + " of money\n");
    }

    private <T extends Coffee> void makeCoffee(T coffee) {
        if (isEnoughResources(coffee)) {
            money += coffee.getPrice();
            water -= coffee.getWater();
            milk -= coffee.getMilk();
            beans -= coffee.getBeans();
            cups--;
        }
    }

    private <T extends Coffee> boolean isEnoughResources(T coffee) {
        boolean isWater = water >= coffee.getWater();
        boolean isMilk = milk >= coffee.getMilk();
        boolean isBeans = beans >= coffee.getBeans();
        boolean isCups = cups > 0;

        final boolean isEnough = isWater && isMilk && isBeans && isCups;

        if (isEnough) {
            printMsg("I have enough resources, making you a coffee!");
        } else {
            printMsg(String.format("Sorry, not enough %s!",
                    !isWater ? "water" : !isMilk ?
                            "milk" : !isBeans ? "coffee beans" : "cups"));
        }
        return isEnough;
    }

    private void fill() {
        printMsg("Write how many ml of water do you want to add:");
        final int water = Integer.parseInt(sc.next());
        this.water += water;

        printMsg("Write how many ml of milk do you want to add:");
        final int milk = Integer.parseInt(sc.next());
        this.milk += milk;

        printMsg("Write how many grams of coffee do you want to add:");
        final int beans = Integer.parseInt(sc.next());
        this.beans += beans;

        printMsg("Write how many disposable cups do you want to add:");
        final int cups = Integer.parseInt(sc.next());
        this.cups += cups;

        currentStatus = status.WAITING;
    }

    private void giveMoney() {
        printMsg(String.format("I gave you %d", money));
        money = 0;
    }

    private static void printMsg(String string) {
        System.out.println(string);
    }
}
