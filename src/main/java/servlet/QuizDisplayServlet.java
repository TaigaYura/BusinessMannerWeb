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
import service.QuizService;

@WebServlet("/quiz")
public class QuizDisplayServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession();

    // 難易度選択時にセットしているはずの属性を取得 or デフォルト
    Integer questionsPerRound = (Integer) session.getAttribute("questionsPerRound");
    if (questionsPerRound == null) {
      questionsPerRound = 5;
      session.setAttribute("questionsPerRound", questionsPerRound);
    }
    Integer totalRounds = (Integer) session.getAttribute("totalRounds");
    if (totalRounds == null) {
      totalRounds = 5;
      session.setAttribute("totalRounds", totalRounds);
    }
    Integer currentRound = (Integer) session.getAttribute("currentRound");
    if (currentRound == null) {
      currentRound = 1;
      session.setAttribute("currentRound", currentRound);
    }
    // 初回だけ敵HPを100で初期化
    if (session.getAttribute("enemyHP") == null) {
      session.setAttribute("enemyHP", 100);
    }

    // 問題リストを取得・シャッフル
    QuizService quizService = new QuizService();
    List<Question> questions = quizService.getShuffledQuestions(questionsPerRound);

    // セッションに保存
    session.setAttribute("currentQuestionList", questions);
    session.setAttribute("questionIndex", 1);
    session.setAttribute("correctCount", 0);

    // quiz.jsp へフォワード
    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/quiz.jsp");
    rd.forward(request, response);
  }
}

