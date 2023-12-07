import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The type Collection astres.
 */
public class CollectionAstres implements Iterable {


    /**
     * The Liste astre.
     */
    private ArrayList<Astre> listeAstre;

    /**
     * Instantiates a new Collection astres.
     */
    public CollectionAstres(){
        this.listeAstre = new ArrayList<>() ;
    }

    /**
     * Add astre.
     *
     * @param astre un astre
     */
    public void addAstre(Astre astre){
        this.listeAstre.add(astre) ;
    }

    /**
     * Add astres.
     *
     * @param astres des astres
     */
    public void addAstres(Astre... astres){
        this.listeAstre.addAll(List.of(astres));
    }



    /**
     * Get astre.
     *
     * @param i l'indice de l'astre
     * @return un astre
     */
    public Astre get(int i){
        return this.listeAstre.get(i) ;
    }
    public Iterator<Astre> iterator(){
        return listeAstre.iterator() ;
    }

    /**
     * Permet de récupérer la taille de la collection
     *
     * @return la taille de la collection
     */
    public int taille(){
        return this.listeAstre.size() ;
    }

    /**
     * Trie.
     *
     * @param newValue the new value
     */
    public void trie(String newValue) {
        if(newValue.contains("1")){
            //Il s'agit du trie par nom
            Collections.sort(this.listeAstre);

        }
        if(newValue.contains("2")){
            ArrayList<Astre> newList = new ArrayList<>() ;
            //Il s'agit du trie par type
            ArrayList<String> lstType = new ArrayList<>() ;
            Iterator<Astre> ite = this.iterator() ;
            while(ite.hasNext()){
                Astre a = ite.next() ;
                if(!lstType.contains(a.getType())){
                    lstType.add(a.getType()) ;
                }
            }
            for (int i = 0; i < lstType.size(); i++) {
                ite = this.iterator();
                while (ite.hasNext()) {
                    Astre astre = ite.next();
                    if (astre.getType().equals(lstType.get(i))) {
                        newList.add(astre);
                    }
                }
            }
                this.listeAstre.clear();
                this.listeAstre.addAll(newList) ;

        }
        if(newValue.contains("3")){
            //Il s'agit du trie par date
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Collections.sort(this.listeAstre, Comparator.comparing(astre -> {
                try {
                    return dateFormat.parse(astre.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                    return null;
                }
            }));
        }
        Collections.reverse(listeAstre);
    }

    /**
     * Fonction qui vérifie su la collction contient un astre.
     * @param a Un astre
     * @return un boolean
     */
    public boolean contient(Astre a){
        return this.listeAstre.contains(a) ;
    }

    /**
     * Supprime un astre de la collection
     * @param a Un Astre
     */
    public void supprimer(Astre a) {
        this.listeAstre.remove(a) ;
    }
}
