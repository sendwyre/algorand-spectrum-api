package algorand.spectrum.websocket.api;

import algorand.spectrum.websocket.api.Rule;
import algorand.spectrum.websocket.api.TxType;
import algorand.spectrum.websocket.api.listeners.Block;
import algorand.spectrum.websocket.api.publishers.TransactionsPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@WebAppConfiguration
class BlockTest {
    @Autowired
    RedisTemplate<String,Object> redisMyTemplate;
    @Autowired
    Block block;
    @Autowired
    TransactionsPublisher transactionsPublisher;
    Rule rule = new Rule();
    String icnomingBlock = "{\"reward\":122637,\"txnRoot\":\"WRS2VL2OQ5LPWBYLNBCZV3MEQ4DACSRDES6IUKHGOWYQERJRWC5A\"," +
            "\"previousBlockHash\":\"LLPMYWPN6I7KZIUBJPDIQNW6N4YDN4JKQLN5U4RZDJOQVZUPXE3A\",\"period\":0,\"seed\":" +
            "\"NL6XO2SBNFFXHI7YLEWDN73MKI4KBILB75ZBTMIXLN4TDST7GXIQ\",\"proposer\":\"E2EIMPLDISONNZLXONGMC33VBYOIBC2R7LVOS4SYIEZYJQK6PYSAPQL7LQ\"," +
            "\"nextProtocolVoteBefore\":6708531,\"nextProtocol\":" +
            "\"https://github.com/algorandfoundation/specs/tree/e5f565421d720c6f75cdd186f7098495caf9101f\"," +
            "\"upgradeApprove\":false,\"currentProtocol\":" +
            "\"https://github.com/algorandfoundation/specs/tree/8096e2df2da75c3339986317f9abe69d4fa86b4b\"," +
            "\"upgradePropose\":\"\",\"round\":6791326,\"rate\":26000001,\"nextProtocolSwitchOn\":6848531," +
            "\"nextProtocolApprovals\":10000,\"hash\":\"OWG5SMGEKATFRIGGCYEGOI4ZYBM4KSAWL2J4QKH5YGQF5CMTEIBA\"," +
            "\"frac\":1264914814,\"txns\":{\"transactions\":[" +
            "{\"type\":\"pay\",\"tx\":\"MDCCBPUCCCLHNNPZTMFMPW3TTZSZQEJAOPANYMDSOKNZNAD4HDTQ\"," +
            "\"from\":\"ZY6BN6YYKBPQHRIWKJPAKUWUHITBZ5TMIURC7IXUJ7QM6VFRGDGA3QB7N4\"," +
            "\"fee\":1000,\"first-round\":6636794,\"last-round\":6636894,\"round\":6636797," +
            "\"payment\":{\"to\":\"A2KHY4YNUEWSIFGHOVFP6QSU5NAS53LGIOYJDMINM4LH3JNA7DAISIAPYU\"," +
            "\"amount\":10000000000,\"torewards\":0,\"closerewards\":0}," +
            "\"fromrewards\":4356254,\"genesisID\":\"mainnet-v1.0\"," +
            "\"genesishashb64\":\"wGHE2Pwdvd7S12BL5FaOP20EGYesN73ktiC1qzkkit8=\"}," +
            "{\"type\":\"pay\",\"tx\":\"N2AQHEEZZCJB5MZTPKMHG33QLMVSCGC5CTFGZT7PTCJ436WBQZKQ\"," +
            "\"from\":\"COVIDR5MYE757XMDCFOCS5BXFF4SKD5RTOF4RTA67F47YTJSBR5U7TKBNU\"," +
            "\"fee\":1000,\"first-round\":6636795,\"last-round\":6637795," +
            "\"noteb64\":\"gqFk3gAQol90pnJlcG9ydKJfdqExp2NvbnNlbnTDomdhFKJnY6JVU6JncqJDQaJnc6Fmo2d6cKM5MzOibXr/onE0w6NxZG7Do3Fkc6oyMDIwLTAzLTE4onFsAaJxegGic3r/onR6/6Fz2SxmTFpYN1NvTDY5VEdDK0JLeDkxS3huVGdJejJCU0dTQTVGRjlIMGRqNFBRPQ==\"," +
            "\"round\":6636797,\"payment\":{\"to\":\"COVIDR5MYE757XMDCFOCS5BXFF4SKD5RTOF4RTA67F47YTJSBR5U7TKBNU\"," +
            "\"amount\":0,\"torewards\":0,\"closerewards\":0}," +
            "\"fromrewards\":0,\"genesisID\":\"mainnet-v1.0\"," +
            "\"genesishashb64\":\"wGHE2Pwdvd7S12BL5FaOP20EGYesN73ktiC1qzkkit8=\"}]},\"timestamp\":1590070043}";

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