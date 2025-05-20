// webapp/js/lobby.js

document.addEventListener('DOMContentLoaded', () => {
  const ctx       = document.body.dataset.contextPath;
  const statusUrl = `${ctx}/lobby/status`;
  const startUrl  = `${ctx}/lobby/startGame`;

  const countEl   = document.getElementById('playerCount');
  const startBtn  = document.getElementById('startBtn');
  const loader    = document.querySelector('.wrapper.loader');

  // 初期表示
  countEl.textContent   = '0';
  loader.style.display   = 'block';

  // 「ゲームを開始する」ボタンで即遷移
  startBtn.addEventListener('click', () => {
    // ローダー非表示＆直接画面遷移
    loader.style.display = 'none';
    window.location.href = `${ctx}/quiz`;
  });

  // サーバからのポーリングは開始後も継続（必要に応じて）
  function updateLobbyStatus() {
    fetch(statusUrl)
      .then(res => {
        if (!res.ok) throw new Error(`Status ${res.status}`);
        return res.json();
      })
      .then(data => {
        countEl.textContent = data.count;
      })
      .catch(console.error);
  }

  updateLobbyStatus();
  setInterval(updateLobbyStatus, 3000);
});
