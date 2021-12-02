package com.example.historysocketserver;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class LogHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder();
        BufferedReader out;
        String str = null;
        try {
            Process process = processBuilder.command("tail", "-f", "history_file", "-n", "1").start();
            out = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while((str = out.readLine()) != null) {
                session.sendMessage(new TextMessage(str));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
