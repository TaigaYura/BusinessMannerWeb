// File: webapp/js/quiz.js

document.addEventListener('DOMContentLoaded', () => {
  const form      = document.getElementById('quizForm');
  const submitBtn = document.getElementById('submitBtn');
  const options   = form.querySelectorAll('input[name="answer"]');
  const labels    = form.querySelectorAll('.difficulty-option');

  // 初期状態ではボタンを無効化
  submitBtn.disabled = true;

  // 選択肢が選ばれたらボタンを有効化
  options.forEach(opt => {
    opt.addEventListener('change', () => {
      submitBtn.disabled = false;
    });
  });

  // 回答ボタンを押したときの処理
  submitBtn.addEventListener('click', e => {
    e.preventDefault();
    if (submitBtn.disabled) return;

    // ① FormData を先に取得（disabled の影響を受けないように）
    const formData = new FormData(form);
    const body     = new URLSearchParams(formData);

    // ② 二重送信防止
    submitBtn.disabled = true;
    // （必要ならラジオにも disabled をかけるが、
    //   その前に FormData を取得しておくことが肝要）

    // ③ Ajax 送信
    fetch(form.action, {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'X-Requested-With': 'XMLHttpRequest'
      },
      body: body
    })
    .then(res => {
      if (!res.ok) throw new Error(res.statusText);
      return res.json();
    })
    .then(data => {
      // フィードバック要素を作成
      const fb = document.createElement('div');
      fb.classList.add('feedback', data.correct ? 'correct' : 'incorrect');
      fb.textContent = data.correct ? '正解！' : '不正解';

      // 選択肢ラベルに正誤クラスを設定
      labels.forEach(lbl => {
        const val = lbl.querySelector('input').value;
        if (data.correct) {
          if (val === data.answer) {
            lbl.classList.add('correct');
          }
        } else {
          if (val === data.answer) {
            lbl.classList.add('incorrect');
          }
          if (val === data.correctAnswer) {
            lbl.classList.add('correct');
          }
        }
      });

      form.appendChild(fb);

      // 1.5秒後にフォーム送信して次の問題へ
      setTimeout(() => form.submit(), 1500);
    })
    .catch(err => {
      console.error('Ajax error:', err);
      // フォールバックで同期送信
      form.submit();
    });
  });
});
