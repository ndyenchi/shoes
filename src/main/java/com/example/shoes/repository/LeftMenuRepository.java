package com.example.shoes.repository;

import com.example.shoes.entity.LeftMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeftMenuRepository extends JpaRepository<LeftMenu, Integer> {
}