package com.PetarsCalorieTracker.person.dailymass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DailyMassService {
    private DailyMassRepository repository;

    @Autowired
    public DailyMassService(DailyMassRepository repository){
        this.repository = repository;
    }


}
