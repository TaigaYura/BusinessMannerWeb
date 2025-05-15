<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>ゲーム終了</title>
  <link rel="stylesheet" href="../css/style.css" />
</head>
<body>
  <h1>ゲーム終了</h1>

  <c:choose>
    <c:when test="${win}">
      <p>おめでとうございます！あなたの勝利です！🎉</p>
    </c:when>
    <c:otherwise>
      <p>残念……ラウンドが終了しました。</p>
      <p>敵の残りHP：<strong>${finalHP}</strong></p>
    </c:otherwise>
  </c:choose>

  <br/>
  <!-- もう一度遊ぶ -->
  <form action="${pageContext.request.contextPath}/quizSetup" method="get">
    <button type="submit">もう一度遊ぶ</button>
  </form>

  <!-- タイトルへ戻る -->
  <form action="${pageContext.request.contextPath}/" method="get">
    <button type="submit">タイトルに戻る</button>
  </form>
</body>
</html>
