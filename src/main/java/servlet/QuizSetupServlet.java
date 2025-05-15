package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import service.LobbyService;
import service.RoundService;

/**
 * クイズ設定画面 (難易度選択)
 */
@WebServlet("/quizSetup")
public class QuizSetupServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LobbyService.reset();
        RoundService.reset();

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        session = request.getSession(true);

        request.getRequestDispatcher("/WEB-INF/jsp/quizSetup.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String difficulty = request.getParameter("difficulty");
        HttpSession session = request.getSession();
        session.setAttribute("difficulty", difficulty);
        response.sendRedirect(request.getContextPath() + "/lobby");
    }
}
