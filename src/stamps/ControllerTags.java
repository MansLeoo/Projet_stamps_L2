import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

public class ControllerTags implements Observateur , Initializable {

    @FXML
    private TilePane lstTag ;
    private ApplicationStamps app ;
    private Stage stage ;

    private ArrayList<CheckBox> checkBoxes ;
    public ControllerTags(ApplicationStamps app, Stage subWindow) {
        this.app = app ;
        this.stage = subWindow ;
        this.app.ajouterObservateur(this);
        this.checkBoxes = new ArrayList<>() ;
    }

    @Override
    public void reagir() {
        this.lstTag.getChildren().clear();
        this.checkBoxes.clear();
        System.out.println("reagir tags");
        ArrayList<String> tags = this.app.getTags();
        Iterator<String> iterator = tags.iterator() ;
        while (iterator.hasNext()){
            String tag = iterator.next();
            CheckBox check = new CheckBox(tag);
            checkBoxes.add(check) ;
            this.lstTag.getChildren().add(check) ;
        }
    }
    @FXML
    private void rechercher(){
        ArrayList<String> lstTag = new ArrayList<>()  ;
        // Ajout des tags dans la liste de tag
        for (int i = 0 ; i < checkBoxes.size() ; i++){
            if (checkBoxes.get(i).isSelected()) {
                lstTag.add(checkBoxes.get(i).getText().replaceAll("[\\[\\]]", "")) ;
            }
        }
        if(lstTag.isEmpty()){
            this.app.setCollectionTrie(this.app.getCollection());
        }
        else {
            this.app.setTrie(true);
            this.app.recherche(lstTag);
        }
        this.stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reagir();
    }
}
