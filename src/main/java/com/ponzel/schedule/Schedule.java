package com.ponzel.schedule;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy = "schedule")
    private List<Shift> shifts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String month;
    private int year;


}
