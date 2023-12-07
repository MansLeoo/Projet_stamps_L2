import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class ControllerAdd implements Initializable , Observateur {
    private ApplicationStamps app ;
    @FXML
    private ImageView view ;
    @FXML
    private TextField nom ;
    @FXML
    private TextField type ;
    @FXML
    private DatePicker date ;
    @FXML
    private TextField description ;
    @FXML
    private TilePane tileCheck ;
    @FXML
    private TextField tag ;

    private CollectionAstres collectionAstres ;
    private String img ;
    private ArrayList<CheckBox> checkBoxes ;
    private Deplacement deplacement ;
    public ControllerAdd(ApplicationStamps app,Deplacement deplacement){
        this.app = app ;
        this.collectionAstres = app.getCollectionTrie() ;
        this.checkBoxes = new ArrayList<>() ;
        this.app.ajouterObservateur(this);
        this.deplacement = deplacement ;
    }
    @FXML
    private void selectImg() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(deplacement.getStage());
        if (selectedFile != null) {
            try {
                copieResource(selectedFile.getAbsolutePath(), selectedFile.getName());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String fileName = selectedFile.getName();
            URL url = null;
            try {
                url = new URL("file:///tmp/stamps/" + fileName);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            if (url != null) {
                InputStream inputStream = null;
                try {
                    inputStream = url.openStream();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Image image = new Image(inputStream);
                this.img = url.toString();
                this.view.setImage(image);
            } else {
                System.err.println("Le fichier " + fileName + " n'a pas été trouvé.");
            }
        }
    }

    @FXML
    private void AjouterAstre()  {
        ArrayList<String> lstTag = new ArrayList<>()  ;
        // Ajout des tags dans la liste de tag
        for (int i = 0 ; i < checkBoxes.size() ; i++){
            if (checkBoxes.get(i).isSelected()) {
                lstTag.add(checkBoxes.get(i).getText().replaceAll("[\\[\\]]", "")) ;
                System.out.println(checkBoxes.get(i).getText());
            }
        }
        Astre astre = new Astre() ;//(this.img,this.nom.getText(),this.date.getValue().toString(),this.type.getText(),this.description.getText(),lstTag.toArray(new String[0])) ;
        if(this.img != null) astre.setImage(this.img);
        astre.setNom(this.nom.getText());
        astre.setType(this.type.getText());
            if (this.date.getValue() != null) {
                astre.setDate(this.date.getValue().toString());
            } else {
                astre.setDate("0000-00-00");
            }
            astre.setTags(lstTag);
            astre.setDescription(this.description.getText());
            this.collectionAstres.addAstre(astre);
            deplacementVueGlobale();
    }
    private  void copieResource(String sourcePathStr,String nom) throws IOException {
        Path sourcePath = Paths.get(sourcePathStr);
        System.out.println(sourcePath);
        //Path destinationPath = Paths.get("resources/"+nom);
        try {
            Path path = Files.createDirectories(Paths.get("/tmp/stamps"));
            Path destinationPath = Paths.get("/tmp/stamps/" + nom);
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {

        }
    }
        private void deplacementVueGlobale() {
            this.deplacement.goToGlobale();
        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("init");
        reagir();
    }
    public void reagir(){
        this.tileCheck.getChildren().clear();
        this.checkBoxes.clear();
        System.out.println("Reagir ajouter");
        ArrayList<String> tags = this.app.getTags();
        Iterator<String> iterator = tags.iterator() ;
        while (iterator.hasNext()){
            String tag = iterator.next();
            CheckBox check = new CheckBox(tag) ;
            this.tileCheck.getChildren().add(check) ;
            checkBoxes.add(check) ;
        }


    }
    @FXML
    private void ajouterTag(){
        if(verifTag()) {
            this.app.ajouterTag(this.tag.getText());
        }
    }
    private boolean verifTag(){
        String tag = this.tag.getText() ;
        if (!this.app.getTags().contains(tag)){
            if (!tag.replace(" ", "").equals("")){
                return true ;
            }
        }
        return false ;
    }
}
