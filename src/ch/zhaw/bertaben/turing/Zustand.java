package ch.zhaw.bertaben.turing;

public class Zustand {

    private String zustand;
    private String eingabe;

    public Zustand(){}

    public Zustand(String zustand, String eingabe){
        this.zustand = zustand;
        this.eingabe = eingabe;
    }

    public String getZustand() {
        return zustand;
    }

    public void setZustand(String zustand) {
        this.zustand = zustand;
    }

    public String getEingabe() {
        return eingabe;
    }

    public void setEingabe(String eingabe) {
        this.eingabe = eingabe;
    }

    @Override
    public int hashCode(){
        return (zustand.hashCode()+eingabe.hashCode())*5;
    }

    @Override
    public boolean equals(Object key){
        if(key.hashCode()==this.hashCode()){
            return true;
        }
        return false;
    }


}
