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
    private static FXDialog main;
    private static Response response;
    
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

    void doInSequential(Animation... an) {
        SequentialTransition st = SequentialTransitionBuilder.create()
                .children(an)
                .cycleCount(1)
                .build();
        st.play();
    }
    
    FXDialog getInstance() {
        return main;
    }
    
    ScaleTransition doScale(double fromX, double fromY, double toX, double toY, double dur) {
        ScaleTransition scale = ScaleTransitionBuilder.create()
                .node(root)
                .duration(Duration.seconds(dur))
                .fromX(fromX)
                .fromY(fromY)
                .toX(toX)
                .toY(toY)
                .build();
        
        scale.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (root.getScaleX() == 0) {
                    System.gc();       
                    primaryStage.close();
                }
            }
        });
        return scale;
    }

    private void showDialog() {
        doInSequential(doScale(0.0, 0.01, 1, 0.01, 0.5), doScale(1, 0.01, 1, 1, 0.5));
        primaryStage.showAndWait();
    }
    
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
    
    public static void showMessageDialog(String message, String title, Dialog messageType) {
        main.replaceScene(DialogType.MESSAGE);

        if (messageType == Dialog.ERROR) {
            MessageDialogController.icon.setImage(new Image("/tabs/icons/" + messageType.getIcon()));
            MessageDialogController.headerPane.setStyle("-fx-background-color: red;");

            primaryStage.setTitle("Error");
        } else if (messageType == Dialog.INFORMATION) {
            MessageDialogController.icon.setImage(new Image("/tabs/icons/" + messageType.getIcon()));
            MessageDialogController.headerPane.setStyle("-fx-background-color: blue;");

            primaryStage.setTitle("Information");
        } else if (messageType == Dialog.WARNING) {
            MessageDialogController.icon.setImage(new Image("/tabs/icons/" + messageType.getIcon()));
            MessageDialogController.headerPane.setStyle("-fx-background-color: orange;");

            primaryStage.setTitle("Warning");
        }
        
        MessageDialogController.lblHeader.setText(title);
        MessageDialogController.lblMsg.setText(message);
  
        main.showDialog();
    }
    
    public static FXDialog showConfirmDialog(String caption, String title, ConfirmationType confirmType) {
        main.replaceScene(DialogType.CONFIRMATION);
        
        if(confirmType == ConfirmationType.DELETE_OPTION) {
            ConfirmationDialogController.btnAccept.setText("Delete");
            ConfirmationDialogController.btnAccept.setStyle("-fx-base: red;");
            ConfirmationDialogController.btnDecline.setText("Don't Delete");  
        }
        else if(confirmType == ConfirmationType.YES_NO_OPTION) {
            ConfirmationDialogController.btnAccept.setText("Yes");
            ConfirmationDialogController.btnAccept.setDefaultButton(true);
            ConfirmationDialogController.btnDecline.setText("No");  
        }
        
        ConfirmationDialogController.lblHeader.setText(title);
        ConfirmationDialogController.lblMsg.setText(caption);
        primaryStage.setTitle("Confirmation");
        main.showDialog();
        
        return main;
    }
    
    void setReponse(Response response) {
        FXDialog.response = response;
    }
    
    public Response getResponse() {
        return response;
    }
}
