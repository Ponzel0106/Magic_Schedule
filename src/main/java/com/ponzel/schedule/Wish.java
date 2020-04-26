package com.ponzel.schedule;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * Description entity Wish
 * The wish of the user for what kind of working shift he wants
 */
@Data
@Entity
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank
    private String month;

    @Min(value = 1)
    @Max(value = 31)
    private int day;

    @Enumerated(EnumType.STRING)
    @Column(name = "shift_type")
    private Shift.TypeOfShift type;

}