package com.cbpaguay.myexcel.dao;

import com.cbpaguay.myexcel.dto.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoDao extends JpaRepository<Auto, Integer> {
}
