<%-- 
    Document   : envoyer_message
    Created on : 26 sept. 2018, 20:59:34
    Author     : Romain
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>CE - Messagerie</title>
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
    <body>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:choose>
            <c:when test="${login != null}">
                <c:choose>
                    <c:when test="${admin != null && admin == true}">
                        <%@ include file="bandeau/bandeau_admin.jsp" %>
                    </c:when>    
                    <c:otherwise>
                        <%@ include file="bandeau/bandeau_connecte.jsp" %>
                    </c:otherwise> 
                </c:choose>
            </c:when>    
            <c:otherwise>
                <%@ include file="bandeau/bandeau_deconnecte.jsp" %>
            </c:otherwise>
        </c:choose>
        <form action="envoyer_message.htm" method="post">
            ${returnMessage}
            <div class="d-flex col-lg-8 m-3">
                <label for="destinataire">Sujet:</label>
                <input type="text" class="form-control mx-sm-3" name="sujet" id="sujet">
                <label for="destinataire">Destinataire:</label>
                <select name="destinataire" class="custom-select mx-sm-3" id="inlineFormCustomSelect">
                    <option selected>Selectionner un destinataire</option>
                    ${options_dest}
                </select>
            </div>
            <div class="m-3">
                <label for="message">Message:</label>
                <textarea class="form-control" rows="5" name="message" id="message"></textarea>
            </div>
            <div class="d-flex m-3 justify-content-center">
                <button type="submit" class="btn btn-primary">Envoyer</button>
            </div>
        </form>
        <%@ include file="bandeau/footer.jsp" %>
    </body>
</html>
