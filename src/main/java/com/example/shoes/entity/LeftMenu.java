package com.example.shoes.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class LeftMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String url;
}
