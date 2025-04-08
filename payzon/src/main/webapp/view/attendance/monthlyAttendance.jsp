<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <title>근태 조회</title>
    <style>
        body { font-family: Arial, sans-serif; }
        
          /* 전체 컨테이너 스타일 / 全体コンテナのスタイル */
        .container {
            width: 90%;
            margin: 20px auto;
        }
        
        /* 버튼 스타일 / ボタンスタイル */
        .tab-container {
            display: flex;
            margin-bottom: 10px;
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

         /* 필터 영역 스타일 / フィルタエリアのスタイル */
        .filter-container {
            display: flex;
            align-items: center;
            gap: 10px;
            flex-wrap: wrap;
            margin-bottom: 20px;
        }
        .filter-container label {
            font-size: 14px;
            margin-right: 5px;
        }
        .filter-container select {
            padding: 5px;
            font-size: 14px;
            width: auto;
        }
        
         /* 테이블 스타일 / テーブルスタイル */
        .table-container {
            width: 100%;
            overflow: auto;
        }
        .e_total ul {
            list-style: none;
            padding: 0;
            margin: 0;
            display: flex;
            flex-wrap: wrap;
        }
        .e_total .date ul {
            list-style: none;
            padding: 0;
            margin: 0;
            display: flex;
            flex-wrap: wrap;
        }
        .e_total .w_24 {
            width: 24px;
            height: 24px;
            text-align: center;
            line-height: 24px;
            border: 1px solid #ddd;
        }
        .e_total .bb_blue { color: blue; }
        .e_total .bb_red { color: red; }
        .e_total .c_red { color: red; font-weight: bold; }
        .w_88 { width: 88px; }
        .w_70 { width: 70px; }
        .w_92 { width: 92px; }
        .w_199 { width: 199px; }
        .w_100 { width: 100px; }
        .b_none { border-right: none; }
        .ulDiligenceList {
            display: flex;
            align-items: center;
            margin-top: 5px;
        }
        .clsCheck {
            display: flex;
        }
    </style>
</head>
<body>
    <h2>勤怠照会</h2> <!-- 페이지 제목을 표시 / ページのタイトルを表示 -->
    <p>社員別の勤怠状況を一度にご覧いただけます。月別や詳細な勤怠内容も 確認できます.</p> <!-- 설명 텍스트 / 説明テキスト -->
    <div class="tab-container">
        <button class="tab-button active-tab" onclick="location.href='monthlyQuery.do';">월별 조회</button> <!-- 월별 조회 버튼 / 月別照会ボタン -->
        <button class="tab-button inactive-tab" onclick="location.href='detailedQuery.do';">상세 조회</button> <!-- 상세 조회 버튼 / 詳細照会ボタン -->
    </div>

    <div class="filter-container">
        <label>年度: 
            <select name="year">
                <c:forEach var="y" begin="2013" end="2024">
                    <option value="${y}" ${y == 2024 ? 'selected' : ''}>${y}</option> <!-- 현재 연도 기본 선택 / 現在の年度をデフォルト選択 -->
                </c:forEach>
            </select>
        </label>
        <label>月: 
            <select name="month">
                <c:forEach var="m" begin="1" end="12">
                    <option value="${m}" ${m == 11 ? 'selected' : ''}>${m}</option> <!-- 현재 월 기본 선택 / 現在の月をデフォルト選択 -->
                </c:forEach>
            </select>
        </label>
        <button type="submit">朝会</button><!-- 조회 버튼 / 照会ボタン -->
    </div>

    <div class="table-container e_total">
        <!-- Header -->
        <ul class="height_53">
            <li class="w_88">区分</li> <!-- 구분 컬럼 / 区分カラム -->
            <li class="w_88"><strong>社員番号</strong></li> <!-- 사원 번호 컬럼 / 社員番号カラム -->
            <li class="w_70"><strong>声明</strong></li> <!-- 성명 컬럼 / 名前カラム -->
            <li class="w_92"><strong>部署</strong></li> <!-- 부서 컬럼 / 部署カラム -->
            <li class="w_88 b_none"><strong>職位</strong></li> <!-- 직위 컬럼 / 職位カラム -->
            <li style="padding-top:0px;">
                <div class="date">
                    <ul>
                        <c:forEach var="day" begin="1" end="16">
                            <li class="w_24">
                                <c:choose>
                                    <c:when test="${day % 7 == 6}"><span class="bb_blue">${day}</span></c:when>
                                    <c:when test="${day % 7 == 0}"><span class="bb_red">${day}</span></c:when>
                                    <c:otherwise>${day}</c:otherwise>
                                </c:choose>
                            </li>
                        </c:forEach>
                    </ul>
                    <ul>
                        <c:forEach var="day" begin="17" end="31">
                            <li class="w_24">
                                <c:choose>
                                    <c:when test="${day % 7 == 6}"><span class="bb_blue">${day}</span></c:when>
                                    <c:when test="${day % 7 == 0}"><span class="bb_red">${day}</span></c:when>
                                    <c:otherwise>${day}</c:otherwise>
                                </c:choose>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </li>
            <li class="w_199">合計</li> <!-- 합계 컬럼 / 合計カラム -->
            <li class="w_100">休暇控除</li> <!-- 휴가 공제 컬럼 / 休暇控除カラム -->
        </ul>

        <!-- Data (기본 틀) -->
        <c:forEach var="i" begin="1" end="12">
    <ul class="anchor ulDiligenceList">
        <li class="w_88">
            <c:choose>
                <c:when test="${i == 6}">契約職</c:when>
                <c:when test="${i == 7 || i == 8}">臨時職</c:when>
                <c:otherwise>正社員</c:otherwise>
            </c:choose>
        </li>
        <li class="w_88">No-1400<i>${i}</i></li>
        <li class="w_70">
            <c:choose>
                <c:when test="${i == 1}">イ・ウンヨル</c:when>
                <c:when test="${i == 2}">キム・ウン</c:when>
                <c:when test="${i == 3}">イ・スザン</c:when>
                <c:when test="${i == 4}">空白な</c:when>
                <c:when test="${i == 5}">キム・ドンヒョン</c:when>
                <c:when test="${i == 6}">イ・ヨンヒ</c:when>
                <c:when test="${i == 7}">パク・チョルス</c:when>
                <c:when test="${i == 8}">イ・ギフン</c:when>
                <c:when test="${i == 9}">パク・チフン</c:when>
                <c:when test="${i == 10}">イ・ヒョンギュ</c:when>
                <c:when test="${i == 11}">キム・ヴァンミン</c:when>
                <c:otherwise>Unknown</c:otherwise>
            </c:choose>
        </li>
        <li class="w_92">
            <c:choose>
                <c:when test="${i == 1}">社長室</c:when>
                <c:when test="${i == 2}">コンテンツチーム</c:when>
                <c:when test="${i == 3}">デザインチーム</c:when>
                <c:when test="${i == 4 || i == 5}">管理チーム</c:when>
                <c:when test="${i == 6}">企画戦略チーム</c:when>
                <c:otherwise>管理チーム</c:otherwise>
            </c:choose>
        </li>
        <li class="w_88 b_none">
            <c:choose>
                <c:when test="${i == 1 || i == 6}">社長</c:when>
                <c:when test="${i == 3}">代理</c:when>
                <c:when test="${i == 4}">部長</c:when>
                <c:otherwise>社員</c:otherwise>
            </c:choose>
        </li>
        <li style="padding-top:0px;">
            <div class="date clsCheck">
                <ul>
                    <c:forEach var="day" begin="1" end="16">
                        <li class="w_24"></li><!-- 빈 날짜 / 空の日時 -->
                    </c:forEach>
                </ul>
                <ul>
                    <c:forEach var="day" begin="17" end="31">
                        <li class="w_24"></li>
                    </c:forEach>
                </ul>
            </div>
        </li>
        <li class="w_199">1</li><!-- 합계 값 / 合計の値 -->
        <li class="w_100">1000</li><!-- 휴가 공제 값 / 休暇控除の値 -->
    </ul>
</c:forEach>

    </div>
</body>
</html>

