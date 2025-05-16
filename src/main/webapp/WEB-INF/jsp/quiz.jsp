<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>クイズ画面</title>
  <link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
  <h1>ビジネスマナークイズ</h1>
  <p>ラウンド：<strong><c:out value="${sessionScope.currentRound}"/> / <c:out value="${sessionScope.totalRounds}"/></strong></p>
  <p>問題：<strong><c:out value="${sessionScope.questionIndex}"/> / <c:out value="${sessionScope.questionsPerRound}"/></strong></p>

  <p><strong>
    <c:out value="${sessionScope.currentQuestionList[sessionScope.questionIndex - 1].text}"/>
  </strong></p>

  <form action="${pageContext.request.contextPath}/answer" method="post">
    <c:forEach var="opt" items="${sessionScope.currentQuestionList[sessionScope.questionIndex - 1].options}">
      <label>
        <input type="radio" name="answer" value="${opt}" required />
        <c:out value="${opt}"/>
      </label><br/>
    </c:forEach>
    <br/>
    <button type="submit">回答する</button>
  </form>
</body>
</html>
