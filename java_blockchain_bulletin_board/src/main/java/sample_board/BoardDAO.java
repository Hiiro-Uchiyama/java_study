package sample_board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
  private Connection con;
  private PreparedStatement pstmt;
  private ResultSet rs;

  // Boardを表示するメソッド
  public List<Board> selectAllBoard() {
    List<Board> results = new ArrayList<Board>();
    try {
      this.getConnection(); // データベースとの接続
      pstmt = con.prepareStatement("select * from board"); // 全てからboardという名称のデータベースを選択
      rs = pstmt.executeQuery(); // データベースを検索
      while (rs.next()) {
        Board Board = new Board();
        Board.setId(rs.getInt("id")); // idをセット
        Board.setName(rs.getString("name")); // nameをセット
        Board.setContent(rs.getString("content")); // contentをセット
        results.add(Board); // Boardへ追加
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      this.close();
    }
    return results; // 結果の配列を返す
  }
  
  // Boardへ追加するメソッド
  public void insertBoard(Board board) {
    try {
      this.getConnection();
      pstmt = con.prepareStatement("insert into board(name,content) values(?,?)"); // データベースの接続とセット
      pstmt.setString(1, board.getName()); // boardから名称を取得し文字列として格納
      pstmt.setString(2, board.getContent()); // 内容を取得し文字列として格納する
      pstmt.executeUpdate(); // データベースの内容を更新する
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      this.close();
    }
  }
  
  public void getConnection() throws SQLException, ClassNotFoundException {
    Class.forName("com.mysql.jdbc.Driver"); // mysqlをセット
    // ユーザーとパスワードをセットしデータベースへの接続を行う
    con = DriverManager.getConnection("jdbc:mysql://localhost/sample?useSSL=false&characterEncoding=utf8", "user", "password");
  }

  // データベースを正しく閉じるメソッド
  private void close() {
	  // 結果がnullでないなら閉じる
	  if (rs != null) {
		  try {
			  rs.close();
		  } catch (SQLException e) {
			  e.printStackTrace();
		  }
	  }
	  // データベースを保存する
	  if (pstmt != null) {
		  try {
			  pstmt.close();
		  } catch (SQLException e) {
			  e.printStackTrace();
		  }
	  }
	  // コネクションを閉じる
	  if (con != null) {
		  try {
			  con.close();
		  } catch (SQLException e) {
			  e.printStackTrace();
		  }
	  }
  }
}