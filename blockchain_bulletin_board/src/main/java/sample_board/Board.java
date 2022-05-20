package sample_board;

public class Board {
  int id;
  String name;
  String content;

  public int getId() {
    return id; // idを取得する
  }

  public void setId(int id) {
    this.id = id; // idを更新する
  }

  public String getName() {
    return name; // 名前を返す
  }

  public void setName(String name) {
    this.name = name; // 名前を更新する
  }

  public String getContent() {
    return content; // contentを返す
  }

  public void setContent(String content) {
    this.content = content; // コンテントを更新する
  }
}