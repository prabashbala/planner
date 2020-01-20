package uk.nb.routes.planner.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.nb.routes.planner.data.RouteInfo;
import uk.nb.routes.planner.data.RouteInfoRepository;

import java.util.Optional;

/**
 * This controller is to handle routes endpoints
 */

@RestController
public class RoutesController {

    private final RouteInfoRepository routeInfoRepository;

    @Autowired
    public RoutesController(final RouteInfoRepository routeInfoRepository) {
        this.routeInfoRepository = routeInfoRepository;
    }

    /**
     * This will return the route for given route id
     *
     * @param routeId route id for the search
     * @return route details
     */
    @RequestMapping(value = "getRoute/{routeId}", method = RequestMethod.GET)
    public RouteInfo getRoute(@PathVariable("routeId") String routeId) {
        Optional<RouteInfo> routeInfoList = routeInfoRepository.findById(routeId);
        return routeInfoList.get();
    }

    /**
     * This will return the list of routes for destination
     *
     * @param planetId planet id for the route search
     * @return list of routes available for that planet
     */
    @RequestMapping(value = "getRouteByDestination/{planetId}", method = RequestMethod.GET)
    public Iterable<RouteInfo> getRouteByDestination(@PathVariable("planetId") String planetId) {
        Iterable<RouteInfo> routeInfoList = routeInfoRepository.findAllByDestination(planetId);
        return routeInfoList;
    }

}
