package machine;

import coffee.*;

import java.util.Scanner;

public class CoffeeMachine {
    private static int water = 400;
    private static int milk = 540;
    private static int beans = 120;
    private static int cups = 9;
    private static int money = 550;
    private static status currentStatus = status.WAITING;
    private static Scanner sc = new Scanner(System.in);

    private enum status {
        WAITING, MAKING_COFFEE, FILL, EXIT
    }


    public static void main(String[] args) {
        while (currentStatus != status.EXIT) {
            process();
        }
    }

    private static void process() {
        switch (currentStatus) {
            case WAITING:
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
                        getStatus();
                        break;
                    case "exit":
                        currentStatus = status.EXIT;
                        break;
                }
                break;
        }
    }

    private static void getStatus() {
        printMsg("The coffee machine has:\n" +
                water + " of water\n" +
                milk + " of milk\n" +
                beans + " of coffee beans\n" +
                cups + " of disposable cups\n" +
                money + " of money\n");
    }

    private static void chooseCoffee() {
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

    private static <T extends Coffee> void makeCoffee(T coffee) {
        if (isEnoughResources(coffee)) {
            money += coffee.getPrice();
            water -= coffee.getWater();
            milk -= coffee.getMilk();
            beans -= coffee.getBeans();
            cups--;
        }
    }

    private static <T extends Coffee> boolean isEnoughResources(T coffee) {
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

    private static void fill() {
        printMsg("Write how many ml of water do you want to add:");

        final int water = Integer.parseInt(sc.next());
        CoffeeMachine.water += water;
        printMsg("Write how many ml of milk do you want to add:");
        final int milk = Integer.parseInt(sc.next());
        CoffeeMachine.milk += milk;
        printMsg("Write how many grams of coffee do you want to add:");
        final int beans = Integer.parseInt(sc.next());
        CoffeeMachine.beans += beans;
        printMsg("Write how many disposable cups do you want to add:");
        final int cups = Integer.parseInt(sc.next());
        CoffeeMachine.cups += cups;
        currentStatus = status.WAITING;
    }

    private static void giveMoney() {
        printMsg(String.format("I gave you %d", money));
        CoffeeMachine.money = 0;
    }

    private static void printMsg(String string) {
        System.out.println(string);
    }
}
