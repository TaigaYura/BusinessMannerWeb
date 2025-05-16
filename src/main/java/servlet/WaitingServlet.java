package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/waiting")
public class WaitingServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpSession session = req.getSession(false);
    if (session == null) {
      // セッション切れの場合は最初へ
      resp.sendRedirect(req.getContextPath() + "/quizSetup");
      return;
    }
    // waiting.jsp に totalPlayers だけリクエスト属性として渡す（あれば）
    Integer total = (Integer) session.getAttribute("totalPlayers");
    if (total != null) {
      req.setAttribute("totalPlayers", total);
    }
    RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/jsp/waiting.jsp");
    rd.forward(req, resp);
  }
}
