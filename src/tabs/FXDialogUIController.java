package tabs;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class FXDialogUIController implements Initializable {
    
    @FXML protected static AnchorPane headerPane;
    @FXML protected static ImageView icon;
    @FXML protected static Label lblHeader;
    @FXML protected static Label lblMsg;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    @FXML private void ok(ActionEvent evt) {
        FXDialog.scaleTransition(1, 1, 0, 0, 0.5);
    }
}
