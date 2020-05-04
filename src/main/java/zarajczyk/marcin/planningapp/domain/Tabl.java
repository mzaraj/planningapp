package zarajczyk.marcin.planningapp.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table(name = "Tabl")
@RequiredArgsConstructor
public class Tabl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tablId")
    private Hours workTime;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "tablId")
    private List<Hours> meetingTimes;

}