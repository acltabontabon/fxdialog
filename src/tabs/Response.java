package tabs;

public enum Response {
    APPROVE(0),
    DECLINE(1);
    
    private int i;
    
    private Response(int i) {
        this.i = i;
    }
}
