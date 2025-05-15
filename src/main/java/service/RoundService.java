package service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ラウンド管理サービス
 */
public class RoundService {
    private static final Map<String, Integer> submissions = new ConcurrentHashMap<>();
    private static double lastAvgRate = 0.0;
    private static int lastDamage = 0;
    private static int lastRemainingHP = 0;

    public static synchronized void submit(String sessionId, int correctCount) {
        submissions.put(sessionId, correctCount);
    }

    public static boolean allSubmitted(int totalPlayers) {
        return submissions.size() >= totalPlayers;
    }

    public static synchronized double calculateAverageRate(int questionsPerRound) {
        double sum = 0;
        for (int correct : submissions.values()) {
            sum += (double) correct / questionsPerRound;
        }
        lastAvgRate = sum / submissions.size();
        return lastAvgRate;
    }

    /** 登録済み回答数を取得 */
    public static int getSubmittedCount() {
        return submissions.size();
    }

    /** 前回ラウンドのダメージ取得 */
    public static int getLastDamage() {
        return lastDamage;
    }

    /** 前回ラウンドの残りHP取得 */
    public static int getLastRemainingHP() {
        return lastRemainingHP;
    }

    public static synchronized void reset() {
        submissions.clear();
        lastAvgRate     = 0.0;
        lastDamage      = 0;
        lastRemainingHP = 0;
    }

    /** 前回ラウンドの平均正答率取得 */
    public static double getLastAvgRate() {
        return lastAvgRate;
    }
}