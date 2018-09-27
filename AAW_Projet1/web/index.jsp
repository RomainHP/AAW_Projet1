<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC  "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Crédit de l'écureuil</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Theme Bootstrap -->
        <link rel="stylesheet" 
              href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" 
              integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" 
              crossorigin="anonymous">
        
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    </head>
    <body>
        <%@ include file="bandeau/bandeau_deconnecte.jsp" %>
        <h1 align="center">Bienvenue !</h1>
        <div class="container-fluid text-center">
            <h2>SERVICES</h2>
            <h4>What we offer</h4>
            <br>
            <div class="row">
              <div class="col-sm-4">
                <span class="glyphicon glyphicon-off"></span>
                <h4>POWER</h4>
                <p>Lorem ipsum dolor sit amet..</p>
              </div>
              <div class="col-sm-4">
                <span class="glyphicon glyphicon-heart"></span>
                <h4>LOVE</h4>
                <p>Lorem ipsum dolor sit amet..</p>
              </div>
              <div class="col-sm-4">
                <span class="glyphicon glyphicon-lock"></span>
                <h4>JOB DONE</h4>
                <p>Lorem ipsum dolor sit amet..</p>
              </div>
              </div>
              <br><br>
            <div class="row">
              <div class="col-sm-4">
                <span class="glyphicon glyphicon-leaf"></span>
                <h4>GREEN</h4>
                <p>Lorem ipsum dolor sit amet..</p>
              </div>
              <div class="col-sm-4">
                <span class="glyphicon glyphicon-certificate"></span>
                <h4>CERTIFIED</h4>
                <p>Lorem ipsum dolor sit amet..</p>
              </div>
              <div class="col-sm-4">
                <span class="glyphicon glyphicon-wrench"></span>
                <h4>HARD WORK</h4>
                <p>Lorem ipsum dolor sit amet..</p>
              </div>
            </div>
        </div>
    </body>
</html>
