package com.mercadolibre.mutantverifier.repository;

import com.mercadolibre.mutantverifier.entity.Stats;
import com.mercadolibre.mutantverifier.entity.StatsError;
import org.springframework.data.repository.CrudRepository;

public interface StatsErrorRepository extends CrudRepository<StatsError, Integer> {
}
