
package tabs;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class ConfirmationDialogController extends MessageDialogController {

    @FXML static Button btnAccept;
    @FXML static Button btnDecline;
    
    @FXML protected void accept(ActionEvent evt) {
        getInstance().setReponse(Response.APPROVE);
        getInstance().primaryStage.close();
    }
    
    @FXML protected void decline(ActionEvent evt) {
        getInstance().setReponse(Response.DECLINE);
        getInstance().primaryStage.close();
    }
}
