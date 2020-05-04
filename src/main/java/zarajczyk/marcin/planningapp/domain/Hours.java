package zarajczyk.marcin.planningapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "hours")
public class Hours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalTime start;

    @NotNull
     private LocalTime end;


}
