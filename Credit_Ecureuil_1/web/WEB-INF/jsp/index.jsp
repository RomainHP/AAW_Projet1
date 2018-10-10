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
        
        <!-- Theme personnel -->
        <link rel="stylesheet" href="css/theme.css" type="text/css">
        
        <!-- Icone -->
        <link rel="shortcut icon" href="images/logo.png?" type="image/x-icon" />
        
        <!-- Scripts Bootstrap -->
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    </head>
    <body style="padding-top:60px">
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:choose>
            <c:when test="${login != null}">
                <%@ include file="bandeau/bandeau_connecte.jsp" %>
            </c:when>    
            <c:otherwise>
                <%@ include file="bandeau/bandeau_deconnecte.jsp" %>
            </c:otherwise>
        </c:choose>
        <div class="jumbotron" style="background-color:#ffb860;">
            <div class="container">
                <h1 class="display-3">Le crédit de l'écureuil</h1>
                <p>
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut id fermentum sapien, in convallis metus. Vestibulum ornare quis est non pretium. 
                    Proin erat est, congue eget augue vitae, vehicula consectetur urna. Phasellus malesuada posuere enim vel vulputate. 
                    Ut nec lobortis velit, sit amet suscipit metus. Nulla rutrum augue eget mi scelerisque interdum. In luctus ligula eu finibus cursus. 
                    Pellentesque feugiat sagittis metus. Maecenas non lectus justo. Donec nunc urna, rutrum vitae dui a, eleifend eleifend diam. Sed sed faucibus mi. 
                    Nulla sed auctor ex, at vehicula arcu. Aliquam sodales dolor sit amet lorem feugiat rhoncus.
                </p>
                <p><a class="btn btn-primary btn-lg" href="#" role="button">Learn more &raquo;</a></p>
            </div>
        </div>

      <div class="container">
        <!-- Example row of columns -->
        <div class="row">
          <div class="col-md-4">
            <h2>Pourquoi nous ?</h2>
            <p>Description</p>
            <p><a class="btn btn-secondary" href="#" role="button">View details &raquo;</a></p>
          </div>
          <div class="col-md-4">
            <h2>Nos avantages</h2>
            <p>Description</p>
            <p><a class="btn btn-secondary" href="#" role="button">View details &raquo;</a></p>
          </div>
          <div class="col-md-4">
            <h2>Vous inscrire</h2>
            <p>Description</p>
            <p><a class="btn btn-secondary" href="#" role="button">View details &raquo;</a></p>
          </div>
        </div>

        <hr>

      </div> <!-- /container -->
    </body>
    
    <%@ include file="bandeau/footer.jsp" %>
</html>
