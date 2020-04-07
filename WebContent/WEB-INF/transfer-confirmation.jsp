<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link rel="stylesheet" href="../style.css">
        <title>B13 Deposit</title>
    </head>
    <body>
        <h1>B13 Banking Application</h1>
        <h2>Confirm Receiver Account</h2>
        
        <%  String name = request.getAttribute("receiverName").toString();
			String accNum = request.getAttribute("receiverAccNum").toString();
			String amount = request.getAttribute("amount").toString(); %>
			
        <form action=process-transfer method="post">
        <label>Recepient Account Number: </label><%=accNum  %><br>
        <label>Recepient Name: </label><%=name  %><br>
        <label>Nominal: $</label><%= amount %><br>
        <input type="submit" value="Confirm">
        <input type="hidden" name="receiverNum" value="<%=accNum%>">
        <input type="hidden" name="amount" value="<%=amount%>">
        
        </form>
        <form action="home" method="post">
          <input type="submit" value="Cancel">
        </form>
        <br/>
    </body>
</html>