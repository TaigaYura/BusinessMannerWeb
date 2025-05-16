// webapp/js/lobby.js

document.addEventListener('DOMContentLoaded', () => {
  // HTML の <body data-context-path> 属性からコンテキストパスを取得
  const ctx = document.body.dataset.contextPath;
  const statusUrl = `${ctx}/lobby/status`;
  const startUrl  = `${ctx}/lobby/startGame`;
  
  const countEl = document.getElementById('playerCount');
  const totalEl = document.getElementById('playerTotal'); // 分母を動的更新している場合
  const startBtn = document.getElementById('startBtn');

  // 初期表示
  countEl.textContent = '0';
  if (totalEl) totalEl.textContent = '0';

  // ゲーム開始ボタンのクリックでサーバにフラグを送信
  startBtn.addEventListener('click', () => {
    fetch(startUrl, { method: 'POST' })
      .then(() => {
        // 送信後すぐに一度ステータスを更新
        updateLobbyStatus();
      })
      .catch(console.error);
  });

  // サーバから参加人数と開始フラグを取得して UI を更新
  function updateLobbyStatus() {
    fetch(statusUrl)
      .then(response => {
        if (!response.ok) throw new Error(`Status ${response.status}`);
        return response.json();
      })
      .then(data => {
        countEl.textContent = data.count;
        if (totalEl) totalEl.textContent = data.total;
        if (data.started) {
          // フラグが立ったらクイズ画面へ遷移
          window.location.href = `${ctx}/quiz`;
        }
      })
      .catch(err => console.error('Lobby polling error:', err));
  }

  // ページロード時と以後3秒ごとにステータスを取得
  updateLobbyStatus();
  setInterval(updateLobbyStatus, 3000);
});
