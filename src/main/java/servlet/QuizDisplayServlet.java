package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Question;
import service.QuizService;

@WebServlet("/quiz")
public class QuizDisplayServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    HttpSession session = req.getSession();

    // currentRound が未設定なら 1 に初期化
    if (session.getAttribute("currentRound") == null) {
      session.setAttribute("currentRound", 1);
    }

    // enemyHP / totalRounds / questionsPerRound は QuizSetupServlet でセット済み

    // 問題リストの準備
    int qpr = (Integer) session.getAttribute("questionsPerRound");
    List<Question> list = new QuizService().getShuffledQuestions(qpr);

    session.setAttribute("currentQuestionList", list);
    session.setAttribute("questionIndex", 1);
    session.setAttribute("correctCount", 0);

    req.getRequestDispatcher("/WEB-INF/jsp/quiz.jsp")
       .forward(req, resp);
  }
}
