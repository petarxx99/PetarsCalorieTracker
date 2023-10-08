package com.PetarsCalorieTracker.person.personbasicinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
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

    public void deleteById(long id){
        repository.deleteById(id);
    }

    public void save(PersonBasicInfo person){
        repository.save(person);
    }

    public int updatePersonsPasswordFindHimById(long id, byte[] newPassword){
        return repository.updatePersonsPasswordFindHimById(id, newPassword);
    }

    public boolean updatePersonsUsernameFindHimById(long id, @NonNull String newUsername){
        Optional<PersonBasicInfo> person = repository.findByUsername(newUsername);
        if (person.isEmpty()){
            repository.updatePersonsUsernameFindHimById(id, newUsername);
            return true;
        } else {
            return false;
        }
    }


    public Optional<PersonBasicInfo> findByUsername(@NonNull String username){
        return repository.findByUsername(username);
    }

    public Optional<PersonBasicInfo> findByEmail(@NonNull String email){
        return repository.findByEmail(email);
    }
    

}
