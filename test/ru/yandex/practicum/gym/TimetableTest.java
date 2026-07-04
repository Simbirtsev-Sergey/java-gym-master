package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {
    static Coach coach;
    static Timetable timetable;

    @BeforeEach
    void beforeEach() {
        coach = new Coach("Васильев", "Николай", "Сергеевич");
        timetable = new Timetable();
    }

    @Test
    void shouldReturnOneTrainingSessionForMonday() {
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);

        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).size());
    }

    @Test
    void shouldReturnZeroTrainingSessionForTuesday() {
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);

        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.TUESDAY, new TimeOfDay(13, 0)).size());
    }

    @Test
    void shouldReturnTrainingSessionsForDayMultipleSessions() {
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);
    }

    @Test
    void shouldReturnOneMultipleSessionForMonday() {
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());

    }

    @Test
    void shouldReturnZeroMultipleSessionForTuesday() {
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.TUESDAY,
                new TimeOfDay(13, 0)).size());
    }

    @Test
    void shouldReturnTwoOrderlyMultipleSessionForThursday() {
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> thursdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        NavigableSet<TimeOfDay> keySet = thursdaySessions.navigableKeySet();

        Assertions.assertEquals(2, keySet.size());
        Assertions.assertEquals(new TimeOfDay(13, 0), keySet.getFirst());
        Assertions.assertEquals(new TimeOfDay(20, 0), keySet.getLast());
    }

    @Test
    void shouldReturnOneMultipleSessionForMondayAt1300() {
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)).size());
    }

    @Test
    void shouldReturnZeroMultipleSessionForMondayAt1400() {
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(14, 0)).size());
    }

    @Test
    void shouldReturnTwoIdenticalMultipleSessionForMondayAt1400() {
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.MONDAY, new TimeOfDay(20, 0));
        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(20, 0));
        timetable.addNewTrainingSession(mondayChildTrainingSession);

        Assertions.assertEquals(2, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(20, 0)).size());

    }

    @Test
    void shouldReturnAEmptyList() {
        Assertions.assertEquals(new ArrayList<>(), timetable.getCountByCoaches());
    }

    @Test
    void shouldReturnASortedListForTrainersWithDifferentTrainingSessionsAmounts() {
        Coach headCoach = new Coach("Петров", "Иван", "Михайлович");
        Coach assistantHeadCoach = new Coach("Иванов", "Сергей", "Георгиевич");

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);

        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, headCoach,
                DayOfWeek.THURSDAY, new TimeOfDay(15, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, assistantHeadCoach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));
        TrainingSession fridayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(17, 0));
        TrainingSession sundayChildTrainingSession = new TrainingSession(groupChild, assistantHeadCoach,
                DayOfWeek.SUNDAY, new TimeOfDay(8, 0));
        TrainingSession wednesdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(19, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);
        timetable.addNewTrainingSession(fridayChildTrainingSession);
        timetable.addNewTrainingSession(wednesdayChildTrainingSession);
        timetable.addNewTrainingSession(sundayChildTrainingSession);

        List<Map.Entry<Coach, Integer>> sortedCoaches = timetable.getCountByCoaches();

        Assertions.assertEquals(coach, sortedCoaches.getFirst().getKey());
        Assertions.assertEquals(3, sortedCoaches.getFirst().getValue());

        Assertions.assertEquals(assistantHeadCoach, sortedCoaches.get(1).getKey());
        Assertions.assertEquals(2, sortedCoaches.get(1).getValue());

        Assertions.assertEquals(headCoach, sortedCoaches.getLast().getKey());
        Assertions.assertEquals(1, sortedCoaches.getLast().getValue());
    }

    @Test
    void shouldReturnASortedListForTrainersWithSameTrainingSessionsAmounts() {
        Coach headCoach = new Coach("Петров", "Иван", "Михайлович");
        Coach assistantHeadCoach = new Coach("Иванов", "Сергей", "Георгиевич");

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);

        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, headCoach,
                DayOfWeek.THURSDAY, new TimeOfDay(15, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, assistantHeadCoach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));
        TrainingSession fridayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(17, 0));
        TrainingSession sundayChildTrainingSession = new TrainingSession(groupChild, assistantHeadCoach,
                DayOfWeek.SUNDAY, new TimeOfDay(8, 0));
        TrainingSession wednesdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(19, 0));
        TrainingSession tuesdayChildTrainingSession = new TrainingSession(groupChild, headCoach,
                DayOfWeek.TUESDAY, new TimeOfDay(6, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);
        timetable.addNewTrainingSession(fridayChildTrainingSession);
        timetable.addNewTrainingSession(wednesdayChildTrainingSession);
        timetable.addNewTrainingSession(sundayChildTrainingSession);
        timetable.addNewTrainingSession(tuesdayChildTrainingSession);

        List<Map.Entry<Coach, Integer>> sortedCoaches = timetable.getCountByCoaches();

        Assertions.assertEquals(coach, sortedCoaches.getFirst().getKey());
        Assertions.assertEquals(3, sortedCoaches.getFirst().getValue());

        Assertions.assertEquals(2, sortedCoaches.get(1).getValue());
        Assertions.assertEquals(2, sortedCoaches.getLast().getValue());

        Assertions.assertEquals(3, sortedCoaches.size());

    }
}