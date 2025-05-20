<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8" />
  <title>難易度選択</title>
  <link rel="stylesheet"
        href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
  <header class="header">
    <h1 class="text-center heading-lg">難易度を選んでください</h1>
  </header>

  <main class="main-content text-center">
    <form action="${pageContext.request.contextPath}/quizSetup"
          method="post"
          class="difficulty-form">
      
      <label class="difficulty-option">
        <input type="radio"
               name="difficulty"
               value="easy"
               checked
               class="radio-input" />
        <span class="option-label">Easy (3問 × 3ラウンド)</span>
      </label>
      
      <label class="difficulty-option">
        <input type="radio"
               name="difficulty"
               value="normal"
               class="radio-input" />
        <span class="option-label">Normal (5問 × 5ラウンド)</span>
      </label>
      
      <label class="difficulty-option">
        <input type="radio"
               name="difficulty"
               value="hard"
               class="radio-input" />
        <span class="option-label">Hard (7問 × 7ラウンド)</span>
      </label>
      
      <button type="submit"
              class="btn btn--primary btn--sm mt-2">
        開始する
      </button>
    </form>
  </main>

  <footer class="footer">
    <p class="footer-text">&copy; 2025 ビジネスマナーゲーム</p>
  </footer>
</body>
</html>
