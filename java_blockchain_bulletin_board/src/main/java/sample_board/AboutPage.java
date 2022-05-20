package sample_board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// aboutルーティング
@WebServlet("/about")
public class AboutPage  extends HttpServlet {

	public AboutPage() {
		super();
	}

	// ユーザーからのリクエストに対して呼び出されるメソッド
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // 表示のエンコーディングにはUTF-8を使用する
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/about.jsp"); // about.jspを返す
		rd.forward(request, response); // 表示を行う
	}
}
