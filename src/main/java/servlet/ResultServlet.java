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

    // 1) 与ダメージと残HP
    int damage    = RoundService.getLastDamage();
    int remaining = RoundService.getLastRemainingHP();
    session.setAttribute("enemyHP", remaining);
    req.setAttribute("roundDamage", damage);
    req.setAttribute("remainingHP", remaining);

    // 2) 平均正答率(0.0～1.0)を取得して100倍して整数化
    double avgRate    = RoundService.getLastAvgRate();
    int    correctRate = (int) Math.round(avgRate * 100);
    req.setAttribute("correctRate", correctRate);

    // 3) 次ラウンド用にセッションをリセット
    int cr = (Integer) session.getAttribute("currentRound");
    session.setAttribute("currentRound", cr + 1);
    session.setAttribute("questionIndex", 1);
    session.setAttribute("correctCount", 0);

    // 4) result.jsp へフォワード
    RequestDispatcher rd =
      req.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
    rd.forward(req, resp);
  }
}
