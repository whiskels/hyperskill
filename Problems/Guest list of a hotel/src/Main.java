//put imports you need here

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        List<String> names = new ArrayList<String>();
        while (scanner.hasNext()) {
            names.add(scanner.next());
        }

        for (int i = names.size()-1; i >= 0; i--) {
            System.out.println(names.get(i));
        }
    }
}