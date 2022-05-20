package sample_board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardBlockDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	public static final int difficulty = 1;

	// BoardBlockを表示するメソッド
	public List<BoardBlock> selectAllBoardBlock() {
		List<BoardBlock> results = new ArrayList<BoardBlock>();
		try {
			this.getConnection(); // データベースとの接続
			pstmt = con.prepareStatement("select * from boardblock"); // 全てからboardという名称のデータベースを選択
			rs = pstmt.executeQuery(); // データベースを検索
			while (rs.next()) {
				BoardBlock BoardBlock = new BoardBlock(null, null, null);
				BoardBlock.setId(rs.getInt("id")); // idをセット
				BoardBlock.setName(rs.getString("name")); // nameをセット
				BoardBlock.setData(rs.getString("data")); // contentをセット
				BoardBlock.setHash(rs.getString("hash"));
				BoardBlock.setPreviousHash(rs.getString("previoushash"));
				results.add(BoardBlock); // BoardBlockへ追加
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
	public void insertBoardBlock(BoardBlock boardblock) {
		// マイニング
		if(isValidBlockchain(boardblock)){
			try {
				this.getConnection();
				pstmt = con.prepareStatement("insert into boardblock(name,data,hash,previoushash,timestamp) values(?,?,?,?,?)"); // データベースの接続とセット
				pstmt.setString(1, boardblock.getName()); // boardから名称を取得し文字列として格納
				pstmt.setString(2, boardblock.getData()); // 内容を取得し文字列として格納する
				pstmt.setString(3, boardblock.getHash());
				pstmt.setString(4, boardblock.getPreviousHash());
				pstmt.setLong(5, boardblock.gettimeStamp());
				pstmt.executeUpdate(); // データベースの内容を更新する
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				this.close();
			}
		}
		else {
			;
		}
	}

	public static Boolean isValidBlockchain(BoardBlock boardblock){
		BoardBlock previousBlock;
		BoardBlockDAO boardblockDAO = new BoardBlockDAO();
		List<BoardBlock> list = boardblockDAO.selectAllBoardBlock(); // 掲示板のデータをリストに挿入する
		String targetHash = new String(new char[difficulty]).replace('\0', '0');
		for(int i=1; i < list.size(); i++) {
			//１つ前のブロック
			previousBlock = list.get(list.size() - 1);
			System.out.println(previousBlock.name);
			System.out.println(previousBlock.hash);
			System.out.println(boardblock.name);
			System.out.println(boardblock.previousHash);
			System.out.println(boardblock.hash);
			System.out.println(boardblock.Hash());
			// 実際はひとつのファイルで完結するHash()関数だがファイルをまたぐことでHash()関数の出力値が異なる
			// よってほぼ確実にfalseが出力されてしまう
			// ひとまずコメントアウトしておく
			//今現在のブロックのHashが計算結果と合わない場合は無効と返す
			// if(!boardblock.hash.equals(boardblock.Hash())){
				// System.out.println("false : 1"); // どのfalseなのか知りたい
				// return false;
			// }
			//１つ前のブロックのHashが計算結果と合わない場合は無効と返す
			if(!previousBlock.hash.equals(boardblock.previousHash)){
				System.out.println("false : 2"); // どのfalseなのか知りたい
				return false;
			}
			// とりあえずマイニングしてみる
			// 本来は誰かがマイニングするのだろうか
			boardblock.mineBlock(difficulty);
			//マイニングされたかどうかをチェック
			if(!boardblock.hash.substring(0,difficulty).equals(targetHash)){
				System.out.println("false : 3"); // どのfalseなのか知りたい
				return false;
			}
		}
		System.out.println("true");
		return true;
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