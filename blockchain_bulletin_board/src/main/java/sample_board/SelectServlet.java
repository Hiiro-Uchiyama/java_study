package sample_board;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/index")
public class SelectServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public SelectServlet() {
    super();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8"); // 表示エンコーディングはUTF-8を採用
    BoardDAO boardDAO = new BoardDAO(); // 管理するためのインスタンスを生成
    List<Board> list = boardDAO.selectAllBoard(); // 掲示板のデータをリストに挿入する
    request.setAttribute("list", list); // リクエストに対してリストをセットする
    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/board.jsp"); // board.jspを返す
    rd.forward(request, response); // 表示
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response); // POSTメソッドの後にdoGetと同じ処理を行う
  }
}