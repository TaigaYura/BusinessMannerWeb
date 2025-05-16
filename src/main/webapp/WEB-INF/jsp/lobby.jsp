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
  <h1>ロビー</h1>
  <p>難易度：<strong><c:out value="${sessionScope.difficulty}"/></strong></p>
  <p>ラウンド：<strong><c:out value="${sessionScope.currentRound}"/> / <c:out value="${sessionScope.totalRounds}"/></strong></p>
  <button id="startBtn">ゲームを開始する</button>
  <p>参加プレイヤー数：<span id="playerCount">--</span> / 15</p>

  <script src="../js/lobby.js"></script>
  <script type="text/javascript">
  // コンテキストパス取得
  const ctx = '<%= request.getContextPath() %>';
  // ステータス取得／開始フラグ用エンドポイント
  const statusUrl = ctx + '/lobby/status';
  const startUrl  = ctx + '/lobby/startGame';

  // 参加人数と開始フラグをサーバから取得して反映
  function updateLobby() {
    fetch(statusUrl)
      .then(res => res.json())
      .then(data => {
        // 参加人数を表示
        document.getElementById('playerCount').textContent = data.count;
        // 開始済フラグを検知したらクイズ画面へ
        if (data.started) {
          window.location.href = ctx + '/quiz';
        }
      })
      .catch(console.error);
  }

  window.addEventListener('load', () => {
    // 初期表示を 0 に
    document.getElementById('playerCount').textContent = '0';

    // 「ゲームを開始する」ボタン
    document.getElementById('startBtn').addEventListener('click', () => {
      fetch(startUrl, { method: 'POST' })
        .then(() => updateLobby())
        .catch(console.error);
    });

    // ページロード時＆以後３秒ごとにステータス更新
    updateLobby();
    setInterval(updateLobby, 3000);
  });
</script>

</body>
</html>
