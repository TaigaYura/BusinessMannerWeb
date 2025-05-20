// File: webapp/js/quiz.js

document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('quizForm');
  const submitBtn = document.getElementById('submitBtn');
  const options = form.querySelectorAll('input[name="answer"]');

  // 初期状態でボタンを無効に
  submitBtn.disabled = true;

  // 選択肢が選ばれたらボタンを有効に
  options.forEach(opt => {
    opt.addEventListener('change', () => {
      submitBtn.disabled = false;
    });
  });

  // ページ離脱警告（必要ならコメント解除）
  // window.addEventListener('beforeunload', e => {
  //   e.preventDefault();
  //   e.returnValue = '';
  // });
});
