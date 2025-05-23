package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import service.RoundService;

@WebServlet("/result")
public class ResultServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpSession session = req.getSession();

    // 与ダメージと残HP
    int damage    = RoundService.getLastDamage();
    int remaining = RoundService.getLastRemainingHP();
    session.setAttribute("enemyHP", remaining);
    req.setAttribute("roundDamage", damage);
    req.setAttribute("remainingHP", remaining);

    // 敵画像の切り替え（HP に応じて）
    String enemyImage;
    if (remaining > 70) {
      enemyImage = "enemy_full.png";
    } else if (remaining > 30) {
      enemyImage = "enemy_half.png";
    } else {
      enemyImage = "enemy_low.png";
    }
    req.setAttribute("enemyImage", enemyImage);

    // 平均正答率をパーセンテージ化して渡す
    double avgRate     = RoundService.getLastAvgRate();       // 0.0～1.0
    int    correctRate = (int) Math.round(avgRate * 100);     // 0～100%
    req.setAttribute("correctRate", correctRate);

    // 次ラウンド準備
    int cr = (Integer) session.getAttribute("currentRound");
    session.setAttribute("currentRound", cr + 1);
    session.setAttribute("questionIndex", 1);
    session.setAttribute("correctCount", 0);

    // result.jsp へフォワード
    RequestDispatcher rd =
      req.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
    rd.forward(req, resp);
  }
}
