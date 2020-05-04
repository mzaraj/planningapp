package zarajczyk.marcin.planningapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zarajczyk.marcin.planningapp.domain.Tabl;


@Repository
public interface TimeTableRepository extends JpaRepository<Tabl,Long> {
}
