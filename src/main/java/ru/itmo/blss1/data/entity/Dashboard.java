package ru.itmo.blss1.data.entity;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User owner;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Set<Pin> pins = new HashSet<>();
}
