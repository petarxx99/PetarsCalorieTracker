package com.PetarsCalorieTracker.person.dailymass;


import com.PetarsCalorieTracker.person.personweightloss.PersonWeightLoss;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Entity
@Table(name = "daily_mass")
public class DailyMass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_mass_id")
    private Long id;


    @Column(name = "mass_in_kilograms", nullable = false)
    private Float massInKilograms;


    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_of_persons_mass")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JsonIgnore
    private PersonWeightLoss personWeightLoss;


    public DailyMass(){}

    public DailyMass(Float massInKilograms, LocalDate date, PersonWeightLoss personWeightLoss) {
        this.massInKilograms = massInKilograms;
        this.date = date;
        this.personWeightLoss = personWeightLoss;
    }

    public DailyMass(Long id, float massInKilograms, LocalDate date, PersonWeightLoss personWeightLoss) {
        this.id = id;
        this.massInKilograms = massInKilograms;
        this.date = date;
        this.personWeightLoss = personWeightLoss;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getMassInKilograms() {
        return massInKilograms;
    }

    public void setMassInKilograms(Float massInKilograms) {
        this.massInKilograms = massInKilograms;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public PersonWeightLoss getPersonWeightLoss() {
        return personWeightLoss;
    }

    public void setPersonWeightLoss(PersonWeightLoss personWeightLoss) {
        this.personWeightLoss = personWeightLoss;
    }

    @Override
    public String toString() {
        return "DailyMass{" +
                "id=" + id +
                ", massInKilograms=" + massInKilograms +
                ", date=" + date +
                '}';
    }
}
