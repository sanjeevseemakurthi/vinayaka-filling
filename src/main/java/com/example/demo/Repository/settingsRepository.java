package com.example.demo.Repository;

import com.example.demo.Entity.stocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Entity.settings;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface settingsRepository extends JpaRepository<settings,String>{
	<List> settings[] findByUserid(Long id);
	settings findById(Long id);
	@Transactional
	@Modifying
	@Query("update settings s set s.property =:property,s.subproperty =:subProperty where s.id =:id")
	void updatesettingsdata(String property,String subProperty,Long id);
	@Transactional
	@Modifying
	@Query("update settings s set s.stockleft =:stockleft , s.stockamount =:stockamount where s.id =:id")
	void updatestocksleftamountbyid(Long stockleft,Long stockamount,Long id);

}
