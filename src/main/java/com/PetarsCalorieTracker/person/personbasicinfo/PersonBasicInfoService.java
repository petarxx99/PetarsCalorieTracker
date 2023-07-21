package com.PetarsCalorieTracker.person.personbasicinfo;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


public class PersonBasicInfoService {

    private final PersonBasicInfoRepository repository;

    @Autowired
    public PersonBasicInfoService(PersonBasicInfoRepository repository){
        this.repository = repository;
    }


    public Optional<PersonBasicInfo> getPersonBasedOnId(long id) throws Exception{
        if (id < 0){
            throw new Exception("Invalid id");
        }

        return repository.findById(id);
    }

    

}
