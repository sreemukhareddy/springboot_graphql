package com.course.graphql.service.command;

import com.course.graphql.datasource.problemz.entity.Problemz;
import com.course.graphql.datasource.problemz.repository.ProblemzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class ProblemzCommandService {

    private Sinks.Many<Problemz> problemzSink = Sinks.many()
            .multicast()
            .onBackpressureBuffer();

    @Autowired
    private ProblemzRepository problemzRepository;

    public Problemz createProblem(Problemz p) {
        var created = problemzRepository.save(p);

        problemzSink.tryEmitNext(created);

        return created;
    }

    public Flux<Problemz> problemzFlux() {
        return problemzSink.asFlux();
    }
}
