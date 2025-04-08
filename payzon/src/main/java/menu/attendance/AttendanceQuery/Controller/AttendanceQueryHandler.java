package menu.attendance.AttendanceQuery.Controller;

import menu.attendance.AttendanceQuery.Model.AttendanceQuery;
import menu.attendance.AttendanceQuery.Service.AttendanceQueryService;
import mvc.command.CommandHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AttendanceQueryHandler implements CommandHandler {

    private final AttendanceQueryService attendanceQueryService = new AttendanceQueryService();

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String requestURI = request.getRequestURI();
        String action = request.getParameter("action");

        // URL 확인 후 action 매개변수가 없는 경우 실행할 작업 결정
        // URLを確認し、actionパラメータが存在しない場合の処理
        if (action == null || action.isEmpty()) {
            if (requestURI.endsWith("/monthlyQuery.do")) {
                return handleMonthlyQuery(request);
            } else if (requestURI.endsWith("/detailedQuery.do")) {
                return handleDetailedQuery(request);
            } else {
                // 특정 엔드포인트나 액션이 없을 때 기본 동작
                // 特定のエンドポイントやアクションがない場合のデフォルト動作
                return handleDefaultAttendanceQuery(request);
            }
        }

        // action 매개변수에 따라 분기 처리
        // action パラメータに基づく分岐処理
        switch (action) {
            case "monthlyQuery":
                return handleMonthlyQuery(request);
            case "employeeDetail":
                return handleEmployeeDetail(request);
            case "printMonthly":
                return handlePrintMonthly(request, response);
            case "excelMonthly":
                return handleExcelMonthly(request, response);
            case "printEmployeeDetail":
                return handlePrintEmployeeDetail(request, response);
            case "excelEmployeeDetail":
                return handleExcelEmployeeDetail(request, response);
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                return null;
        }
    }

    // 기본 근태 조회 처리
    // デフォルトの勤怠照会処理
    private String handleDefaultAttendanceQuery(HttpServletRequest request) {
        try {
            List<AttendanceQuery> allAttendanceRecords = attendanceQueryService.getAllAttendanceRecords();
            request.setAttribute("employeeAttendance", allAttendanceRecords);
            return "/view/attendance/employeeDetail.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error retrieving attendance records.");
            return "/view/error.jsp";
        }
    }

    // 월별 조회 처리
    // 月別照会処理
    private String handleMonthlyQuery(HttpServletRequest request) {
        try {
            int year = Integer.parseInt(request.getParameter("year") != null ? request.getParameter("year") : "2024");
            int month = Integer.parseInt(request.getParameter("month") != null ? request.getParameter("month") : "11");

            String department = request.getParameter("department") != null ? request.getParameter("department") : "";
            String position = request.getParameter("position") != null ? request.getParameter("position") : "";

            List<AttendanceQuery> monthlyAttendance = attendanceQueryService.getMonthlyAttendance(year, month, department, position);
            request.setAttribute("monthlyAttendance", monthlyAttendance);
            request.setAttribute("year", year);
            request.setAttribute("month", month);

            return "/view/attendance/monthlyAttendance.jsp";
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid format for year or month.");
            return "/view/error.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error in handleMonthlyQuery: " + e.getMessage());
        }
    }

    // 사원 상세 조회 처리
    // 社員詳細照会処理
    private String handleEmployeeDetail(HttpServletRequest request) {
        try {
            int employeeId = Integer.parseInt(request.getParameter("employeeId"));
            int year = Integer.parseInt(request.getParameter("year"));
            int month = Integer.parseInt(request.getParameter("month"));

            List<AttendanceQuery> employeeAttendance = attendanceQueryService.getEmployeeMonthlyAttendance(employeeId, year, month);
            request.setAttribute("employeeAttendance", employeeAttendance);
            request.setAttribute("employeeId", employeeId);
            request.setAttribute("year", year);
            request.setAttribute("month", month);

            return "/view/attendance/employeeDetail.jsp";
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid format for employee ID, year, or month.");
            return "/view/error.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error in handleEmployeeDetail: " + e.getMessage());
        }
    }

    // 상세 조회 처리
    // 詳細照会処理
    private String handleDetailedQuery(HttpServletRequest request) {
        try {
            boolean inputDateCheck = "on".equals(request.getParameter("inputDateCheck"));
            String inputDate = request.getParameter("inputDate");
            String attendanceStartDate = request.getParameter("attendanceStartDate");
            String attendanceEndDate = request.getParameter("attendanceEndDate");
            String department = request.getParameter("departmentCheck") != null ? request.getParameter("department") : null;
            String employeeName = request.getParameter("employeeNameCheck") != null ? request.getParameter("employeeName") : null;
            String attendanceGroup = request.getParameter("attendanceGroupCheck") != null ? request.getParameter("attendanceGroup") : null;
            String attendanceItem = request.getParameter("attendanceItemCheck") != null ? request.getParameter("attendanceItem") : null;
            String vacationItem = request.getParameter("vacationItemCheck") != null ? request.getParameter("vacationItem") : null;
            String bikou = request.getParameter("noteCheck") != null ? request.getParameter("note") : null;

            // Service로 전달하여 필터링된 데이터 조회
            // Serviceに渡してフィルタリングされたデータを取得する
            List<AttendanceQuery> filteredAttendanceRecords = attendanceQueryService.getFilteredAttendanceRecords(
                inputDateCheck, inputDate, department, employeeName, attendanceGroup, attendanceItem,
                vacationItem, attendanceStartDate, attendanceEndDate, bikou
            );

            // 필터링된 결과를 JSP로 전달
            // フィルタリング結果をJSPに渡す
            request.setAttribute("employeeAttendance", filteredAttendanceRecords);
            return "/view/attendance/employeeDetail.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error retrieving filtered attendance records.");
            return "/view/error.jsp";
        }
    }

    // 월별 출력 처리
    // 月別印刷処理
    private String handlePrintMonthly(HttpServletRequest request, HttpServletResponse response) {
        try {
            int year = Integer.parseInt(request.getParameter("year"));
            int month = Integer.parseInt(request.getParameter("month"));
            String department = request.getParameter("department") != null ? request.getParameter("department") : "";
            String position = request.getParameter("position") != null ? request.getParameter("position") : "";

            List<AttendanceQuery> monthlyAttendance = attendanceQueryService.getMonthlyAttendanceForPrint(year, month, department, position);
            request.setAttribute("monthlyAttendance", monthlyAttendance);

            return "/view/attendance/printMonthly.jsp";
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid format for year or month.");
            return "/view/error.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error in handlePrintMonthly: " + e.getMessage());
        }
    }

    // 월별 엑셀 파일 출력 처리
    // 月別Excelファイル出力処理
    private String handleExcelMonthly(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=monthlyAttendance.xlsx");

        // TODO: Excel 생성 로직 구현
        // TODO: Excel生成ロジックの実装

        return null;
    }

    // 사원 상세 출력 처리
    // 社員詳細印刷処理
    private String handlePrintEmployeeDetail(HttpServletRequest request, HttpServletResponse response) {
        try {
            int employeeId = Integer.parseInt(request.getParameter("employeeId"));
            int year = Integer.parseInt(request.getParameter("year"));
            int month = Integer.parseInt(request.getParameter("month"));

            List<AttendanceQuery> employeeAttendance = attendanceQueryService.getEmployeeAttendanceForPrint(employeeId, year, month);
            request.setAttribute("employeeAttendance", employeeAttendance);

            return "/view/attendance/printEmployeeDetail.jsp";
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid format for employee ID, year, or month.");
            return "/view/error.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error in handlePrintEmployeeDetail: " + e.getMessage());
        }
    }

    // 사원 상세 엑셀 파일 출력 처리
    // 社員詳細Excelファイル出力処理
    private String handleExcelEmployeeDetail(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=employeeDetail.xlsx");

        // TODO: Excel 생성 로직 구현
        // TODO: Excel生成ロジックの実装

        return null;
    }
}
