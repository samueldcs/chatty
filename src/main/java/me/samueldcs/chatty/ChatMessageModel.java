package me.samueldcs.chatty;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class ChatMessageModel {
	@Id
	private ObjectId id;
	
	private String sender, channel, content;
	
	public ObjectId getId() {
		return id;
	}
	
	public void setId(final ObjectId id) {
		this.id = id;
	}
	
	public String getSender() {
		return sender;
	}
	
	public void setSender(final String sender) {
		this.sender = sender;
	}
	
	public String getChannel() {
		return channel;
	}
	
	public void setChannel(final String channel) {
		this.channel = channel;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(final String content) {
		this.content = content;
	}
	
	public ChatMessageModel() {}
	
	public ChatMessageModel(final String sender, final String channel, final String content) {
		this.id = new ObjectId();
		this.sender = sender;
		this.channel = channel;
		this.content = content;
	}
	
	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final ChatMessageModel that = (ChatMessageModel) o;
		return Objects.equals(id, that.id) && Objects.equals(sender, that.sender) && Objects.equals(channel, that.channel) && Objects.equals(content, that.content);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, sender, channel, content);
	}
	
	@Override
	public String toString() {
		return "ChatMessageModel{" +
				"id=" + id +
				", sender='" + sender + '\'' +
				", channel='" + channel + '\'' +
				", content='" + content + '\'' +
				'}';
	}
}
