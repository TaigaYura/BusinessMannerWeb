<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>全員の回答待機</title>
</head>
<body>
  <h1>ラウンド完了！全員の回答を待っています…</h1>
  <p>提出済み：<span id="sub">0</span> / <span id="tot">${sessionScope.totalPlayers}</span></p>

  <script>
    const statusUrl = '${pageContext.request.contextPath}/round/status';
    function tick() {
      fetch(statusUrl)
        .then(response => response.json())
        .then(data => {
          document.getElementById('sub').textContent = data.submitted;
          if (data.ready) {
            window.location.href = '${pageContext.request.contextPath}/result';
          }
        })
        .catch(console.error);
    }
    setInterval(tick, 2000);
    tick();
  </script>
</body>
</html>
