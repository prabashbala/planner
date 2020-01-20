package uk.nb.routes.planner.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.nb.routes.planner.data.PlanetInfo;
import uk.nb.routes.planner.data.PlanetInfoRepository;

/**
 * This is to handle planet endpoints
 */
@RestController
public class PlanetsController {

    private final PlanetInfoRepository planetInfoRepository;

    @Autowired
    public PlanetsController(final PlanetInfoRepository planetInfoRepository) {
        this.planetInfoRepository = planetInfoRepository;
    }

    @RequestMapping(value = "getPlanets", method = RequestMethod.GET)
    public Iterable<PlanetInfo> getPlanets() {
        Iterable<PlanetInfo> planetInfoList = planetInfoRepository.findAll();
        return planetInfoList;
    }
}
