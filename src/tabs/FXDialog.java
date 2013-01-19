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
    
    static {
        main = new FXDialog();
    }
    
    public FXDialog() {
        try {
            primaryStage = new Stage();

            root = FXMLLoader.load(getClass().getResource("FXDialogUI.fxml"));
            Scene scene = new Scene(root, Color.TRANSPARENT);

            primaryStage.centerOnScreen();
            primaryStage.setScene(scene);
            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.initStyle(StageStyle.TRANSPARENT);

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

    private void showDialog() {
        primaryStage.show();
        scaleTransition(0, 0, 1, 1, 0.6);
    }

    public static void showMessageDialog(String message, Dialog dialogType) {
        main.showDialog();

        if (dialogType == Dialog.ERROR) {
            MessageDialogController.icon.setImage(new Image("/tabs/icons/" + dialogType.getIcon()));
            MessageDialogController.headerPane.setStyle("-fx-background-color: red;");
            MessageDialogController.lblHeader.setText("ERROR");

            primaryStage.setTitle("Error");
        } else if (dialogType == Dialog.INORMATION) {
            MessageDialogController.icon.setImage(new Image("/tabs/icons/" + dialogType.getIcon()));
            MessageDialogController.headerPane.setStyle("-fx-background-color: blue;");
            MessageDialogController.lblHeader.setText("INFORMATION");

            primaryStage.setTitle("Information");
        } else if (dialogType == Dialog.WARNING) {
            MessageDialogController.icon.setImage(new Image("/tabs/icons/" + dialogType.getIcon()));
            MessageDialogController.headerPane.setStyle("-fx-background-color: orange;");
            MessageDialogController.lblHeader.setText("WARNING");

            primaryStage.setTitle("Warning");
        }

        MessageDialogController.lblMsg.setText(message);

    }
}
