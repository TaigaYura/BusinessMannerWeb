package service;

/**
 * ゲーム進行に関わるロジックをまとめるサービスクラス
 */
public class GameService {

    /** 敵の残りHP */
    private int enemyHP = 100;

    /**
     * 全員の平均正答率からダメージ量を計算
     * @param avgRate  全員の正答率平均 (0.0～1.0)
     * @param baseDamage  ラウンドあたりの最大ダメージ（例：questionsPerRound）
     * @return 実際に与えるダメージ量（整数）
     */
    public int calculateDamage(double avgRate, int baseDamage) {
        return (int) Math.round(avgRate * baseDamage);
    }

    /**
     * 計算したダメージを敵HPに反映
     */
    public void applyDamage(int damage) {
        enemyHP = Math.max(0, enemyHP - damage);
    }

    /** 敵の現在HPを取得 */
    public int getEnemyHP() {
        return enemyHP;
    }
}
