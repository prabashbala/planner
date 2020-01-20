package uk.nb.routes.planner.endpoint;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.nb.routes.planner.data.PlanetInfo;
import uk.nb.routes.planner.data.PlanetInfoRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlanetsControllerTest {
    private PlanetInfoRepository planetInfoRepository;
    private PlanetsController classUnderTest;
    private Fixture fixture;

    @BeforeAll
    public void setUp() {
        planetInfoRepository = Mockito.mock(PlanetInfoRepository.class);
        classUnderTest = new PlanetsController(planetInfoRepository);
        fixture = new Fixture();
    }

    @Test
    public void getPlanets_returnPlanets() {
        fixture.givenWeHavePlanetData();
        fixture.whenGetPlanetIsCalled();
        fixture.thenPlanetsReturned();
    }

    private class Fixture {
        private static final String LOCATION_ID = "NXB1";
        private static final String LOCATION_NAME = "Next Bus";
        private Iterable<PlanetInfo> planets;

        public void givenWeHavePlanetData() {
            List<PlanetInfo> planetsList = new ArrayList<>();
            planetsList.add(new PlanetInfo(LOCATION_ID, LOCATION_NAME));
            when(planetInfoRepository.findAll()).thenReturn(planetsList);
        }

        public void whenGetPlanetIsCalled() {
            planets = classUnderTest.getPlanets();
        }

        public void thenPlanetsReturned() {
            assertTrue(((Collection<?>) planets).size() > 0, "has items");
        }
    }
}