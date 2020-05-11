package com.example.trello.models;

import com.example.trello.services.CardService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


//id bigint generated by default as identity primary key,
//        title varchar(255),
//        created timestamp,
//        desk_id bigint not null,
//        foreign key (desk_id) references desks(id)
@Entity
@Table(name = "lists")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "desk_id")
    private DeskEntity desk;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "list")
    private List<CardEntity> cards;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @CreationTimestamp
    private Date created;
}

