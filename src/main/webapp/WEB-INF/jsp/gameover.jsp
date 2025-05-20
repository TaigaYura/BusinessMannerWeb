<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8" />
  <title>ゲーム終了</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
  <header class="header">
    <h1 class="heading-lg text-center">ゲーム終了</h1>
  </header>

  <main class="main-content text-center">
    <div class="result-message">
      <c:choose>
        <c:when test="${win}">
          <p class="message-success">おめでとうございます！あなたの勝利です！🎉</p>
        </c:when>
        <c:otherwise>
          <p class="message-fail">残念……ラウンドが終了しました。</p>
          <p class="mt-1">敵の残りHP：<strong><c:out value="${finalHP}"/></strong></p>
        </c:otherwise>
      </c:choose>
    </div>

    <div class="button-group mt-2">
      <form action="${pageContext.request.contextPath}/quizSetup" method="get">
        <button class="btn btn--primary btn--lg" type="submit">もう一度遊ぶ</button>
      </form>
      <form action="${pageContext.request.contextPath}/" method="get">
        <button class="btn btn--secondary btn--lg ml-1" type="submit">タイトルに戻る</button>
      </form>
    </div>
  </main>

  <footer class="footer">
    <p class="footer-text text-center">&copy; 2025 Business Manner Quiz</p>
  </footer>
</body>
</html>
