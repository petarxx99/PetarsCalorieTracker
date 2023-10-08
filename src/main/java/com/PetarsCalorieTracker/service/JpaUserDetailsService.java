package com.PetarsCalorieTracker.service;

import com.PetarsCalorieTracker.person.personbasicinfo.PersonBasicInfoService;
import com.PetarsCalorieTracker.person.security.users.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class JpaUserDetailsService implements UserDetailsService {


    private final PersonBasicInfoService personBasicInfoService;

    public JpaUserDetailsService(PersonBasicInfoService personBasicInfoService){
        this.personBasicInfoService = personBasicInfoService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return personBasicInfoService.findByUsername(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("No user with username " + username));
    }


}
