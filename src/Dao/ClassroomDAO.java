package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import Vo.ClassroomVo;
import Vo.CourseVo;


public class ClassroomDAO {


	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;
	
	//컨넥션풀 얻는 생성자
	public ClassroomDAO() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:/comp/env/jdbc/edumanager");
			
		}catch(Exception e) {
			System.out.println("커넥션풀 얻기 실패 : " + e.toString());
		}
	}
	
	//자원해제(Connection, PreparedStatment, ResultSet)기능의 메소드 
	private void closeResource() {
		try {
				if(con != null) { con.close(); }
				if(pstmt != null) { pstmt.close(); }
				if(rs != null) { rs.close(); }
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 현재 DB의 majorInformation테이블에 저장된 값들 중 majorCode와 일치하는 열 조회
	public String getMajorNameInfo(String majorCode) {
		
		String result = null;
		
		try {
			
			con = ds.getConnection();
			
			String sql = "select * from majorinformation where majorcode=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, majorCode);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getString("majorname");
			}
			
		} catch (Exception e) {
			System.out.println("ClassroomDAO의 getMajorInfo메소드에서 오류 ");
			e.printStackTrace();
		} finally {
			closeResource();
		}
		
		return result;
	}

	//------------
	// DB의 classroom 테이블에 저장된 모든 열 조회
	public ArrayList<ClassroomVo> getClassroomAllInfo() {
		
		ArrayList<ClassroomVo> list = new ArrayList<ClassroomVo>();
		ClassroomVo vo;
		
		try {
			
			con = ds.getConnection();
			
			String sql = "select * from classroom";
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vo = new ClassroomVo(rs.getString("room_id"),
						 rs.getInt("capacity"),
						 rs.getString("equipment"));
				
				list.add(vo);
			}
			
		} catch (Exception e) {
			System.out.println("ClassroomDAO의 getClassroomAllInfo메소드에서 오류 ");
			e.printStackTrace();
		} finally {
			closeResource(); // 자원 해제
		}
		
		return list;
	}

	
	//------------
	// DB의 classroom 테이블에 교수가 등록한 강의를 저장
	public int registerInsertCourse(String course_name, String majorcode, String room_id, String professor_id) {

		int result = 0;
		String sql = null;
		
		try {
			
			con = ds.getConnection();
			
			sql = "INSERT INTO course (course_name, professor_id, majorcode, room_id) VALUES (?, ?, ?, ?)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, course_name);
            pstmt.setString(2, professor_id);
            pstmt.setString(3, majorcode);
            pstmt.setString(4, room_id);
            
			result = pstmt.executeUpdate();
			
			return result;
			
		} catch (Exception e) {
			System.out.println("ClassroomDAO의 registerInsertCourse메소드에서 오류 ");
			e.printStackTrace();
		} finally {
			closeResource(); // 자원 해제
		}
		
		return result;
	}

	//------------
	// 해당 교수의 강의를 DB에서 찾아 반환하는 함수
	public ArrayList<CourseVo> courseSearch(String professor_id) {
		
		ArrayList<CourseVo> courseList = new ArrayList<CourseVo>();
		CourseVo course;
		
		try {
			
			con = ds.getConnection();
			
			String sql = "select c.course_id, c.course_name, r.room_id, r.capacity, r.equipment from course c "
					   + "inner join classroom r on c.room_id = r.room_id "
					   + "where professor_id=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, professor_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				course = new CourseVo();
				course.setCourse_id(rs.getString("course_id"));
				course.setCourse_name(rs.getString("course_name"));
				
				// ClassroomVo 객체 생성 후 강의실 정보를 설정
				ClassroomVo classroom = new ClassroomVo();
				classroom.setRoom_id(rs.getString("room_id"));
				classroom.setCapacity(rs.getInt("capacity"));
				classroom.setEquipment(rs.getString("equipment"));
				
				// CourseVo에 ClassroomVo 객체를 설정
				course.setClassroom(classroom);

				courseList.add(course);
			}
			
		} catch (Exception e) {
			System.out.println("ClassroomDAO의 courseSearch메소드에서 오류 ");
			e.printStackTrace();
		} finally {
			closeResource(); // 자원 해제
		}
		
		return courseList;
	}

	//-----------
	// DB에서 강의를 삭제하는 함수
	public int courseDelete(String course_id) {
		
		int result = 0;
		
		String sql = null;
		
		try {
			
			con = ds.getConnection();
			
			sql = "DELETE FROM course WHERE course_id=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, course_id);
            
			result = pstmt.executeUpdate();
			
			return result;
			
		} catch (Exception e) {
			System.out.println("ClassroomDAO의 courseDelete메소드에서 오류 ");
			e.printStackTrace();
		} finally {
			closeResource(); // 자원 해제
		}
		
		return result;
		
	}

	//-----------
	// 교수가 강의를 수정하기 위한 함수
	public int updateCourse(String course_id, String course_name, String room_id) {
		
		int result = 0;
		
		String sql = null;
		
		try {
			
			con = ds.getConnection();
			
			sql = "UPDATE course SET "
					+ "course_name=?, room_id=? "
					+ "WHERE course_id=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, course_name);
			pstmt.setString(2, room_id);
			pstmt.setString(3, course_id);
            
			result = pstmt.executeUpdate();
			
			return result;
			
		} catch (Exception e) {
			System.out.println("ClassroomDAO의 updateCourse메소드에서 오류 ");
			e.printStackTrace();
		} finally {
			closeResource(); // 자원 해제
		}
		
		return result;
		
	}

	//----------
	// classroom 테이블에 강의실을 등록하기 위한 함수
	public int roomRegister(String room_id, String capacity, String[] equipment) {

		int result = 0;
		String sql = null;
		
		try {
			
			con = ds.getConnection();
			
			// equipment 배열을 문자열로 변환
	        String equipmentStr = String.join(",", equipment);
			
			sql = "INSERT INTO classroom (room_id, capacity, equipment)VALUES (?, ?, ?)";
			
			pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, room_id);
	        pstmt.setString(2, capacity);
	        pstmt.setString(3, equipmentStr);
            
			result = pstmt.executeUpdate();
			
			return result;
			
		} catch (Exception e) {
			System.out.println("ClassroomDAO의 roomRegister메소드에서 오류 ");
			e.printStackTrace();
		} finally {
			closeResource(); // 자원 해제
		}
		
		return result;
		
	}

	//-----------
	// 관리자가 강의실 조회하기 위한 함수 
	public ArrayList<ClassroomVo> roomShearch() {
		
		ArrayList<ClassroomVo> courseList = new ArrayList<ClassroomVo>();
		
		try {
			
			con = ds.getConnection();
			
			String sql = "select * from classroom";
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				// ClassroomVo 객체 생성 후 강의실 정보를 설정
				ClassroomVo classroom = new ClassroomVo();
				classroom.setRoom_id(rs.getString("room_id"));
				classroom.setCapacity(rs.getInt("capacity"));
				classroom.setEquipment(rs.getString("equipment"));

				courseList.add(classroom);
			}
			
		} catch (Exception e) {
			System.out.println("ClassroomDAO의 roomSearch메소드에서 오류 ");
			e.printStackTrace();
		} finally {
			closeResource(); // 자원 해제
		}
		
		return courseList;
	}

	//-----------
	// 관리자가 강의실의 정보를 수정하기 위해 호출하는 함수
	public int updateRoom(String room_id, String capacity, String room_equipment) {
		
		int result = 0;
		
		String sql = null;
		
		try {
			
			con = ds.getConnection();
			
			sql = "UPDATE classroom SET "
					+ "capacity=?, equipment=? "
					+ "WHERE room_id=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(capacity));
			pstmt.setString(2, room_equipment);
			pstmt.setString(3, room_id);
            
			result = pstmt.executeUpdate();
			
			return result;
			
		} catch (Exception e) {
			System.out.println("ClassroomDAO의 updateCourse메소드에서 오류 ");
			e.printStackTrace();
		} finally {
			closeResource(); // 자원 해제
		}
		
		return result;
		
	}

	//-----------
	// 관리자가 강의실의 정보를 DB에서 삭제하기 위해 호출하는 함수
	public int deleteRoom(String room_id) {
		
		int result = 0;
		
		String sql = null;
		
		try {
			
			con = ds.getConnection();
			
			sql = "DELETE FROM classroom WHERE room_id=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, room_id);
            
			result = pstmt.executeUpdate();
			
			return result;
			
		} catch (Exception e) {
			System.out.println("ClassroomDAO의 deleteRoom메소드에서 오류 ");
			e.printStackTrace();
		} finally {
			closeResource(); // 자원 해제
		}
		
		return result;
	}

}
