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
import service.GameService;
import service.RoundService;

@WebServlet("/answer")
public class AnswerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        // セッションから現在の問題リストとインデックスを取得
        @SuppressWarnings("unchecked")
        java.util.List<Question> questions = 
            (java.util.List<Question>) session.getAttribute("currentQuestionList");
        Integer questionIndex = (Integer) session.getAttribute("questionIndex");
        Integer correctCount = (Integer) session.getAttribute("correctCount");
        Integer questionsPerRound = (Integer) session.getAttribute("questionsPerRound");
        Integer totalPlayers = (Integer) session.getAttribute("totalPlayers");

        // フォームから選択された答えを取得
        String[] selected = request.getParameterValues("choice");
        int selectedIndex = (selected != null && selected.length > 0)
                ? Integer.parseInt(selected[0])
                : -1;

        // 正誤判定
        Question current = questions.get(questionIndex - 1);
        if (current.getAnswerIndex() == selectedIndex) {
            correctCount++;
        }
        // セッションに更新
        session.setAttribute("correctCount", correctCount);
        session.setAttribute("questionIndex", questionIndex + 1);

        // RoundService にこのプレイヤーの正答数を提出
        RoundService.submit(session.getId(), correctCount);

        // 全員の提出待ちかチェック
        if (!RoundService.allSubmitted(totalPlayers)) {
            // 待機画面へ
            response.sendRedirect(request.getContextPath() + "/waiting");
            return;
        }

        // 全員提出されたらダメージ計算
        double avgRate = RoundService.calculateAverageRate(questionsPerRound);

        // GameService をセッションから取得 or 新規作成
        GameService gameService = (GameService) session.getAttribute("gameService");
        if (gameService == null) {
            gameService = new GameService();
            session.setAttribute("gameService", gameService);
        }

        // ダメージ計算・適用
        int damage = gameService.calculateDamage(avgRate, questionsPerRound);
        gameService.applyDamage(damage);
        int remainingHP = gameService.getEnemyHP();

        // ビューに渡す
        request.setAttribute("damageDealt", damage);
        request.setAttribute("remainingHP", remainingHP);
        request.setAttribute("averageRate", avgRate);

        // ラウンド結果ページへ
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
        rd.forward(request, response);
    }
}
