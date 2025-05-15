<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>ロビー画面</title>
  <!-- コンテキストパスを含めた絶対パス指定 -->
  <link rel="stylesheet"
        href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
  <h1>ロビー</h1>
  <p>難易度：<strong><c:out value="${sessionScope.difficulty}" /></strong></p>
  <p>ラウンド：<strong>
    <c:out value="${sessionScope.currentRound}" /> /
    <c:out value="${sessionScope.totalRounds}" />
  </strong></p>
  <p>参加プレイヤー数：
    <span id="playerCount"><c:out value="${playerCount}" /></span> / 15
  </p>
  <button id="startBtn">ゲームを開始する</button>

  <script>
  // コンテキストパスを先頭で取得
  const context = '<%= request.getContextPath() %>';  // 例: "/BusinessMannerWeb"
  const statusUrl = context + '/lobby/status';
  const startUrl  = context + '/lobby/startGame';

  // ロビーの参加数とゲーム開始フラグをポーリング
  function updateLobby() {
    fetch(statusUrl)
      .then(res => res.json())
      .then(data => {
        // 参加人数を更新
        document.getElementById('playerCount').textContent = data.count;
        // ゲーム開始フラグを検知したらクイズ画面へ
        if (data.started) {
          console.log('【Lobby】ゲーム開始検知 → クイズ画面へ');
          window.location.href = context + '/quiz';
        }
      })
      .catch(err => console.error('ロビーステータス取得エラー', err));
  }

  // ページ読み込み時と 3 秒おきにポーリング開始
  window.addEventListener('load', () => {
    updateLobby();
    setInterval(updateLobby, 3000);
  });

  // 「ゲームを開始する」ボタンクリックで POST
  document.getElementById('startBtn').addEventListener('click', () => {
    fetch(startUrl, { method: 'POST' })
      .then(res => console.log('startGame status=', res.status))
      .catch(err => console.error('ゲーム開始リクエストエラー', err));
  });
</script>

</body>
</html>
