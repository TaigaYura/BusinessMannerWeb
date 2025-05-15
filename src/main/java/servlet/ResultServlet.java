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

    // 1) 直前ラウンドの結果を取得
    int damage      = RoundService.getLastDamage();
    int remainingHP = RoundService.getLastRemainingHP();

    // 2) セッション更新
    session.setAttribute("enemyHP", remainingHP);
    Integer cr = (Integer) session.getAttribute("currentRound");
    if (cr == null) cr = 1;
    session.setAttribute("currentRound", cr + 1);
    session.setAttribute("questionIndex", 1);
    session.setAttribute("correctCount", 0);

    // 3) JSPに渡す
    req.setAttribute("roundDamage", damage);
    req.setAttribute("remainingHP", remainingHP);

    // 4) result.jsp へフォワード
    RequestDispatcher rd = req.getRequestDispatcher(
        "/WEB-INF/jsp/result.jsp");
    rd.forward(req, resp);
  }
}
