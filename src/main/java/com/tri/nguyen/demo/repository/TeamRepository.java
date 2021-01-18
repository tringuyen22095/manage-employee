package com.tri.nguyen.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tri.nguyen.demo.models.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

}
