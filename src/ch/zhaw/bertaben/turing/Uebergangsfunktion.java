package ch.zhaw.bertaben.turing;

class Uebergangsfunktion {

    private String uebergangsZustand;
    private String zuSchreibendesSymbol;
    private String richtung;

    Uebergangsfunktion(){}

    String getUebergangsZustand() {
        return uebergangsZustand;
    }

    void setUebergangsZustand(String uebergangsZustand) {
        this.uebergangsZustand = uebergangsZustand;
    }

    String getZuSchreibendesSymbol() {
        return zuSchreibendesSymbol;
    }

    void setZuSchreibendesSymbol(String zuSchreibendesSymbol) {
        this.zuSchreibendesSymbol = zuSchreibendesSymbol;
    }

    String getRichtung() {
        return richtung;
    }

    void setRichtung(String richtung) {
        this.richtung = richtung;
    }

}
