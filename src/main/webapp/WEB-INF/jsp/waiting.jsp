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
  <h1 class="heading-md text-center">
    ラウンド完了！<br/>全員の回答を待っています…
  </h1>
  <p class="text-center">
    提出済み：<span id="submitted">--</span>
    / <span id="total">--</span>
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

  <script type="text/javascript">
  document.addEventListener('DOMContentLoaded', () => {
    const ctx    = '<%= request.getContextPath() %>';
    const url    = ctx + '/round/status';
    const loader = document.querySelector('.wrapper.loader');

    loader.style.display = 'block'; // ローダーを表示

    function tick() {
      fetch(url)
        .then(res => {
          if (!res.ok) throw new Error('Status ' + res.status);
          return res.json();
        })
        .then(data => {
          document.getElementById('submitted').textContent = data.submitted;
          document.getElementById('total').textContent     = data.total;
          if (data.ready) {
            loader.style.display = 'none';               // ローダーを隠す
            window.location.href = ctx + '/result';
          }
        })
        .catch(err => console.error('Polling error:', err));
    }

    tick();
    setInterval(tick, 2000);
  });
</script>

</body>
</html>
