package tabs;

import java.net.*;
import java.util.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

public class MessageDialogController extends FXDialog implements Initializable {
    
    @FXML static HBox headerPane;
    @FXML static ImageView icon;
    @FXML static Label lblHeader;
    @FXML static Label lblMsg;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    @FXML private void ok(ActionEvent evt) {
        primaryStage.close();
    }
}
