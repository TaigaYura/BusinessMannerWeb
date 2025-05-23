<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8"/>
  <title>Defeat…</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body class="defeat">
  <header class="header">
    <h1 class="heading-xl text-center">Defeat…</h1>
  </header>

  <main class="main-content text-center">
    <img src="${pageContext.request.contextPath}/images/defeat.png" alt="敗北キャラクター" class="result-image"/>
    <p class="mt-2">あと少しだった…！でも、挑戦してくれてありがとう。</p>
    <form action="${pageContext.request.contextPath}/" method="get" class="mt-2">
      <button type="submit" class="btn btn--defeat btn--lg">もう一度プレイ</button>
    </form>
  </main>

  <footer class="footer">
    <p class="footer-text text-center">&copy; 2025 Business Manner Quiz</p>
  </footer>
</body>
</html>
