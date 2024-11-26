package Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Vo.MenuItemVo;

public class MenuItemService {
	
	private Map<String, List<MenuItemVo>> roleMenuMap;

	public MenuItemService() {
		initializeRoleMenuMap();
	}
	
	private void initializeRoleMenuMap() {
		
		roleMenuMap = new HashMap<>(); 
		
		// 관리자 메뉴
        roleMenuMap.put("관리자", Arrays.asList(
        		
            new MenuItemVo("사용자 관리", "/#", Arrays.asList(
            	new MenuItemVo("학생 등록", "/student/studentManage.bo?center=/view_admin/studentManager/studentManage.jsp"),
                new MenuItemVo("학생 조회", "/student/viewStudentList.do?"),
                
                new MenuItemVo("교수 등록", "/professor/professorAdd.bo?center=/view_admin/professorManager/professoradd.jsp"),
                new MenuItemVo("교수 조회", "/professor/professorquiry.do?center=/view_admin/professorManager/professorinquiry.jsp"),
                
                new MenuItemVo("관리자 등록", "/admin/adminjoin.bo?center=/view_admin/adminManager/adminjoin.jsp"),
                new MenuItemVo("관리자 조회", "/admin/managerview.do?center=/view_admin/adminManager/adminquiry.jsp")
            )),
            
            new MenuItemVo("학사 관리", "departmentManage.jsp", Arrays.asList(
                new MenuItemVo("학과 관리", "/major/MajorInput.do"),
                new MenuItemVo("학과 수정/삭제", "/major/searchMajor.do"),

            	new MenuItemVo("강의실 등록", "/classroom/roomRegister.bo?center=/view_admin/roomRegister.jsp"),
            	new MenuItemVo("강의실 조회", "/classroom/roomSearch.bo?center=/view_admin/roomSearch.jsp")
            	
            )),
            
            new MenuItemVo("정보 관리", "/Board/list.bo?center=/view_admin/noticeManage.jsp", Arrays.asList(
                new MenuItemVo("공지사항 관리", "/Board/list.bo?center=/view_admin/noticeManage.jsp"),
                new MenuItemVo("학사일정 관리", "/Board/viewSchedule.bo?center=/view_admin/calendarEdit.jsp")
            ))
        ));
        
        // 학생 메뉴
        roleMenuMap.put("학생", Arrays.asList(
            new MenuItemVo("강의실", "/classroom/classroom.bo?classroomCenter=studentMyCourse.jsp"),
//            , Arrays.asList(
//                new MenuItemVo("수강신청", "courseRegister.jsp"),
//                new MenuItemVo("과제제출", "assignmentSubmit.jsp"),
//                new MenuItemVo("성적조회", "gradeCheck.jsp")
//            )),
            new MenuItemVo("마이페이지", "/student/myPage.bo?center=/view_admin/studentManager/myPage.jsp"),
            new MenuItemVo("공지사항", "notice.jsp"),
            new MenuItemVo("학사일정", "/Board/boardCalendar.bo"),
            new MenuItemVo("중고책방", "/Book/bookpostboard.bo?center=/view_student/booktradingboard.jsp")
        ));

        // 교수 메뉴
        roleMenuMap.put("교수", Arrays.asList(
            new MenuItemVo("강의실", "/classroom/classroom.bo?classroomCenter=professorMyCourse.jsp"),
//            , Arrays.asList(
//                new MenuItemVo("강의 개설", "lectureOpen.jsp"),
//                new MenuItemVo("과제 관리", "assignmentManage.jsp"),
//                new MenuItemVo("공지사항 등록", "noticeRegister.jsp")
//            )),
            new MenuItemVo("강의 관리", "lectureManage.jsp"),
            new MenuItemVo("학생 관리", "studentManage.jsp")
        ));
        
	}
	
	
	public String generateMenuHtml (String userRole, String contextPath) {
		
		StringBuilder htmlLoad = new StringBuilder();		
		List<MenuItemVo> menus = roleMenuMap.get(userRole);
		
		if (menus != null) {
	        htmlLoad.append("<ul>");
	        for (MenuItemVo menu : menus) {
	            // 역할에 따른 디렉토리 경로 설정
//	            String rolePath = "";
//	            if ("관리자".equals(userRole)) {
//	                rolePath = "/view_admin/";
//	            } else if ("교수".equals(userRole)) {
//	                rolePath = "/view_professor/";
//	            } else if ("학생".equals(userRole)) {
//	                rolePath = "/view_student/";
//	            }

	            htmlLoad.append("<li><a href=\"")
	                .append(contextPath)
	                .append(menu.getPage())
	                .append("\">")
	                .append(menu.getName())
	                .append("</a>");

	            if (!menu.getSubMenus().isEmpty()) {
	                htmlLoad.append("<ul>");
	                for (MenuItemVo subMenu : menu.getSubMenus()) {
	                    htmlLoad.append("<li><a href=\"")
	                        .append(contextPath)
	                        .append(subMenu.getPage())
	                        .append("\">")
	                        .append(subMenu.getName())
	                        .append("</a></li>");
	                    
	                    // 하위 메뉴의 하위 메뉴 처리
	                    if (!subMenu.getSubMenus().isEmpty()) {
	                        htmlLoad.append("<ul>");
	                        for (MenuItemVo subSubMenu : subMenu.getSubMenus()) {
	                            htmlLoad.append("<li><a href=\"")
	                                .append(contextPath)
	                                .append(subSubMenu.getPage())
	                                .append("\">")
	                                .append(subSubMenu.getName())
	                                .append("</a></li>");
	                        }
	                        htmlLoad.append("</ul>");
	                    }
	                    
	                }
	                htmlLoad.append("</ul>");
	            }
	            htmlLoad.append("</li>");
	        }
	        htmlLoad.append("</ul>");
	    }
		
        return htmlLoad.toString();
		
	}
}
