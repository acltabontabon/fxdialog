
package tabs;

import java.net.*;
import java.util.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class ConfirmationDialogController extends MessageDialogController implements Initializable {

    @FXML static Button btnAccept;
    @FXML static Button btnDecline;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML private void accept(ActionEvent evt) {
        setReponse(Response.APPROVE);
        primaryStage.close();
    }
    
    @FXML private void decline(ActionEvent evt) {
        setReponse(Response.DECLINE);
        primaryStage.close();
    }
}
