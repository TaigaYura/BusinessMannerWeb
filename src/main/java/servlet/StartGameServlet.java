package servlet;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import service.LobbyService;

@WebServlet("/lobby/startGame")
public class StartGameServlet extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    LobbyService.startGame();
    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
  }
}
