package com.PetarsCalorieTracker.person.personweightloss;

import com.PetarsCalorieTracker.food.consumedfoodquantity.ConsumedFoodQuantity;
import com.PetarsCalorieTracker.person.DailyMass;
import com.PetarsCalorieTracker.person.personbasicinfo.PersonBasicInfo;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.springframework.lang.NonNull;

import java.util.Set;

@Entity
@Table(name = "people_weight_loss")
public class PersonWeightLoss{

    @Id
    @Column(name = "basic_persons_info_id")
    private Long id;


    @Column(name = "height_in_centimeters")
    private Short heightInCentimeters;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "basic_persons_info_id", referencedColumnName = "person_id")
    private PersonBasicInfo personBasicInfo;


    @OneToMany(mappedBy = "personWeightLoss", fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Set<DailyMass> dailyMassesInKilograms;

    @OneToMany(mappedBy = "personWeightLoss", fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Set<ConsumedFoodQuantity> consumedFoodQuantities;



    public PersonWeightLoss(){}

    public PersonWeightLoss(PersonBasicInfo personBasicInfo, Short heightInCentimeters)
    {
        this.personBasicInfo = personBasicInfo;
        this.heightInCentimeters = heightInCentimeters;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonBasicInfo getPersonBasicInfo() {
        return personBasicInfo;
    }

    public void setPersonBasicInfo(PersonBasicInfo personBasicInfo) {
        this.personBasicInfo = personBasicInfo;
    }

    public Set<DailyMass> getDailyMassesInKilograms() {
        return dailyMassesInKilograms;
    }

    public void setDailyMassesInKilograms(Set<DailyMass> dailyMassesInKilograms) {
        this.dailyMassesInKilograms = dailyMassesInKilograms;
    }

    public Set<ConsumedFoodQuantity> getConsumedFoodQuantities() {
        return consumedFoodQuantities;
    }

    public void setConsumedFoodQuantities(Set<ConsumedFoodQuantity> consumedFoodQuantities) {
        this.consumedFoodQuantities = consumedFoodQuantities;
    }

    public Short getHeightInCentimeters() {
        return heightInCentimeters;
    }

    public void setHeightInCentimeters(Short heightInCentimeters) {
        this.heightInCentimeters = heightInCentimeters;
    }


    @Override
    public String toString() {
        return "PersonWeightLoss{" +
                 personBasicInfo.toString() +
                "}";
    }
}
