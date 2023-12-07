import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public  class Deplacement {

    private String page;
    private Stage stage ;
    private ApplicationStamps app ;

    public Deplacement(Stage primaryStage,ApplicationStamps app) {
        stage = primaryStage ;
        this.app = app ;
        this.page = "" ;
    }

    public  void goToDetail(){
        this.page = "detail" ;
        this.app.clearObservateur();
        //Lecture du fichier FXML
        FXMLLoader fxmlLoader3 = new FXMLLoader(Deplacement.class.getResource("VueDetail.fxml"));
        fxmlLoader3.setControllerFactory(iC->new ControllerVueDetail(this.app,this));
        FXMLLoader fxmlLoader2 = new FXMLLoader(Deplacement.class.getResource("outils.fxml"));
        fxmlLoader2.setControllerFactory(iC->new ControllerMenus(this.app,this));
        //Ajout de la scene et affichage de la fenêtre
        BorderPane border = new BorderPane() ;
        Scene scene = new Scene(border, 1000, 1000);
        try {
            border.setCenter(fxmlLoader3.load());
            border.setTop(fxmlLoader2.load());
        }catch(IOException e){
            throw new RuntimeException("PB GoToDetail") ;
        }
        this.stage.setScene(scene);

    }
    public  void goToEdit(){
        this.page = "edit" ;
        app.clearObservateur();
        //Lecture du fichier FXML
        FXMLLoader fxmlLoader3 = new FXMLLoader(Deplacement.class.getResource("VueEdition.fxml"));
        fxmlLoader3.setControllerFactory(iC->new ControllerEdition(this.app,this));
        FXMLLoader fxmlLoader2 = new FXMLLoader(Deplacement.class.getResource("outils.fxml"));
        fxmlLoader2.setControllerFactory(iC->new ControllerMenus(this.app,this));
        //Ajout de la scene et affichage de la fenêtre
        BorderPane border = new BorderPane() ;
        Scene scene = new Scene(border, 1000, 1000);
        try {
            border.setCenter(fxmlLoader3.load());
            border.setTop(fxmlLoader2.load());
        }catch(IOException e){
            throw new RuntimeException("PB GoToEdit") ;
        }
        stage.setScene(scene);
    }
    public void goToAdd(){
        this.page = "add" ;
        app.clearObservateur();
        //Lecture du fichier FXML
        FXMLLoader fxmlLoader3 = new FXMLLoader(Deplacement.class.getResource("VueAjoute.fxml"));
        ControllerAdd  control = new ControllerAdd(this.app,this) ;
        fxmlLoader3.setControllerFactory(iC->control);
        FXMLLoader fxmlLoader2 = new FXMLLoader(Deplacement.class.getResource("outils.fxml"));
        fxmlLoader2.setControllerFactory(iC->new ControllerMenus(this.app,this));


        //Ajout de la scene et affichage de la fenêtre
        BorderPane border = new BorderPane() ;

        Scene scene = new Scene(border, 1000, 1000);
        try {
            border.setCenter(fxmlLoader3.load());
            border.setTop(fxmlLoader2.load());
        }catch(IOException e){
            throw new RuntimeException("Probleme goToAdd") ;
        }
        stage.setScene(scene);
    }
    public void goToGlobale(){
        this.page = "globale" ;
        app.clearObservateur();
        //Lecture du fichier FXML
        FXMLLoader fxmlLoader3 = new FXMLLoader(Deplacement.class.getResource("VueGlobale.fxml"));
        ControllerGen control = new ControllerGen(this.app,this) ;
        fxmlLoader3.setControllerFactory(iC->control);

        FXMLLoader fxmlLoader2 = new FXMLLoader(Deplacement.class.getResource("outils.fxml"));
        fxmlLoader2.setControllerFactory(iC->new ControllerMenus(this.app,this));

        //Ajout de la scene et affichage de la fenêtre
        BorderPane border = new BorderPane() ;
        Scene scene = new Scene(border, 1000, 1000);
        try {
            border.setCenter(fxmlLoader3.load());
            border.setTop(fxmlLoader2.load());
        }catch(IOException e){
            throw new RuntimeException("PB goToGlobale") ;
        }
        stage.setScene(scene);
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
    public boolean isGlobale(){
        return getPage().equals("globale") ;
    }
    public boolean isDetail(){
        return getPage().equals("detail") ;
    }
    public boolean isEdit(){
        return getPage().equals("edit") ;
    }
    public boolean isAdd(){
        return getPage().equals("add") ;
    }
}
