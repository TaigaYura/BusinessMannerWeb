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

@WebServlet("/lobby")
public class LobbyServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpSession session = req.getSession();
    LobbyService.join(session);
    session.setAttribute("totalPlayers", LobbyService.getPlayerCount());
    RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/jsp/lobby.jsp");
    rd.forward(req, resp);
  }
}
