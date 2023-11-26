package com.example.utils;

import com.example.chart.service.DetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Optional;

/**
 * Author:XY
 * PACkAGE:com.example.utils
 * Date:2023/11/24 15:57
 *
 * @author BaiYiChen
 */
@Component
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler implements AsyncMethod {
    private final DetailsService detailsService;
    private final ObjectMapper objectMapper;
    private WebSocketSession session;

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
        try {
            this.session = session;
            log.info("建立连接：{}", session.getId());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Async
    @Scheduled(cron = "*/2 * * * * ?")
    @Override
    public void sendData() {
        Optional.ofNullable(session)
                .ifPresent(session1 -> {
                    synchronized (session1) {
                        if (session1.isOpen()) {
                            try {
                                session1.sendMessage(new TextMessage(objectMapper.writeValueAsString(detailsService.getAll())));
                            } catch (Exception e) {
                                log.error(e.getMessage());
                            }
                        }
                    }
                });
    }

}
