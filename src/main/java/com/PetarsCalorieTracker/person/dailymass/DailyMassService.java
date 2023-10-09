package com.PetarsCalorieTracker.person.dailymass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return repository.getCaloriesByDayByUsername(username);
    }

    public List<DailyCalories> getCaloriesByDayById(long id){
        return repository.getCaloriesByDayById(id);
    }

}
