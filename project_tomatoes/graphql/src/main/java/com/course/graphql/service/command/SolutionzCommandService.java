package com.course.graphql.service.command;

import com.course.graphql.datasource.problemz.entity.Problemz;
import com.course.graphql.datasource.problemz.entity.Solutionz;
import com.course.graphql.datasource.problemz.repository.SolutionzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Optional;

@Service
public class SolutionzCommandService {

    @Autowired
    private SolutionzRepository solutionzRepository;

    private Sinks.Many<Solutionz> solutionzSink = Sinks.many()
            .multicast()
            .onBackpressureBuffer();

    public Solutionz createSolutionz(Solutionz solutionz) {
        var created = solutionzRepository.save(solutionz);
        solutionzSink.tryEmitNext(created);
        return created;
    }

    public Optional<Solutionz> voteBad(String solutionId) {
        solutionzRepository.addVoteBadCount(solutionId);

        var created = solutionzRepository.findById(solutionId);
        if (!created.isEmpty()) {
            solutionzSink.tryEmitNext(created.get());
        }
        return created;
    }

    public Optional<Solutionz> voteGood(String solutionId) {
        solutionzRepository.addVoteGoodCount(solutionId);

        var created = solutionzRepository.findById(solutionId);
        if (!created.isEmpty()) {
            solutionzSink.tryEmitNext(created.get());
        }
        return created;
    }

    public Flux<Solutionz> solutionzFlux() {
        return solutionzSink.asFlux();
    }

}
