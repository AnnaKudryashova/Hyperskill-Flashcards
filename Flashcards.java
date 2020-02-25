import java.util.*;
import java.io.*;


public class Flashcards {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<String> log = new ArrayList<>();
    static HashMap<String, String> cards = new LinkedHashMap<>();
    static ArrayList<String> hardCards = new ArrayList<>();
    static HashMap<String, Integer> mistakes = new LinkedHashMap<>();

    public static void main(String[] args) {
        boolean exporting = false;
        String expFilename = "";
        for (int i = 0; i < args.length;  i += 2) {
            if (args[i].equals("-import")) {
                importAction(args[i + 1]);
            }
            if (args[i].equals("-export")) {
                exporting = true;
                expFilename = args[i + 1];
            }
        }
        boolean running = true;
        while (running) {
            System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            log.add("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            String action = scanner.nextLine();
            log.add(action);
            switch (action) {
                case "add" :
                    add();
                    break;
                case "remove" :
                    remove();
                    break;
                case "import" :
                    importAction();
                    break;
                case "export" :
                    export();
                    break;
                case "ask" :
                    ask();
                    break;
                case "exit" :
                    running = false;
                    System.out.println("Bye bye!");
                    if (exporting) {
                        export(expFilename);
                    }
                    break;
                case "log" :
                    log();
                    break;
                case "hardest card" :
                    hardestCard();
                    break;
                case "reset stats" :
                    reset();
                    break;
                default :
                    System.out.println("Unknown command.");
                    break;
            }
        }
    }

    public static void add() {
        String term = "";
        String definition = "";
        System.out.println("The card");
        log.add("The card");
        term = scanner.nextLine();
        log.add(term);
        if (cards.containsKey(term)) {
            System.out.println("The card \"" + term + "\" already exists.");
            log.add("The card \"" + term + "\" already exists.");
        }
        else {
            System.out.println("The definition of the card");
            log.add("The definition of the card");
            definition = scanner.nextLine();
            log.add(definition);
            if (cards.containsValue(definition)) {
                System.out.println("The definition \"" + definition + "\" already exists.");
                log.add("The definition \"" + definition + "\" already exists.");
            }
            else {
                cards.put(term, definition);
                mistakes.put(term, 0);
                System.out.println("The pair (" + term + ":" + definition + ") has been added.");
                log.add("The pair (" + term + ":" + definition + ") has been added.");
            }
        }
    }

    public static void remove() {
        System.out.println("The card");
        log.add("The card");
        String term = scanner.nextLine();
        log.add(term);
        if (cards.containsKey(term)) {
            cards.remove(term);
            mistakes.remove(term);
            System.out.println("The card has been removed.");
            log.add("The card has been removed.");
        }
        else {
            System.out.println("Can't remove \"" + term + "\": there is no such card.");
            log.add("Can't remove \"" + term + "\": there is no such card.");
        }
    }

    public static void importAction() {
        System.out.println("File name:");
        log.add("File name:");
        String filename = scanner.nextLine();
        log.add(filename);
        File file = new File("./" + filename);
        int num = 0;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String term = scanner.nextLine();
                String definition = scanner.nextLine();
                int mistake = scanner.nextInt();
                scanner.nextLine();
                cards.put(term, definition);
                mistakes.put(term, mistake);
                num++;
            }
            System.out.println(num + " cards have been loaded.");
            log.add(num + " cards have been loaded.");
        } catch (Exception e) {
            System.out.println("File not found.");
            log.add("File not found.");
        }
    }

    public static void importAction(String filename) { ;
        File file = new File("./" + filename);
        int num = 0;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String term = scanner.nextLine();
                String definition = scanner.nextLine();
                int mistake = scanner.nextInt();
                scanner.nextLine();
                cards.put(term, definition);
                mistakes.put(term, mistake);
                num++;
            }
            System.out.println(num + " cards have been loaded.");
            log.add(num + " cards have been loaded.");
        } catch (Exception e) {
            System.out.println("File not found.");
            log.add("File not found.");
        }
    }

    public static void export() {
        System.out.println("File name:");
        log.add("File name:");
        String filename = scanner.nextLine();
        log.add(filename);
        File file = new File("./" + filename);
        int num = 0;
        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (Map.Entry entry : cards.entrySet()) {
                printWriter.println(entry.getKey());
                printWriter.println(entry.getValue());
                printWriter.println(mistakes.get(entry.getKey()));
                num++;
            }
            System.out.println(num + " cards have been saved.");
            log.add(num + " cards have been saved.");
        } catch (Exception e) {
            System.out.println("Failed to export.");
            log.add("Failed to export.");
        }
    }

    public static void export(String filename) {
        File file = new File("./" + filename);
        int num = 0;
        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (Map.Entry entry : cards.entrySet()) {
                printWriter.println(entry.getKey());
                printWriter.println(entry.getValue());
                printWriter.println(mistakes.get(entry.getKey()));
                num++;
            }
            System.out.println(num + " cards have been saved.");
            log.add(num + " cards have been saved.");
        } catch (Exception e) {
            System.out.println("Failed to export.");
            log.add("Failed to export.");
        }
    }

    public static void ask() {
        Object[] keys = cards.keySet().toArray();
        Random random = new Random();
        if (keys.length > 0) {
            System.out.println("How many times to ask?");
            log.add("How many times to ask?");
            int num = scanner.nextInt();
            log.add(Integer.toString(num));
            scanner.nextLine();
            for (int i = 0; i < num; i++) {
                String randomKey = (String) keys[random.nextInt(keys.length)];
                System.out.println("Print the definition of \"" + randomKey + "\":");
                log.add("Print the definition of \"" + randomKey + "\":");
                String answer = scanner.nextLine();
                log.add(answer);
                if (cards.containsValue(answer)) {
                    if (answer.equals(cards.get(randomKey))) {
                        System.out.println("Correct answer");
                        log.add("Correct answer");
                    } else {
                        mistakes.put(randomKey, mistakes.get(randomKey) + 1);
                        System.out.println("Wrong answer. The correct one is \"" + cards.get(randomKey) +
                                "\", you've just written the definition of \"" + findDefinition(answer) + "\".");
                        log.add("Wrong answer. The correct one is \"" + cards.get(randomKey) +
                                "\", you've just written the definition of \"" + findDefinition(answer) + "\".");
                    }
                } else {
                    mistakes.put(randomKey, mistakes.get(randomKey) + 1);
                    System.out.println("Wrong answer. The correct one is \"" + cards.get(randomKey) + "\"");
                    log.add("Wrong answer. The correct one is \"" + cards.get(randomKey) + "\"");
                }
            }
        }
        else {
            System.out.println("There are no cards.");
            log.add("There are no cards.");
        }
    }

    public static void log() {
        System.out.println("File name:");
        log.add("File name:");
        String filename = scanner.nextLine();
        log.add(filename);
        File file = new File("./" + filename);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (String entry : log) {
                printWriter.println(entry);
            }
            System.out.println("The log has been saved.");
            log.add("The log has been saved.");
        } catch (Exception e) {
            System.out.println("Failed to create log.");
            log.add("Failed to create log.");
        }
    }

    public static void hardestCard() {
        StringBuilder output = new StringBuilder();
        int count = 0;
        for (Integer entry : mistakes.values()) {
            if (entry > count) {
                count = entry;
            }
        }
        if (count > 0) {
            for (String entry : mistakes.keySet()) {
                if (mistakes.get(entry) == count) {
                    if (!hardCards.contains(entry)) {
                        hardCards.add(entry);
                    }
                }
            }
            if (hardCards.size() > 1) {
                output.append("The hardest cards are \"");
                for (int i = 0; i < hardCards.size(); i++) {
                    output.append(hardCards.get(i) + "\", \"");
                }
                output.replace(output.length() - 3, output.length(), ". You have " +
                        count + " errors answering them.");
            }
            else {
                output.append("The hardest card is \"" + hardCards.get(0) + "\".  You have " +
                        count + " errors answering it.");
            }
        }
        else {
            output.append("There are no cards with errors.");
        }
        hardCards.clear();
        System.out.println(output);
        log.add(output.toString());
    }

    public static void reset() {
        for (String entry : mistakes.keySet()) {
            mistakes.put(entry, 0);
	}
            System.out.println("Card statistics has been reset.");
            log.add("Card statistics has been reset.");
    }

    public static String findDefinition (String answer) {
        String correct = "";
        Set<Map.Entry<String, String>> entrySet = cards.entrySet();
        for (Map.Entry<String, String> pair : entrySet) {
            if (answer.equals(pair.getValue()))
                correct = pair.getKey();
        }
        return correct;
    }

    public static String findDefinition (Integer mistakes, HashMap<String, Integer> cards) {
        String term = "";
        Set<Map.Entry<String, Integer>> entrySet = cards.entrySet();
        for (Map.Entry<String, Integer> pair : entrySet) {
            if (mistakes.equals(pair.getValue()))
                term = pair.getKey();
        }
        return term;
    }
}

