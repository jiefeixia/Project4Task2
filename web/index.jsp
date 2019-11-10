<%--
  Created by IntelliJ IDEA.
  User: jiefei
  Date: 11/7/2019
  Time: 11:13 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="resource.Log" %>
<%@ page import="java.util.LinkedList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <title>Dashboard</title>
  </head>
  <body>
  <h1>Analytics Statistics</h1>
  <h3>Top 3 Intent:</h3>
  <%= request.getAttribute("top_intent") %> <br>
  <h3>Top 3 Language:</h3>
  <%= request.getAttribute("top_language_code") %><br>
  <h3>Top 3 Date: </h3>
  <%= request.getAttribute("top_date") %><br><br>
  <h1>Log History:</h1>
  <table>
    <tr bgcolor="00FF7F">
      <th><b>Session ID</b></th>
      <th><b>Timestamp</b></th>
      <th><b>method</b></th>
      <th><b>Language Code</b></th>
      <th><b>Query</b></th>
      <th><b>Intent</b></th>
      <th><b>Confidence</b></th>
      <th><b>Response ID</b></th>
      <th><b>Fullfillment Message</b></th>
    </tr>
    <%
      LinkedList<Log> logs = (LinkedList<Log>) request.getAttribute("logs");
      for(Log log : logs){%>
    <tr>
      <td><%=log.sessionID%></td>
      <td><%=log.timestamp%></td>
      <td><%=log.method%></td>
      <td><%=log.language_code%></td>
      <td><%=log.query%></td>
      <td><%=log.intent%></td>
      <td><%=log.confidence%></td>
      <td><%=log.response_id%></td>
      <td><%=log.fullfillment_message%></td>
    </tr>
    <%}%>
  </table>
  </body>
</html>
