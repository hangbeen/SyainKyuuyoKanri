package menu.attendance.AttendanceQuery.Model;

import java.util.Date;

public class AttendanceQuery {

    // 사원 ID
    // 社員ID
    private int employeeId;
    
    // 사원 이름
    // 社員名
    private String employeeName;
    
    // 부서
    // 部署
    private String department;
    
    // 직위
    // 職位
    private String position;
    
    // 근태 일자
    // 勤怠日
    private Date attendanceDate;
    
    // 근태 항목 ID
    // 勤怠項目ID
    private int attendanceType;
    
    // 금액
    // 金額
    private int amount;
    
    // 근태 시간
    // 勤怠時間
    private int hours;
    
    // 입력 일자
    // 入力日
    private Date inputDate;
    
    // 고용 형태
    // 雇用形態
    private String category;
    
    // 근태 항목명
    // 勤怠項目名
    private String attendanceItem;
    
    // 근태 기간
    // 勤怠期間
    private String attendancePeriod;
    
    // 근태 일수
    // 勤怠日数
    private int attendanceDays;
    
    // 비고
    // 備考
    private String bikou;

    // Getter와 Setter
    // ゲッターとセッター
    public String getBikou() {
        return bikou;
    }

    public void setBikou(String bikou) {
        this.bikou = bikou;
    }

    public int getAttendanceDays() {
        return attendanceDays;
    }

    public void setAttendanceDays(int attendanceDays) {
        this.attendanceDays = attendanceDays;
    }

    public String getAttendancePeriod() {
        return attendancePeriod;
    }

    public void setAttendancePeriod(String attendancePeriod) {
        this.attendancePeriod = attendancePeriod;
    }

    public String getAttendanceItem() {
        return attendanceItem;
    }

    public void setAttendanceItem(String attendanceItem) {
        this.attendanceItem = attendanceItem;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public int getAttendanceType() {
        return attendanceType;
    }

    public void setAttendanceType(int attendanceType) {
        this.attendanceType = attendanceType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        return "AttendanceQuery{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                ", attendanceDate=" + attendanceDate +
                ", attendanceType=" + attendanceType +
                ", amount=" + amount +
                ", hours=" + hours +
                ", inputDate=" + inputDate +
                ", category='" + category + '\'' +
                ", attendanceItem='" + attendanceItem + '\'' +
                ", attendancePeriod='" + attendancePeriod + '\'' +
                ", attendanceDays=" + attendanceDays +
                ", bikou='" + bikou + '\'' +
                '}';
    }
}
