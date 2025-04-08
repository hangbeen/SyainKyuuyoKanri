package menu.attendance.AttendanceRecordManagement.Service;

import java.util.List;
import menu.attendance.AttendanceRecordManagement.DAO.AttendanceRecordDAO;
import menu.attendance.AttendanceRecordManagement.Model.AttendanceRecord;

public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRecordDAO attendanceRecordDAO = AttendanceRecordDAO.getInstance();

    // 전체 사원 목록 조회
    // 全社員のリストを取得
    @Override
    public List<AttendanceRecord> getEmployeeList() throws Exception {
        List<AttendanceRecord> attendanceRecords = attendanceRecordDAO.selectAllEmployees();

        // 만약 데이터가 없으면 콘솔에 메시지를 출력
        // データがない場合、コンソールにメッセージを表示
        if (attendanceRecords == null || attendanceRecords.isEmpty()) {
            System.out.println("Service Layer: No records found.");
        } else {
            System.out.println("Service Layer: Fetched " + attendanceRecords.size() + " records.");
        }

        return attendanceRecords;
    }

    // 특정 사원의 근태 기록 조회
    // 特定の社員の勤怠記録を取得
    @Override
    public List<AttendanceRecord> getEmployeeAttendanceRecords(int shainId) {
        try {
            List<AttendanceRecord> records = attendanceRecordDAO.selectEmployeeData(shainId);
            System.out.println("Service Layer: Retrieved " + records.size() + " records for shainId " + shainId);
            return records;
        } catch (Exception e) {
            System.err.println("Service Layer: Error retrieving attendance records for shainId " + shainId);
            e.printStackTrace();
            throw new RuntimeException("Service error: Unable to retrieve attendance records", e);
        }
    }

    // 특정 사원의 근태 기록 및 휴가 데이터 조회
    // 特定の社員の勤怠記録および休暇データを取得
    @Override
    public List<AttendanceRecord> getEmployeeData(int shainId) throws Exception {
        return attendanceRecordDAO.selectEmployeeData(shainId);
    }

    // 근태 기록을 저장
    // 勤怠記録を保存
    @Override
    public void saveAttendanceRecord(AttendanceRecord record) throws Exception {
        attendanceRecordDAO.insertAttendanceRecord(record);
    }

    // 특정 사원의 휴가 상태 조회
    // 特定の社員の休暇状況を取得
    @Override
    public List<AttendanceRecord> getEmployeeLeaveStatus(int shainId) throws Exception {
        return attendanceRecordDAO.selectEmployeeData(shainId);
    }
}
