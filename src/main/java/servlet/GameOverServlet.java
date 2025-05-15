package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import service.LobbyService;

@WebServlet("/gameover")
public class GameOverServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    HttpSession session = req.getSession();

    // どちらで終了したか判定
    Integer enemyHP = (Integer) session.getAttribute("enemyHP");
    Integer totalRounds = (Integer) session.getAttribute("totalRounds");
    Integer currentRound = (Integer) session.getAttribute("currentRound");

    boolean win = (enemyHP != null && enemyHP <= 0);
    // あるいはラウンド切れで負けたか
    boolean roundsUp = (currentRound != null && totalRounds != null
                        && currentRound > totalRounds && enemyHP > 0);

    req.setAttribute("win", win);
    req.setAttribute("roundsUp", roundsUp);
    req.setAttribute("finalHP", enemyHP);

    // ロビー状態をクリア（必要なら）
    LobbyService.reset();

    // JSP へフォワード
    RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/jsp/gameover.jsp");
    rd.forward(req, resp);
  }
}
