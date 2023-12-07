/**
 * La fabrique de numéro qui permet de générer les numéros d'étapes et les numéros des astres.
 */
public class FabriqueNumero {
    private int cptAstre ;


    private static FabriqueNumero instance = new FabriqueNumero() ;

    /**
     * @return l'instance de FabriqueNumero
     */
    public static FabriqueNumero getInstances(){

        return instance ;
    }
    private FabriqueNumero() {
        this.cptAstre = 0;
    }
    public int getNumero(){
        return cptAstre++ ;
    }
}