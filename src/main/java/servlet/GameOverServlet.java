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

@WebServlet("/gameover")
public class GameOverServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpSession session = req.getSession();
    int hp = (Integer) session.getAttribute("enemyHP");

    // セッションのクリーンアップ
    LobbyService.reset();

    // HP に応じて victory.jsp または defeat.jsp に振り分け
    String view = (hp <= 0) ? "/WEB-INF/jsp/victory.jsp" : "/WEB-INF/jsp/defeat.jsp";

    RequestDispatcher rd = req.getRequestDispatcher(view);
    rd.forward(req, resp);
  }
}
