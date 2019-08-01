package eos.websocket.api;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ElasticSearchPublisher {
    private static final transient Logger logger = LoggerFactory.getLogger(ElasticSearchPublisher.class);

    private RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
            RestClient.builder(
                    new HttpHost("es-test.eostribe.io", 9200, "http")));



    private BulkProcessor bulkProcessor = BulkProcessor.builder(
             restHighLevelClient,
            new BulkProcessor.Listener() {
                @Override
                public void beforeBulk(long executionId,
                                       BulkRequest bulkRequest) {
                    logger.info("bulk request numberOfActions:" + bulkRequest.numberOfActions());
                }

                @Override
                public void afterBulk(long executionId,
                                      BulkRequest bulkRequest,
                                      BulkResponse bulkResponse) {
                    logger.info("bulk response has failures: " + bulkResponse.hasFailures());

                }

                @Override
                public void afterBulk(long executionId,
                                      BulkRequest request,
                                      Throwable failure) {
                    logger.warn("bulk failed: " + failure);

                }
            })
            .setBulkActions(20000)
            .setGlobalType("_doc")
            .setBulkSize(new ByteSizeValue(25, ByteSizeUnit.MB))
            .setFlushInterval(TimeValue.timeValueSeconds(5))
            .setConcurrentRequests(5)
            .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
            .build();


    public void pubActions(ArrayList<JSONObject> actions){

        this.bulkProcessor.add(new IndexRequest("test-mainet-actions").source(""));
    }

}


