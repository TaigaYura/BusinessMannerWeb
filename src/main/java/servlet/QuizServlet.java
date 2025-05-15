package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Question;
import service.QuizService;

/**
 * 問題表示用サーブレット
 */
@WebServlet("/quiz")
public class QuizServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer questionsPerRound = (Integer) session.getAttribute("questionsPerRound");
        if (questionsPerRound == null) {
            questionsPerRound = 5;
            session.setAttribute("questionsPerRound", questionsPerRound);
        }

        QuizService quizService = new QuizService();
        java.util.List<Question> questions = quizService.getShuffledQuestions(questionsPerRound);

        session.setAttribute("currentQuestionList", questions);
        session.setAttribute("questionIndex", 1);
        session.setAttribute("correctCount", 0);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/quiz.jsp");
        rd.forward(request, response);
    }
}
