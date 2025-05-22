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
	// ゲーム開始フラグをサーバに立てる
	fetch(startUrl, { method: 'POST' })
	.then(res => {
	if (!res.ok) throw new Error('startGame failed');
	// ホスト自身もクイズ画面へ
	window.location.href = `${ctx}/quiz`;
	})
	.catch(err => {
	console.error(err);
	// 万が一失敗したら同期遷移
	window.location.href = `${ctx}/quiz`;
	});
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
		// サーバ側でゲーム開始フラグが true なら全員クイズ画面へ
		if (data.started) {
		window.location.href = `${ctx}/quiz`;
		}
      })
      .catch(console.error);
  }

  updateLobbyStatus();
  setInterval(updateLobbyStatus, 3000);
});
