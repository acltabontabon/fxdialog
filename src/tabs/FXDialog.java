package tabs;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.ScaleTransition;
import javafx.animation.ScaleTransitionBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class FXDialog extends Application {

    private double initX;
    private double initY;
    
    private static Parent root;
    private static Stage primaryStage;
    
    public FXDialog(String message, Dialog dialogType) throws IOException {
        primaryStage = new Stage();

        root = FXMLLoader.load(getClass().getResource("FXDialogUI.fxml"));
        Scene scene = new Scene(root, Color.TRANSPARENT);

        primaryStage.centerOnScreen();
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();

        scaleTransition(0, 0, 1, 1, 0.6);
        
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
        
        if (dialogType == Dialog.ERROR) {
            FXDialogUIController.icon.setImage(new Image("/tabs/icons/" + dialogType.getIcon()));
            FXDialogUIController.headerPane.setStyle("-fx-background-color: red;");
            FXDialogUIController.lblHeader.setText("ERROR");
        }
        else if (dialogType == Dialog.INORMATION) {
            FXDialogUIController.icon.setImage(new Image("/tabs/icons/" + dialogType.getIcon()));
            FXDialogUIController.headerPane.setStyle("-fx-background-color: blue;");
            FXDialogUIController.lblHeader.setText("INFORMATION");
        }
        else if (dialogType == Dialog.WARNING) {
            FXDialogUIController.icon.setImage(new Image("/tabs/icons/" + dialogType.getIcon()));
            FXDialogUIController.headerPane.setStyle("-fx-background-color: orange;");
            FXDialogUIController.lblHeader.setText("WARNING");
        }
        
        FXDialogUIController.lblMsg.setText(message);
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

    public static void showMessageDialog(String message, Dialog dialogType) {    
        try {
            new FXDialog(message, dialogType);
        } catch (IOException ex) {
            Logger.getLogger(FXDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
