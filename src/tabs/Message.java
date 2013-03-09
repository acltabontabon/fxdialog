package tabs;

public enum Message {

    ERROR("error.png"),
    INFORMATION("information.png"),
    WARNING("warning.png");
    private String ico;

    private Message(String ico) {
        this.ico = ico;
    }

    protected String getIcon() {
        return ico;
    }
}
