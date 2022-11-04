package me.samueldcs.chatty;

import com.google.protobuf.Empty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChatServiceImplementationTest {
	
	@Mock
	private ChatRepository chatRepository;
	
	@InjectMocks
	private ChatServiceImplementation chatServiceImplementation;
	
	@Test
	@DisplayName("should stream messages by channel")
	public void itStreamsByChannel() {
		final String expectedChannel = "expectedChannel";
		
		var messageOne = buildModel("messageOne");
		var messageTwo = buildModel("messageTwo");
		
		when(chatRepository.findAllByChannel(expectedChannel))
				.thenReturn(Flux.just(messageOne, messageTwo));
		
		final ChatListenRequest request = ChatListenRequest.newBuilder().setChannel(expectedChannel).build();
		
		StepVerifier.create(chatServiceImplementation.readMessages(Mono.just(request)))
				.expectNext(buildExpectedChatMessage("messageOne"))
				.expectNext(buildExpectedChatMessage("messageTwo"))
				.expectComplete()
				.verify();
	}
	
	@Test
	@DisplayName("should post messages")
	public void itPostsMessages() {
		final String messageContent = "test message";
		
		when(chatRepository.save(any()))
				.thenAnswer(i -> Mono.just(i.getArgument(0)));
		
		final Mono<ChatMessage> testMessage = Mono.just(buildExpectedChatMessage(messageContent));
		StepVerifier.create(chatServiceImplementation.postMessage(testMessage))
				.expectNext(Empty.getDefaultInstance())
				.verifyComplete();
	}
	
	private static ChatMessage buildExpectedChatMessage(final String messageOne) {
		return ChatMessage.newBuilder()
				.setChannel("expectedChannel")
				.setSender("sender")
				.setContent(messageOne)
				.build();
	}
	
	private static ChatMessageModel buildModel(final String messageContent) {
		final ChatMessageModel chatMessageModel = new ChatMessageModel("sender", "expectedChannel", messageContent);
		return chatMessageModel;
	}
}
