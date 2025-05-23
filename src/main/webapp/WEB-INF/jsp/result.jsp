<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8"/>
  <title>ラウンド結果</title>
  <link rel="stylesheet"
        href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
  <header class="header">
    <h1 class="heading-lg text-center">
      ラウンド ${sessionScope.currentRound - 1} の結果
    </h1>
    <p class="subtitle text-center">あなたの成果をチェックしよう！</p>
  </header>

  <main class="main-content text-center">
    <div class="enemy-container">
      <img src="${pageContext.request.contextPath}/images/${enemyImage}" alt="敵画像" class="enemy-image" />
    </div>

    <div class="hp-bar mt-2">
      <div class="hp-fill" style="width: ${remainingHP}%;"></div>
    </div>

    <div class="stats-box mt-2">
      <p><strong><c:out value="${correctRate}"/>%</strong> の正答率</p>
      <p>与えたダメージ：<strong><c:out value="${roundDamage}"/></strong></p>
      <p>敵の残りHP：<strong><c:out value="${remainingHP}"/></strong></p>
    </div>

    <div class="button-group mt-2">
      <c:choose>
        <c:when test="${sessionScope.currentRound <= sessionScope.totalRounds 
                       && remainingHP > 0}">
          <form action="${pageContext.request.contextPath}/quiz" method="get">
            <button class="btn btn--secondary btn--lg" type="submit">
              次のラウンドへ
            </button>
          </form>
        </c:when>
        <c:otherwise>
          <form action="${pageContext.request.contextPath}/gameover" method="get">
            <button class="btn btn--primary btn--lg" type="submit">
              ゲーム終了
            </button>
          </form>
        </c:otherwise>
      </c:choose>
    </div>
  </main>

  <footer class="footer">
    <p class="footer-text text-center">&copy; 2025 Business Manner Quiz</p>
  </footer>
</body>
</html>
