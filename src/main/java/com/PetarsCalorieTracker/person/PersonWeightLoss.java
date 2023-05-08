package com.PetarsCalorieTracker.person;

import com.PetarsCalorieTracker.food.ConsumedFoodQuantity;
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
    private short heightInCentimeters;

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

    public PersonWeightLoss(@NonNull PersonBasicInfo personBasicInfo, short heightInCentimeters)
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

    public short getHeightInCentimeters() {
        return heightInCentimeters;
    }

    public void setHeightInCentimeters(short heightInCentimeters) {
        this.heightInCentimeters = heightInCentimeters;
    }
}
