package ch.zhaw.bertaben.turing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        File dir = new File(".");
        File[] files = dir.listFiles(((dir1, name) -> name.endsWith(".txt")));

        Scanner scanner = new Scanner(System.in);

        System.out.println("Wähle aus folgenden Bändern (default: 0):");
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                System.out.println(i + ": " + files[i].getName());
            }
        } else {
            System.out.println("Es hat keine Files in der Ordner.");
            System.exit(1);
        }

        int inputProgramm = -1;
        String input = null;

        while (inputProgramm == -1) {
            System.out.print("Eingabe (0-" + (files.length - 1) + "): ");
            input = scanner.nextLine();

            try {
                inputProgramm = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Es muss eine Zahl eingegeben werden.");
                continue;
            }

            if (inputProgramm <= 0 && inputProgramm >= files.length) {
                System.out.println("Die eingegeben Zahl muss zwischen <0> und <anzahl files> sein.");
            }
        }

        System.out.print("Im Step-Modus? ([K]omplett/[B]erechnung/[A]us) (default: a):");
        int stepMode = 0;
        input = scanner.nextLine();

        switch (input.toLowerCase()) {
            case "k":
                stepMode = 1;
                break;
            case "b":
                stepMode = 2;
                break;
            default:
                break;
        }

        TuringMachine tm = new TuringMachine(stepMode);

        tm.bandEinlegen(files[inputProgramm]);
        try {
            tm.leseUebergangsfunktionen();
            tm.printUebergangsfunktionen();
            tm.startTM();
        } catch (IOException e) {
            System.out.println("Could not read from System.in.");
            System.exit(1);
        }
    }
}
