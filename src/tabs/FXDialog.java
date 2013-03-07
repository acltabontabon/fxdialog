package tabs;

import java.io.*;
import java.util.logging.*;
import javafx.application.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.stage.*;

public class FXDialog extends Application {

    private double initX;
    private double initY;
    
    private static Parent root;
    private static FXDialog main;
    private static Response response;
    protected static Stage primaryStage;
    
    static {
        main = new FXDialog();
    }
    
    public FXDialog() {
        primaryStage = new Stage();

        primaryStage.centerOnScreen();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
    }

    @Override
    public void start(final Stage stage) throws Exception { }
    
    private void replaceScene(DialogType dialog) {
        try {
            root = FXMLLoader.load(getClass().getResource(dialog.getFXML()));
            
            Scene scene = new Scene(root, Color.TRANSPARENT);
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();

            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent me) {
                    initX = me.getScreenX() - primaryStage.getX();
                    initY = me.getScreenY() - primaryStage.getY();
                }
            });

            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent me) {
                    primaryStage.setX(me.getScreenX() - initX);
                    primaryStage.setY(me.getScreenY() - initY);
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(FXDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void setReponse(Response response) { FXDialog.response = response; }
    
    public Response getResponse() { return response; }
    
    public static void showMessageDialog(String message, String title, Dialog messageType) {
        main.replaceScene(DialogType.MESSAGE);

        if (messageType == Dialog.ERROR) {
            MessageDialogController.icon.setImage(new Image("/tabs/icons/" + messageType.getIcon()));
            MessageDialogController.headerPane.setStyle("-fx-background-color: red;");
        } else if (messageType == Dialog.INFORMATION) {
            MessageDialogController.icon.setImage(new Image("/tabs/icons/" + messageType.getIcon()));
            MessageDialogController.headerPane.setStyle("-fx-background-color: blue;");

        } else if (messageType == Dialog.WARNING) {
            MessageDialogController.icon.setImage(new Image("/tabs/icons/" + messageType.getIcon()));
            MessageDialogController.headerPane.setStyle("-fx-background-color: orange;");
        }
        
        primaryStage.setTitle(messageType.toString());
        
        MessageDialogController.lblHeader.setText(title);
        MessageDialogController.lblMsg.setText(message);
  
        primaryStage.showAndWait();
    }
    
    public static FXDialog showConfirmDialog(String caption, String title, ConfirmationType confirmType) {
        main.replaceScene(DialogType.CONFIRMATION);
        
        if(confirmType == ConfirmationType.DELETE_OPTION) {
            ConfirmationDialogController.btnAccept.setText("Delete");
            ConfirmationDialogController.btnDecline.setDefaultButton(true);
            ConfirmationDialogController.btnDecline.requestFocus();
            ConfirmationDialogController.btnDecline.setText("Don't Delete");  
        }
        else if(confirmType == ConfirmationType.YES_NO_OPTION) {
            ConfirmationDialogController.btnAccept.setText("Yes");
            ConfirmationDialogController.btnAccept.setDefaultButton(true);
            ConfirmationDialogController.btnDecline.setText("No");  
        }
        else if(confirmType == ConfirmationType.ACCEPT_DECLINE_OPTION) {
            ConfirmationDialogController.btnAccept.setText("Accept");
            ConfirmationDialogController.btnAccept.setDefaultButton(true);
            ConfirmationDialogController.btnDecline.setText("Decline");  
        }
        
        ConfirmationDialogController.lblHeader.setText(title);
        ConfirmationDialogController.lblMsg.setText(caption);
        
        primaryStage.setTitle("CONFIRMATION");
        primaryStage.showAndWait();
        
        return main;
    }
}
