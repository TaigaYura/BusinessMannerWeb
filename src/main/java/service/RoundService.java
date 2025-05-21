package service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RoundService {
    // セッションID → 正答数
    private static final Map<String,Integer> submissions = new ConcurrentHashMap<>();
    // 最新ラウンドの集計結果
    private static double lastAvgRate      = 0.0;
    private static int    lastDamage       = 0;
    private static int    lastRemainingHP  = 0;

    /** 回答を登録し、全員揃ったら集計 */
    public static synchronized void submit(String sessionId,
                                           int correctCount,
                                           int questionsPerRound,
                                           int currentHP) {
        submissions.put(sessionId, correctCount);
        if (submissions.size() == LobbyService.getPlayerCount()) {
            calculateAll(questionsPerRound, currentHP);
            // 提出済みリストは次ラウンド前にクリア
            submissions.clear();
        }
    }

    /** 全員回答済みか */
    public static boolean allSubmitted() {
        // 提出済みが空なら、すでに calculateAll で集計済みとみなす
        if (submissions.isEmpty()) {
            return lastDamage != 0 || lastAvgRate > 0.0;
        }
        return submissions.size() == LobbyService.getPlayerCount();
    }

    /** 現在登録されている回答数 */
    public static int getSubmittedCount() {
        return submissions.size();
    }

    /** 最新ラウンドのダメージ */
    public static int getLastDamage() {
        return lastDamage;
    }

    /** 最新ラウンドの残りHP */
    public static int getLastRemainingHP() {
        return lastRemainingHP;
    }

    /** 全状態をクリア（新ゲーム用） */
    public static synchronized void reset() {
        submissions.clear();
        lastAvgRate     = 0.0;
        lastDamage      = 0;
        lastRemainingHP = 0;
    }
    //──────────────────────────
    // internal
    private static void calculateAll(int qpr, int currentHP) {
        lastAvgRate = submissions.values()
            .stream()
            .mapToDouble(c -> c / (double) qpr)
            .average()
            .orElse(0.0);
        // ダメージは正答率×MAX_DAMAGE
        lastDamage      = (int) Math.round(lastAvgRate * GameService.MAX_DAMAGE);
        lastRemainingHP = currentHP - lastDamage;
    }
    /** 平均正答率 (0.0〜1.0) を外部で取得できるように */
    public static double getLastAvgRate() {
        return lastAvgRate;
    }
}
