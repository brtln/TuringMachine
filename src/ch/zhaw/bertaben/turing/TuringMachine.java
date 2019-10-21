package ch.zhaw.bertaben.turing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TuringMachine {

    private int stepMode = 0;

    private Map<String, String> bandAlphabet;
    private Map<String, Integer> richtungen;
    private Map<String, String> richtungenSymbol;

    private List<String> band;
    private String leerzeichen;
    private Integer position;
    private Integer schritte;
    private String startZustand;
    private List<String> endZustaende;
    private Map<Zustand, Uebergangsfunktion> uebergangsfunktionen;

    public TuringMachine(int stepMode){
        this.stepMode = stepMode;

        this.position = 0;
        this.schritte = 0;
        this.startZustand = "0";
        this.endZustaende = new ArrayList<String>();
        this.endZustaende.add("00");
        this.uebergangsfunktionen = new HashMap<Zustand, Uebergangsfunktion>();

        this.bandAlphabet = new HashMap<String, String>();
        this.bandAlphabet.put("0", "0");
        this.bandAlphabet.put("00", "1");
        this.bandAlphabet.put("000", "_");
        this.bandAlphabet.put("0000", "X");
        this.bandAlphabet.put("00000", "Y");
        this.bandAlphabet.put("000000", "Z");

        this.leerzeichen = "_";

        this.richtungen = new HashMap<String, Integer>();
        this.richtungen.put("0", -1);
        this.richtungen.put("00", 1);
        //this.richtungen.put("000", 0);
        this.richtungenSymbol = new HashMap<String, String>();
        this.richtungenSymbol.put("0", "L");
        this.richtungenSymbol.put("00", "R");
        //this.richtungenSymbol.put("000", "S");
    }

    public void bandEinlegen(File bandFile){
        String bandString = new String("");

        try {
            bandString = Files.readAllLines(bandFile.toPath()).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        char[] bandArr = bandString.toCharArray();
        this.band = new ArrayList<String>();
        for (char c : bandArr) {
            band.add(String.valueOf(c));
        }

        for(int i=0; i<=20;i++){
            band.add(this.leerzeichen);
        }

        // Ausgabe Band
        System.out.println();
        System.out.print("Band: ");
        for(String s : band){
            System.out.print(s);
        }
        System.out.println("\n");

        if(stepMode == 1){
            System.out.println("Drücke Enter um fortzufahren.");
        }
    }

    void leseUebergangsfunktionen() throws IOException{

        while(!band.get(position).equals("1") || !band.get(position+1).equals("1") || !band.get(position+2).equals("1")){
            if(band.get(position).equals("1") && band.get(position+1).equals("1")){
                bewegeLesekopf("00"); // skip 1
                bewegeLesekopf("00"); // skip 1
            }
            Zustand zustand = new Zustand();
            zustand.setZustand(getNaechstenCode());
            bewegeLesekopf("00"); // skip 1

            zustand.setEingabe(bandAlphabet.get(getNaechstenCode()));
            bewegeLesekopf("00"); // skip 1

            Uebergangsfunktion uf = new Uebergangsfunktion();
            uf.setUebergangsZustand(getNaechstenCode());
            bewegeLesekopf("00"); // skip 1

            uf.setZuSchreibendesSymbol(bandAlphabet.get(getNaechstenCode()));
            bewegeLesekopf("00"); // skip 1

            uf.setRichtung(getNaechstenCode());

            uebergangsfunktionen.put(zustand, uf);
        }

        // skip 111
        for (int i = 0; i < 3; i++){
            bewegeLesekopf("00");
        }

    }

    void printUebergangsfunktionen(){
        System.out.println("------------------------------------------------");
        uebergangsfunktionen.forEach((K, V) -> System.out.println(
                  "Zustand: "+K.getZustand()+" \t"
                +" Eingabe: "+K.getEingabe()+"  \t"
                +" Übergangszustand: "+V.getUebergangsZustand()+"  \t"
                +" Schreibe: "+V.getZuSchreibendesSymbol()+"  \t"
                +" Richtung: "+V.getRichtung()+"  \t"
                +" HashCode: "+K.hashCode()));
        System.out.println("------------------------------------------------");
    }

    void startTM() throws IOException{
        if(stepMode == 2){
            stepMode = 1;
        }

        Zustand aktuellerZustand = new Zustand(this.startZustand, String.valueOf(band.get(position)));
        printTM();
        while(uebergangsfunktionen.containsKey(aktuellerZustand)){
            Uebergangsfunktion uf = uebergangsfunktionen.get(aktuellerZustand);
            printZustandTM(aktuellerZustand, uf);
            band.set(position, uf.getZuSchreibendesSymbol());
            bewegeLesekopf(uf.getRichtung());
            aktuellerZustand.setZustand(uf.getUebergangsZustand());
            aktuellerZustand.setEingabe(band.get(position));
        }

        if (endZustaende.contains(aktuellerZustand.getZustand())){
            System.out.println("\nAkzeptierender Zustand Q("+aktuellerZustand.getZustand().length()+")");
            printResultat();
        } else {
            System.out.println("\nVerwerfender Zustand Q("+aktuellerZustand.getZustand().length()+")");
        }
        printTM();
    }

    private void printTM(){
        int beginIndex = position-15;
        int endIndex = position+15;
        if(beginIndex<0){
            for(int i=0;i<=15-position;i++){
                System.out.print(this.leerzeichen);
            }
            beginIndex = 0;
        }

        for(int i = beginIndex;i<=endIndex;i++){
            if(band.size()<=i){
                band.add("_");
            }
            System.out.print(band.get(i));
        }

        System.out.println("");
        for(int i = 0;i<15;i++){
            System.out.print(" ");
        }
        System.out.println("^");
    }

    private void printZustandTM(Zustand aktuellerZustand, Uebergangsfunktion uf){
        System.out.flush();
        System.out.println("Übergang: Q("+aktuellerZustand.getZustand().length()+") - "+aktuellerZustand.getEingabe()+"/"+uf.getZuSchreibendesSymbol()
                +","+this.richtungenSymbol.get(uf.getRichtung())+" - Q("+uf.getUebergangsZustand().length()+")");
        System.out.println("Schritte: "+this.schritte);
        System.out.println("Position Kopf: "+this.position);
    }

    private void printResultat(){
        int index = 0;
        for(int i=position;i>0;i--){
            if(band.get(i-1).equals("1") && band.get(i-2).equals("1") && band.get(i-3).equals("1")){
                index = i;
                break;
            }
        }

        int nullZaehler = 0;
        for(int i=index;i<band.size();i++){
            if(band.get(i).equals("0")){
                nullZaehler++;
            }
        }
        System.out.println("Nullen gezählt: "+nullZaehler);
    }

    private void bewegeLesekopf(String richtung) throws IOException {
        this.position += richtungen.get(richtung);
        this.schritte++;
        if (stepMode == 1) {
            System.in.read();
        }
        printTM();
    }

    private String getNaechstenCode() throws IOException{
        StringBuilder code = new StringBuilder();
        while(!band.get(position).equals("1")){
            code.append(band.get(position));
            bewegeLesekopf("00");
        }

        return code.toString();
    }

}
