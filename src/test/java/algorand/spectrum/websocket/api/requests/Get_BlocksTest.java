package algorand.spectrum.websocket.api.requests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Get_BlocksTest {
    Get_Blocks get_blocks;
    String sessionId;
    @BeforeEach
    void setUp(){
        get_blocks = new Get_Blocks();
        sessionId = "sessionId";

    }

    @Test
    void addSession() {
        get_blocks.addSession(sessionId);
        assertTrue(get_blocks.getSessionId().contains(sessionId));
    }

    @Test
    void delSession() {
        get_blocks.addSession(sessionId);
        assertTrue(get_blocks.getSessionId().contains(sessionId));
        get_blocks.delSession(sessionId);
        assertFalse(get_blocks.getSessionId().contains(sessionId));
    }
}