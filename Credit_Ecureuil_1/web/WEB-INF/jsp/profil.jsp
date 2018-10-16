<%-- 
    Document   : profil
    Created on : 26 sept. 2018, 21:06:32
    Author     : Romain
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>CE - Profil</title>
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
                <%@ include file="bandeau/bandeau_connecte.jsp" %>
            </c:when>    
            <c:otherwise>
                <%@ include file="bandeau/bandeau_deconnecte.jsp" %>
            </c:otherwise>
        </c:choose>
        <div class="container">
            <div id="login-row" class="row justify-content-center align-items-center">
                <div id="login-column" class="col-md-6">
                    <div class="login-box col-md-12">
                        <form id="login-form" class="form" action="" method="post">
                            <h3 class="text-center text-info">Modifier le profil</h3>
                            <div class="form-group mb-3">
                                <label for="email" class="text-info">Email:</label>
                                <input type="email" class="form-control" name="email" id="email" value="${email}">
                            </div>
                            <div class="form-row mb-3">
                                <div class="col-md-6">
                                    <label for="text" class="text-info">Nom:</label>
                                    <input type="text" class="form-control" name="company" id="company" value="${nom}">
                                </div>
                                <div class="col-md-6">
                                    <label for="text" class="text-info">Prenom:</label>
                                    <input type="text" class="form-control" name="siret" id="siret" value="${prenom}">
                                </div>
                            </div>
                            <c:choose>
                                <c:when test="${pro != null && pro == true}">
                                    <div class="form-group mb-3">
                                        <label for="text" class="text-info">Entreprise:</label>
                                        <input type="text" class="form-control" name="company" id="company" value="${entreprise}">
                                    </div>
                                </c:when>
                            </c:choose>
                            <div class="form-group mb-3">
                                <label for="password" class="text-info">Mot de passe:</label>
                                <input type="password" class="form-control" name="password" id="password" value="${password}">
                            </div>
                            <div class="form-group mb-3">
                                <label for="password" class="text-info">Confirmation:</label>
                                <input type="password" class="form-control" name="password" id="password" value="${password}">
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label"></label>
                                <div class="col-md-8">
                                  <input class="btn btn-primary" value="Sauvegarder" type="button">
                                  <span></span>
                                  <input class="btn btn-default" value="Annuler" type="reset">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
    
    <%@ include file="bandeau/footer.jsp" %>
</html>
