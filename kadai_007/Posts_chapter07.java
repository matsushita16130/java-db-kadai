package kadai_007;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Posts_chapter07 {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

		Connection con = null;
		PreparedStatement prestatement = null;
		Statement statement = null;
		
		// 投稿データリスト
		String[][] postList = {
			{ "1003", "2023-02-08", "昨日の夜は徹夜でした・・", "13" },
			{ "1002", "2023-02-08", "お疲れ様です！", "12" },
			{ "1003", "2023-02-09", "今日も頑張ります！", "18" },
			{ "1001", "2023-02-09", "無理は禁物ですよ！", "17" },
			{ "1002", "2023-02-10", "明日から連休ですね！", "20" }
		};
		
		try {
			// データベースに接続
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"root"
			);
			
			System.out.println("データベース接続成功：" + con);
			
			// SQLクエリを準備
			String sql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES(?,?,?,?);";
			prestatement = con.prepareStatement(sql);	
			
			// SQLクエリを実行
			System.out.println("レコード追加を実行します");
			int rowCnt = 0;
			for( int i = 0; i < postList.length; i++) {
				prestatement.setString(1, postList[i][0]);
				prestatement.setString(2, postList[i][1]);
				prestatement.setString(3, postList[i][2]);
				prestatement.setString(4, postList[i][3]);
				rowCnt = prestatement.executeUpdate();				
			}
			System.out.println( rowCnt + "件のレコードが追加されました");
			
			statement = con.createStatement();
			String selectSql = "SELECT posted_at, post_content, likes FROM posts WHERE user_id = 1002;";
			ResultSet result = statement.executeQuery(selectSql);
			System.out.println("ユーザーIDが1002のレコードを検索しました");
			while(result.next()) {
				//int user_id = result.getInt("user_id");
				Date posted_at = result.getDate("posted_at");
				String post_content = result.getString("post_content");
				int likes = result.getInt("likes");
				System.out.println(result.getRow() + "件目：投稿日時=" + posted_at + "／投稿内容=" + post_content + "／いいね数=" + likes);
			}
			
		} catch(SQLException e) {
			System.out.println("エラー発生" + e.getMessage());
		} finally {
			// 使用したオブジェクトを解放
			if( statement != null ) {
				try { statement.close(); } catch(SQLException ignore) {}
			}
			if( con != null ) {
				try { con.close(); } catch(SQLException ignore) {};
			}
		}
	}

}
