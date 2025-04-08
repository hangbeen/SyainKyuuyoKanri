package menu.attendance.AttendanceRecordManagement.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import menu.attendance.AttendanceRecordManagement.Model.AttendanceRecord;
import menu.attendance.AttendanceRecordManagement.Service.AttendanceService;
import menu.attendance.AttendanceRecordManagement.Service.AttendanceServiceImpl;
import mvc.command.CommandHandler;

public class AttendanceManagementHandler implements CommandHandler {
    private final AttendanceService attendanceService = new AttendanceServiceImpl();

    @Override
    public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 요청으로부터 action 파라미터를 가져와서, 수행할 작업을 결정합니다.
        // リクエストからactionパラメータを取得し、実行する作業を決定します。
        String action = req.getParameter("action");
        String shainIdParam = req.getParameter("shain_id");

        if ("viewAttendance".equals(action)) {
            return handleViewAttendance(req, res, shainIdParam);
        } else if ("viewLeaveStatus".equals(action)) {
            return handleViewLeaveStatus(req, res, shainIdParam);
        }

        // 기본 액션: 사원 목록을 표시합니다.
        // デフォルトのアクション: 社員リストを表示します。
        return showEmployeeList(req);
    }

    private String handleViewAttendance(HttpServletRequest req, HttpServletResponse res, String shainIdParam) throws IOException {
        // viewAttendance 요청이 들어왔을 때 해당 사원의 근태 기록을 조회합니다.
        // viewAttendanceリクエストが来た場合、その社員の勤怠記録を照会します。
        System.out.println("Received viewAttendance request for shainId: " + shainIdParam);

        if (shainIdParam == null || shainIdParam.trim().isEmpty()) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid shain_id parameter");
            return null;
        }

        try {
            int shainId = Integer.parseInt(shainIdParam);
            List<AttendanceRecord> attendanceList = attendanceService.getEmployeeAttendanceRecords(shainId);
            System.out.println("Attendance records retrieved: " + attendanceList);

            // 조회한 근태 기록을 JSON 형식으로 변환하여 응답으로 전송합니다.
            // 照会した勤怠記録をJSON形式に変換し、レスポンスとして送信します。
            String json = new Gson().toJson(attendanceList);
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(json);
        } catch (NumberFormatException e) {
            // 잘못된 사원 ID 형식이 입력되었을 때 오류를 반환합니다.
            // 無効な社員ID形式が入力された場合、エラーを返します。
            System.err.println("Invalid shain_id format: " + e.getMessage());
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "shain_id must be a valid integer");
        } catch (Exception e) {
            // 근태 기록을 조회하는 중에 예외가 발생하면 서버 오류를 반환합니다.
            // 勤怠記録を照会する際に例外が発生した場合、サーバーエラーを返します。
            System.err.println("Error retrieving attendance records: " + e.getMessage());
            e.printStackTrace();
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving attendance records");
        }
        return null;
    }

    private String handleViewLeaveStatus(HttpServletRequest req, HttpServletResponse res, String shainIdParam) throws IOException {
        // viewLeaveStatus 요청이 들어왔을 때 해당 사원의 휴가 상태를 조회합니다.
        // viewLeaveStatusリクエストが来た場合、その社員の休暇状況を照会します。
        if (shainIdParam == null || shainIdParam.trim().isEmpty()) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid shain_id parameter");
            return null;
        }

        List<AttendanceRecord> leaveStatus = new ArrayList<>();
        for (String id : shainIdParam.split(",")) {
            try {
                int shainId = Integer.parseInt(id.trim());
                leaveStatus.addAll(attendanceService.getEmployeeLeaveStatus(shainId));
            } catch (NumberFormatException e) {
                // 잘못된 형식의 사원 ID가 입력되었을 때 오류 메시지를 출력합니다.
                // 無効な形式の社員IDが入力された場合、エラーメッセージを出力します。
                System.out.println("Invalid shain_id in leave status: Not a valid integer - " + id);
            } catch (Exception e) {
                // 휴가 상태를 조회하는 중에 예외가 발생하면 오류 메시지를 출력합니다.
                // 休暇状況を照会する際に例外が発生した場合、エラーメッセージを出力します。
                System.out.println("Error retrieving leave status for shain_id " + id + ": " + e.getMessage());
                e.printStackTrace();
            }
        }

        // 조회한 휴가 상태를 JSON 형식으로 변환하여 응답으로 전송합니다.
        // 照会した休暇状況をJSON形式に変換し、レスポンスとして送信します。
        String json = new Gson().toJson(leaveStatus);
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(json);
        return null;
    }

    private String showEmployeeList(HttpServletRequest req) throws Exception {
        // 사원 목록을 조회하여 화면에 표시합니다.
        // 社員リストを照会して画面に表示します。
        List<AttendanceRecord> employeeList = attendanceService.getEmployeeList();
        System.out.println("Employee list retrieved: " + employeeList); // 데이터를 확인하기 위한 로그
        // データ確認用のログ

        req.setAttribute("employeeList", employeeList);
        return "/view/attendance/attendanceManagement.jsp";
    }
}
