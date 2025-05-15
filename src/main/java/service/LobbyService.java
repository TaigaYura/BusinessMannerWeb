package service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.servlet.http.HttpSession;

/**
 * ロビー管理サービス
 */
public class LobbyService {
    private static final Set<String> sessions = ConcurrentHashMap.newKeySet();
    private static boolean gameStarted = false;

    public static synchronized void join(HttpSession session) {
        sessions.add(session.getId());
    }

    public static synchronized void startGame() {
        gameStarted = true;
    }

    public static int getPlayerCount() {
        return sessions.size();
    }

    public static boolean isGameStarted() {
        return gameStarted;
    }

    public static synchronized void reset() {
        sessions.clear();
        gameStarted = false;
    }
}