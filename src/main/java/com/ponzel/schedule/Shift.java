package com.ponzel.schedule;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Random;

/**
 * Description entity Shift
 */
@Data
@Entity
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Min(value = 1)
    @Max(value = 31)
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

    /**
     * Generation random type of shift
     *
     * @return random type of shift
     */
    public static TypeOfShift generateTypeOfShift(){
        Random random = new Random();
        int randomInt = random.nextInt(100)+1;
        if(randomInt <= 26) return TypeOfShift.SUPER_EARLY;
        if(randomInt <= 54) return TypeOfShift.EARLY;
        if(randomInt <= 80) return TypeOfShift.LATE;
        return TypeOfShift.DAY_OFF;
    }

}
