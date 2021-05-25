package com.mercadolibre.mutantverifier.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.mercadolibre.mutantverifier.entity.Stats;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public interface StatsRepository extends CrudRepository<Stats, String> {

    @Query(value = "SELECT count(s) FROM Stats s where s.isMutant= ?1")
    Optional<Integer> countElements(Boolean type);
}