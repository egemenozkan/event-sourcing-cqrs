package org.example.case4.data.notification.repository;

import org.example.case4.data.notification.entity.NotificationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface NotificationRepository extends ReactiveMongoRepository<NotificationEntity, String> {

    Flux<List<NotificationEntity>> findAllByUserId(String userId);

    @Query("{ 'type': ?0, 'timestamp': { $gte: ?1 } }")
    Mono<Long> countByTypeAndTimestampAfter(String type, long timestamp);

}
