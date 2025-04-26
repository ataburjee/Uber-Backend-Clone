package com.uber.repository;

import com.uber.model.DriverES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverESRepository extends ElasticsearchRepository<DriverES, String> {

    Optional<DriverES> findByDriverId(String driver);

    List<DriverES> findByAvailable(boolean available);

}
