<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>難易度選択</title>
  <link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/style.css" />

</head>
<body>
  <h1>難易度を選んでください</h1>
  <form action="quizSetup" method="post">
    <label>
      <input type="radio" name="difficulty" value="easy" checked />
      Easy (ラウンド3問×3ラウンド)
    </label><br/>
    <label>
      <input type="radio" name="difficulty" value="normal" />
      Normal (ラウンド5問×5ラウンド)
    </label><br/>
    <label>
      <input type="radio" name="difficulty" value="hard" />
      Hard (ラウンド7問×7ラウンド)
    </label><br/><br/>
    <button type="submit">開始する</button>
  </form>
</body>
</html>
