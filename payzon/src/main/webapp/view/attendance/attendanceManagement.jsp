<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>勤怠記録 / 管理</title> <!-- 근태 기록 / 관리 페이지 제목 설정 -->
<!-- 勤怠記録 / 管理ページのタイトルを設定 -->
<style>
    body {
        font-family: 'Arial', sans-serif;
        margin: 20px;
    }

    h1 {
        text-align: center;
        margin-bottom: 20px;
    }

    .container {
        display: flex;
        gap: 20px;
        align-items: flex-start;
    }

    .employee-list {
        width: 60%;
    }

    .attendance-management {
        width: 35%;
        padding: 20px;
        border: 1px solid #ccc;
        background-color: #f9f9f9;
        box-sizing: border-box;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        border: 1px solid #ccc;
    }

    th, td {
        border: 1px solid #ccc;
        padding: 10px;
        text-align: center;
    }

    th {
        background-color: #f0f0f0;
    }

    .popup, .overlay {
        display: none;
    }

    .popup {
        position: fixed;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        background-color: white;
        padding: 20px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        z-index: 10;
        width: 500px;
        max-width: 90%;
    }

    .overlay {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        z-index: 9;
    }

    .close {
        float: right;
        cursor: pointer;
        font-weight: bold;
    }
</style>
</head>
<body>
    <h1>勤怠記録 / 管理</h1> <!-- 페이지 제목 표시 -->
    <!-- ページのタイトルを表示 -->

    <p style="text-align: center;">給与と関連する勤怠記録を管理するメニューです。社員別の勤怠内訳を管理できます。</p> <!-- 설명문 -->
    <!-- 説明文 -->

    <div class="container">
        <!-- 사원 목록 섹션 -->
        <!-- 社員リストセクション -->
        <div class="employee-list">
            <table>
                <thead>
                    <tr>
                        <th>選択</th>
                        <th>区分</th>
                        <th>社員番号</th>
                        <th>氏名</th>
                        <th>部署</th>
                        <th>職位</th>
                        <th>勤怠記録</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="employee" items="${employeeList}">
                        <tr>
                            <td><input type="checkbox" name="selectedEmployee" value="${employee.shain_id}"></td>
                            <td>${employee.employment_type}</td>
                            <td>No-${employee.shain_id}</td>
                            <td>${employee.shain_name}</td>
                            <td>${employee.department}</td>
                            <td>${employee.yakushoku}</td>
                            <td><button onclick="viewAttendance()">管理</button></td> <!-- 근태 기록 보기 버튼 -->
                            <!-- 勤怠記録を見るボタン -->
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- 근태 관리 입력 폼 -->
        <!-- 勤怠管理入力フォーム -->
        <div class="attendance-management">
           <form>
                <label>入力日付:</label>
                <input type="date" id="inputDate"><br>

                <label>勤怠項目:</label>
                <select id="attendanceType">
                    <option value="">選択してください。</option>
                    <option value="年次休暇">年次休暇</option>
                </select><br>

                <label>期間:</label>
                <input type="date" id="startDate"> ~ <input type="date" id="endDate"><br>

                <div class="button-group">
                    <label>勤怠日数:</label>
                    <input type="number" id="days">
                </div>

                <label>金額（手当）:</label>
                <input type="text" id="amount"><br>

                <label>備考:</label>
                <input type="text" id="description"><br>

                <div class="button-group">
                    <button type="button" onclick="viewLeaveStatus()">休暇状況</button> <!-- 휴가 상황 보기 버튼 -->
                    <!-- 休暇状況を見るボタン -->
                    <button type="reset">内容をクリア</button> <!-- 내용 초기화 버튼 -->
                    <!-- 内容をクリアボタン -->
                </div>
            </form>
        </div>
    </div>

    <!-- 근태 기록 팝업 -->
    <!-- 勤怠記録ポップアップ -->
    <div id="attendancePopup" class="popup">
        <span class="close" onclick="closePopup()">X</span> <!-- 팝업 닫기 버튼 -->
        <!-- ポップアップを閉じるボタン -->
        <h3>社員別勤怠記録</h3>
        <table>
            <thead>
                <tr>
                    <th>番号</th>
                    <th>入力日付</th>
                    <th>勤怠項目</th>
                    <th>勤怠期間</th>
                    <th>勤怠日数</th>
                    <th>金額</th>
                    <th>備考</th>
                    <th>修正/削除</th>
                </tr>
            </thead>
            <tbody>
                <!-- 예시 데이터 행 -->
                <!-- サンプルデータ行 -->
                <tr>
                    <td>1</td>
                    <td>2024-11-01</td>
                    <td>年次休暇</td>
                    <td>2024-11-01 ~ 2024-11-02</td>
                    <td>2</td>
                    <td>10000</td>
                    <td>備考1</td>
                    <td><button>修正</button><button>削除</button></td> <!-- 수정/삭제 버튼 -->
                    <!-- 修正/削除ボタン -->
                </tr>
                <tr>
                    <td>2</td>
                    <td>2024-11-03</td>
                    <td>病欠</td>
                    <td>2024-11-03 ~ 2024-11-04</td>
                    <td>2</td>
                    <td>0</td>
                    <td>備考2</td>
                    <td><button>修正</button><button>削除</button></td> <!-- 수정/삭제 버튼 -->
                    <!-- 修正/削除ボタン -->
                </tr>
            </tbody>
        </table>
    </div>
    <div id="overlay" class="overlay" onclick="closePopup();"></div> <!-- 팝업 오버레이 -->
    <!-- ポップアップオーバーレイ -->

    <script>
    // 근태 기록 보기 팝업 열기 함수
    // 勤怠記録を見るポップアップを開く関数
    function viewAttendance() {
        document.getElementById("attendancePopup").style.display = "block";
        document.getElementById("overlay").style.display = "block";
    }

    // 팝업 닫기 함수
    // ポップアップを閉じる関数
    function closePopup() {
        document.getElementById("attendancePopup").style.display = "none";
        document.getElementById("overlay").style.display = "none";
    }
    </script>
</body>
</html>
