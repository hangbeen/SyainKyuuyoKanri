package menu.attendance.AttendanceRecordManagement.Service;

import java.util.List;
import menu.attendance.AttendanceRecordManagement.Model.AttendanceRecord;

public interface AttendanceService {

    // 전체 사원 목록을 조회하는 메서드
    // 全社員のリストを取得するメソッド
    List<AttendanceRecord> getEmployeeList() throws Exception;

    // 사원별 근태 기록 조회 메서드
    // 社員別の勤怠記録を取得するメソッド
    List<AttendanceRecord> getEmployeeAttendanceRecords(int shainId) throws Exception;

    // 사원별 근태 기록 및 휴가 상태 조회 메서드
    // 社員別の勤怠記録および休暇状況を取得するメソッド
    List<AttendanceRecord> getEmployeeData(int shainId) throws Exception;

    // 근태 기록을 저장하는 메서드
    // 勤怠記録を保存するメソッド
    void saveAttendanceRecord(AttendanceRecord record) throws Exception;

    // 사원별 휴가 상태를 조회하는 메서드
    // 社員別の休暇状況を取得するメソッド
    List<AttendanceRecord> getEmployeeLeaveStatus(int shainId) throws Exception;
}
