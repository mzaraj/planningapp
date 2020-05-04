package zarajczyk.marcin.planningapp.domain;

import lombok.Data;
import java.time.LocalTime;

@Data
public class AskMeeting {

    private LocalTime meetingDuration;

    private Long timetableOne;

    private Long timetableTwo;
}
