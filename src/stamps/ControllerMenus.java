import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;

import java.io.*;
import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.ResourceBundle;
import com.google.gson.Gson;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

public class ControllerMenus implements Observateur,Initializable  {
    private ApplicationStamps app ;
    @FXML
    private MenuItem delete ;
    @FXML
    private MenuItem importJSON ;
    @FXML
    private MenuItem exportJSON ;
    @FXML
    private MenuItem edit ;
    @FXML
    private MenuItem vue ;
    private Deplacement deplacement ;
    public ControllerMenus(ApplicationStamps app , Deplacement dep){
        this.deplacement = dep ;
        this.app = app ;
        this.app.ajouterObservateur(this);
    }
    @FXML
    private void quitter(){
        Platform.exit();
    }
    @FXML
    private void ecrireJSON() {
        Gson gson = new Gson();
        FileChooser fileChooser = new FileChooser();
        if(deplacement.isDetail()) {
            //Il faut exporter un astres en JSON
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers JSON", "*.json"));
            fileChooser.setInitialFileName(this.app.getAstreCourant().getNom() + ".json");

            File file = fileChooser.showSaveDialog(this.deplacement.getStage());
            if (file != null) {
                // L'emplacement de fichier sélectionné par l'utilisateur
                String filePath = file.getAbsolutePath();
                try (FileWriter writer = new FileWriter(filePath)) {
                    gson.toJson(this.app.getAstreCourant(), writer);
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Exportation impossible");
                    alert.setHeaderText(null);
                    alert.setContentText("L'écriture du fichier JSON a échoué.");
                    alert.showAndWait();
                }
            }
        }
        if(deplacement.isGlobale()){
            //Il faut exporter la collection d'astre en JSON

            System.out.println("exit");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers JSON", "*.json"));
            fileChooser.setInitialFileName("Application.json");

            File file = fileChooser.showSaveDialog(this.deplacement.getStage());
            if (file != null) {
                // L'emplacement de fichier sélectionné par l'utilisateur
                String filePath = file.getAbsolutePath();
                try (FileWriter writer = new FileWriter(filePath)){
                    gson.toJson(this.app.getCollectionTrie(), writer);

                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Exportation impossible");
                    alert.setHeaderText(null);
                    alert.setContentText("L'écriture du fichier JSON a échoué.");
                    alert.showAndWait();
                }
            }
        }

    }
    @FXML
    private void lectureJSON() {
        //Récupération du fichier

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers JSON", "*.json"));

        File selectedFile = fileChooser.showOpenDialog(deplacement.getStage());

        if (selectedFile != null) {
            String chemin = selectedFile.getAbsolutePath();


            //traitement du fichier

            String json = "";
            Gson gson = new Gson();
            try  {
                Reader reader = new FileReader(chemin) ;
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    json += line;
                }
                if (json.contains("{\"listeAstre\":[{")) {
                    //Il s'agit d'une applicaiton
                    CollectionAstres col = gson.fromJson(json, CollectionAstres.class);
                    Iterator<Astre> ite = col.iterator() ;
                    while(ite.hasNext()) {
                        Astre a = ite.next() ;
                        this.app.addAstre(a);
                    }
                }
                else {
                    //Il s'agit d'un Astre
                    Astre a = gson.fromJson(json, Astre.class);
                        if(a.getNom() != "" && a.getDescription() != "" && a.getType() != "" ) { // Vérification si il s'agit bien d'un astre non vide
                            this.app.addAstre(a);
                        }
                }
            } catch (Exception e) {

            }
        }
    }
    @FXML
    private void supprimer(){
        Astre astre = this.app.getAstreCourant() ;
        CollectionAstres collectionAstresTrie = this.app.getCollectionTrie();
        CollectionAstres collectionAstres = this.app.getCollection();
        collectionAstres.supprimer(astre);
        collectionAstresTrie.supprimer(astre);
        this.app.setCollection(collectionAstres);
        this.app.setCollectionTrie(collectionAstresTrie);
        this.deplacement.goToGlobale();
    }
    @Override
    public void reagir() {
        if(deplacement.isDetail()){
            this.delete.setDisable(false);
            this.exportJSON.setDisable(false);
            this.edit.setDisable(false);
            this.vue.setDisable(true);
        }
        if(deplacement.isGlobale()){
            this.edit.setDisable(false);
            this.vue.setDisable(false);
            this.delete.setDisable(true);
            this.importJSON.setDisable(false);
            this.exportJSON.setDisable(false);
            if(app.isEdit()){
                this.edit.setDisable(true);
                this.vue.setDisable(false);
            }else{
                this.edit.setDisable(false);
                this.vue.setDisable(true);
            }
        }
        if(deplacement.isEdit()){
            this.edit.setDisable(true);
            this.vue.setDisable(false);
        }

    }
    @FXML
    private void setEdit(){
        if(deplacement.isGlobale()) {
            this.app.setEdit(true);
        }
        if(deplacement.isDetail()){
            this.deplacement.goToEdit();
        }
    }
    @FXML
    private void setView(){
        if(deplacement.isGlobale()) {
            this.app.setEdit(false);
        }
        if(deplacement.isEdit()){
            this.deplacement.goToDetail();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reagir();
    }
}
