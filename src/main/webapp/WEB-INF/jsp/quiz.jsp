<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8" />
  <title>ビジネスマナークイズ</title>
  <link rel="stylesheet"
        href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
  <header class="header">
    <h1 class="text-center heading-xl">ビジネスマナークイズ</h1>
  </header>

  <main class="main-content">
    <div class="text-center subtitle mb-2">
      ラウンド：
      <strong><c:out value="${sessionScope.currentRound}"/> /
               <c:out value="${sessionScope.totalRounds}"/></strong>
      &nbsp;&nbsp;問題：
      <strong><c:out value="${sessionScope.questionIndex}"/> /
               <c:out value="${sessionScope.questionsPerRound}"/></strong>
    </div>

    <div class="question-container text-center">
      <p class="question-text heading-md">
        <c:out value="${sessionScope.currentQuestionList[sessionScope.questionIndex - 1].text}"/>
      </p>
    </div>

    <form id="quizForm"
          action="${pageContext.request.contextPath}/answer"
          method="post"
          class="quiz-form text-center">
      <c:forEach var="opt"
                 items="${sessionScope.currentQuestionList[sessionScope.questionIndex - 1].options}">
        <label class="difficulty-option">
          <input type="radio"
                 name="answer"
                 value="${opt}"
                 class="radio-input" />
          <span class="option-label"><c:out value="${opt}"/></span>
        </label>
      </c:forEach>

      <button id="submitBtn"
              type="submit"
              class="btn btn--primary btn--lg mt-2"
              disabled>
        回答する
      </button>
    </form>
  </main>

  <footer class="footer">
    <p class="footer-text">&copy; 2025 ビジネスマナーゲーム</p>
  </footer>

  <script src="${pageContext.request.contextPath}/js/quiz.js"></script>
</body>
</html>
