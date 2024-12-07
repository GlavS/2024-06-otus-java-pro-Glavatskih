package ru.otus.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import reactor.util.annotation.NonNull;
import ru.otus.controllers.ChatException;

@Slf4j
public class Room1408ChannelInterceptor implements ChannelInterceptor {
    private static final String TOPIC_TEMPLATE = "/topic/response.";

    @Override
    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
        var stomp = StompHeaderAccessor.wrap(message);
        var headers = SimpMessageHeaderAccessor.wrap(message);
        long roomId = 0;
        //        if(!(headers.getDestination() == null)){
        //            roomId = parseRoomId(headers.getDestination());
        //            log.info("Room id intercepted: {}", roomId);
        //        }

        return message;
    }

    private long parseRoomId(String simpDestination) {
        try {
            var idxRoom = simpDestination.lastIndexOf(TOPIC_TEMPLATE);
            return Long.parseLong(simpDestination.substring(idxRoom).replace(TOPIC_TEMPLATE, ""));
        } catch (Exception ex) {
            log.error("Can not get roomId", ex);
            throw new ChatException("Can not get roomId");
        }
    }
}
