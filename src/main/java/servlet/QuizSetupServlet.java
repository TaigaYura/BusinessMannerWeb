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
    int totalRounds, questionsPerRound;
    switch (difficulty) {
      case "easy":
        totalRounds = 3; questionsPerRound = 3; break;
      case "normal":
        totalRounds = 5; questionsPerRound = 5; break;
      case "hard":
        totalRounds = 7; questionsPerRound = 7; break;
      default:
        totalRounds = 5; questionsPerRound = 5;
    }
    HttpSession session = req.getSession();
    session.setAttribute("difficulty", difficulty);
    session.setAttribute("totalRounds", totalRounds);
    session.setAttribute("questionsPerRound", questionsPerRound);
    session.setAttribute("currentRound", 1);
    session.setAttribute("questionIndex", 1);
    session.setAttribute("correctCount", 0);
    resp.sendRedirect(req.getContextPath() + "/lobby");
  }
}
