# 概要

Javaでブロックチェーンを実装してみる。

# 機能

- transaction機能
- mining機能
- wallet機能


# 注意

- Sync機能は未実装。
- P2P機能はなし。
	- RestControllerで代用。
	
# 実装予定

- [ ] マイニングをスレッドで自動実行する。
- [ ] Dokcerで擬似的に複数のチェーンサーバーを作成。
- [ ] address作成のアルゴリズムをビットコインと同じフローにする。
- [ ] walletサーバーとチェーンサーバーを切り離す。
- [ ] chainの永続化を行う。
- [ ] エラー処理とレスポンス内容を修正する。
- [ ] toStringのカスタマイズ。

# 利用ライブラリ

- Gson
- ToStringBuilder