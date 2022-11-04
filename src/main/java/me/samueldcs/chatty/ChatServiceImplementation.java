package me.samueldcs.chatty;

import com.google.protobuf.Empty;
import com.salesforce.grpc.contrib.spring.GrpcService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@GrpcService
public class ChatServiceImplementation extends ReactorChatServiceGrpc.ChatServiceImplBase {
	
	private final ChatRepository chatRepository;
	
	public ChatServiceImplementation(final ChatRepository chatRepository) {
		this.chatRepository = chatRepository;
	}
	
	@Override
	public Mono<Empty> postMessage(final Mono<ChatMessage> request) {
		return request
				.map(req -> new ChatMessageModel(
						req.getSender(),
						req.getChannel(),
						req.getContent()
				))
				.flatMap(chatRepository::save)
				.then(Mono.just(Empty.getDefaultInstance()));
	}
	
	@Override
	public Flux<ChatMessage> readMessages(final Mono<ChatListenRequest> request) {
		return request
				.map(ChatListenRequest::getChannel)
				.flatMapMany(chatRepository::findAllByChannel)
				.map(domain -> ChatMessage.newBuilder()
						.setContent(domain.getContent())
						.setChannel(domain.getChannel())
						.setSender(domain.getSender())
						.build());
	}
}
