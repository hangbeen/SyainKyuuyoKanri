<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <title>勤怠照会 - 詳細照会</title> <!-- 페이지 제목 설정 / ページのタイトルを設定 -->
    <style>
        body { font-family: Arial, sans-serif; }
        .container { width: 90%; margin: 20px auto; display: flex; } 
        .filter-container { width: 30%; margin-right: 20px; } 
        .filter-container label { display: block; margin-top: 10px; } 
        .filter-container select, .filter-container input[type="text"], .filter-container input[type="date"] {
            width: 100%; padding: 5px; margin-top: 5px;
        }
        .filter-container button { width: 48%; padding: 10px; margin-top: 10px; font-size: 16px; } 
        .table-container { width: 70%; overflow: auto; } 
        table { width: 100%; border-collapse: collapse; margin-top: 10px; } 
        th, td { border: 1px solid #ddd; padding: 8px; text-align: center; } 
        th { background-color: #f2f2f2; } 
        .tab-container {
            display: flex;
            margin-bottom: 20px;
        }
        .tab-button {
            flex: 1;
            padding: 5px 10px;
            font-size: 14px;
            border: none;
            cursor: pointer;
            border-radius: 5px;
            max-width: 100px;
        }
        .active-tab {
            background-color: #008b8b;
            color: white;
        }
        .inactive-tab {
            background-color: #f2f2f2;
            color: #333;
        }
    </style>
    <script>
    function applyClientSideFilters() {
        const inputDate = document.querySelector("input[name='inputDate']").value;
        const attendanceStartDate = document.querySelector("input[name='attendanceStartDate']").value;
        const attendanceEndDate = document.querySelector("input[name='attendanceEndDate']").value;
        const department = document.querySelector("select[name='department']").value;
        const employeeName = document.querySelector("input[name='employeeName']").value;
        const attendanceGroup = document.querySelector("select[name='attendanceGroup']").value;
        const attendanceItem = document.querySelector("select[name='attendanceItem']").value;
        const vacationItem = document.querySelector("select[name='vacationItem']").value;
        const note = document.querySelector("input[name='note']").value;

        const rows = document.querySelectorAll("tbody tr");

        rows.forEach(row => {
            const rowInputDate = row.querySelector(".inputDate")?.textContent.trim();
            const rowDepartment = row.querySelector(".department")?.textContent.trim();
            const rowEmployeeName = row.querySelector(".employeeName")?.textContent.trim();
            const rowAttendanceGroup = row.querySelector(".attendanceGroup")?.textContent.trim();
            const rowAttendanceItem = row.querySelector(".attendanceItem")?.textContent.trim();
            const rowVacationItem = row.querySelector(".vacationItem")?.textContent.trim();
            const rowNote = row.querySelector(".note")?.textContent.trim();

            let isMatch = true;

            if (inputDate && rowInputDate !== inputDate) isMatch = false;
            if (attendanceStartDate && rowInputDate < attendanceStartDate) isMatch = false;
            if (attendanceEndDate && rowInputDate > attendanceEndDate) isMatch = false;
            if (department && department !== rowDepartment) isMatch = false;
            if (employeeName && !rowEmployeeName.includes(employeeName)) isMatch = false;
            if (attendanceGroup && attendanceGroup !== rowAttendanceGroup) isMatch = false;
            if (attendanceItem && attendanceItem !== rowAttendanceItem) isMatch = false;
            if (vacationItem && vacationItem !== rowVacationItem) isMatch = false;
            if (note && !rowNote.includes(note)) isMatch = false;

            row.style.display = isMatch ? "" : "none";
        });
    }
    </script>
</head>
<body>
    <h2>勤怠照会</h2> <!-- 페이지 제목 표시 / ページのタイトルを表示 -->
    <p>社員別の勤怠状況を一度にご覧いただけます。月別や詳細な勤怠内容も確認できます。</p> <!-- 설명 텍스트 / 説明テキスト -->
    <div class="tab-container">
        <button class="tab-button inactive-tab" onclick="location.href='monthlyQuery.do';">月別照会</button> <!-- 월별 조회 버튼 / 月別照会ボタン -->
        <button class="tab-button active-tab" onclick="location.href='detailedQuery.do';">詳細照会</button> <!-- 상세 조회 버튼 / 詳細照会ボタン -->
    </div>
    <div class="container">
        <div class="filter-container">
            <form id="filterForm" onsubmit="event.preventDefault(); applyClientSideFilters();">
                <label><input type="checkbox" name="inputDateCheck"> 入力日付:</label> <!-- 입력일자 필터 / 入力日付フィルタ -->
                <input type="date" name="inputDate">

                <label><input type="checkbox" name="attendancePeriodCheck"> 勤怠期間:</label> <!-- 근태기간 필터 / 勤怠期間フィルタ -->
                <input type="date" name="attendanceStartDate"> ~ 
                <input type="date" name="attendanceEndDate">

                <label><input type="checkbox" name="departmentCheck"> 部署:</label> <!-- 부서 필터 / 部署フィルタ -->
                <select name="department">
                    <option value="">選択してください</option>
                    <option value="社長室">社長室</option>
                    <option value="開発チーム">開発チーム</option>
                    <option value="コンテンツチーム">コンテンツチーム</option>
                    <option value="業務支援チーム">業務支援チーム</option>
                    <option value="デザインチーム">デザインチーム</option>
                    <option value="管理チーム">管理チーム</option>
                    <option value="企画戦略チーム">企画戦略チーム</option>
                </select>

                <label><input type="checkbox" name="employeeNameCheck"> 氏名:</label> <!-- 성명 필터 / 氏名フィルタ -->
                <input type="text" name="employeeName" placeholder="氏名を入力してください。">

                <label><input type="checkbox" name="attendanceGroupCheck"> 勤怠グループ:</label> <!-- 근태그룹 필터 / 勤怠グループフィルタ -->
                <select name="attendanceGroup">
                    <option value="">選択してください</option>
                    <option value="休暇">休暇</option>
                    <option value="延長勤務">延長勤務</option>
                    <option value="遅刻早退">遅刻早退</option>
                    <option value="特勤">特勤</option>
                    <option value="その他">その他</option>
                </select>

                <label><input type="checkbox" name="attendanceItemCheck"> 勤怠項目:</label> <!-- 근태항목 필터 / 勤怠項目フィルタ -->
                <select name="attendanceItem">
                    <option value="">選択してください</option>
                    <option value="年次休暇">年次休暇</option>
                    <option value="半日休暇">半日休暇</option>
                    <option value="遅刻">遅刻</option>
                    <option value="早退">早退</option>
                    <option value="外勤">外勤</option>
                    <option value="休日勤務">休日勤務</option>
                    <option value="延長勤務">延長勤務</option>
                    <option value="報奨休暇">報奨休暇</option>
                    <option value="夜勤">夜勤</option>
                    <option value="請願休暇">請願休暇</option>
                </select>

                <label><input type="checkbox" name="vacationItemCheck"> 休暇項目:</label> <!-- 휴가항목 필터 / 休暇項目フィルタ -->
                <select name="vacationItem">
                    <option value="">選択してください</option>
                    <option value="2014_年次休暇">2014_年次休暇</option>
                    <option value="2015_報奨休暇">2015_報奨休暇</option>
                    <option value="2015_年次休暇">2015_年次休暇</option>
                    <option value="2016_報奨休暇">2016_報奨休暇</option>
                    <option value="2016_年次休暇">2016_年次休暇</option>
                    <option value="2017_報奨休暇">2017_報奨休暇</option>
                    <option value="2017_年次休暇">2017_年次休暇</option>
                    <option value="2018_報奨休暇">2018_報奨休暇</option>
                </select>

                <label><input type="checkbox" name="noteCheck"> 備考:</label> <!-- 비고 필터 / 備考フィルタ -->
                <input type="text" name="note">

                <button type="button" onclick="applyClientSideFilters()">検索</button> <!-- 검색 버튼 / 検索ボタン -->
                <button type="reset" onclick="window.location.href='detailedQuery.do';">全体表示</button> <!-- 전체보기 버튼 / 全体表示ボタン -->
            </form>
        </div>

        <div class="table-container">
            <!-- 필터링된 결과를 표시하는 테이블 / フィルタリングされた結果を表示するテーブル -->
            <table>
                <thead>
                    <tr>
                        <th>入力日付</th> <!-- 입력일자 / 入力日付 -->
                        <th>区分</th> <!-- 구분 / 区分 -->
                        <th>氏名</th> <!-- 성명 / 氏名 -->
                        <th>部署</th> <!-- 부서 / 部署 -->
                        <th>職位</th> <!-- 직위 / 職位 -->
                        <th>勤怠項目</th> <!-- 근태항목 / 勤怠項目 -->
                        <th>勤怠期間</th> <!-- 근태기간 / 勤怠期間 -->
                        <th>勤怠日数</th> <!-- 근태일수 / 勤怠日数 -->
                        <th>金額</th> <!-- 금액 / 金額 -->
                        <th>備考</th> <!-- 비고 / 備考 -->
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="attendance" items="${employeeAttendance}">
                        <tr>
                            <td class="inputDate"><c:out value="${attendance.inputDate}" /></td>
                            <td class="category"><c:out value="${attendance.category}" /></td>
                            <td class="employeeName"><c:out value="${attendance.employeeName}" /></td>
                            <td class="department"><c:out value="${attendance.department}" /></td>
                            <td class="position"><c:out value="${attendance.position}" /></td>
                            <td class="attendanceItem"><c:out value="${attendance.attendanceItem}" /></td>
                            <td class="attendancePeriod"><c:out value="${attendance.attendancePeriod}" /></td>
                            <td class="attendanceDays"><c:out value="${attendance.attendanceDays}" /></td>
                            <td class="amount"><c:out value="${attendance.amount}" /></td>
                            <td class="note"><c:out value="${attendance.bikou}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
