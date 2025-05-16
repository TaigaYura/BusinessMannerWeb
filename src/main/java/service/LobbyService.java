package service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jakarta.servlet.http.HttpSession;

public class LobbyService {
    // セッションIDを保持
    private static final Set<String> sessions =
        Collections.synchronizedSet(new HashSet<>());
    // ゲーム開始フラグ
    private static volatile boolean gameStarted = false;

    /** ロビーに参加 */
    public static void join(HttpSession session) {
        sessions.add(session.getId());
    }

    /** 現在の参加人数 */
    public static int getPlayerCount() {
        return sessions.size();
    }

    /** ホストがゲーム開始ボタンを押したとき */
    public static void startGame() {
        gameStarted = true;
    }

    /** ゲーム開始済みか */
    public static boolean isGameStarted() {
        return gameStarted;
    }

    /** 新ゲーム用にリセット */
    public static synchronized void reset() {
        sessions.clear();
        gameStarted = false;
    }
}
