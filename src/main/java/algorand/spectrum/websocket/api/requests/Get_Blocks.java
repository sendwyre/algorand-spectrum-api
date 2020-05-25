package algorand.spectrum.websocket.api.requests;

import java.util.ArrayList;
import java.util.List;

public class Get_Blocks {
    private List<String> sessionId;

    public Get_Blocks() {
        sessionId = new ArrayList();
        }
     public void addSession(String sessionId){
        this.sessionId.add(sessionId);
     }
     public void delSession(String sessionId){
        this.sessionId.remove(sessionId);
     }

    public List getSessionId() {
        return sessionId;
    }

    public void setSessionId(List sessionId) {
        this.sessionId = sessionId;
    }
}
