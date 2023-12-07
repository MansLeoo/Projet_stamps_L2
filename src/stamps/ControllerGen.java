
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

public class ControllerGen implements Observateur , Initializable {
    private ApplicationStamps app;
    @FXML
    private TilePane tile;
    private CollectionAstres astres;
    private Stage stage;
    @FXML
    private BorderPane topPane ;
    private ArrayList<ImageView> lstImg;
    private Compteur compt ;
    @FXML
    private TextField field ;
    @FXML
    private Button but1 ;
    @FXML
    private Button recherche ;
    @FXML
    private Pane topBox ;
    @FXML
    private Label nbAstres ;
    private Deplacement deplacement ;
    private Rectangle rectangle ;

    @FXML
    private Button reset ;
    public ControllerGen(ApplicationStamps app,Deplacement dep) {
        this.app = app;
        this.astres = app.getCollection();
        this.stage = dep.getStage();
        this.lstImg = new ArrayList<>();
        this.deplacement = dep ;
        this.app.setCollectionTrie(app.getCollection());
        this.app.ajouterObservateur(this);
        this.compt = new Compteur() ;
        this.app.setTrie(false);
    }




    public void goTodetail() {
        this.deplacement.goToDetail();
    }

    public void goToAdd() {
        this.deplacement.goToAdd();
    }

    public void goToEdit() {
        this.deplacement.goToEdit();
    }



    @FXML
    private void tags(){
        Stage subWindow = new Stage();
        subWindow.initOwner(deplacement.getStage());
        subWindow.initModality(Modality.WINDOW_MODAL);
        subWindow.initStyle(StageStyle.UTILITY);
        subWindow.setTitle("Recherche par tag");


        FXMLLoader fxmlLoader3 = new FXMLLoader(Deplacement.class.getResource("tags.fxml"));
        ControllerTags control = new ControllerTags(app,subWindow) ;
        fxmlLoader3.setControllerFactory(ic->control);
        BorderPane border = new BorderPane();
        try {
            border.setCenter(fxmlLoader3.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        subWindow.setScene(new Scene(border));
        subWindow.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nbAstres.textProperty().bind(compt.getPropertyValue().asString());
        Color colcent = Color.web("#6d6d6d") ;
        Color col2 = Color.web("#343333") ;
        field.setStyle("-fx-background-color:#7a7a7a;-fx-border-color:#8a8a8a; -fx-border-width: 2px ;-fx-border-style: solid;");
        topPane.setStyle("-fx-border-width: 2px ;-fx-border-style: solid;");
        RadialGradient gradient = new RadialGradient(20,0, 500, 0, 500, false,
                CycleMethod.NO_CYCLE, new Stop(0, colcent), new Stop(0.5, col2));
        topPane.setBackground(new Background(new BackgroundFill(gradient, null, null)));
        ImageView view1 = new ImageView(new Image("sort.png")) ;
        view1.setFitHeight(50);
        view1.setFitWidth(50);
        but1.setStyle("-fx-background-color:#414141;-fx-border-color:#8a8a8a; -fx-border-width: 2px ;-fx-border-style: solid;");
        but1.setText("");
        but1.setGraphic(view1);
        ImageView view2 = new ImageView(new Image("loupe.png")) ;
        view2.setFitHeight(20);
        view2.setFitWidth(20);
        recherche.setText("");
        recherche.setGraphic(view2) ;

        reagir();
    }
    @Override
    public void reagir() {
        //Gestion du bouton supprimer filtre
        if(this.app.isTrie()) {
            reset.setVisible(true);
        }
        else{
            reset.setVisible(false);
        }
        compt.reset(); // reset le compteur d'astre
        this.astres = this.app.getCollectionTrie() ;


        //Mise a jour des composants Graphique

        this.tile.getChildren().clear();
        this.lstImg.clear();
        for (int i = 0; i < astres.taille(); i++) {
            //Pour chaque Astre
            compt.incrementer();
            Astre a = astres.get(i);
            //Mise en place de l'image
            Image img = new Image(a.getImage());
            ImageView imageView = new ImageView(img);
            //Mise en place du Menu
            ContextMenu contextMenu = new ContextMenu();
            MenuItem menuItem1 = new MenuItem("Supprimer");
            MenuItem menuItem2 = new MenuItem("Editer");
            menuItem1.setOnAction(ev -> {
                this.app.supprimer(a) ;
            });
            menuItem2.setOnAction(ev -> {
                this.app.setAstreCourant(a);
                goToEdit();
            });
            contextMenu.getItems().addAll(menuItem1, menuItem2);

            // Associez le menu contextuel à l'ImageView
            imageView.setOnContextMenuRequested(event -> {
                contextMenu.show(imageView, event.getScreenX(), event.getScreenY());
            });
            BorderPane imgWrap = new BorderPane();
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);
            //Gestion du tooltip et du changement de couleur
            Tooltip tooltip = new Tooltip(a.getNom() + " : " + a.getType());
            Tooltip.install(imageView, tooltip);
            imgWrap.setOnMouseEntered(e -> {

                if (!this.app.isEdit()) {
                    imgWrap.setStyle("-fx-border-color:#3EFF00;");
                } else {
                    imgWrap.setStyle("-fx-border-color:#00FFF3;");
                }
            });
            imgWrap.setOnMouseExited(e -> {

                imgWrap.setStyle("-fx-border-color:#FF0000;");
            });
            imgWrap.setCenter(imageView);
            imgWrap.setStyle("-fx-border-color:#FF0000; -fx-border-width: 2px ;-fx-border-style: solid;\n");

            this.tile.getChildren().add(imgWrap);
            imgWrap.setOnMouseClicked(ic -> {
                if (ic.getButton() == MouseButton.PRIMARY) {
                    if(!app.isEdit()) {
                        app.setAstreCourant(a);
                        goTodetail();
                    }else{
                        app.setAstreCourant(a);
                        goToEdit();
                    }
                }
            });
            lstImg.add(imageView);
        }
        ImageView imageView = new ImageView(new Image("plus.png"));
        BorderPane imgWrap = new BorderPane();
        imgWrap.setCenter(imageView);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imgWrap.setOnMouseClicked(ic -> {
            if (ic.getButton() == MouseButton.PRIMARY) {
                goToAdd() ;
            }
        });
        this.tile.getChildren().add(imgWrap);
        imgWrap.setStyle("-fx-border-color:#FF0000; -fx-border-width: 2px ;-fx-border-style: solid;\n");
        imgWrap.setOnMouseEntered(e -> {

            imgWrap.setStyle("-fx-border-color:#3EFF00;");

        });
        imgWrap.setOnMouseExited(e -> {

            imgWrap.setStyle("-fx-border-color:#FF0000;");
        });


    }

    /**
     * Fonction qui gere l'animation du bouton de trie
     */
    @FXML
    private void animeBout(){
        this.rectangle = new Rectangle(20,20,Color.RED) ;
        this.rectangle.setOnMouseClicked(ev -> {
            Stage subWindow = new Stage();
            subWindow.initOwner(deplacement.getStage());
            subWindow.initModality(Modality.WINDOW_MODAL);
            subWindow.initStyle(StageStyle.UTILITY);
            subWindow.setTitle("Trie");

            Button closeButton = new Button("Close");
            closeButton.setOnAction(event -> subWindow.close());

            StackPane subRoot = new StackPane();
            subRoot.getChildren().add(closeButton);
            FXMLLoader fxmlLoader3 = new FXMLLoader(Deplacement.class.getResource("trie.fxml"));
            ControllerTrie control = new ControllerTrie(app,subWindow) ;
            fxmlLoader3.setControllerFactory(ic->control);
            BorderPane border = new BorderPane();
            try {
                border.setCenter(fxmlLoader3.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            subWindow.setScene(new Scene(border));
            subWindow.show();

        });
        this.rectangle.setOpacity(0.3);
        this.rectangle.setX(this.topBox.getWidth()/2-10);
        this.rectangle.setY(this.topBox.getHeight()/2-10);

        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(1000));
        scaleTransition.setNode(this.rectangle);

        scaleTransition.setByY(1.5);
        scaleTransition.setByX(1.5);

        scaleTransition.setCycleCount(1);

        scaleTransition.setAutoReverse(false);
        scaleTransition.play();
        topBox.getChildren().add(this.rectangle);
    }
    @FXML
    private void removeRect(){

        topBox.getChildren().remove(rectangle) ;
        this.rectangle.setOpacity(0);
    }
    @FXML
    private void recherche(){
        CollectionAstres newCollection = new CollectionAstres() ;
        String name = this.field.getText() ;
        if(name.length() > 0) {
            Iterator<Astre> ite = this.app.getCollection().iterator();
            while (ite.hasNext()) {
                Astre a = ite.next();
                if (a.getNom().toLowerCase().contains(name.toLowerCase()) && !newCollection.contient(a)) {
                    newCollection.addAstre(a);
                }
            }
            this.app.setTrie(true);
        }else {
            newCollection = this.app.getCollection() ;
        }
        this.app.setCollectionTrie(newCollection);
    }
    @FXML
    private void suprimerFiltre(){
        this.app.setTrie(false);
        this.app.setCollectionTrie(this.app.getCollection());
    }
}


