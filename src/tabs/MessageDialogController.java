package tabs;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

public class MessageDialogController extends FXDialog {
    
    @FXML static HBox headerPane;
    @FXML static ImageView icon;
    @FXML static Label lblHeader;
    @FXML static Label lblMsg;
    
    @FXML private void ok(ActionEvent evt) {
        getInstance().primaryStage.close();
    }
}
