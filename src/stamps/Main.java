import com.google.gson.Gson;
import com.sun.javafx.scene.traversal.ContainerTabOrder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Définition de la collection d'astres
        CollectionAstres collect = new CollectionAstres();
        //création des models
        ApplicationStamps app = new ApplicationStamps(collect);
        Deplacement deplacement = new Deplacement(primaryStage, app);
        primaryStage.setTitle("Observation Astre");
        // Gestion Graphique
        deplacement.goToGlobale();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
