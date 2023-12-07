import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Compteur {
    private IntegerProperty valeur ;
    public Compteur() {
        this.valeur = new SimpleIntegerProperty() ;
    }
    public void incrementer() {
        valeur.setValue(valeur.intValue()+1);
    }
    public IntegerProperty getPropertyValue() {
        return valeur ;
    }
    public int getValue() { return valeur.intValue();}
    public void reset(){this.valeur.setValue(0);}
}