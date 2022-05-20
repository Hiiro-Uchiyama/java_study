package sample_board;

import java.security.MessageDigest;
import java.util.Date;

public class BoardBlock {
	int id;
	public String name;
	private String data;
	public String hash;
	public String previousHash;
	public long timeStamp;
	private int answer;

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

	public String getData() {
		return data; // contentを返す
	}
	
	public void setData(String data) {
		this.data = data; // コンテントを更新する
	}
	
	public String getHash() {
		return hash; // contentを返す
	}
	
	public void setHash(String hash) {
	    this.hash = hash;
	}

	public String getPreviousHash() {
		return previousHash; // contentを返す
	}
	
	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash; // コンテントを更新する
	}
	
	public long gettimeStamp() {
		return timeStamp;
	}
	
	public void settimeStamp(long timeStamp) {
		this.timeStamp = timeStamp; // コンテントを更新する
	}

	//Block コンストラクタ
	public BoardBlock(String name, String data, String previousHash) {
		this.name = name;
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = Hash();
	}

	public class Sha256Algorithm {
		//String型のInput値を入力して、ハッシュ値を返す
		public static String sha256(String input){
			try { //メッセージ・ダイジェストは、任意サイズのデータを取得して固定長のハッシュ値を出力する安全な一方向のハッシュ機能です
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				byte[] hash = digest.digest(input.getBytes("UTF-8"));
				StringBuffer hexStr = new StringBuffer(); // 16進数としてハッシュ値を持つ
				for (int i = 0; i < hash.length; i++) {
					String hex = Integer.toHexString(0xff & hash[i]);
					if(hex.length() == 1) hexStr.append('0');
					hexStr.append(hex);
				}
				return hexStr.toString();
			}
			catch(Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public String Hash() {
		String hash = Sha256Algorithm.sha256( previousHash + data + Long.toString(timeStamp) + Integer.toString(answer));
		return hash;
	}

	public void mineBlock(int difficulty) {
		String targetedHash = new String(new char[difficulty]).replace('\0', '0');  
		while(!hash.substring( 0, difficulty).equals(targetedHash)) {
			answer ++;
			hash = Hash();
		}
		System.out.println("mining!blockHash : " + hash);
	}
}