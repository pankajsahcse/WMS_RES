package com.res.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.res.entity.Measurement;





@Repository
public interface MeasurementRepository  extends JpaRepository<Measurement, Long>{

	List<Measurement> findByEstimateSorIdOrderByIdDesc(Long estimateSorId);

	List<Measurement> findByWorkIdAndDscStatus(Long workId, Object object);

	
	@Query(value = "SELECT SUM(calculatedamount)"
			+ " FROM measurement "
			+ " WHERE work_id = :workId"
			+ " " ,nativeQuery = true)
	Double findAllMeasurementByWorkId(@Param("workId") Long workId);
List<Measurement> findByWorkId(Long workId);
	

}
