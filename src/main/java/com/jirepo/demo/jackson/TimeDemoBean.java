package com.jirepo.demo.jackson;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeDemoBean {

    private LocalDateTime localDateTime;
    private LocalDate localDate; 
    private OffsetDateTime offsetTime;
    private Date date;
    private String userName; 
    private List<String> userNames;


    public TimeDemoBean(){ 
        this.localDateTime = LocalDateTime.now();
        this.offsetTime = OffsetDateTime.now();
        this.localDate = LocalDateTime.now().toLocalDate();
        this.date = new Date();
    }
    
}
