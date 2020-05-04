package zarajczyk.marcin.planningapp.service;

import zarajczyk.marcin.planningapp.domain.AskMeeting;
import zarajczyk.marcin.planningapp.domain.Tabl;
import zarajczyk.marcin.planningapp.vm.MeetingPosibilitiesVm;
import zarajczyk.marcin.planningapp.vm.TablVm;

import java.util.Optional;

public interface TimeTableService {
     public Tabl createTimetable(Tabl tabl);

    Optional<TablVm> getTimetableById(Long id);

    MeetingPosibilitiesVm showMeetingPosibilities(AskMeeting askMeeting);
}
