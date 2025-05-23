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
import service.RoundService;

@WebServlet("/quizSetup")
public class QuizSetupServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // 新ゲーム用にサービスとセッションをリセット
    LobbyService.reset();
    RoundService.reset();
    HttpSession old = req.getSession(false);
    if (old != null) old.invalidate();
    req.getSession(true);
    RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/jsp/quizSetup.jsp");
    rd.forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String difficulty = req.getParameter("difficulty");

    int totalRounds, questionsPerRound, enemyHP;

    switch (difficulty) {
      case "easy":
        questionsPerRound = 5;
        totalRounds = 3;
        enemyHP = 150;
        break;
      case "normal":
        questionsPerRound = 10;
        totalRounds = 5;
        enemyHP = 400;
        break;
      case "hard":
        questionsPerRound = 20;
        totalRounds = 7;
        enemyHP = 665;
        break;
      default:
        questionsPerRound = 10;
        totalRounds = 5;
        enemyHP = 400;
    }

    HttpSession session = req.getSession();
    session.setAttribute("difficulty", difficulty);
    session.setAttribute("totalRounds", totalRounds);
    session.setAttribute("questionsPerRound", questionsPerRound);
    session.setAttribute("enemyHP", enemyHP);
    session.setAttribute("currentRound", 1);
    session.setAttribute("questionIndex", 1);
    session.setAttribute("correctCount", 0);

    resp.sendRedirect(req.getContextPath() + "/lobby");
  }
}
