package com.github.gather.WebSocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

import java.security.Principal;

@Slf4j
public class PrincipalStompDecoratorFactory implements WebSocketHandlerDecoratorFactory {

    @Override
    public WebSocketHandler decorate(WebSocketHandler handler) {
        return new WebSocketHandlerDecorator(handler) {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                Principal principal = (Principal) session.getAttributes().get("PRINCIPAL");

                if (principal != null) {
                    session.getAttributes().put("PRINCIPAL", principal);
                }
                super.afterConnectionEstablished(session);
            }
        };
    }
}
