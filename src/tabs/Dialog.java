package tabs;

public enum Dialog {
    
    ERROR("error.png"),
    INFORMATION("information.png"),
    WARNING("warning.png");
    
    private String ico;
    
    private Dialog(String ico) {
        this.ico = ico;
    }
    
    protected String getIcon() {
        return ico;
    }
}
