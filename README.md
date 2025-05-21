## Business Manner Quiz Web Application

ビジネスマナーに関するクイズを複数人で協力プレイできる Java Servlet＋JSP の Web アプリケーションです。
ラウンド制・問題数制・回答バリアによる同期機能を備え、1 人プレイもマルチプレイも可能です。

---

## 🎯 主な機能

* **難易度選択**
  3 段階（easy／normal／hard）の中から選択
* **ロビー機能（最大 15 人）**
  全員が「ゲーム開始」をクリックするとスタート
* **ラウンド制クイズ**

  * ラウンド数／1 ラウンドあたりの問題数は難易度依存
  * 各問題終了後、全員の回答完了でラウンド結果へ
* **協力プレイ**

  * 各ラウンドの全員の正答率の平均でモンスターにダメージ
  * 1 人でも、マルチでも同じ画面遷移
* **正誤フィードバック**

  * Ajax 送信で回答→正解時は緑枠・「正解！」
  * 不正解時は赤枠（自分）・緑枠（正解）・「不正解」

---

## 📁 フォルダー構成

```
BusinessMannerWeb/                    ← プロジェクトルート
├─ src/
│  └─ main/
│     ├─ java/                       ← Java ソース
│     │  ├─ servlet/                ← コントローラー層
│     │  ├─ service/                ← ビジネスロジック層
│     │  └─ model/                  ← ドメインモデル層
│     ├─ resources/                 ← クラスパス静的リソース
│     │  └─ data/quizdata.json      ← 問題データ(JSON)
│     └─ webapp/                    ← Web アプリケーションルート
│        ├─ index.jsp               ← トップ画面
│        ├─ css/                     
│        │  └─ style.css             ← 共通スタイル
│        ├─ js/
│        │  ├─ lobby.js              ← ロビー用ポーリング
│        │  └─ quiz.js               ← クイズ用 Ajax 制御
│        └─ WEB-INF/
│           ├─ lib/                  ← 外部ライブラリ JAR
│           │  ├─ gson-2.10.1.jar
│           │  ├─ jackson-core.jar
│           │  ├─ jackson-databind.jar
│           │  ├─ jackson-annotations.jar
│           │  └─ jakarta.servlet.jsp.jstl-3.0.1.jar
│           └─ jsp/                  ← JSP テンプレート
│              ├─ quizSetup.jsp
│              ├─ lobby.jsp
│              ├─ waiting.jsp
│              ├─ quiz.jsp
│              ├─ result.jsp
│              └─ gameover.jsp
└─ README.md                        ← 本ファイル
```

---

## ⚙️ 環境・依存ライブラリ

* **Java 17+**
* **Apache Tomcat 10** (Jakarta Servlet 6.0)
* **Jackson**

  * `jackson-core`
  * `jackson-databind`
  * `jackson-annotations`
* **Gson** (`gson-2.10.1.jar`)
* **JSTL 3.0**

  * `jakarta.servlet.jsp.jstl-3.0.1.jar`
  * `jakarta.servlet.jsp.jstl-api-3.0.2.jar`

---

## 🚀 セットアップ＆起動

1. Eclipse（または任意の IDE）で**Dynamic Web Project** としてインポート
2. Tomcat サーバーに **BusinessMannerWeb** をデプロイ
3. プロジェクトをクリーン & ビルド → Tomcat を起動
4. ブラウザで以下にアクセスしてゲームを開始：

   ```
   ```

[http://localhost:8080/BusinessMannerWeb/](http://localhost:8080/BusinessMannerWeb/)

```

---

## 🛠️ 開発者向けメモ

- **JSP–JSTL**  
  - `quiz.jsp` などでは `<%@ taglib prefix="c" uri="http://xmlns.jcp.org/jsp/jstl/core" %>` を使用
  - `WEB-INF/lib` に配置した JAR の TLD `<uri>` を JSP で合わせること
- **Ajax フロー**  
  - `quiz.js` がフォーム送信をキャッチ → `/answer` へ POST(XHR)  
  - `AnswerServlet` は `X-Requested-With` ヘッダ or `Accept: application/json` を見て JSON レスポンス  
  - クライアント側で正誤ハイライト → 2 秒後に次の問題へ同期リロード
- **マルチプレイ同期**  
  - `LobbyStatusServlet` / `StatusServlet` でポーリング  
  - `RoundService` によるバリア同期（全員回答完了検知）
- **問題データの追加**  
  - `resources/data/quizdata.json` に JSON 配列で新問題を追加  
  - `Question` モデルの `@JsonProperty` でマッピング

---

## 📝 ライセンス

MIT License  
詳細は `LICENSE` ファイルを参照してください。

```
