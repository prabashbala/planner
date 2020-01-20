package uk.nb.routes.planner.data;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface PlanetInfoRepository extends CrudRepository<PlanetInfo, String> {
}