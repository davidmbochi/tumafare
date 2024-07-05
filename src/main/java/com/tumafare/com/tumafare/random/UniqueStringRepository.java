package com.tumafare.com.tumafare.random;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UniqueStringRepository extends JpaRepository<UniqueString, Long> {
    boolean existsByValue(String value);
}
