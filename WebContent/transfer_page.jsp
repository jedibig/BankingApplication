<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link rel="stylesheet" href="style.css">
        <title>B13 Transfer</title>
    </head>
    <body>
        <h1>B13 Banking Application</h1>
        <h2>Transfer</h2>
        <form action="transfer" method="post">
          <label for="receiverNum">Receiver Account Number</label>
          <input type="number" name="receiverNum" id="receiverNum" required><br>
          <label for="amount">Amount</label>
          <input type="number" min="0.00" max="10000.00" step="0.01" name="amount" id="amount" required><br>
          <input type="submit" value="Send">
        </form>
        <br/>
        <a href="home">Click here</a><p> to go back home.</p>
    </body>
</html>