package tabs;

import java.io.*;
import java.util.logging.*;
import javafx.animation.*;
import javafx.application.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.stage.*;
import javafx.util.*;

public class FXDialog extends Application {

    private double initX;
    private double initY;
    
    private static Parent root;
    private static Stage primaryStage;
    protected static FXDialog main;
    private Response response;
    
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
    public void start(final Stage stage) throws Exception {
        // do nothing...
    }

    protected static void scaleTransition(double fromX, double fromY, double toX, double toY, double dur) {
        ScaleTransition scale = ScaleTransitionBuilder.create()
                .node(root)
                .duration(Duration.seconds(dur))
                .fromX(fromX)
                .fromY(fromY)
                .toX(toX)
                .toY(toY)
                .build();
        scale.play();

        scale.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (root.getScaleX() == 0) {
                    primaryStage.close();
                }
            }
        });
    }

    private void replaceScene(DialogType dialog) {
        try {
            root = FXMLLoader.load(getClass().getResource(dialog.getFXML()));
            
            Scene scene = new Scene(root, Color.TRANSPARENT);
            primaryStage.setScene(scene);

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

    private void showDialog() {
        scaleTransition(0, 0, 1, 1, 0.6);
        primaryStage.showAndWait();
    }

    public static void showMessageDialog(String message, Dialog messageType) {
        main.replaceScene(DialogType.MESSAGE);

        if (messageType == Dialog.ERROR) {
            MessageDialogController.icon.setImage(new Image("/tabs/icons/" + messageType.getIcon()));
            MessageDialogController.headerPane.setStyle("-fx-background-color: red;");
            MessageDialogController.lblHeader.setText("ERROR");

            primaryStage.setTitle("Error");
        } else if (messageType == Dialog.INFORMATION) {
            MessageDialogController.icon.setImage(new Image("/tabs/icons/" + messageType.getIcon()));
            MessageDialogController.headerPane.setStyle("-fx-background-color: blue;");
            MessageDialogController.lblHeader.setText("INFORMATION");

            primaryStage.setTitle("Information");
        } else if (messageType == Dialog.WARNING) {
            MessageDialogController.icon.setImage(new Image("/tabs/icons/" + messageType.getIcon()));
            MessageDialogController.headerPane.setStyle("-fx-background-color: orange;");
            MessageDialogController.lblHeader.setText("WARNING");

            primaryStage.setTitle("Warning");
        }

        MessageDialogController.lblMsg.setText(message);
        
        main.showDialog();
    }
    
    public static FXDialog showConfirmDialog(String caption, ConfirmationType confirmType) {
        main.replaceScene(DialogType.CONFIRMATION);
        
        if(confirmType == ConfirmationType.DELETE_OPTION) {
            ConfirmationDialogController.btnAccept.setText("Delete");
            ConfirmationDialogController.btnAccept.setStyle("-fx-base: red;");
            ConfirmationDialogController.btnDecline.setText("Don't Delete");  
        }
        else if(confirmType == ConfirmationType.YES_NO_OPTION) {
            ConfirmationDialogController.btnAccept.setText("Yes");
            ConfirmationDialogController.btnAccept.setStyle("-fx-base: blue;");
            ConfirmationDialogController.btnDecline.setText("No");  
        }
        
        ConfirmationDialogController.lblMsg.setText(caption);
        main.showDialog();
        
        return main;
    }
    
    public Response getResponse() {
        return response;
    }
    
    protected void setReponse(Response response) {
        this.response = response;
    }
}
