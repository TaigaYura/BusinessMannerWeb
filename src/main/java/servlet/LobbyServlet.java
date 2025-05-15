package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import service.LobbyService;

@WebServlet("/lobby")
public class LobbyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        LobbyService.join(session);
        session.setAttribute("totalPlayers", LobbyService.getPlayerCount());
        request.getRequestDispatcher("/WEB-INF/jsp/lobby.jsp")
               .forward(request, response);
    }
}