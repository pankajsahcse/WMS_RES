package com.res.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.res.entity.workEmb;



@Repository
public interface workEmbRepository extends JpaRepository<workEmb, Long> {

	workEmb findByWorkId(Long workid);

	List<workEmb> findAllByEngineerId(Long id);

	List<workEmb> findByEmbNoContaining(String searchBoxVal);

	workEmb findByEmbNo(String searchBoxVal);

}
