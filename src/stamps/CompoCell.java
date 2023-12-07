import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;

public class CompoCell extends ListCell<String> {

    public CompoCell(Astre a){
        setStyle("-fx-background-color: #515151; -fx-text-fill: white;");
        this.setOnMouseEntered(ev ->{
            if(!isEmpty()) {
                setStyle("-fx-background-color: #C00000; -fx-text-fill: white;");
            }
        });
        this.setOnMouseExited(ev ->{
            setStyle("-fx-background-color: #515151; -fx-text-fill: white;");
        });
        this.setOnMouseClicked(event ->{
            if (!isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                String itemToRemove = getItem();
                ListView<String> listView = getListView();
                listView.getItems().remove(itemToRemove);
                a.removeTag(itemToRemove);
            }
        }
        );
    }
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
        } else {
            setText(item);
        }
    }
}
