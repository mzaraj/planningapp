package zarajczyk.marcin.planningapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import zarajczyk.marcin.planningapp.domain.AskMeeting;
import zarajczyk.marcin.planningapp.domain.Hours;
import zarajczyk.marcin.planningapp.domain.Tabl;
import zarajczyk.marcin.planningapp.mapper.TimeTableMapper;
import zarajczyk.marcin.planningapp.repository.TimeTableRepository;
import zarajczyk.marcin.planningapp.vm.MeetingPosibilitiesVm;
import zarajczyk.marcin.planningapp.vm.TablVm;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.MINUTES;

@Slf4j
@Service
@AllArgsConstructor
public class TimeTableServiceImpl implements TimeTableService {

    private TimeTableMapper timeTableMapper;
    private TimeTableRepository timeTableRepository;
    public static final LocalTime TIME_ZERO = LocalTime.parse("00:00");

    @Override
    public Tabl createTimetable(Tabl tabl) {
        Tabl timetableSaved = timeTableRepository.save(tabl);
        log.debug("Created timetable on number", timetableSaved.getId());
        return timetableSaved;
    }

    @Override
    public Optional<TablVm> getTimetableById(Long id) {
        Optional<Tabl> timetableEntity = timeTableRepository.findById(id);
        if(!timetableEntity.isPresent())
            throw new ObjectNotFoundException("", "Not found timetable with " + id + " id");

        Optional<TablVm> timeTableVm = timetableEntity.map(timeTableMapper::toVm);

        return timeTableVm;
    }

    @Override
    public MeetingPosibilitiesVm showMeetingPosibilities(AskMeeting askMeeting) {
        Optional<Tabl> timetableEntityOne = timeTableRepository.findById(askMeeting.getTimetableOne());
        if(!timetableEntityOne.isPresent())
            throw new ObjectNotFoundException("", "Not found timetable with " + askMeeting.getTimetableOne() + " id");

        Optional<Tabl> timetableEntityTwo = timeTableRepository.findById(askMeeting.getTimetableTwo());
        if(!timetableEntityTwo.isPresent())
            throw new ObjectNotFoundException("", "Not found timetable with " + askMeeting.getTimetableTwo() + " id");

        timetableEntityOne = sortTable(timetableEntityOne);
        timetableEntityTwo = sortTable(timetableEntityTwo);

        List<Hours> resultTwoPosibilitiesMeeting = calculatePosibilitiesMeetingComparingTwoTimetables(timetableEntityOne,timetableEntityTwo,askMeeting.getMeetingDuration());
        return new MeetingPosibilitiesVm(resultTwoPosibilitiesMeeting);

    }

    private List<Hours> calculatePosibilitiesMeetingComparingTwoTimetables(Optional<Tabl> timetableEntityOne, Optional<Tabl> timetableEntityTwo, LocalTime meetingDuration) {

        Hours workTime  = new Hours();
        workTime.setStart(chooseGreaterTime(timetableEntityOne.get().getWorkTime().getStart(),timetableEntityTwo.get().getWorkTime().getStart()));
        workTime.setEnd(chooseSmallerTime(timetableEntityOne.get().getWorkTime().getEnd(),timetableEntityTwo.get().getWorkTime().getEnd()));

        List<Hours> findFreeHoursInTableOne = FindFreeHoursInTable(workTime,timetableEntityOne);
        List<Hours> findFreeHoursInTableTwo = FindFreeHoursInTable(workTime,timetableEntityTwo);

        List<Hours> resultMeetingTimes =  compareTablesAndTakeMeetingHoursIncludingMeetingDuring(findFreeHoursInTableOne,findFreeHoursInTableTwo, meetingDuration);
        return resultMeetingTimes;
    }

    private List<Hours> compareTablesAndTakeMeetingHoursIncludingMeetingDuring(List<Hours> findFreeHoursInTableOne, List<Hours> findFreeHoursInTableTwo,LocalTime meetingDuration) {
        List<Hours> compareTwoTimetables = chooseTimeBeweenTwoTImesTable(findFreeHoursInTableOne, findFreeHoursInTableTwo);

        List<Hours> finalPosibilitesToMeet = compareTimeToMeetWithMeetingDuration(compareTwoTimetables,meetingDuration);

        return finalPosibilitesToMeet;
    }

    private List<Hours> compareTimeToMeetWithMeetingDuration(List<Hours> compareTwoTimetables, LocalTime meetingDuration) {
        List<Hours> resultHourseList = new ArrayList<>();
        for (Hours h: compareTwoTimetables) {
            long freeTimeCalculatedToMinutes = MINUTES.between(h.getStart(), h.getEnd());
            long meetingCalculatedToMinutes =  TIME_ZERO.until(meetingDuration, MINUTES);
            if(freeTimeCalculatedToMinutes >= meetingCalculatedToMinutes)
            resultHourseList.add(new Hours(h.getId(), h.getStart(), h.getEnd()));
            }

        return resultHourseList;
    }

    private List<Hours> chooseTimeBeweenTwoTImesTable(List<Hours> findFreeHoursInTableOne, List<Hours> findFreeHoursInTableTwo) {
        List<Hours> compareTwoTimetables = new ArrayList<>();
       int counter =0;
        for (Hours h1:findFreeHoursInTableOne) {
            for (Hours h2: findFreeHoursInTableTwo) {
                LocalTime greaterStartTime = chooseGreaterTime(h1.getStart(),h2.getStart());

                if(counter == 0) {
                    if (greaterStartTime.isBefore(h2.getEnd())) {
                        compareTwoTimetables.add(new Hours(h1.getId(), greaterStartTime, chooseSmallerTime(h1.getEnd(), h2.getEnd())));
                        counter++;
                    }
                }
            }
            counter = 0;
        }
        return compareTwoTimetables;
    }

    private List<Hours> FindFreeHoursInTable(Hours workTime, Optional<Tabl> timetableEntityOne) {
    List<Hours> resultFreeHourse= new ArrayList<>();
        for (Hours h:timetableEntityOne.get().getMeetingTimes()) {
            if (workTime.getStart().isBefore(h.getStart()))
                resultFreeHourse.add(new Hours(h.getId(), workTime.getStart(), h.getStart()));
                workTime.setStart(h.getEnd());
        }
        if(workTime.getStart().isBefore(workTime.getEnd()))
            resultFreeHourse.add(new Hours(Long.valueOf(resultFreeHourse.size()), workTime.getStart(), workTime.getEnd()));

        return resultFreeHourse;
    }


    private Optional<Tabl> sortTable(Optional<Tabl> timetableEntityOne) {
        List<Hours> notSorted = timetableEntityOne.get().getMeetingTimes();
        List<Hours> result2 = notSorted.stream().sorted(Comparator.comparing(Hours::getStart)).collect(Collectors.toList());
        timetableEntityOne.get().setMeetingTimes(result2);
        return timetableEntityOne;
    }

    private LocalTime chooseGreaterTime(LocalTime start, LocalTime start1) {
        if(start.isAfter(start1))
            return start;
        else
            return start1;
    }

    private LocalTime chooseSmallerTime(LocalTime start, LocalTime start1) {
        if (start.isBefore(start1))
            return start;
        else
            return start1;

    }
}
