package com.anish.api.objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class WCobjectDAO {

    private final String INDEX = "bookdata";
    private final String TYPE = "books";

    private RestHighLevelClient restHighLevelClient;

    private ObjectMapper objectMapper;

    public WCobjectDAO( ObjectMapper objectMapper, RestHighLevelClient restHighLevelClient) {
        this.objectMapper = objectMapper;
        this.restHighLevelClient = restHighLevelClient;
    }

    public WCObject insertWCObject(WCObject wcObject){
        @SuppressWarnings("unchecked")
		Map<String, Object> dataMap = objectMapper.convertValue(wcObject, Map.class);
        IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, wcObject.getId().toString())
                .source(dataMap);
        try {
            restHighLevelClient.index(indexRequest);
        } catch(ElasticsearchException e) {
            e.getDetailedMessage();
        } catch (java.io.IOException ex){
            ex.getLocalizedMessage();
        }
        return wcObject;
    }

    public WCObject getWCObjectById(String id){
        GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
        SearchResponse searchResponse = null;
        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder search = new SearchSourceBuilder();
        search.query(QueryBuilders.matchQuery("wildcraftId", "qqq").fuzziness(Fuzziness.ZERO).operator(Operator.AND));
        try {
        	searchResponse = restHighLevelClient.search(searchRequest);
        } catch (java.io.IOException e){
            e.getLocalizedMessage();
        }
        Map<String, Object> sourceAsMap = searchResponse.getHits().getAt(0).getSourceAsMap();
        return objectMapper.convertValue(sourceAsMap, WCObject.class);
    }

}