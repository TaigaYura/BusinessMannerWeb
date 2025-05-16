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
    int hp   = (Integer) session.getAttribute("enemyHP");
    int tot  = (Integer) session.getAttribute("totalRounds");
    int cur  = (Integer) session.getAttribute("currentRound");
    boolean win      = hp <= 0;
    boolean roundsUp = cur > tot && hp > 0;
    req.setAttribute("win", win);
    req.setAttribute("roundsUp", roundsUp);
    req.setAttribute("finalHP", hp);
    LobbyService.reset();
    RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/jsp/gameover.jsp");
    rd.forward(req, resp);
  }
}
