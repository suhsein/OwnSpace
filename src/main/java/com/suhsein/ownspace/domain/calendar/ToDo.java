package com.suhsein.ownspace.domain.calendar;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Data
@Entity
@NoArgsConstructor
public class ToDo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    @Embedded
    MyDate myDate;

    @NotBlank
    private String title;

    private String place;

    @DateTimeFormat(pattern = "hh:mm")
    private LocalTime time;

    @Lob
    private String description;

    @Enumerated(value = EnumType.STRING)
    private ToDoStatus status;

    @Builder
    public ToDo(MyDate myDate, String title, String place, LocalTime time, String description, ToDoStatus status) {
        this.myDate = myDate;
        this.title = title;
        this.place = place;
        this.time = time;
        this.description = description;
        this.status = status;
    }
}
