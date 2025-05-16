package service;

public class GameService {
    /** 1ラウンドあたりの最大ダメージ */
    public static final int MAX_DAMAGE = 100;

    /**
     * 正答数からダメージを計算
     * @param correctCount 正答数
     * @param questionsPerRound 問題数
     */
    public int calculateDamage(int correctCount, int questionsPerRound) {
        double rate = correctCount / (double) questionsPerRound;
        return (int) Math.round(rate * MAX_DAMAGE);
    }

    /**
     * 平均正答率からダメージを計算
     * @param avgRate 平均正答率 (0.0～1.0)
     */
    public int calculateDamageByRate(double avgRate) {
        return (int) Math.round(avgRate * MAX_DAMAGE);
    }
}
