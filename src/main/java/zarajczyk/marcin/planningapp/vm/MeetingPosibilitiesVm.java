package zarajczyk.marcin.planningapp.vm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import zarajczyk.marcin.planningapp.domain.Hours;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class MeetingPosibilitiesVm {

    List<Hours> meetingPosibilities;

}
