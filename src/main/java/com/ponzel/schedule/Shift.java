package com.ponzel.schedule;

import lombok.Data;

import javax.persistence.*;
import java.util.Random;

@Data
@Entity
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int day;

    @Enumerated(EnumType.STRING)
    @Column(name = "shift_type")
    private TypeOfShift type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public enum TypeOfShift{
        SUPER_EARLY,
        EARLY,
        LATE,
        DAY_OFF
    }

    public static TypeOfShift generateTypeOfShift(){
        Random random = new Random();
        int randomInt = random.nextInt(100)+1;
        if(randomInt <= 26) return TypeOfShift.SUPER_EARLY;
        if(randomInt <= 54) return TypeOfShift.EARLY;
        if(randomInt <= 80) return TypeOfShift.LATE;
        return TypeOfShift.DAY_OFF;
    }



}
