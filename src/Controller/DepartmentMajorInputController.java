package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Service.DepartmentInputService;
import Service.MajorInputService;

@WebServlet("/DMI/*")
public class DepartmentMajorInputController extends HttpServlet {
    private DepartmentInputService departmentInputService;
    private MajorInputService majorInputService;

    @Override
    public void init() throws ServletException {
        // 학부 입력 서비스 및 전공 입력 서비스 초기화
        departmentInputService = new DepartmentInputService();
        majorInputService = new MajorInputService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        String action = request.getPathInfo();

        switch (action) {
            case "/DepartmentInput.do":
                departmentInputService.departmentInput(request);
                break;
            default:
                System.out.println("jsp 소실");
                break;
        }
    }
}
