<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>ラウンド結果</title>
  <link rel="stylesheet" href="../css/style.css" />
</head>
<body>
  <h1>ラウンド ${sessionScope.currentRound - 1} の結果</h1>
  <p>与えたダメージ：<strong>${roundDamage}</strong></p>
  <p>敵の残りHP：<strong>${remainingHP}</strong></p>

  <c:choose>
    <c:when test="${sessionScope.currentRound <= sessionScope.totalRounds && remainingHP > 0}">
      <form action="${pageContext.request.contextPath}/quiz" method="get">
        <button type="submit">次のラウンドへ</button>
      </form>
    </c:when>
    <c:otherwise>
      <form action="${pageContext.request.contextPath}/gameover" method="get">
        <button type="submit">ゲーム終了</button>
      </form>
    </c:otherwise>
  </c:choose>
</body>
</html>
