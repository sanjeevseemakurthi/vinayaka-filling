package com.example.demo.Repository;

import com.example.demo.Entity.people;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface peopleRepository extends JpaRepository<people,Long> {
    @Query("select Max(p.id) from people p")
    long getLatest();
    people[] findByUid(long pid);
    @Query("select p from people p where p.id =:id")
    people[] serchbyid(long id);
}
