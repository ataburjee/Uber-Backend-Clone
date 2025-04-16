package com.uber.repository;

import com.uber.model.DriverES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface DriverESRepository extends ElasticsearchRepository<DriverES, String> {

    List<DriverES> findByAvailable(boolean available);

}
