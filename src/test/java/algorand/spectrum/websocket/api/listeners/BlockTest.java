package algorand.spectrum.websocket.api.listeners;

import algorand.spectrum.websocket.api.Rule;
import algorand.spectrum.websocket.api.TxType;
import algorand.spectrum.websocket.api.publishers.TransactionsPublisher;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.web.WebAppConfiguration;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@WebAppConfiguration
class BlockTest {
    @Autowired
    RedisTemplate<String,Object> redisMyTemplate;
    @Autowired
    Block block;
    Rule rule = new Rule();
    TransactionsPublisher transactionsPublisher;
    String icnomingBlock = "{\"reward\":122637,\"txnRoot\":\"WRS2VL2OQ5LPWBYLNBCZV3MEQ4DACSRDES6IUKHGOWYQERJRWC5A\",\"previousBlockHash\":\"LLPMYWPN6I7KZIUBJPDIQNW6N4YDN4JKQLN5U4RZDJOQVZUPXE3A\",\"period\":0,\"seed\":\"NL6XO2SBNFFXHI7YLEWDN73MKI4KBILB75ZBTMIXLN4TDST7GXIQ\",\"proposer\":\"E2EIMPLDISONNZLXONGMC33VBYOIBC2R7LVOS4SYIEZYJQK6PYSAPQL7LQ\",\"nextProtocolVoteBefore\":6708531,\"nextProtocol\":\"https://github.com/algorandfoundation/specs/tree/e5f565421d720c6f75cdd186f7098495caf9101f\",\"upgradeApprove\":false,\"currentProtocol\":\"https://github.com/algorandfoundation/specs/tree/8096e2df2da75c3339986317f9abe69d4fa86b4b\",\"upgradePropose\":\"\",\"round\":6791326,\"rate\":26000001,\"nextProtocolSwitchOn\":6848531,\"nextProtocolApprovals\":10000,\"hash\":\"OWG5SMGEKATFRIGGCYEGOI4ZYBM4KSAWL2J4QKH5YGQF5CMTEIBA\",\"frac\":1264914814,\"txns\":{},\"timestamp\":1590070043}";

    @Autowired
    public void setTransactionsPublisher(TransactionsPublisher transactionsPublisher){
        this.transactionsPublisher = transactionsPublisher;
    }
    @BeforeEach
    void setUp() {
        rule = new Rule();
        rule.setTrxAccount("trxAccount");
        rule.setTxType(TxType.pay);
        rule.setWebsocketSessionId(new ArrayList<>());
        rule.addWebsocketSessionId("sessionId");
        redisMyTemplate.opsForValue().set("trxAccount", rule);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void handleMessage() {
    }
    @Test
    void getRulesList() throws JsonProcessingException {
        Rule ruleRedis = (Rule) redisMyTemplate.opsForValue().get("trxAccount");
        assertEquals(ruleRedis, rule);

//        Rule rule = new ObjectMapper().readValue(str, Rule.class);
//        assertEquals("rule", rule);
    }
    @Test
    void processBlock(){
        transactionsPublisher.publish(new Gson().toJson(rule));
    }

    @Test
    void getTxAccounts() {
        block.getTransactions(icnomingBlock.replace("\\",""));
    }
}