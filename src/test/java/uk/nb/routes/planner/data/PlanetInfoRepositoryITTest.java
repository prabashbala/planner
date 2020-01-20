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

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("local")
@TestPropertySource(properties = {
        "amazon.dynamodb.endpoint=http://localhost:8000/",
        "amazon.aws.accesskey=test1",
        "amazon.aws.secretkey=test231"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlanetInfoRepositoryITTest {

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    private PlanetInfoRepository planetInfoRepository;

    private static final String EXPECTED_LOCATION_ID = "NXinS1";
    private static final String EXPECTED_LOCATION = "5";

    @BeforeAll
    public void setup() throws Exception {
        try {
            dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

            CreateTableRequest planetTableRequest = dynamoDBMapper.generateCreateTableRequest(PlanetInfo.class);
            planetTableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

            amazonDynamoDB.createTable(planetTableRequest);

        } catch (ResourceInUseException e) {

        }

        dynamoDBMapper.batchDelete((List<PlanetInfo>) planetInfoRepository.findAll());
    }

    @Test
    public void givenItemWithExpectedLocation_whenRunFindAll_thenItemIsFound() {
        PlanetInfo dave = new PlanetInfo(EXPECTED_LOCATION_ID, EXPECTED_LOCATION);
        PlanetInfo pin = planetInfoRepository.save(dave);

        List<PlanetInfo> result = (List<PlanetInfo>) planetInfoRepository.findAll();

        assertTrue(result.size() > 0, "Not empty");
        assertTrue(result.get(0).getLocationName().equals(EXPECTED_LOCATION), "Contains item with expected location name");
    }

    @Test
    public void givenItemWithExpectedLocation_whenRunFindById_thenItemIsFound() {
        PlanetInfo dave = new PlanetInfo(EXPECTED_LOCATION_ID, EXPECTED_LOCATION);
        PlanetInfo pin = planetInfoRepository.save(dave);

        PlanetInfo result = planetInfoRepository.findById(EXPECTED_LOCATION_ID).get();

        assertTrue(result.getLocationName().equals(EXPECTED_LOCATION), "Contains item with expected location name");
    }
}