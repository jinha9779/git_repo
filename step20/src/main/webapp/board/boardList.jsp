<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
#currentPaging {color: red; text-decoration: underline;}
#paging {color: blue; text-decoration: none;}

#subjectA:link {color:black; text-decoration: none;}
#subjectA:visited {color:black; text-decoration: none;}
#subjectA:action {color:black; text-decoration: none;}
#subjectA:hover {color:green; text-decoration: underline;}
</style>
<script type="text/javascript">
	function isLogin(seq) {
		<% if(session.getAttribute("memId") == null) {%>
			alert("먼저 로그인 하세요.");
		<% } else { %>
			location.href="boardView.do?seq=" + seq
					 	+ "&pg=" + ${pg};
		<% } %>
	}
</script>
</head>
<body>
<table border="1">
	<tr bgcolor="#ffff00" align="center">
		<th width="70">글번호</th>
		<th width="200">제목</th>
		<th width="100">작성자</th>
		<th width="100">작성일</th>
		<th width="70">조회수</th>
	</tr>
<c:forEach var="boardDTO" items="${list}">
	<tr bgcolor="#ffffcc" align="center">
		<td>${boardDTO.seq}</td>
		<td><a href="#" id="subjectA" 
			onclick="isLogin(${boardDTO.seq})">
			${boardDTO.subject}</a></td>
		<td>${boardDTO.id}</td>
		<td>${boardDTO.logtime}</td>
		<td>${boardDTO.hit}</td>
	</tr>
</c:forEach>	
	<!-- 페이징 -->
	<tr>
		<td colspan="5" align="center">
		<c:if test="${startPage > 3}">
			[<a id="paging" href="boardList.do?pg=${startPage-1}">이전</a>]
		</c:if>
		
		<c:forEach var="i" begin="${startPage}" 
				   end="${endPage}" step="1">
			<c:if test="${i == pg}">
				[<a id="currentPaging" href="boardList.do?pg=${i}">${i}</a>]
			</c:if>
			<c:if test="${i != pg}">
				[<a id="paging" href="boardList.do?pg=${i}">${i}</a>]
			</c:if>
		</c:forEach>		
		
		<c:if test="${endPage < totalP}">
			[<a id="paging" href="boardList.do?pg=${endPage+1}">다음</a>]
		</c:if> 
		</td>
	</tr>
</table>
<br>
<c:if test="${memId != null}">
	<a href="../board/boardWriteForm.do">새글쓰기</a>
</c:if>
<c:if test="${memId == null}">
	<a href="../member/loginForm.jsp">로그인</a>
</c:if>
</body>
</html>


