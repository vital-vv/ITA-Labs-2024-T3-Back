package com.ventionteams.applicationexchange.controller;

import com.ventionteams.applicationexchange.dto.create.UserAuthDto;
import com.ventionteams.applicationexchange.entity.Notification;
import com.ventionteams.applicationexchange.entity.SubscriptionData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    List<SubscriptionData> subscriptions = new ArrayList<>();

    @GetMapping(path = "/open-sse-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent> openSseStream(@AuthenticationPrincipal UserAuthDto user) {

        return Flux.create(fluxSink -> {
            log.info("create subscription for user with id: {}, email:{}", user.id(), user.email());

            SubscriptionData subscriptionData = new SubscriptionData(user.id(), fluxSink);
            subscriptions.add(subscriptionData);

            fluxSink.onCancel(
                    () -> {
                        subscriptions.remove(subscriptionData);
                        log.info("subscription " + user.id() + " was closed");
                    }

            );

            ServerSentEvent<String> helloEvent = ServerSentEvent.builder("Notification stream was created").build();
            fluxSink.next(helloEvent);
        });
    }

    @PutMapping(path = "/send-message-by-name")
    public void sendMessageByName(@RequestBody Notification notification) {

        ServerSentEvent<String> event = ServerSentEvent
                .builder(notification.getType() + notification.getDescription())
                .build();

        subscriptions.forEach((subscriptionData) -> {
                    if (notification.getTo().getId().equals(subscriptionData.getUserId())) {
                        subscriptionData.getFluxSink().next(event);
                    }
                }
        );
    }
}
