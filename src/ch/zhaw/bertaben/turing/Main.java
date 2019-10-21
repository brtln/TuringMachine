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
        for(int i=0; i<files.length; i++){
            System.out.println(i+": "+files[i].getName());
        }

        System.out.print("Eingabe (0-"+(files.length-1)+"): ");
        int inputProgramm = 0;
        String input = scanner.nextLine();
        if(Integer.valueOf(input)>0 && Integer.valueOf(input) < files.length){
            inputProgramm = Integer.valueOf(input);
        }

        System.out.print("Im Step-Modus? ([K]omplett/[B]erechnung/[A]us) (default: a):");
        int stepMode = 0;
        input = scanner.nextLine();
        if(input.equals("k") || input.equals("K")){
            stepMode = 1;
        }else if(input.equals("b") || input.equals("B")){
            stepMode = 2;
        }

        TuringMachine tm = new TuringMachine(stepMode);

        tm.bandEinlegen(files[inputProgramm]);
        tm.leseUebergangsfunktionen();
        tm.printUebergangsfunktionen();
        tm.startTM();
    }
}
