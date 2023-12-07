import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerTrie  implements Observateur , Initializable {

    private ApplicationStamps app ;
    private Stage sub ;
    @FXML
    private ListView<String> list ;

    public ControllerTrie(ApplicationStamps app, Stage subWindow) {
        this.app = app ;
        this.sub = subWindow ;
    }

    @Override
    public void reagir() {
        ObservableList<String> items = FXCollections.observableArrayList("1.Trie par nom", "2.Trie par type", "3.Trie par date");
        list.setItems(items);
        MultipleSelectionModel<String> selectionModel = list.getSelectionModel() ;
        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            this.app.trier(newValue) ;
            sub.close();

        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    reagir();
    }
}
