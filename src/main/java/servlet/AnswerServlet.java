package servlet;

import java.io.IOException;
import java.io.PrintWriter;
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

    @SuppressWarnings("unchecked")
    List<Question> list = (List<Question>) session.getAttribute("currentQuestionList");
    Integer qiObj       = (Integer) session.getAttribute("questionIndex");
    Integer qprObj      = (Integer) session.getAttribute("questionsPerRound");
    Integer ccObj       = (Integer) session.getAttribute("correctCount");
    Integer hpObj       = (Integer) session.getAttribute("enemyHP");
    if (list == null || qiObj == null || qprObj == null || ccObj == null || hpObj == null) {
      resp.sendRedirect(req.getContextPath() + "/quizSetup");
      return;
    }

    int questionIndex     = qiObj;
    int questionsPerRound = qprObj;
    int correctCount      = ccObj;
    int currentHP         = hpObj;

    // 正解・ユーザー回答を取得
    Question currentQ = list.get(questionIndex - 1);
    String rawCorrect = currentQ.getAnswer();
    String rawUser    = req.getParameter("answer");

    // 空白をトリムして比較
    String correctAnswer = (rawCorrect != null ? rawCorrect.trim() : "");
    String userAnswer    = (rawUser    != null ? rawUser.trim()    : "");
    boolean correct      = correctAnswer.equals(userAnswer);

    // 正解ならカウントアップ
    if (correct) {
      correctCount++;
      session.setAttribute("correctCount", correctCount);
    }

    // Ajax 判定（JSON レスポンス）
    String xhr    = req.getHeader("X-Requested-With");
    String accept = req.getHeader("Accept");
    boolean isAjax = "XMLHttpRequest".equals(xhr)
                   || (accept != null && accept.contains("application/json"));
    if (isAjax) {
      resp.setContentType("application/json;charset=UTF-8");
      try (PrintWriter out = resp.getWriter()) {
        out.write("{"
          + "\"correct\":"         + correct
          + ",\"answer\":\""       + userAnswer
          + "\",\"correctAnswer\":\"" + correctAnswer
          + "\"}");
      }
      return;
    }

    // 同期処理：次の問題 or 結果ページへ
    questionIndex++;
    session.setAttribute("questionIndex", questionIndex);
    if (questionIndex <= questionsPerRound) {
      RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/jsp/quiz.jsp");
      rd.forward(req, resp);
      return;
    }

    // ラウンド終了処理
    RoundService.submit(session.getId(), correctCount, questionsPerRound, currentHP);
    if (LobbyService.getPlayerCount() <= 1) {
      resp.sendRedirect(req.getContextPath() + "/result");
      return;
    }
    if (!RoundService.allSubmitted()) {
      resp.sendRedirect(req.getContextPath() + "/waiting");
      return;
    }
    resp.sendRedirect(req.getContextPath() + "/result");
  }
}
