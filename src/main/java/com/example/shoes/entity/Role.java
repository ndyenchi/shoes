package com.example.shoes.entity;

import com.example.shoes.common.ERole;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    private ERole name;


    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}
