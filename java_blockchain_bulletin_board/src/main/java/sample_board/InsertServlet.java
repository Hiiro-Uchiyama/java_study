package sample_board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/insert")
public class InsertServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public InsertServlet() {
    super();
  }
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
  // Exceptionを投げる
  // 特に何も返さない
  throws ServletException, IOException {
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    Board b = new Board(); // Boardのデータからインスタンスを生成する
    b.setName(request.getParameter("name")); // HTMLページからの受け取りとインスタンスへのデータセット
    b.setContent(request.getParameter("content")); // HTMLページからの受け取りとインスタンスへのデータセット
    BoardDAO boardDAO = new BoardDAO(); // 掲示板の管理を行うインスタンス
    boardDAO.insertBoard(b); // 掲示板へ追加されたデータを追加する
    response.sendRedirect(request.getContextPath() + "/index"); // 追加後はリダイレクトを行う
  }
}