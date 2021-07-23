package controllers;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

         // セッションスコープからタスクのIDを取得して
         // 該当のIDのタスク1件のみをデータベースから取得
         Task t = em.find(Task.class, (Integer)(request.getSession().getAttribute("task_id")));


         // フォームの内容を各フィールドに上書き
         String content = request.getParameter("content");
         t.setContent(content);

         Timestamp currentTime = new Timestamp(System.currentTimeMillis());
         t.setUpdated_at(currentTime);    // 更新日時のみ上書き


         // データベースを更新
         em.getTransaction().begin();
         em.getTransaction().commit();
         em.close();

         // セッションスコープ上の不要になったデータを削除
         request.getSession().removeAttribute("task_id");

         // indexページへリダイレクト
         response.sendRedirect(request.getContextPath() + "/index");
     }

    }

}
