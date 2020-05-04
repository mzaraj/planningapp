package zarajczyk.marcin.planningapp.resources;

import io.github.jhipster.web.util.ResponseUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zarajczyk.marcin.planningapp.domain.AskMeeting;
import zarajczyk.marcin.planningapp.domain.Tabl;
import zarajczyk.marcin.planningapp.service.TimeTableService;
import zarajczyk.marcin.planningapp.vm.MeetingPosibilitiesVm;
import zarajczyk.marcin.planningapp.vm.TablVm;
import javax.validation.Valid;
import java.net.URISyntaxException;

@Slf4j
@RestController
@RequestMapping
@AllArgsConstructor
public class TimeTableResources {

    private TimeTableService timeTableService;

    @PostMapping("/putTable")
    public ResponseEntity<Tabl> createTimeTable(@Valid @RequestBody Tabl tabl) throws URISyntaxException {
        Tabl newTabl = timeTableService.createTimetable(tabl);
        System.out.println("moja dodana data to : " + newTabl.toString());
        return ResponseEntity.ok().body(newTabl);
    }

    @PostMapping("/meeting")
    public MeetingPosibilitiesVm showMeetingPosibilities(@Valid @RequestBody AskMeeting askMeeting) throws URISyntaxException {
        MeetingPosibilitiesVm meetingPosibilitiesVm = timeTableService.showMeetingPosibilities(askMeeting);
        System.out.println("moja dodana data to : " + meetingPosibilitiesVm.toString());
        return meetingPosibilitiesVm;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TablVm> getCategory(@PathVariable Long id) {
        log.debug("REST request to get Timetable on ", id);
        return ResponseUtil.wrapOrNotFound(timeTableService.getTimetableById(id));
    }

}
