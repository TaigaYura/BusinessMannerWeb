<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8" />
  <title>Business Manner Quiz</title>
  <!-- コンテキストパスを含めて絶対パスで読み込む -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
  <header class="header">
    <h1 class="text-center heading-xl">ビジネスマナークイズへようこそ！</h1>
    <p class="subtitle text-center">プロのマナーを、楽しくクイズで学ぼう！</p>
  </header>

  <main class="main-content text-center">
    <form action="${pageContext.request.contextPath}/quizSetup" method="get" class="start-form">
      <button class="btn btn--primary btn--lg" type="submit">ゲームスタート</button>
    </form>
  </main>

  <footer class="footer">
    <p class="footer-text text-center">&copy; 2025 Business Manner Quiz</p>
  </footer>
</body>
</html>
