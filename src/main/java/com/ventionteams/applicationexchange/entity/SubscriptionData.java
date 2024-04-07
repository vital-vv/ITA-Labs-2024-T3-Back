package com.ventionteams.applicationexchange.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.FluxSink;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class SubscriptionData {

    private UUID userId;

    private FluxSink<ServerSentEvent> fluxSink;

}
