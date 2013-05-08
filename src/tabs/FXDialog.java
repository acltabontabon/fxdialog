/*
Copyright (c) 2013, Alvin Cris Tabontabon
All rights reserved.
 
Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
 
1. Redistributions of source code must retain the above copyright notice, this
list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice,
this list of conditions and the following disclaimer in the documentation
and/or other materials provided with the distribution.
 
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/


package tabs;

import java.io.*;
import java.util.logging.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.stage.*;

public class FXDialog {

    private double initX;       // X-Coordinate location of the dialog
    private double initY;       // Y-Coordinate location of the dialog
    
    private Parent root;
    private Response response;
    private static FXDialog main;

    protected Stage primaryStage;

    public FXDialog() {
        primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
    }

    protected static FXDialog getInstance() {
        if(main == null) {
            main = new FXDialog();
        }
        return main;
    }
    
    /*
     * The purpose of this method is to change the scene depending on the speciied 
     * type of the dialog will be shown.
     * 
     * @param dialogType
     */
    private void replaceScene(DialogType dialogType) {
        try {
            root = FXMLLoader.load(getClass().getResource(dialogType.getFXML()));

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
        } finally {
            System.gc();
        }
    }
    
    /*
     * The purpose of this method is to retain the selected action in the confirmation
     * dialog.
     * 
     * @param response;
     */
    protected void setReponse(Response response) { this.response = response; }

    public static void showMessageDialog(String message, String title, Message messageType) {
        getInstance().replaceScene(DialogType.MESSAGE);

        if (messageType == Message.ERROR) {
            MessageDialogController.icon.setImage(new Image("/tabs/icons/" + messageType.getIcon()));
            MessageDialogController.headerPane.setStyle("-fx-background-color: red;");
        } else if (messageType == Message.INFORMATION) {
            MessageDialogController.icon.setImage(new Image("/tabs/icons/" + messageType.getIcon()));
            MessageDialogController.headerPane.setStyle("-fx-background-color: blue;");

        } else if (messageType == Message.WARNING) {
            MessageDialogController.icon.setImage(new Image("/tabs/icons/" + messageType.getIcon()));
            MessageDialogController.headerPane.setStyle("-fx-background-color: orange;");
        }
 
        MessageDialogController.lblHeader.setText(title);
        MessageDialogController.lblMsg.setText(message);
        
        getInstance().primaryStage.setTitle(messageType.toString());
        getInstance().primaryStage.showAndWait();
    }

    public static boolean showConfirmDialog(String caption, String title, ConfirmationType confirmType) {
        getInstance().replaceScene(DialogType.CONFIRMATION);
        
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
        
        getInstance().primaryStage.setTitle("CONFIRMATION");
        getInstance().primaryStage.showAndWait();
        
        return (getInstance().response.getValue() ? true : false);
    }
    
    public static String showInputDialog(String caption, String title) {
        getInstance().replaceScene(DialogType.INPUT);
        
        InputDialogController.lblHeader.setText(title);
        InputDialogController.lblMsg.setText(caption);
        
        getInstance().primaryStage.showAndWait();
     
        return (getInstance().response.getValue() ? InputDialogController.inputField.getText().trim() : null);
    }
    
    protected enum Response {

        APPROVE(true),
        DECLINE(false);
        private boolean val;

        private Response(boolean val) {
            this.val = val;
        }

        public boolean getValue() {
            return val;
        }
    }
}
