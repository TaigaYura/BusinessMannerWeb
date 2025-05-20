<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>ロビー</title>
  <link rel="stylesheet"
        href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body data-context-path="${pageContext.request.contextPath}">
  <h1 class="heading-lg text-center">ロビー</h1>
  <p class="text-center">
    難易度：
      <strong><c:out value="${sessionScope.difficulty}"/></strong>
    ／ ラウンド：
      <strong><c:out value="${sessionScope.currentRound}"/> /
              <c:out value="${sessionScope.totalRounds}"/></strong>
  </p>

  <button id="startBtn" class="btn btn--primary btn--sm">
    ゲームを開始する
  </button>
  <p class="text-center mt-1">
    参加プレイヤー数：<span id="playerCount">--</span> / 15
  </p>

  <!-- ローディングアニメーション -->
  <div class="wrapper loader">
    <div class="circle"></div>
    <div class="circle"></div>
    <div class="circle"></div>
    <div class="shadow"></div>
    <div class="shadow"></div>
    <div class="shadow"></div>
    <span>Loading</span>
  </div>

  <!-- ここを修正 -->
  <script src="${pageContext.request.contextPath}/js/lobby.js"></script>
</body>
</html>
