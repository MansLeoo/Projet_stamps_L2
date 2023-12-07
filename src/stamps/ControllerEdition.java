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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

/**
 * The type Controller edition.
 */
public class ControllerEdition implements Initializable , Observateur {
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
    private TextField nom ;
    /**
     * The Type.
     */
    @FXML
    private TextField type ;
    /**
     * The Date.
     */
    @FXML
    private DatePicker date ;
    /**
     * The Description.
     */
    @FXML
    private TextField description ;
    /**
     * The Tile check.
     */
    @FXML
    private TilePane tileCheck ;
    /**
     * The Tag.
     */
    @FXML
    private TextField tag ;

    /**
     * The Collection astres.
     */
    private CollectionAstres collectionAstres ;
    /**
     * The Img.
     */
    private String img ;
    /**
     * The Check boxes.
     */
    private  ArrayList<CheckBox> checkBoxes ;
    private Deplacement deplacement ;

    /**
     * Instantiates a new Controller edition.
     *
     * @param app         the app
     * @param deplacement the deplacement
     */
    public ControllerEdition(ApplicationStamps app,Deplacement deplacement) {
        this.app = app ;
        this.collectionAstres = app.getCollectionTrie() ;
        this.checkBoxes = new ArrayList<>() ;
        this.deplacement = deplacement ;
        this.app.ajouterObservateur(this);
    }


    @FXML
    private void selectImg(){
            FileChooser fileChooser = new FileChooser();

            File selectedFile = fileChooser.showOpenDialog(deplacement.getStage());

            if (selectedFile != null) {
                try {
                    copieResource(selectedFile.getAbsolutePath(), selectedFile.getName());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                String fileName = selectedFile.getName() ;
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
                    this.img =  url.toString() ;
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
            }
        }
        Astre astre = this.app.getAstreCourant() ;//(this.img,this.nom.getText(),this.date.getValue().toString(),this.type.getText(),this.description.getText(),lstTag.toArray(new String[0])) ;
        if(this.img != null) astre.setImage(this.img);
        astre.setNom(this.nom.getText());
        astre.setType(this.type.getText());
        if(this.date.getValue() != null)astre.setDate(this.date.getValue().toString());
        astre.setTags(lstTag);
        astre.setDescription(this.description.getText());
        deplacementVueGlobale();
    }

    /**
     * Copie le fichier dans le répertoire ressource.
     * @param sourcePathStr le chemin du fichier
     * @param nom   le nom du fichier
     * @throws IOException the io exception
     */
    private  void copieResource(String sourcePathStr,String nom) throws IOException {
        Path sourcePath = Paths.get(sourcePathStr);
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
        Astre courant = this.app.getAstreCourant() ;
        this.nom.setText(courant.getNom());
        this.type.setText(courant.getType());
        this.description.setText(courant.getDescription());
        this.date.setPromptText(courant.getDate());
        this.view.setImage(new Image(courant.getImage()));
        reagir();
    }
    public void reagir(){
        //Remplissage des Input
        this.tileCheck.getChildren().clear();
        this.checkBoxes.clear();
        Astre courant = this.app.getAstreCourant() ;
        Iterator<String> iterator = this.app.getTags().iterator() ;
        while (iterator.hasNext()){
            String tag = iterator.next();
            CheckBox check = new CheckBox(tag) ;
            if (courant.getTags().contains(tag)){
                check.setSelected(true);
            }
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
            if (!tag.replace("", "").equals("")){
                return true ;
            }
        }
        return false ;
    }
}
