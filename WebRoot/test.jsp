<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.houstar.*,java.util.List,java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JDBC测试</title>
</head>
<%
	UserService usrSrv = new UserService();
	String userName = request.getParameter("userName");
	String userAge = request.getParameter("userAge");
	int age = 0;
	if (userName == null) {
		userName = "";
	}
	if (userAge != null && (!"".equals(userAge))) {
		try {
			age = Integer.parseInt(userAge);
		} catch (Exception e) {
			e.printStackTrace();
			age = 0;
		}
	}

	String flag = "index";
	if (request.getParameter("flag") != null) {
		flag = request.getParameter("flag");
		if (flag.equals("del")) {
			String delName = request.getParameter("delName");
			delName = new String(delName.getBytes("ISO8859_1"), "UTF-8");
			usrSrv.delUser(delName);
			flag = "index";
		} else if (flag.equals("upd")) {
			String oldname = request.getParameter("oldname");
			oldname = new String(oldname.getBytes("ISO8859_1"),
						"UTF-8");
			String user_name_m = request.getParameter("user_name_m");
			if (user_name_m != null && (!"".equals(user_name_m))) {
				user_name_m = new String(user_name_m.getBytes("ISO8859_1"),
						"UTF-8");

				String user_passwd_m = request
						.getParameter("user_passwd_m");
				if (user_passwd_m != null && (!"".equals(user_passwd_m))) {
					user_passwd_m = new String(
							user_passwd_m.getBytes("ISO8859_1"), "UTF-8");
				}

				String user_age_m = request.getParameter("user_age_m");
				int sage_m = 0;
				if (user_age_m != null && (!"".equals(user_age_m))) {
					try {
						sage_m = Integer.parseInt(user_age_m);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				usrSrv.updUser(user_name_m, user_passwd_m, sage_m,oldname);
			}
			flag = "index";
		} else if (flag.equals("insert")) {

			String user_name = request.getParameter("user_name");
			if (user_name != null && (!"".equals(user_name))) {
				user_name = new String(user_name.getBytes("ISO8859_1"),
						"UTF-8");

				String user_passwd = request
						.getParameter("user_passwd");
				if (user_passwd != null && (!"".equals(user_passwd))) {
					user_passwd = new String(
							user_passwd.getBytes("ISO8859_1"), "UTF-8");
				}

				String user_age = request.getParameter("user_age");
				int sage = 0;
				if (user_age != null && (!"".equals(user_age))) {
					try {
						sage = Integer.parseInt(user_age);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				usrSrv.addUser(user_name, user_passwd, sage);
			}
			flag = "index";
		}
	}

	request.setAttribute("flag", flag);
	List<User> usrList = new ArrayList<User>();
	usrList = UserService.getUsers(userName, age);
	request.setAttribute("usrList", usrList);
%>
<body>

	<c:if test="${flag =='index' }">
		<div align="center">
			<h1>用户列表</h1>
			<form name="frm" method="post" action="test.jsp">
				<br> 请输入用户名<input type="text" name="userName">&nbsp;请输入年龄
				<input type="text" name="userAge"> <input type="submit"
					value="检索">
				<table border="1">
					<tr>
						<th height="30" width="150">姓名</th>
						<th height="30" width="150">密码</th>
						<th height="30" width="150">年龄</th>
						<th height="30" width="150">操作</th>
					</tr>
					<c:forEach var="usr" items="${usrList}">
						<tr>
							<td>${usr.userName}</td>
							<td>${usr.userPasswd}</td>
							<td>${usr.userAge}</td>
							<td><a href="test.jsp?flag=del&delName=${usr.userName}">删除</a>
								&nbsp;&nbsp;&nbsp; <a
								href="test.jsp?flag=updPage&updName=${usr.userName}">修改</a>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="4" align="left">&nbsp;&nbsp;<a
							href="test.jsp?flag=insertPage">添加</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</c:if>
	<c:if test="${flag=='insertPage'}">
		<div align="center">
			<form method="post" name="form2" action="test.jsp?flag=insert">
				<table border="1">
					<tr>
						<th colspan="2">用户添加页面</th>
					</tr>
					<tr>
						<td width="100" height="30">姓名:</td>
						<td width="200"><input type="text" name="user_name">
						</td>
					</tr>
					<tr>
						<td width="100" height="30">密码:</td>
						<td><input type="text" name="user_passwd"></td>
					</tr>
					<tr>
						<td width="100" height="30">年龄:</td>
						<td><input type="text" name="user_age"></td>
					</tr>
					<tr>
						<td colspan="2" height="30"><input type="submit" value="添加">&nbsp;&nbsp;<input
							type="reset" value="取消">
						</td>
					</tr>
				</table>
			</form>
		</div>
	</c:if>
	<c:if test="${flag=='updPage'}">
		<%
			String updName = request.getParameter("updName");
				updName = new String(updName.getBytes("ISO8859_1"), "UTF-8");
				User usr = usrSrv.getUser(updName);
				request.setAttribute("usr", usr);
		%>
		<div align="center">
			<form method="post" name="form2" action="test.jsp?flag=upd">
				<table border="1">
					<tr>
						<th colspan="2">用户修改页面</th>
					</tr>
					<tr>
						<td width="100" height="30">姓名:</td>
						<td width="200"><input type="text" name="user_name_m"
							value="${usr.userName }"> <input type="hidden"
							name="oldname" value="${usr.userName }"></td>
					</tr>
					<tr>
						<td width="100" height="30">密码:</td>
						<td><input type="text" name="user_passwd_m"
							value="${usr.userPasswd }"></td>
					</tr>
					<tr>
						<td width="100" height="30">年龄:</td>
						<td><input type="text" name="user_age_m"
							value="${usr.userAge }"></td>
					</tr>
					<tr>
						<td colspan="2" height="30"><input
								type="submit" value="修改">
						&nbsp;&nbsp;<input type="reset" value="取消">
						</td>
					</tr>
				</table>
			</form>
		</div>
	</c:if>
</body>
</html>