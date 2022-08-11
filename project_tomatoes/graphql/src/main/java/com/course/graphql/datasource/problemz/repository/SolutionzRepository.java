package com.course.graphql.datasource.problemz.repository;

import com.course.graphql.datasource.problemz.entity.Problemz;
import com.course.graphql.datasource.problemz.entity.Solutionz;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface SolutionzRepository extends CrudRepository<Solutionz, String> {
    @Query(nativeQuery = true, value = "select * from solutionz where upper(content) like upper(:keyword)")
    List<Solutionz> findByKeyword(@Param("keyword") String keyword);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
    value = "update solutionz set vote_bad_count = vote_bad_count + 1 where id = :solutionzId")
    void addVoteBadCount(@Param("solutionzId") String id );

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "update solutionz set vote_good_count = vote_good_count + 1 where id = :solutionzId")
    void addVoteGoodCount(@Param("solutionzId") String id );
}
