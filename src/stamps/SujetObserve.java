import java.util.ArrayList;

/**
 * Un sujet Observe qui permet de faire réagir des observateurs
 */
public class SujetObserve {
    private ArrayList<Observateur> list ;

    /**
     * Constructeur d'un sujet Observe
     */
    public SujetObserve(){
        this.list = new ArrayList<>() ;
    }

    /**
     * Fonction qui ajoute un observateur
     * @param obs l'observateur a ajouter
     */
    public void ajouterObservateur(Observateur obs){
        this.list.add(obs) ;
    }
    /**
     * Fonction qui supprime un observateur
     * @param obs l'observateur a supprimer
     */
    public void supprimerObservateur(Observateur obs){
        this.list.remove(obs) ;
    }

    /**
     * Fonction qui vide la liste des observateur
     */
    public void clearObservateur(){
        this.list.clear();
    }
    /**
     * Fonction qui fais réagir les observateur lié au sujetObserve
     */
    public void notifierObservateur(){
        ArrayList<Observateur> lst = new ArrayList<>() ;
        lst.addAll(this.list) ;
        for (Observateur obs : lst){
            obs.reagir() ;
        }
    }
}

