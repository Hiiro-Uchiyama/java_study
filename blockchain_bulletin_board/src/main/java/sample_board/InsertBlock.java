package sample_board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/insertblock")
public class InsertBlock extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public InsertBlock() {
    super();
  }
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    String previousHash;
    BoardBlock b = new BoardBlock(null, null, null); // Boardのデータからインスタンスを生成する
    String name = request.getParameter("name"); // HTMLページからの受け取りとインスタンスへのデータセット
    String data = request.getParameter("data"); // HTMLページからの受け取りとインスタンスへのデータセット
    BoardBlockDAO boardblocksDAO = new BoardBlockDAO();
    List<BoardBlock> list = boardblocksDAO.selectAllBoardBlock();
    if(list==null||list.size()==0) {
    	previousHash = "0";
    	System.out.println("if");
    }
    else {
    	BoardBlock lastblock = list.get(list.size() - 1);
        previousHash = lastblock.getHash();
        System.out.println("else");
    }
    BoardBlockDAO boardblockDAO = new BoardBlockDAO(); // 掲示板の管理を行うインスタンス
    b.setName(name);
    b.setData(data);
    b.setPreviousHash(previousHash);
    boardblockDAO.insertBoardBlock(b); // 掲示板へ追加されたデータを追加する
    response.sendRedirect(request.getContextPath() + "/indexblock"); // 追加後はリダイレクトを行う
  }
}