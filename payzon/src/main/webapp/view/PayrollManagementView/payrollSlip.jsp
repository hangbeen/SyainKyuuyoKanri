<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="menu.payroll.payrollSlip.Controller.PayrollSlipController"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>給与明細書</title>
<!-- 급여명세서 제목 -->
<link rel="stylesheet" type="text/css" href="styles.css">
<style>
body {
	display: flex;
	justify-content: space-between;
}

.left-table, .right-table {
	flex: 1;
	margin: 10px;
}

table {
	width: 100%;
	border-collapse: collapse;
}

tr:hover {
	background-color: #f1f1f1;
}

td {
	padding: 8px;
	border: 1px solid #ccc;
	text-align: center;
}

.button {
	background-color: #4CAF50;
	color: white;
	padding: 8px 16px;
	cursor: pointer;
	border-radius: 5px;
}
</style>
<script>
	function populateDetails(row) {
		document.getElementById("name").innerText = row
				.getAttribute("data-name");
		document.getElementById("department").innerText = row
				.getAttribute("data-department");
		document.getElementById("position").innerText = row
				.getAttribute("data-position");
		document.getElementById("entryDate").innerText = row
				.getAttribute("data-entryDate");
		document.getElementById("payDate").innerText = row
				.getAttribute("data-payDate");
		document.getElementById("basicPay").innerText = row
				.getAttribute("data-basicPay");
		document.getElementById("foodAllowance").innerText = row
				.getAttribute("data-foodAllowance");
		document.getElementById("pension").innerText = row
				.getAttribute("data-pension");
		document.getElementById("healthInsurance").innerText = row
				.getAttribute("data-healthInsurance");
		document.getElementById("longTermCare").innerText = row
				.getAttribute("data-longTermCare");
		document.getElementById("incomeTax").innerText = row
				.getAttribute("data-incomeTax");
		document.getElementById("localIncomeTax").innerText = row
				.getAttribute("data-localIncomeTax");
	}
</script>
</head>
<body>

	<div class="left-table">
		<h1>給与明細書</h1>
		<!-- 급여명세서 제목 -->

		<%
		// Create the controller and retrieve all payroll data
		PayrollSlipController controller = new PayrollSlipController();
		List<String[]> payrollData = controller.getAllPayrolls();
		%>

		<table border="1">
			<thead>
				<tr>
					<th>区分</th>
					<!-- 구분 -->
					<th>氏名</th>
					<!-- 성명 -->
					<th>実支給額</th>
					<!-- 실지급액 -->
					<th>通知トーク</th>
					<!-- 알림톡 -->
					<th>E-mail</th>
				</tr>
			</thead>
			<tbody>
				<%
				if (payrollData != null && !payrollData.isEmpty()) {
					for (String[] row : payrollData) {
						double shikyuuzoukyuu = 0;
						double koujo_sougaku = 0;
						double shiji_kingaku = 0;
						try {
					shikyuuzoukyuu = Double.parseDouble(row[3]);
					koujo_sougaku = Double.parseDouble(row[4]);
					shiji_kingaku = shikyuuzoukyuu - koujo_sougaku;
						} catch (NumberFormatException e) {
					shiji_kingaku = 0;
						}
				%>
				<tr onclick="populateDetails(this)" data-name="<%=row[1]%>"
					data-department="<%=row[2]%>" data-position="직급 정보"
					data-entryDate="입사일 정보" data-payDate="급여 지급일 정보"
					data-basicPay="<%=row[3]%>" data-foodAllowance="식비 정보"
					data-pension="국민연금 정보" data-healthInsurance="건강보험 정보"
					data-longTermCare="장기요양보험 정보" data-incomeTax="<%=row[4]%>"
					data-localIncomeTax="지방소득세 정보">
					<td><%=row[0]%></td>
					<!-- 구분 -->
					<td><%=row[1]%></td>
					<!-- 성명 -->
					<td><%=shiji_kingaku%></td>
					<!-- 실지급액 -->
					<td><button class="button">通知トーク</button></td>
					<td><button class="button">E-mail</button></td>
				</tr>


				<%
				} // end for loop
				} else {
				%>
				<tr>
					<td colspan="5">給与情報がありません。</td>
				</tr>
				<!-- No data available message -->
				<%
				}
				%>
			</tbody>
		</table>
	</div>

	<div class="right-table">
		<h2>給与情報詳細</h2>
		<!-- Detailed Payroll Information -->
		<table border="1">
			<tr>
				<td>성명 (姓名)</td>
				<td id="name"></td>
			</tr>
			<tr>
				<td>부서 (部署)</td>
				<td id="department"></td>
			</tr>
			<tr>
				<td>직급 (職級)</td>
				<td id="position"></td>
			</tr>
			<tr>
				<td>입사일 (入社日)</td>
				<td id="entryDate"></td>
			</tr>
			<tr>
				<td>급여 지급일 (給与支給日)</td>
				<td id="payDate"></td>
			</tr>
			<tr>
				<td>기본급</td>
				<td id="basicPay"></td>
			</tr>
			<tr>
				<td>식비</td>
				<td id="foodAllowance"></td>
			</tr>
			<tr>
				<td>국민연금</td>
				<td id="pension"></td>
			</tr>
			<tr>
				<td>건강보험</td>
				<td id="healthInsurance"></td>
			</tr>
			<tr>
				<td>장기요양보험</td>
				<td id="longTermCare"></td>
			</tr>
			<tr>
				<td>소득세</td>
				<td id="incomeTax"></td>
			</tr>
			<tr>
				<td>지방소득세</td>
				<td id="localIncomeTax"></td>
			</tr>
		</table>
	</div>

</body>
</html>


<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ page
   import="menu.payroll.payrollSlip.Controller.PayrollSlipController"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>給与明細書</title>
<!-- 급여명세서 제목 (給与明細書タイトル) -->
<link rel="stylesheet" type="text/css" href="styles.css">
<style>
body {
   display: flex;
   justify-content: space-between;
}

.left-table {
   flex: 1;
   margin-right: 20px;
}

table {
   width: 100%;
   border-collapse: collapse;
}

tr {
   cursor: pointer;
}

tr:hover {
   background-color: #f1f1f1;
}

td {
   padding: 10px;
   border: 1px solid #ccc;
   text-align: center;
}

h2 {
   text-align: center;
}

.button {
   background-color: #4CAF50; /* 버튼 배경색 (ボタンの背景色) */
   color: white; /* 버튼 글자색 (ボタンの文字色) */
   border: none; /* 버튼 테두리 제거 (ボタンの枠を削除) */
   padding: 8px 16px; /* 버튼 패딩 (ボタンのパディング) */
   text-align: center; /* 텍스트 중앙 정렬 (テキストを中央揃え) */
   text-decoration: none; /* 링크 밑줄 제거 (リンクの下線を削除) */
   display: inline-block; /* 버튼 형태 유지 (ボタンの形状を維持) */
   font-size: 14px; /* 버튼 글자 크기 (ボタンの文字サイズ) */
   margin: 4px 2px; /* 버튼 간격 (ボタンの間隔) */
   cursor: pointer; /* 마우스 포인터 변경 (マウスポインタの変更) */
   border-radius: 5px; /* 버튼 모서리 둥글게 (ボタンの角を丸く) */
}

.button:hover {
   background-color: #45a049; /* 호버 시 배경색 변화 (ホバー時の背景色の変化) */
}
</style>
</head>
<body>

   <div class="left-table">
      <h1>給与明細書</h1>
      <!-- 급여명세서 제목 (給与明細書タイトル) -->

      <%
      // 급여명세서 컨트롤러 생성 (給与明細書コントローラーを生成)
      PayrollSlipController controller = new PayrollSlipController();
      // 모든 급여 데이터를 가져옴 (すべての給与データを取得)
      List<String[]> payrollData = controller.getAllPayrolls();
      %>
      <div class="controls">
         <label for="year">適用年</label>
         <!-- 귀속연도 (適用年) -->
         <select id="year" name="year">
            <%
            for (int i = 2020; i <= 2030; i++) {
            %>
            <!-- 2020년부터 2030년까지의 연도를 선택할 수 있도록 설정 (2020年から2030年までの年を選択できるように設定) -->
            <option value="<%=i%>"><%=i%></option>
            <%
            }
            %>
         </select> <label for="month">適用月</label>
         <!-- 귀속월 (適用月) -->
         <select id="month" name="month">
            <%
            for (int i = 1; i <= 12; i++) {
            %>
            <!-- 1월부터 12월까지의 월을 선택할 수 있도록 설정 (1月から12月までの月を選択できるように設定) -->
            <option value="<%=i%>"><%=i%></option>
            <%
            }
            %>
         </select> <label for="payrollCycle">給与回数</label>
         <!-- 급여차수 (給与回数) -->
         <select id="payrollCycle" name="payrollCycle">
            <option value="1">1回目</option>
            <!-- 첫 번째 급여차수 (1回目) -->
            <option value="2">2回目</option>
            <!-- 두 번째 급여차수 (2回目) -->
            <option value="3">3回目</option>
            <!-- 세 번째 급여차수 (3回目) -->
         </select> <label for="settlementStart">精算期間</label>
         <!-- 정산기간 시작일 (精算期間開始日) -->
         <input type="date" id="settlementStart" name="settlementStart">

         <label for="settlementEnd">~</label>
         <!-- 정산기간 종료일 (精算期間終了日) -->
         <input type="date" id="settlementEnd" name="settlementEnd"> <label
            for="payrollDate">給与支給日</label>
         <!-- 급여 지급일 (給与支給日) -->
         <input type="date" id="payrollDate" name="payrollDate">
      </div>
      <div class="button-group">
         <input type="text" placeholder="検索語を入力" aria-label="検索語を入力">
         <!-- 검색어 입력 필드 (検索語を入力するフィールド) -->
         <button type="button">検索</button>
         <!-- 검색 (検索) -->
         <button type="button">全て表示</button>
         <!-- 전체보기 (全て表示) -->
         <button type="button">SMS通知送信</button>
         <!-- SMS알림발송 (SMS通知送信) -->
         <button type="button">E-mail送信</button>
         <!-- E-mail발송 (Eメール送信) -->
      </div>

      <%
      if (payrollData != null && !payrollData.isEmpty()) {
      %>
      <table border="1">
         <thead>
            <tr>
               <th>区分</th>
               <!-- 구분 (区分) -->
               <th>氏名</th>
               <!-- 성명 (氏名) -->
               <th>実支給額</th>
               <!-- 실지급액 (実支給額) -->
               <th>通知トーク</th>
               <!-- 알림톡 (通知トーク) -->
               <th>E-mail</th>
            </tr>
         </thead>
         <tbody>
            <%
            for (String[] row : payrollData) {
               double shikyuuzoukyuu = 0;
               double koujo_sougaku = 0;
               double shiji_kingaku = 0;
               try {
                  shikyuuzoukyuu = Double.parseDouble(row[3]); // 지급 총액 (支給総額)
                  koujo_sougaku = Double.parseDouble(row[4]); // 공제 총액 (控除総額)
                  shiji_kingaku = shikyuuzoukyuu - koujo_sougaku; // 실지급액 계산 (実支給額を計算)
               } catch (NumberFormatException e) {
                  shiji_kingaku = 0; // 예외 발생 시 기본값 설정 (例外発生時の基本値設定)
               }
            %>
            <tr>
               <td><%=row[0]%></td>
               <!-- 구분 출력 (区分を出力) -->
               <td><%=row[1]%></td>
               <!-- 성명 출력 (氏名を出力) -->
               <td><%=shiji_kingaku%></td>
               <!-- 실지급액 출력 (実支給額を出力) -->
               <td>
                  <button class="button">通知トーク</button> <!-- 알림톡 버튼 (通知トークボタン) -->
               </td>
               <td>
                  <button class="button">E-mail</button> <!-- E-mail 버튼 (E-mailボタン) -->
               </td>
            </tr>
            <%
            }
            %>
         </tbody>
      </table>
      <%
      } else if (payrollData != null) {
      %>
      <p>給与情報がありません。</p>
      <!-- 급여 정보 없음 메시지 (給与情報がありません) -->
      <%
      }
      %>
   </div>
<div>
<table border="1">
    <thead>
        <tr>
            <th>항목 (項目)</th>
            <th>내용 (内容)</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>성명 (姓名)</td> <!-- 성명 --> 
            <td>1</td>
        </tr>
        <tr>
            <td>부서 (部署)</td> <!-- 부서 -->
            <td>1</td>
        </tr>
        <tr>
            <td>직급 (職級)</td> <!-- 직급 -->
            <td>1</td>
        </tr>
        <tr>
            <td>입사일 (入社日)</td> <!-- 입사일 -->
            <td>1</td>
        </tr>
        <tr>
            <td>급여 지급일 (給与支給日)</td> <!-- 급여 지급일 -->
            <td>1</td>
        </tr>
        <tr>
            <td> 기본급 </td>
            <td>1</td>
        </tr>
        <tr>
            <td>식비</td>
            <td>1</td>
        </tr>
        <tr>
           <td>국민연금</td>
           <td>1</td>
        </tr>
        <tr>
           <td>건강보험</td>
           <td>1</td>
        </tr>
        <tr>
           <td>장기요양보험</td>
           <td>1</td>
        </tr>
        <tr>
           <td>소득세</td>
           <td>1</td>
        </tr>
        <tr>
           <td>지방소득세</td>
           <td>1</td>
        </tr>
    </tbody>
</table>
</div>
</body>
</html>
 --%>
