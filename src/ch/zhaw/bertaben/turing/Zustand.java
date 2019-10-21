package ch.zhaw.bertaben.turing;

public class Zustand {

    private String zustand;
    private String eingabe;

    Zustand() {

    }

    Zustand(String zustand, String eingabe){
        this.zustand = zustand;
        this.eingabe = eingabe;
    }

    String getZustand() {
        return zustand;
    }

    void setZustand(String zustand) {
        this.zustand = zustand;
    }

    String getEingabe() {
        return eingabe;
    }

    void setEingabe(String eingabe) {
        this.eingabe = eingabe;
    }

    @Override
    public int hashCode(){
        return (zustand.hashCode()+eingabe.hashCode())*5;
    }

    @Override
    public boolean equals(Object key){
        return key.hashCode() == this.hashCode();
    }


}
