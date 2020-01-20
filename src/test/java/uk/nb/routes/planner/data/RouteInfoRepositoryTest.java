package uk.nb.routes.planner.data;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("local")
@TestPropertySource(properties = {
        "amazon.dynamodb.endpoint=http://localhost:8000/",
        "amazon.aws.accesskey=test1",
        "amazon.aws.secretkey=test231"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RouteInfoRepositoryTest {
    private static final String DESTINATION_PLANET = "1";
    private static final String ROUTE_ID = "2";
    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    private RouteInfoRepository routeInfoRepository;

    @BeforeAll
    public void setup() throws Exception {
        try {
            dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

            CreateTableRequest routeTableRequest = dynamoDBMapper.generateCreateTableRequest(RouteInfo.class);
            routeTableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

            amazonDynamoDB.createTable(routeTableRequest);

        } catch (ResourceInUseException e) {

        }

        dynamoDBMapper.batchDelete((List<RouteInfo>) routeInfoRepository.findAll());
    }

    @Test
    public void givenRouteWithDestination_whenRunFindByDestination_thenItemIsFound() {
        RouteInfo dave = new RouteInfo();
        dave.setRouteId(ROUTE_ID);
        dave.setDestination(DESTINATION_PLANET);
        RouteInfo pin = routeInfoRepository.save(dave);

        List<RouteInfo> result = (List<RouteInfo>) routeInfoRepository.findAllByDestination(DESTINATION_PLANET);

        assertTrue(result.size() > 0, "Not empty");
        assertTrue(result.get(0).getRouteId().equals(ROUTE_ID), "Contains item with expected route id");
    }
}