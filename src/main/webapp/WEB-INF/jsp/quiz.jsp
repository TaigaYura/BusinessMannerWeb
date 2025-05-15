<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>クイズ画面</title>
  <link rel="stylesheet" href="../css/style.css" />
</head>
<body>
  <h1>ビジネスマナークイズ</h1>

  <c:if test="${not empty sessionScope.currentQuestionList}">
    <p>ラウンド：<strong>${sessionScope.currentRound} / ${sessionScope.totalRounds}</strong></p>
    <p>問題：<strong>${sessionScope.questionIndex} / ${sessionScope.questionsPerRound}</strong></p>

    <!-- 問題文 -->
    <p><strong>
      ${sessionScope.currentQuestionList[sessionScope.questionIndex - 1].text}
    </strong></p>

    <!-- 選択肢（ラジオボタン） -->
    <form action="${pageContext.request.contextPath}/answer" method="post">
      <c:forEach var="opt" items="${sessionScope.currentQuestionList[sessionScope.questionIndex - 1].options}">
        <label>
          <input type="radio" name="answer" value="${opt}" required />
          ${opt}
        </label><br/>
      </c:forEach>
      <br/>
      <button type="submit">回答する</button>
    </form>
  </c:if>

  <c:if test="${empty sessionScope.currentQuestionList}">
    <p>問題が読み込まれていません。</p>
  </c:if>
</body>
</html>
