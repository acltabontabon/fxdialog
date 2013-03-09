package tabs;


public enum DialogType {
    CONFIRMATION("ConfirmationDialog.fxml"),
    INPUT("InputDialog.fxml"),
    MESSAGE("MessageDialog.fxml");
    
    private String fxml;
    
    private DialogType(String fxml) {
        this.fxml = fxml;
    }
    
    protected String getFXML() {
        return fxml;
    }
}
