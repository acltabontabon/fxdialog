
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
        getInstance().doInSequential(doScale(1, 1, 1, 0.01, 0.5), doScale(1, 0.01, 0.0, 0.01, 0.5));
        setReponse(Response.APPROVE);
    }
    
    @FXML private void decline(ActionEvent evt) {
        getInstance().doInSequential(doScale(1, 1, 1, 0.01, 0.5), doScale(1, 0.01, 0.0, 0.01, 0.5));
        setReponse(Response.DECLINE);
    }
}
