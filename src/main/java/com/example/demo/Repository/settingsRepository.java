package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Entity.settings;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface settingsRepository extends JpaRepository<settings,String>{
	<List> settings[] findByUserid(Long id);
	@Transactional
	@Modifying
	@Query("update settings s set s.property =:property,s.subproperty =:subProperty where s.id =:id")
	void updatesettingsdata(String property,String subProperty,Long id);
}
