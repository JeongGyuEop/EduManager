package Controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.MenuItemService;

@WebServlet("/menu/*")
public class MenuItemController extends HttpServlet {
	private MenuItemService menuService;
	
	@Override
    public void init() {
        menuService = new MenuItemService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
    	doHandel(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
    	doHandel(request, response);
    }
    
    protected void doHandel(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        String nextPage = null; // 포워딩 경로가 저장될 변수
        String center = null;
        
        String action = request.getPathInfo(); // 2단계 요청주소
        
        switch(action) {
        
        case "/topside.do":
            
            center = request.getParameter("center");
        	request.setAttribute("center", center);
            
            nextPage = "/main.jsp";
            
        
        }

        // 디스패처 방식 포워딩(재요청)
     	RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
     	dispatch.forward(request, response);
        
    }
}
