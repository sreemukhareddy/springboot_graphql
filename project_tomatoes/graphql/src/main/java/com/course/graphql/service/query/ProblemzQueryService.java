package com.course.graphql.service.query;

import com.course.graphql.datasource.problemz.entity.Problemz;
import com.course.graphql.datasource.problemz.entity.Solutionz;
import com.course.graphql.datasource.problemz.repository.ProblemzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ProblemzQueryService {

    @Autowired
    private ProblemzRepository repository;

    public List<Problemz> problemzLatestList() {
        var problemz =  repository.findAllByOrderByCreationTimestampDesc();
        /*
        problemz
                .forEach(p -> {
                    p.getSolutions().sort(Comparator.comparing(Solutionz::getCreationTimestamp).reversed());
                });
         */
        return problemz;
    }

    public Optional<Problemz> problemzDetail(String problemzId) {
        return repository.findById(problemzId);
    }

    public List<Problemz> problemzByKeyword(String keyword) {
        return repository.findByKeyword(keyword);
        //return repository.findByKeyword("%" + keyword + "%");
    }
}
