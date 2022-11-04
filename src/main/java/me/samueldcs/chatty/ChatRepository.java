package me.samueldcs.chatty;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ChatRepository extends ReactiveMongoRepository<ChatMessageModel, ObjectId> {
	
	@Tailable
	Flux<ChatMessageModel> findAllByChannel(String channel);

}
