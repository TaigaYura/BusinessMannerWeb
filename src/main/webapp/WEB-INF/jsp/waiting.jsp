<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>回答待機中</title>
  <link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body data-context-path="${pageContext.request.contextPath}">
  <h1>ラウンド完了！<br/>全員の回答を待っています…</h1>
  <p>
    提出済み：<span id="submitted">--</span>
    / <span id="total">--</span>
  </p>

  <script type="text/javascript">
    // ① コンテキストパスを取得
    const ctx = '<%= request.getContextPath() %>';
    const statusUrl = ctx + '/round/status';

    // ② ポーリング関数
    function tick() {
      fetch(statusUrl)
        .then(res => {
          if (!res.ok) throw new Error('Status ' + res.status);
          return res.json();
        })
        .then(data => {
          console.log('round/status →', data);
          document.getElementById('submitted').textContent = data.submitted;
          document.getElementById('total').textContent     = data.total;
          if (data.ready) {
            window.location.href = ctx + '/result';
          }
        })
        .catch(err => console.error('Polling error:', err));
    }

    // ③ DOM 完全ロード後に即・定期実行
    document.addEventListener('DOMContentLoaded', () => {
      tick();
      setInterval(tick, 2000);
    });
  </script>
</body>
</html>
