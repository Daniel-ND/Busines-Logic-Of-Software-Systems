package ru.itmo.blss1.data.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Data
@Entity
public class Dashboard implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private User owner;

    private String name;

    @ManyToMany
    @JoinColumn(name = "pin_id")
    private Set<Pin> pins = new HashSet<>();
}
