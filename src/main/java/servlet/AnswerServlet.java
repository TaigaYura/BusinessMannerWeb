package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Question;
import service.LobbyService;
import service.RoundService;

@WebServlet("/answer")
public class AnswerServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    HttpSession session = req.getSession(false);
    if (session == null) {
      resp.sendRedirect(req.getContextPath() + "/quizSetup");
      return;
    }

    Integer qprObj      = (Integer) session.getAttribute("questionsPerRound");
    Integer qiObj       = (Integer) session.getAttribute("questionIndex");
    Integer ccObj       = (Integer) session.getAttribute("correctCount");
    Integer hpObj       = (Integer) session.getAttribute("enemyHP");
    @SuppressWarnings("unchecked")
    List<Question> list = (List<Question>) session.getAttribute("currentQuestionList");

    if (qprObj == null || qiObj == null || ccObj == null || hpObj == null || list == null) {
      resp.sendRedirect(req.getContextPath() + "/quizSetup");
      return;
    }

    int questionsPerRound = qprObj;
    int questionIndex     = qiObj;
    int correctCount      = ccObj;
    int currentHP         = hpObj;

    // 正誤判定
    String userAnswer = req.getParameter("answer");
    Question currentQ = list.get(questionIndex - 1);
    if (currentQ.getAnswer().equals(userAnswer)) {
      correctCount++;
      session.setAttribute("correctCount", correctCount);
    }

    // 次の問題へ
    questionIndex++;
    session.setAttribute("questionIndex", questionIndex);
    if (questionIndex <= questionsPerRound) {
      RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/jsp/quiz.jsp");
      rd.forward(req, resp);
      return;
    }

    // 最終問題回答 → バリア登録
    RoundService.submit(session.getId(), correctCount, questionsPerRound, currentHP);

    // **１人プレイなら即リザルトへ**
    if (LobbyService.getPlayerCount() <= 1) {
      resp.sendRedirect(req.getContextPath() + "/result");
      return;
    }

    // マルチプレイ時：全員揃うまで待機
    if (!RoundService.allSubmitted()) {
      resp.sendRedirect(req.getContextPath() + "/waiting");
      return;
    }

    // 全員揃ったらリザルトへ
    resp.sendRedirect(req.getContextPath() + "/result");
  }
}
