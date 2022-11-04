# chatty
A simple chat application powered by gRPC, Spring WebFlux, Project Reactor and Reactive Mongo.

## gRPC
A netty gRPC server runs on port 8087. Two functions are exposed - postMessage and readMessages;


```http request
GRPC localhost:8087/me.samueldcs.chatty.ChatService/postMessage

{
  "content": "This is a test",
  "channel": "Sample Channel",
  "sender": "Anonymous"
}
```

```http request
GRPC localhost:8087/me.samueldcs.chatty.ChatService/readMessages

{
  "channel": "Sample Channel"
}
```

The read connection will remain open, polling from the Mongo collection indefinitely.

## Mongo
Running Mongo on docker will do. It's important to [cap the messages collection](https://www.mongodb.com/docs/manual/core/capped-collections/) to allow tailing.

