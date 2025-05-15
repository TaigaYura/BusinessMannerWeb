package servlet;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import service.LobbyService;

@WebServlet("/lobby/status")
public class LobbyStatusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {
        int count = LobbyService.getPlayerCount();
        boolean started = LobbyService.isGameStarted();

        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(
            "{\"count\":" + count + ",\"started\":" + started + "}"
        );
    }
}
