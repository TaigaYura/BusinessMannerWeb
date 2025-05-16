package servlet;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import service.LobbyService;
import service.RoundService;

@WebServlet("/round/status")
public class StatusServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    int submitted = RoundService.getSubmittedCount();
    int total     = LobbyService.getPlayerCount();   // ← 動的に参加人数を取得
    boolean ready = RoundService.allSubmitted();
    resp.setContentType("application/json;charset=UTF-8");
    resp.getWriter().write(
      String.format(
        "{\"submitted\":%d,\"total\":%d,\"ready\":%b}",
        submitted, total, ready
      )
    );
  }
}
