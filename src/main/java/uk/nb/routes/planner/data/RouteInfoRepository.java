package uk.nb.routes.planner.data;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

@EnableScan
public interface RouteInfoRepository extends CrudRepository<RouteInfo, String> {
    public Iterable<RouteInfo> findAllByDestination(String destination);

}
