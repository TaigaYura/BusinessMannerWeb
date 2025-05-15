package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import service.LobbyService;
import service.RoundService;

/**
 * StatusServlet.java
 * (旧 RoundStatusServlet → 衝突回避のためリネーム)
 *
 * ラウンドごとの回答状況を JSON で返します。
 */
@WebServlet("/lobby/roundStatus")
public class StatusServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // 現在ロビーにいるプレイヤー総数
    int totalPlayers = LobbyService.getPlayerCount();

    // 今ラウンドに回答を送信済みのプレイヤー数
    int submittedCount = RoundService.getSubmittedCount();

    // 全員の回答がそろったかどうか
    boolean allSubmitted = RoundService.allSubmitted(totalPlayers);

    // JSON 用にパラメータを組み立て
    Map<String, Object> status = new HashMap<>();
    status.put("totalPlayers",  totalPlayers);
    status.put("submittedCount", submittedCount);
    status.put("allSubmitted",    allSubmitted);

    // レスポンスを JSON で返却
    resp.setContentType("application/json;charset=UTF-8");
    mapper.writeValue(resp.getWriter(), status);
  }
}
