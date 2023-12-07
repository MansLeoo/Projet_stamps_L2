import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Controller vue detail.
 */
public class ControllerVueDetail implements  Observateur , Initializable {
    /**
     * The App.
     */
    private ApplicationStamps app ;


    /**
     * The View.
     */
    @FXML
    private ImageView view ;
    /**
     * The Nom.
     */
    @FXML
    private Label nom ;
    /**
     * The Type.
     */
    @FXML
    private Label type ;
    /**
     * The Date.
     */
    @FXML
    private Label date ;
    /**
     * The Lst view.
     */
    @FXML
    private ListView lstView;
    /**
     * The Area.
     */
    @FXML
    private TextArea area ;
    /**
     * The Gauche.
     */
    @FXML
    private Pane gauche ;
    /**
     * The Droite.
     */
    @FXML
    private Pane droite ;
    /**
     * The Mid.
     */
    @FXML
    private Pane mid ;
    /**
     * The Circle.
     */
    private Circle circle ;
    /**
     * The Deplacement.
     */
    private Deplacement deplacement ;

    /**
     * Instantiates a new Controller vue detail.
     *
     * @param app         l'application
     * @param deplacement le gestionnaire de deplacement
     */
    public ControllerVueDetail(ApplicationStamps app, Deplacement deplacement){

        this.deplacement = deplacement ;
        this.app = app ;
        this.app.ajouterObservateur(this);
        circle = new Circle(20, Color.GREY);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    this.reagir();

    }

    /**
     * Go to globale.
     */
    @FXML
    private void goToGlobale(){
        this.deplacement.goToGlobale();
    }

    /**
     * Lance la vue detail de l'astre suivant dans la collection triée.
     */
    @FXML
    private void goToSuivant(){
        Astre a = app.getAstreCourant() ;
        int num = a.getNum() + 1 ;
        if (num >= this.app.getCollectionTrie().taille()){
            num = 0 ;
        }
        app.setAstreCourant(app.get(num));
        this.deplacement.goToDetail();
    }

    /**
     * Lance la vue detail de l'astre precedent dans la collection triée
     */
    @FXML
    private void goToPrecedent(){
        Astre a = app.getAstreCourant() ;

        int num = a.getNum() - 1 ;
        if (num < 0){
            num = this.app.getCollectionTrie().taille() - 1;
        }
        app.setAstreCourant(app.get(num));
        this.deplacement.goToDetail();
    }

    @Override
    public void reagir() {

        Astre astre = this.app.getAstreCourant() ;
        int i = 0 ;
        int j = 0 ;
        this.nom.setText(astre.getNom());
        this.type.setText(astre.getType());
        this.date.setText(astre.getDate());
        this.area.setText(astre.getDescription());
        Image img = new Image(astre.getImage());
        this.view.setImage(img);
        ObservableList<String> items = FXCollections.observableArrayList() ;

        for (String tag : astre.getTags()) {
            items.add(tag) ;
        }
        lstView.setCellFactory(listView -> new CompoCell(astre));
        lstView.setItems(items);
        lstView.setStyle("-fx-background-color: lightgray;-fx-text-fill: white;");

    }

    /**
     * Fonction qui permet de lancer l'animation sur le bouton situé à droite
     */
    @FXML
    private void animeBoutDroite(){
        this.circle = new Circle(20,Color.RED) ;
        this.circle.setOnMouseClicked(ev -> {
            goToSuivant();
        });
        this.circle.setOpacity(0.3);
        this.circle.setCenterX(droite.getWidth()/2);
        this.circle.setCenterY(droite.getHeight()/2);
         ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(1000));
        scaleTransition.setNode(this.circle);
        scaleTransition.setByY(1.5);
        scaleTransition.setByX(1.5);
        scaleTransition.setCycleCount(1);
        scaleTransition.setAutoReverse(false);
        scaleTransition.play();
        droite.getChildren().add(this.circle);
    }

    /**
     * Fonction qui permet de lancer l'animation sur le bouton du millieu
     */
    @FXML
    private void animeBoutMid(){
        this.circle = new Circle(20,Color.RED) ;
        this.circle.setOnMouseClicked(ev -> {
            goToGlobale();
        });
        this.circle.setOpacity(0.3);
        this.circle.setCenterX(mid.getWidth()/2);
        this.circle.setCenterY(mid.getHeight()/2);
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(1000));
        scaleTransition.setNode(this.circle);

        scaleTransition.setByY(1.5);
        scaleTransition.setByX(1.5);

        scaleTransition.setCycleCount(1);

        scaleTransition.setAutoReverse(false);
        scaleTransition.play();
        mid.getChildren().add(this.circle);
    }

    /**
     * Fonction qui permet de lancer l'animation sur le bouton gauche
     */
    @FXML
    private void animeBoutGauche(){
        this.circle = new Circle(20,Color.RED) ;
        this.circle.setOnMouseClicked(ev -> {
            goToPrecedent();
        });
        this.circle.setOpacity(0.3);
        this.circle.setCenterX(gauche.getWidth()/2);
        this.circle.setCenterY(gauche.getHeight()/2);
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(1000));
        scaleTransition.setNode(this.circle);

        scaleTransition.setByY(1.5);
        scaleTransition.setByX(1.5);

        scaleTransition.setCycleCount(1);

        scaleTransition.setAutoReverse(false);
        scaleTransition.play();
        gauche.getChildren().add(this.circle);
    }

    /**
     * Remove circle.
     */
    @FXML
    private void removeCircle(){

        droite.getChildren().remove(circle) ;
        this.circle.setOpacity(0);
        this.circle.setRadius(20);
        this.circle.setFill(Color.RED);
    }
}
