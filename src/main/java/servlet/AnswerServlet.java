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
import service.RoundService;

@WebServlet("/answer")
public class AnswerServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // (1) セッションが存在しない or 切れていたら最初へ
    HttpSession session = req.getSession(false);
    if (session == null) {
      resp.sendRedirect(req.getContextPath() + "/quizSetup");
      return;
    }

    // (2) 必須属性を一括で取得して null チェック
    Integer qprObj      = (Integer) session.getAttribute("questionsPerRound");
    Integer qiObj       = (Integer) session.getAttribute("questionIndex");
    Integer ccObj       = (Integer) session.getAttribute("correctCount");
    Integer hpObj       = (Integer) session.getAttribute("enemyHP");
    @SuppressWarnings("unchecked")
    List<Question> list = (List<Question>) session.getAttribute("currentQuestionList");

    if (qprObj == null || qiObj == null || ccObj == null || hpObj == null || list == null) {
      // 何かが欠けていたら最初の画面へ
      resp.sendRedirect(req.getContextPath() + "/quizSetup");
      return;
    }

    // (3) アンボックス
    int questionsPerRound = qprObj;
    int questionIndex     = qiObj;
    int correctCount      = ccObj;
    int currentHP         = hpObj;

    // (4) 回答を受け取り、正誤判定
    String userAnswer = req.getParameter("answer");
    Question currentQ = list.get(questionIndex - 1);
    if (currentQ.getAnswer().equals(userAnswer)) {
      correctCount++;
      session.setAttribute("correctCount", correctCount);
    }

    // (5) 次の問題へ
    questionIndex++;
    session.setAttribute("questionIndex", questionIndex);
    if (questionIndex <= questionsPerRound) {
      // まだ問題が残っていれば quiz.jsp へ
      RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/jsp/quiz.jsp");
      rd.forward(req, resp);
      return;
    }

    // (6) ラウンド最終回答　→　バリアへ登録
    RoundService.submit(session.getId(), correctCount, questionsPerRound, currentHP);

    // (7) 全員揃うまで waiting.jsp で待機
    if (!RoundService.allSubmitted()) {
      resp.sendRedirect(req.getContextPath() + "/waiting");
      return;
    }

    // (8) 全員揃ったら結果画面へ
    resp.sendRedirect(req.getContextPath() + "/result");
  }
}
