package menu.attendance.AttendanceRecordManagement.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class AttendanceRecord {

    private int kintai_kiroku_id; // 근태 기록 ID (勤怠記録ID)
    private int shain_id; // 사원 ID (社員ID)
    private int kintai_id; // 근태 항목 ID (勤怠項目ID)
    private Date kintai_bi; // 근태 일자 (勤怠日)
    private int kinngaku; // 금액 (金額)
    private int kintai_zikan; // 근태 시간 (勤怠時間)
    private String bikou; // 비고 (備考)

    private String shain_name; // 사원 이름 (社員名)
    private String department; // 부서 (部署)
    private String employment_type; // 고용 형태 (雇用形態)
    private String yakushoku; // 직위 (職位)
    private String kintai_shurui; // 근태 항목 종류 (勤怠項目種類) (Attendance Popup Test)

    // 휴가 현황 관련 항목 (Leave Status Test)
    private String kyuuka_shurui; // 휴가 항목 (休暇項目)
    private int kyuuka_nissuu; // 전체 휴가 일수 (全休暇日数)
    private int shiyou_kyuuka_nissuu; // 사용 휴가 일수 (使用休暇日数)
    private int nokori_kyuuka_nissuu; // 잔여 휴가 일수 (残り休暇日数)
    
    // 기본 생성자
    // デフォルトコンストラクタ
    public AttendanceRecord() {
    }
    
    // 모든 필드를 초기화하는 생성자
    // 全フィールドを初期化するコンストラクタ
    public AttendanceRecord(int kintai_kiroku_id, int shain_id, int kintai_id, Date kintai_bi, int kinngaku, 
                            int kintai_zikan, String bikou, String shain_name, String employment_type, 
                            String department, String yakushoku, String kintai_shurui) {
        this.kintai_kiroku_id = kintai_kiroku_id;
        this.shain_id = shain_id;
        this.kintai_id = kintai_id;
        this.kintai_bi = kintai_bi;
        this.kinngaku = kinngaku;
        this.kintai_zikan = kintai_zikan;
        this.bikou = bikou;
        this.shain_name = shain_name;
        this.employment_type = employment_type;
        this.department = department;
        this.yakushoku = yakushoku;
        this.kintai_shurui = kintai_shurui;
    }

    // Getters and Setters (ゲッターとセッター)
    public int getKintai_kiroku_id() {
        return kintai_kiroku_id;
    }

    public void setKintai_kiroku_id(int kintai_kiroku_id) {
        this.kintai_kiroku_id = kintai_kiroku_id;
    }

    public int getShain_id() {
        return shain_id;
    }

    public void setShain_id(int shain_id) {
        this.shain_id = shain_id;
    }

    public int getKintai_id() {
        return kintai_id;
    }

    public void setKintai_id(int kintai_id) {
        this.kintai_id = kintai_id;
    }

    public Date getKintai_bi() {
        return kintai_bi;
    }

    public void setKintai_bi(Date kintai_bi) {
        this.kintai_bi = kintai_bi;
    }

    public int getKinngaku() {
        return kinngaku;
    }

    public void setKinngaku(int kinngaku) {
        this.kinngaku = kinngaku;
    }

    public int getKintai_zikan() {
        return kintai_zikan;
    }

    public void setKintai_zikan(int kintai_zikan) {
        this.kintai_zikan = kintai_zikan;
    }

    public String getBikou() {
        return bikou;
    }

    public void setBikou(String bikou) {
        this.bikou = bikou;
    }

    public String getShain_name() {
        return shain_name;
    }

    public void setShain_name(String shain_name) {
        this.shain_name = shain_name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmployment_type() {
        return employment_type;
    }

    public void setEmployment_type(String employment_type) {
        this.employment_type = employment_type;
    }

    public String getYakushoku() {
        return yakushoku;
    }

    public void setYakushoku(String yakushoku) {
        this.yakushoku = yakushoku;
    }

    public String getKintai_shurui() {
        return kintai_shurui;
    }

    public void setKintai_shurui(String kintai_shurui) {
        this.kintai_shurui = kintai_shurui;
    }

    public String getKyuuka_shurui() {
        return kyuuka_shurui;
    }

    public void setKyuuka_shurui(String kyuuka_shurui) {
        this.kyuuka_shurui = kyuuka_shurui;
    }

    public int getKyuuka_nissuu() {
        return kyuuka_nissuu;
    }

    public void setKyuuka_nissuu(int kyuuka_nissuu) {
        this.kyuuka_nissuu = kyuuka_nissuu;
    }

    public int getShiyou_kyuuka_nissuu() {
        return shiyou_kyuuka_nissuu;
    }

    public void setShiyou_kyuuka_nissuu(int shiyou_kyuuka_nissuu) {
        this.shiyou_kyuuka_nissuu = shiyou_kyuuka_nissuu;
    }

    public int getNokori_kyuuka_nissuu() {
        return nokori_kyuuka_nissuu;
    }

    public void setNokori_kyuuka_nissuu(int nokori_kyuuka_nissuu) {
        this.nokori_kyuuka_nissuu = nokori_kyuuka_nissuu;
    }

    // toString 메서드 - 객체의 필드를 문자열로 반환
    // toStringメソッド - オブジェクトのフィールドを文字列で返す
    @Override
    public String toString() {
        return "AttendanceRecord{" +
                "kintai_kiroku_id=" + kintai_kiroku_id +
                ", shain_id=" + shain_id +
                ", kintai_id=" + kintai_id +
                ", kintai_bi=" + kintai_bi +
                ", kinngaku=" + kinngaku +
                ", kintai_zikan=" + kintai_zikan +
                ", bikou='" + bikou + '\'' +
                ", shain_name='" + shain_name + '\'' +
                ", department='" + department + '\'' +
                ", employment_type='" + employment_type + '\'' +
                ", yakushoku='" + yakushoku + '\'' +
                ", kintai_shurui='" + kintai_shurui + '\'' +
                ", kyuuka_shurui='" + kyuuka_shurui + '\'' +
                ", kyuuka_nissuu=" + kyuuka_nissuu +
                ", shiyou_kyuuka_nissuu=" + shiyou_kyuuka_nissuu +
                ", nokori_kyuuka_nissuu=" + nokori_kyuuka_nissuu +
                '}';
    }

    // ResultSet에서 AttendanceRecord 객체로 변환하는 메서드
    // ResultSetからAttendanceRecordオブジェクトに変換するメソッド
    private AttendanceRecord convertAttendanceRecord(ResultSet rs) throws SQLException {
        return new AttendanceRecord(
            rs.getInt("kintai_kiroku_ID"), // 근태 기록 ID (勤怠記録ID)
            rs.getInt("shain_ID"),         // 사원 ID (社員ID)
            rs.getInt("kintai_ID"),        // 근태 항목 ID (勤怠項目ID)
            rs.getDate("kintai_bi"),       // 근태 일자 (勤怠日)
            rs.getInt("kinngaku"),         // 금액 (金額)
            rs.getInt("kintai_zikan"),     // 근태 시간 (勤怠時間)
            rs.getString("bikou"),         // 비고 (備考)
            rs.getString("shain_name"),    // 사원 이름 (社員名)
            rs.getString("employment_type"), // 고용 형태 (雇用形態)
            rs.getString("department"),    // 부서 (部署)
            rs.getString("yakushoku"),     // 직위 (職位)
            rs.getString("kintai_shurui")  // 근태 항목 종류 (勤怠項目種類)
        );
    }
}
