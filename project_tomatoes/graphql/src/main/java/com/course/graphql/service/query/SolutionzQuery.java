package com.course.graphql.service.query;

import com.course.graphql.datasource.problemz.entity.Solutionz;
import com.course.graphql.datasource.problemz.repository.SolutionzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolutionzQuery {
    @Autowired
    private SolutionzRepository repository;

    public List<Solutionz> solutionzByKeyword(String keyword) {
        return repository.findByKeyword(keyword);
        //return repository.findByKeyword("%"+keyword+"%");
    }
}
