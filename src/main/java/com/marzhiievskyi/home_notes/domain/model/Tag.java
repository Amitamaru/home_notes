package com.marzhiievskyi.home_notes.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(schema = "home_notes", name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "note_tag",
            joinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "note_id", referencedColumnName = "id"))
    private List<Note> notes;

    @Column(name = "time_insert")
    @CreationTimestamp
    private LocalDateTime timeInsert;
}
