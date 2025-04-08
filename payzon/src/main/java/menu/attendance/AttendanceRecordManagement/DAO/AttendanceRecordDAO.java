package menu.attendance.AttendanceRecordManagement.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.connection.ConnectionProvider;
import menu.attendance.AttendanceRecordManagement.Model.AttendanceRecord;

public class AttendanceRecordDAO {

    private static AttendanceRecordDAO instance = new AttendanceRecordDAO();

    public static AttendanceRecordDAO getInstance() {
        return instance;
    }

    // 모든 사원의 간단한 정보 조회
    // 全従業員の基本情報を取得する
    public List<AttendanceRecord> selectAllEmployees() throws SQLException {
        String sql = "SELECT koyou_keitai AS employment_type, shain_ID AS shain_id, shain_namae AS shain_name, bu AS department, yakushoku "
                   + "FROM shain_touroku "
                   + "ORDER BY shain_ID ASC";

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            List<AttendanceRecord> employeeList = new ArrayList<>();
            while (rs.next()) {
                AttendanceRecord record = new AttendanceRecord();
                // 각 필드를 설정합니다.
                // 各フィールドを設定する
                record.setEmployment_type(rs.getString("employment_type"));
                record.setShain_id(rs.getInt("shain_id"));
                record.setShain_name(rs.getString("shain_name"));
                record.setDepartment(rs.getString("department"));
                record.setYakushoku(rs.getString("yakushoku"));
                employeeList.add(record);
            }
            return employeeList;
        }
    }

    // 특정 사원의 연도별 근태 기록 조회
    // 特定従業員の勤怠記録を年度ごとに取得する
    public List<AttendanceRecord> selectEmployeeData(int employeeId) throws SQLException {
        String sql = "SELECT k.kintai_kiroku_ID, k.kintai_bi, k.kinngaku, k.kintai_zikan, k.bikou, "
                   + "s.shain_namae, s.bu, s.yakushoku, "
                   + "ks.kintai_shurui, kn.kyuuka_nissuu, kn.shiyou_kyuuka_nissuu, kn.nokori_kyuuka_nissuu "
                   + "FROM kintai_kiroku k "
                   + "JOIN shain_touroku s ON k.shain_ID = s.shain_ID "
                   + "JOIN kintai_koumoku_settei ks ON k.kintai_ID = ks.kintai_ID "
                   + "LEFT JOIN kyuuka_nissuu kn ON k.shain_ID = kn.shain_ID "
                   + "WHERE k.shain_ID = ?";

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, employeeId);

            try (ResultSet rs = pstmt.executeQuery()) {
                List<AttendanceRecord> records = new ArrayList<>();
                while (rs.next()) {
                    AttendanceRecord record = new AttendanceRecord();
                    // 각 필드를 ResultSet에서 AttendanceRecord 객체로 설정합니다.
                    // 各フィールドをResultSetからAttendanceRecordオブジェクトに設定する
                    record.setKintai_kiroku_id(rs.getInt("kintai_kiroku_ID"));
                    record.setKintai_bi(rs.getDate("kintai_bi"));
                    record.setKinngaku(rs.getInt("kinngaku"));
                    record.setKintai_zikan(rs.getInt("kintai_zikan"));
                    record.setBikou(rs.getString("bikou"));
                    record.setShain_name(rs.getString("shain_namae"));
                    record.setDepartment(rs.getString("bu"));
                    record.setYakushoku(rs.getString("yakushoku"));
                    record.setKintai_shurui(rs.getString("kintai_shurui"));
                    record.setKyuuka_nissuu(rs.getInt("kyuuka_nissuu"));
                    record.setShiyou_kyuuka_nissuu(rs.getInt("shiyou_kyuuka_nissuu"));
                    record.setNokori_kyuuka_nissuu(rs.getInt("nokori_kyuuka_nissuu"));

                    records.add(record);
                }
                System.out.println("DAO Layer: Retrieved records for employeeId " + employeeId + ": " + records);
                return records;
            }
        } catch (SQLException e) {
            System.err.println("SQL Error while retrieving data for employeeId " + employeeId + ": " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // 새로운 근태 기록 삽입
    // 新しい勤怠記録を挿入する
    public void insertAttendanceRecord(AttendanceRecord record) throws SQLException {
        String sql = "INSERT INTO kintai_kiroku (kintai_kiroku_ID, shain_ID, kintai_ID, kintai_bi, kinngaku, kintai_zikan, bikou) "
                   + "VALUES (kintai_seq.nextval, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, record.getShain_id());
            pstmt.setInt(2, record.getKintai_id());
            pstmt.setDate(3, new java.sql.Date(record.getKintai_bi().getTime()));
            pstmt.setInt(4, record.getKinngaku());
            pstmt.setInt(5, record.getKintai_zikan());
            pstmt.setString(6, record.getBikou());
            pstmt.executeUpdate();
        }
    }

    // 근태 기록 수정
    // 勤怠記録を更新する
    public void updateAttendanceRecord(AttendanceRecord record) throws SQLException {
        String sql = "UPDATE kintai_kiroku SET kintai_ID = ?, kintai_bi = ?, kinngaku = ?, kintai_zikan = ?, bikou = ? "
                   + "WHERE kintai_kiroku_ID = ?";
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, record.getKintai_id());
            pstmt.setDate(2, new java.sql.Date(record.getKintai_bi().getTime()));
            pstmt.setInt(3, record.getKinngaku());
            pstmt.setInt(4, record.getKintai_zikan());
            pstmt.setString(5, record.getBikou());
            pstmt.setInt(6, record.getKintai_kiroku_id());
            pstmt.executeUpdate();
        }
    }

    // 근태 기록 삭제
    // 勤怠記録を削除する
    public void deleteAttendanceRecord(int recordId) throws SQLException {
        String sql = "DELETE FROM kintai_kiroku WHERE kintai_kiroku_ID = ?";
        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, recordId);
            pstmt.executeUpdate();
        }
    }

    // ResultSet에서 AttendanceRecord 객체로 변환
    // ResultSetからAttendanceRecordオブジェクトに変換する
    private AttendanceRecord convertAttendanceRecord(ResultSet rs) throws SQLException {
        return new AttendanceRecord(
            rs.getInt("KINTAI_KIROKU_ID"),    // 근태 기록 ID (勤怠記録ID)
            rs.getInt("SHAIN_ID"),            // 사원 ID (社員ID)
            rs.getInt("KINTAI_ID"),           // 근태 항목 ID (勤怠項目ID)
            rs.getDate("KINTAI_BI"),          // 근태 일자 (勤怠日)
            rs.getInt("KINNGAKU"),            // 금액 (金額)
            rs.getInt("KINTAI_ZIKAN"),        // 근태 시간 (勤怠時間)
            rs.getString("BIKOU"),            // 비고 (備考)
            rs.getString("SHAIN_NAMAE"),      // 사원 이름 (社員名)
            rs.getString("KOYOU_KEITAI"),     // 고용 형태 (雇用形態)
            rs.getString("BU"),               // 부서 (部署)
            rs.getString("YAKUSHOKU"),        // 직위 (職位)
            rs.getString("KINTAI_SHURUI")     // 근태 항목 종류 (勤怠項目種類)
        );
    }
}
