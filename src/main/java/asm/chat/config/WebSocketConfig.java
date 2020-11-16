package asm.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker //Para habilitar websocket
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer { // se usa la interfaz para obtener metodos de configuracion

    /**
     * STOMP is a messaging protocol that defines the format and rules for data exchange.
     * Why do we need STOMP? Well, WebSocket is just a communication protocol.
     * It doesn’t define things like - How to send a message only to users who are subscribed to a particular topic,
     * or how to send a message to a particular user. We need STOMP for these functionalities.
     * @param registry -
     */
    @Override //Este metodo configura un endpoint para conectarse al websocket
    public void registerStompEndpoints(StompEndpointRegistry registry) { // STOMP = Simple Text Oriented Messaging Protocol
        registry.addEndpoint("/ws").withSockJS(); //el metodo withSockJs() se usa para habilitar WebSocket a navegadores incompatibles
    }

    /**
     * configuring a message broker that will be used to route messages from one client to another.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app"); // the messages whose destination starts with “/app” should be routed to message-handling methods

        // the messages whose destination starts with “/topic” should be routed to the message broker.
        // Message broker broadcasts messages to all the connected clients who are subscribed to a particular topic.
        // Use this for enabling a Full featured broker like RabbitMQ
        registry.enableStompBrokerRelay("/topic")
                .setRelayHost("localhost")
                .setRelayPort(61613)
                .setClientLogin("guest")
                .setClientPasscode("guest");
    }
}