import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Astre implements Iterable, Comparable<Astre>   {

    private int num ;
    private String image ;
    private String nom ;
    private String date ;
    private String type ;
    private  String description ;
    private ArrayList<String> tags ;


    public Astre(String image,String nom,String date,String type,String description,String... tags){
        this.num = FabriqueNumero.getInstances().getNumero();
        this.image = image ;
        this.nom = nom ;
        this.date = date ;
        this.description = description ;
        this.type = type ;
        this.tags = new ArrayList<>() ;
        this.tags.addAll(List.of(tags)) ;
    }
    public Astre(String nom){
        this.num = FabriqueNumero.getInstances().getNumero();
        this.date = "0000-00-00" ;
        this.nom = nom;}
    public Astre(String image,String nom) {
        this.num = FabriqueNumero.getInstances().getNumero();
        this.image = image;
        this.nom = nom;
        this.tags = new ArrayList<>() ;
        this.date = "0000-00-00" ;

    }
    public Astre(String image,String nom,String type) {
        this.num = FabriqueNumero.getInstances().getNumero();
        this.image = image;
        this.nom = nom;
        this.type = type ;
        this.tags = new ArrayList<>() ;
        this.date = "0000-00-00" ;

    }

    public Astre() {
        this.num = FabriqueNumero.getInstances().getNumero();
        this.image = "default.png" ;
        this.nom = "" ;
        this.description = "" ;
        this.tags = new ArrayList<>() ;
        this.date = "0000-00-00" ;
        this.type = "" ;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
    public void addTags(String... tag){
        this.tags.addAll(List.of(tag)) ;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder() ;
        builder.append("nom : ").append(this.getNom())
                .append("\nimage: ").append(this.getImage())
                .append("\ndate: ").append(this.getDate())
                .append("\ntype :").append(this.getType())
                .append("\n Desc :").append(this.getDescription())
                .append("\ntags : ") ;
        for (String tag : this.getTags()){
            builder.append(tag + " ") ;
        }
        return builder.toString();
    }
    public int getNum(){
        return this.num ;
    }

    public void removeTag(String tag){
        this.tags.remove(tag) ;
    }
    @Override
    public Iterator<String> iterator() {
        return this.tags.iterator();
    }
    @Override
    public int compareTo(Astre autreAstre) {
        return this.nom.compareTo(autreAstre.getNom());
    }
}

