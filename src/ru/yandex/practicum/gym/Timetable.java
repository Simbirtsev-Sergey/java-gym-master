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
        }
        timetable.put(dayOfWeek, sessionsForDay);

        // создаём список занятий для времени, если его ещё нет
        ArrayList<TrainingSession> temp = sessionsForDay.get(timeOfDay);
        if (temp == null) {
            temp = new ArrayList<>();
        }
        sessionsForDay.put(timeOfDay, temp);

        temp.add(trainingSession);
    }

    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.containsKey(dayOfWeek) ? timetable.get(dayOfWeek) : new TreeMap<>();
    }


    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        return timetable.containsKey(dayOfWeek) && timetable.get(dayOfWeek).containsKey(timeOfDay) ?
                timetable.get(dayOfWeek).get(timeOfDay) : new ArrayList<>();
    }

    public List<Map.Entry<Coach, Integer>> getCountByCoaches() {
        Map<Coach, Integer> unsortedListCoach = new HashMap<>();

        for (Map<TimeOfDay, ArrayList<TrainingSession>> value : timetable.values()) {
            for (List<TrainingSession> trainingSessions : value.values()) {
                for (TrainingSession trainingSession : trainingSessions) {
                    Coach coach = trainingSession.getCoach();
                    unsortedListCoach.put(coach, unsortedListCoach.getOrDefault(coach, 0) + 1);
                }
            }
        }


        List<Map.Entry<Coach, Integer>> sortedListCoach = new ArrayList<>();

        while (!unsortedListCoach.isEmpty()) {
            int max = -1;
            Coach coachWithMaxTraining = null;
            for (Coach coach : unsortedListCoach.keySet()) {
                int countTraining = unsortedListCoach.get(coach);
                if (countTraining > max) {
                    max = countTraining;
                    coachWithMaxTraining = coach;
                }
            }
            sortedListCoach.add(new AbstractMap.SimpleEntry<>(coachWithMaxTraining, max));
            unsortedListCoach.remove(coachWithMaxTraining);
        }
        return sortedListCoach;
    }
}
