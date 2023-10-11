package com.PetarsCalorieTracker.person.dailymass;

import com.PetarsCalorieTracker.person.dailymass.dailymasscalories.DailyCalories;
import com.PetarsCalorieTracker.person.dailymass.dailymasscalories.DailyMassAndCalories;
import com.PetarsCalorieTracker.person.personweightloss.PersonWeightLoss;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DailyMassService {
    private DailyMassRepository repository;

    @Autowired
    public DailyMassService(DailyMassRepository repository){
        this.repository = repository;
    }

    public void save(DailyMass dailyMass){
        repository.save(dailyMass);
    }

    public void delete(long id){
        repository.deleteById(id);
    }

    public List<DailyCalories> getCaloriesByDayByUsername(String username){
        List<Tuple> tuples = repository.getCaloriesByDayByUsername(username);
        return tuples.stream().map(t ->
                new DailyCalories(
                        t.get(0, BigDecimal.class),
                        t.get(1, java.sql.Date.class).toLocalDate()))
                .collect(Collectors.toList());
    }


    public List<DailyCalories> getCaloriesByDayByUsernameFromDayAToDayB(
            String username,
            LocalDate startDate,
            LocalDate endDate){
        return repository.getCaloriesByDayByUsernameFromDayAToDayB(username, startDate, endDate)
                .stream()
                .map(tuple -> new DailyCalories(
                        tuple.get(0, BigDecimal.class),
                        tuple.get(1, java.sql.Date.class).toLocalDate()
                ))
                .collect(Collectors.toList());
    }

    public List<DailyMassAndCalories> getDailyMassAndCaloriesWhenBothExistByUsername(
            @NonNull String username,
            @NonNull LocalDate startDate,
            @NonNull LocalDate endDate
    ) throws UsernameNotFoundException{
        PersonWeightLoss person = repository.getPersonAndFoodByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username not found: " + username));

        HashMap<Long, DailyMass> dailyMasses = new HashMap<>();

        person.getDailyMassesInKilograms().stream()
                .filter(dm -> (dm.getDate().isEqual(startDate) || dm.getDate().isAfter(startDate)) &&
                        (dm.getDate().isEqual(endDate) || dm.getDate().isBefore(endDate)))
                .forEach(dailyMass -> dailyMasses.put(dailyMass.getDate().toEpochDay(), dailyMass));

        List<DailyCalories> dailyCalories = getCaloriesByDayByUsernameFromDayAToDayB(username, startDate, endDate);

        return dailyCalories.stream()
                .filter(dailyKcal -> massForThatDayExists(dailyKcal.getDate(), dailyMasses))
                .map(dailyKcal -> {
                    long dailyMassesKey = dailyKcal.getDate().toEpochDay();
                    DailyMass dailyMass = dailyMasses.get(dailyMassesKey);

                    float massInKilograms = dailyMass.getMassInKilograms();
                    BigDecimal caloriesConsumed = dailyKcal.getCaloriesConsumed();
                    LocalDate date = dailyMass.getDate();

                    return new DailyMassAndCalories(massInKilograms, caloriesConsumed, date);
                })
                .collect(Collectors.toList());
    }

    private <T> boolean massForThatDayExists(LocalDate date, Map<Long, T> dailyMasses){
        return dailyMasses.containsKey(date.toEpochDay());
    }


    public List<DailyMassAndCalories> getDailyMassAndCaloriesByUsername(
            @NonNull String username,
            @NonNull LocalDate startDate,
            @NonNull LocalDate endDate
    ) throws UsernameNotFoundException{
        PersonWeightLoss person = repository.getPersonAndFoodByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username not found: " + username));

        HashMap<Long, DailyMassAndCalories> dailyMassesAndCalories = new HashMap<>();

        person.getDailyMassesInKilograms().stream()
                .filter(dm -> (dm.getDate().isEqual(startDate) || dm.getDate().isAfter(startDate)) &&
                        (dm.getDate().isEqual(endDate) || dm.getDate().isBefore(endDate)))
                .forEach(dailyMass -> {
                    LocalDate date = dailyMass.getDate();
                    Float massInKilograms = dailyMass.getMassInKilograms();
                    dailyMassesAndCalories.put(date.toEpochDay(), new DailyMassAndCalories(massInKilograms, date));
                });

        List<DailyCalories> dailyCalories = getCaloriesByDayByUsernameFromDayAToDayB(username, startDate, endDate);

        dailyCalories.forEach(dailyKcal -> {
                    BigDecimal caloriesConsumed = dailyKcal.getCaloriesConsumed();
                    LocalDate date = dailyKcal.getDate();

                    long dailyMassesKey = date.toEpochDay();
                    DailyMassAndCalories dailyMassAndKcal = dailyMassesAndCalories.get(dailyMassesKey);

                    if (dailyMassAndKcal == null){
                        dailyMassesAndCalories.put(dailyMassesKey, new DailyMassAndCalories(caloriesConsumed, date));
                    } else {
                        dailyMassAndKcal.setCaloriesConsumed(caloriesConsumed);
                    }
        });

        return dailyMassesAndCalories.values().stream().toList();
    }

}
