package com.mandarin.petching.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter @Setter
public class WorkingDayAndTime {

    @Id
    @Column(name = "working_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String monStartTime;
    private String monFinishTime;

    private String tueStartTime;
    private String tueFinishTime;

    private String wedStartTime;
    private String wedFinishTime;

    private String thuStartTime;
    private String thuFinishTime;

    private String friStartTime;
    private String friFinishTime;

    private String satStartTime;
    private String satFinishTime;

    private String sunStartTime;
    private String sunFinishTime;
}
