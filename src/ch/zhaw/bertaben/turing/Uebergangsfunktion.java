package ch.zhaw.bertaben.turing;

public class Uebergangsfunktion {

    private String uebergangsZustand;
    private String zuSchreibendesSymbol;
    private String richtung;

    public Uebergangsfunktion(){}

    public String getUebergangsZustand() {
        return uebergangsZustand;
    }

    public void setUebergangsZustand(String uebergangsZustand) {
        this.uebergangsZustand = uebergangsZustand;
    }

    public String getZuSchreibendesSymbol() {
        return zuSchreibendesSymbol;
    }

    public void setZuSchreibendesSymbol(String zuSchreibendesSymbol) {
        this.zuSchreibendesSymbol = zuSchreibendesSymbol;
    }

    public String getRichtung() {
        return richtung;
    }

    public void setRichtung(String richtung) {
        this.richtung = richtung;
    }

}
