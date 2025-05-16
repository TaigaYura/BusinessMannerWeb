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
    int dmg = RoundService.getLastDamage();
    int hp  = RoundService.getLastRemainingHP();
    session.setAttribute("enemyHP", hp);
    int cr = (Integer) session.getAttribute("currentRound");
    session.setAttribute("currentRound", cr + 1);
    session.setAttribute("questionIndex", 1);
    session.setAttribute("correctCount", 0);
    req.setAttribute("roundDamage", dmg);
    req.setAttribute("remainingHP", hp);
    RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
    rd.forward(req, resp);
  }
}
