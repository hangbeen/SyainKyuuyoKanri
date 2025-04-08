package menu.attendance.AttendanceQuery.Service;

import menu.attendance.AttendanceQuery.DAO.AttendanceQueryDAO;
import menu.attendance.AttendanceQuery.Model.AttendanceQuery;

import java.util.List;

public class AttendanceQueryService {

    // AttendanceQueryDAO 인스턴스 생성
    // AttendanceQueryDAO インスタンスの生成
    private final AttendanceQueryDAO attendanceQueryDAO = new AttendanceQueryDAO();

    // 월별 조회
    // 月別照会
    public List<AttendanceQuery> getMonthlyAttendance(int year, int month, String department, String position) {
        return attendanceQueryDAO.getMonthlyAttendance(year, month, department, position);
    }

    // 사원 상세 조회
    // 社員詳細照会
    public List<AttendanceQuery> getEmployeeMonthlyAttendance(int employeeId, int year, int month) {
        return attendanceQueryDAO.getEmployeeMonthlyAttendance(employeeId, year, month);
    }

    // 근태 항목 리스트 가져오기
    // 勤怠項目リストを取得する
    public List<String> getAttendanceItems() {
        return attendanceQueryDAO.getAttendanceItems();
    }

    // 휴가 항목 리스트 가져오기
    // 休暇項目リストを取得する
    public List<String> getVacationItems() {
        return attendanceQueryDAO.getVacationItems();
    }

    // 인쇄용 월별 조회 데이터 가져오기 (기존 getMonthlyAttendance와 통합)
    // 印刷用月別データを取得する（既存のgetMonthlyAttendanceと統合）
    public List<AttendanceQuery> getMonthlyAttendanceForPrint(int year, int month, String department, String position) {
        return getMonthlyAttendance(year, month, department, position);
    }

    // 인쇄용 사원 상세 조회 데이터 가져오기 (기존 getEmployeeMonthlyAttendance와 통합)
    // 印刷用社員詳細データを取得する（既存のgetEmployeeMonthlyAttendanceと統合）
    public List<AttendanceQuery> getEmployeeAttendanceForPrint(int employeeId, int year, int month) {
        return getEmployeeMonthlyAttendance(employeeId, year, month);
    }
    
    // 전체 근태 기록 조회
    // 全体の勤怠記録を取得する
    public List<AttendanceQuery> getAllAttendanceRecords() {
        return attendanceQueryDAO.getAllAttendanceRecords();
    }
    
    // 필터링된 근태 기록 조회
    // フィルタリングされた勤怠記録を取得する
    public List<AttendanceQuery> getFilteredAttendanceRecords(
            boolean inputDateCheck, String inputDate, String department, 
            String employeeName, String attendanceGroup, 
            String attendanceItem, String vacationItem, 
            String attendanceStartDate, String attendanceEndDate, String bikou) {
        
        // DAO의 필터링 메서드 호출
        // DAOのフィルタリングメソッド呼び出し
        return attendanceQueryDAO.getFilteredAttendanceRecords(
                inputDateCheck, inputDate, department, employeeName, 
                attendanceGroup, attendanceItem, vacationItem, 
                attendanceStartDate, attendanceEndDate, bikou);
    }
    
}
