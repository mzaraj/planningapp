package zarajczyk.marcin.planningapp.vm;

import lombok.Data;
import zarajczyk.marcin.planningapp.domain.Hours;
import java.util.List;

@Data
public class TablVm {

    Long Id;

    Hours workTime;

    List<Hours> meetingTimes;
}
