import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * The type Application stamps.
 */
public class ApplicationStamps extends SujetObserve{

    /**
     * La collection d'astre
     */
    private CollectionAstres collection ;

    /**
     * La collection d'astre trie
     */
    private CollectionAstres collectionTrie ;

    /**
     * L'Astre courant.
     */
    private Astre astreCourant ;
    /**
     * La variable du mode edition
     */
    private boolean edit ;
    /**
     * La liste des tags
     */
    private ArrayList<String> tags ;
    /**
     * Variable de trie
     */
    private boolean trie ;

    /**
     * Instantiates a new Application stamps.
     *
     * @param collection the collection
     */
    public ApplicationStamps(CollectionAstres collection){
        this.collection = collection ;
        this.trie = false ;
        this.collectionTrie = collection ;
        if (collection.taille()  != 0) {
            this.astreCourant = collection.get(0);
        }
        this.tags = new ArrayList<>() ;
        this.ajouterTags();
    }

    /**
     * Gets collection.
     *
     * @return the collection
     */
    public CollectionAstres getCollection() {
        return collection;
    }

    /**
     * Sets collection.
     *
     * @param collection une collection
     */
    public void setCollection(CollectionAstres collection) {
        this.collection = collection;
    }

    /**
     * Gets collection trie.
     *
     * @return La collection trie
     */
    public CollectionAstres getCollectionTrie() {
        return collectionTrie;
    }

    /**
     * La collection trie.
     *
     * @param collectionTrie une collection d'Astre
     */
    public void setCollectionTrie(CollectionAstres collectionTrie) {
        this.collectionTrie = collectionTrie;
        this.notifierObservateur();
    }

    /**
     * Gets astre courant.
     *
     * @return L 'astre courant
     */
    public Astre getAstreCourant() {
        return astreCourant;
    }

    /**
     * Sets astre courant.
     *
     * @param astreCourant un astre
     */
    public void setAstreCourant(Astre astreCourant) {
        this.astreCourant = astreCourant;
        this.notifierObservateur();
    }

    /**
     * Is edit boolean.
     *
     * @return the boolean
     */
    public boolean isEdit() {
        return edit;
    }

    /**
     * Sets edit.
     *
     * @param edit the edit
     */
    public void setEdit(boolean edit) {
        this.edit = edit;
        this.notifierObservateur();

    }



    /**
     * Get astre.
     *
     * @param i l'indice de l'astre
     * @return l 'astre a l'indice i
     */
    public Astre get(int i){
        return this.collectionTrie.get(i) ;
    }


    /**
     * Add astre.
     *
     * @param astre un astre
     */
    public void addAstre(Astre astre){
        this.collection.addAstre(astre); ;
        this.notifierObservateur();
        this.ajouterTags() ;
    }

    /**
     * Add astres.
     *
     * @param astres un tableau d'astre
     */
    public void addAstres(Astre... astres){
        this.collectionTrie.addAstres(astres);
        this.collection.addAstres(astres);
        this.notifierObservateur();
        this.ajouterTags() ;
    }

    /**
     * Ajouter tags.
     */
    private void ajouterTags(){
            //Récupération de tous les tags
            CollectionAstres collection = this.collection ;
            Iterator<Astre> ite = collection.iterator() ;
            while(ite.hasNext()){
                Astre a = ite.next() ;
                Iterator<String> astreIte = a.iterator() ;
                while (astreIte.hasNext()) {
                    String tag = astreIte.next();
                    if (!this.tags.contains(tag)) {
                        this.tags.add(tag);
                    }
                }
            }
    }

    /**
     * Ajouter tag.
     *
     * @param tag un tag
     */
    public void ajouterTag(String tag){
        this.tags.add(tag) ;
        this.notifierObservateur();
    }


    /**
     * Gets tags.
     *
     * @return the tags
     */
    public ArrayList<String> getTags() {
        return this.tags ;
    }

    /**
     * Supprimer l'astre de la collection.
     *
     * @param a un Astre
     */
    public void supprimer(Astre a) {
        this.collectionTrie.supprimer(a) ;
        this.notifierObservateur();
    }

    /**
     * Trier.
     *
     * @param val La valeur pour le tri
     */
    public void trier(String val) {
        this.collectionTrie.trie(val);
        this.notifierObservateur();
    }


    /**
     * Recherche les astres qui contennant un tag présent dans la liste de tags
     *
     * @param lstTag la liste de tags
     */
    public void recherche(ArrayList<String> lstTag) {
        CollectionAstres newCollection = new CollectionAstres() ;
        Iterator<Astre> ite = this.collectionTrie.iterator() ;
        //Parcourt de la collction
        while(ite.hasNext()){
            Astre a = ite.next() ;
            for(String tag : lstTag){
                if(a.getTags().contains(tag) && !newCollection.contient(a)){ // si l'astre contient l'astre et la nouvelle collection ne le comptient pas
                    //On évite les doublons
                    newCollection.addAstre(a);
                }
            }
        }
        this.collectionTrie = newCollection ;
        this.notifierObservateur();
    }

    /**
     * Is tri boolean.
     *
     * @return the boolean
     */
    public boolean isTrie() {
        return trie;
    }

    /**
     * Sets trie.
     *
     * @param trie the trie
     */
    public void setTrie(boolean trie) {
        this.trie = trie;
    }
}
