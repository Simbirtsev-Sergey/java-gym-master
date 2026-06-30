package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    // расписание
    private HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable;

    Timetable() {
        timetable = new HashMap<>();
    }


    public void addNewTrainingSession(TrainingSession trainingSession) {
        // сохраняем занятие в расписании
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        // создаём внутренний TreeMap для дня, если его ещё нет
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> sessionsForDay = timetable.get(dayOfWeek);
        if (sessionsForDay == null) {
            sessionsForDay = new TreeMap<>();
            timetable.put(dayOfWeek, sessionsForDay);
        }

        // создаём список занятий для времени, если его ещё нет
        ArrayList<TrainingSession> temp = sessionsForDay.get(timeOfDay);
        if (temp == null) {
            temp = new ArrayList<>();
            sessionsForDay.put(timeOfDay, temp);
        }

        temp.add(trainingSession);
    }

    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek);
    }


    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        return timetable.containsKey(dayOfWeek) ? timetable.get(dayOfWeek).get(timeOfDay) : new ArrayList<>();
    }
}
