package readability;

import java.nio.file.*;
import java.util.*;
import java.util.function.IntPredicate;

public class ReadabilityEstimator {
    private static final HashMap<Integer, String> AGE;
    private static final Set<Character> VOWELS;

    private final Scanner sc;
    private final Path file;
    private String fileContent;
    private String[] words;
    private int totalWords, totalSentences, totalCharacters, totalSyllables, totallPolysyllabels;
    private double ari, smog, colemanLiau, fleschKincaid;
    private QUERIES selectedQuery;

    private enum QUERIES {NULL, ARI, FK, SMOG, CL, all}

    static {
        VOWELS = Set.of('a', 'e', 'i', 'o', 'u', 'y');

        AGE = new HashMap<>() {
            {
                put(1, "6");
                put(2, "7");
                put(3, "9");
                put(4, "10");
                put(5, "11");
                put(6, "12");
                put(7, "13");
                put(8, "14");
                put(9, "15");
                put(10, "16");
                put(11, "17");
                put(12, "18");
                put(13, "24");
                put(14, "24+");
            }
        };
    }

    public ReadabilityEstimator(String fileName) {
        this.file = Paths.get(fileName);
        this.sc = new Scanner(System.in);
        selectedQuery = QUERIES.NULL;
    }

    public void run() {
        getTotals();
        estimate(sc.nextLine());
    }

    private void getTotals() {
        try {
            fileContent = Files.readString(file).replaceAll("\\n", "");
            totalSentences = fileContent.split("[.?!]")
                    .length;
            totalWords = fileContent.replaceAll("[.?!]", " ")
                    .trim()
                    .split("\\s+")
                    .length;
            totalCharacters = fileContent.replaceAll("\\t", " ")
                    .replaceAll("\\s+", "")
                    .length();
            words = fileContent.replaceAll("[.?!]", " ")
                    .trim()
                    .toLowerCase()
                    .split("\\s+");
            totalSyllables = countSyllables();
            totallPolysyllabels = countPolysyllables();

            printTotals();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private int countSyllablesWithPredicate(IntPredicate predicate) {
        return Arrays.stream(words)
                .mapToInt(word -> {
                    word = word.charAt(word.length() - 1) == 'e' ? word.substring(0, word.length() - 1) : word;
                    boolean isConsecutiveVowel = false;
                    int syllables = 0;
                    for (char ch : word.toCharArray()) {
                        if (VOWELS.contains(ch)) {
                            if (!isConsecutiveVowel) {
                                syllables++;
                            }
                            isConsecutiveVowel = true;
                        } else {
                            isConsecutiveVowel = false;
                        }
                    }

                    return syllables > 0 ? syllables : 1;
                })
                .filter(predicate)
                .sum();
    }

    private int countSyllables() {
        return Arrays.stream(words)
                .mapToInt(word -> {
                    word = word.charAt(word.length() - 1) == 'e' ? word.substring(0, word.length() - 1) : word;
                    boolean isConsecutiveVowel = false;
                    int syllables = 0;
                    for (char ch : word.toCharArray()) {
                        if (VOWELS.contains(ch)) {
                            if (!isConsecutiveVowel) {
                                syllables++;
                            }
                            isConsecutiveVowel = true;
                        } else {
                            isConsecutiveVowel = false;
                        }
                    }

                    return syllables > 0 ? syllables : 1;
                })
                .sum();
    }

    private int countPolysyllables() {
        return (int) Arrays.stream(words)
                .mapToInt(word -> {
                    word = word.charAt(word.length() - 1) == 'e' ? word.substring(0, word.length() - 1) : word;
                    boolean isConsecutiveVowel = false;
                    int syllables = 0;
                    for (char ch : word.toCharArray()) {
                        if (VOWELS.contains(ch)) {
                            if (!isConsecutiveVowel) {
                                syllables++;
                            }
                            isConsecutiveVowel = true;
                        } else {
                            isConsecutiveVowel = false;
                        }
                    }

                    return syllables > 0 ? syllables : 1;
                })
                .filter(s -> s > 2)
                .count();
    }

    private void estimate(String query) {
        selectedQuery = QUERIES.valueOf(query);
        calculateScore();
    }

    private void calculateScore() {
        System.out.println();
        switch (selectedQuery) {
            case ARI:
                printAri();
                break;
            case CL:
                printCl();
                break;
            case FK:
                printFk();
                break;
            case SMOG:
                printSmog();
                break;
            case all:
                printAll();
                break;
        }
    }

    private void printAri() {
        ari = 4.71 * totalCharacters / totalWords +
                0.5 * totalWords / totalSentences - 21.43;

        System.out.println(String.format("Automated Readability Index: %.2f (about %s year olds)",
                ari, getAgeString(QUERIES.ARI)));
    }

    private void printCl() {
        colemanLiau = 0.0588 * totalCharacters * 100 / totalWords - 0.296 * totalSentences * 100 / totalWords - 15.8;

        System.out.println(String.format("Coleman–Liau Index: %.2f (about %s year olds)",
                colemanLiau, getAgeString(QUERIES.CL)));
    }

    private void printFk() {
        fleschKincaid = 0.39 * totalWords / totalSentences +
                11.8 * totalSyllables / totalWords - 15.59;

        System.out.println(String.format("Flesch–Kincaid readability tests: %.2f (about %s year olds)",
                fleschKincaid, getAgeString(QUERIES.FK)));
    }

    private void printSmog() {
        smog = 1.043 + Math.sqrt(totallPolysyllabels * 30d / totalSentences) + 3.1291;

        System.out.println(String.format("Simple Measure of Gobbledygook: %.2f (about %s year olds)",
                smog, getAgeString(QUERIES.SMOG)));
    }

    private void printAll() {
        printAri();
        printFk();
        printSmog();
        printCl();
        System.out.println();
        System.out.println(String.format("This text should be understood in average by %s year olds.",
                getAgeString(QUERIES.all)));
    }

    private String getAgeString(QUERIES query) {
        switch (query) {
            case ARI:
                return getAgeString(ari);
            case CL:
                return getAgeString(colemanLiau);
            case FK:
                return getAgeString(fleschKincaid);
            case SMOG:
                return getAgeString(smog);
            case all:
                double ageAri = getAgeInt(ari);
                double ageCole = getAgeInt(colemanLiau);
                double ageFk = getAgeInt(colemanLiau);
                double ageSmog = getAgeInt(smog);
                return String.format("%.2f", (ageAri + ageCole + ageFk + ageSmog) / 4);
        }
        return null;
    }

    private int getAgeInt(double score) {
        return Integer.parseInt(getAgeString(score).replace("+", ""));
    }

    private String getAgeString(double score) {
        return Math.ceil(score) > 14 ? AGE.get(14) : AGE.get((int) Math.ceil(score));
    }

    private void printTotals() {
        System.out.println(String.format("The text is: %s", fileContent));
        System.out.println();
        System.out.println(String.format("Words: %d", totalWords));
        System.out.println(String.format("Sentences: %d", totalSentences));
        System.out.println(String.format("Characters: %d", totalCharacters));
        System.out.println(String.format("Syllables: %d", totalSyllables));
        System.out.println(String.format("Polysyllables: %d", totallPolysyllabels));
        System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, Cl, all):");
    }
}
