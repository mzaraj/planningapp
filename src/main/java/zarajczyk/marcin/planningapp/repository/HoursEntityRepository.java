package zarajczyk.marcin.planningapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zarajczyk.marcin.planningapp.domain.Hours;

@Repository
public interface HoursEntityRepository extends JpaRepository<Hours, Long> {

}
