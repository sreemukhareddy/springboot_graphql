package com.course.graphql.datasource.problemz.repository;

import com.course.graphql.datasource.problemz.entity.Problemz;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemzRepository extends CrudRepository<Problemz, String> {

    List<Problemz> findAllByOrderByCreationTimestampDesc();

    @Query(nativeQuery = true, value = "select * from problemz p "
                    + "where upper(content) like upper(:keyword) "
                    + "or upper(title) like upper(:keyword) "
                    + "or upper(tags) like upper(:keyword)"
    )
    List<Problemz> findByKeyword(@Param("keyword") String keyword);
    
}
