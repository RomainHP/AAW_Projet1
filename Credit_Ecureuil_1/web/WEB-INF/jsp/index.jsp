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
                <p align="justify">
                    La banque en ligne Credit de l'Ecureuil est créée dans le cadre du cours de AAW du Master 2 Informatique
                    de l'Université de Poitiers.
                </p>
                <p align="justify">
                    Ce site reste une maquette, par exemple les boutons sur la page d'index sont factices. Néanmoins les
                    interactions avec la base de données sont réelles et il est possible d'interagir normalement. Le système de la 
                    banque a été simplifié, il faut par exemple s'inscrire avec une adresse mail. Il n'y a pas de restrictions avec le mot
                    de passe (n'importe quel mot de passe est accepté).
                </p>
                <p align="justify">
                    Vous pourrez retrouver ci-dessous des explications sur les fonctionnalités disponibles sur ce site.
                </p>
                <p><a class="btn btn-primary btn-lg" href="#" role="button">Learn more &raquo;</a></p>
            </div>
        </div>

      <div class="container">
        <!-- Example row of columns -->
        <div class="row">
          <div class="col-md-4">
            <h2>Pourquoi nous ?</h2>
            <p align="justify">
                Nous permettons à nos utilisateurs d'organiser leur compte bancaire à leur souhait. Il est ainsi possible
                d'ouvrir n'importe quel livret où vous pourrez organiser votre argent. Vous pouvez également les supprimer
                si le solde du livret est nul. Il est également possible d'ouvrir un compte joint de 1 à n autres personnes.
                Seul le propriétaire (créateur) peut supprimer le compte joint.
            </p>
            <p><a class="btn btn-secondary" href="#" role="button">View details &raquo;</a></p>
          </div>
          <div class="col-md-4">
            <h2>Nos avantages</h2>
            <p align="justify">
                Nous permettons la gestion de profil pour vous laisser le choix de modifier vos informations personnelles.
                Il est également possible de communiquer avec les autres utilisateurs grâce à notre service de messagerie.
                Enfin dès l'ouverture d'un compte, vous obtenez 100€ !
            </p>
            <p><a class="btn btn-secondary" href="#" role="button">View details &raquo;</a></p>
          </div>
          <div class="col-md-4">
            <h2>Vous inscrire</h2>
            <p align="justify">
                Il existe 2 catégories : Utilisateur et UtilisateurPro. L'utilisateur pro doit posséder une entreprise (un siret),
                il pourra ainsi accéder à l'ordre en bourse qui permet de vendre des parts de son entreprise. Les informations
                de l'entreprise sont accessibles depuis le profil comme celles de l'utilisateur.
            </p>
            <p><a class="btn btn-secondary" href="#" role="button">View details &raquo;</a></p>
          </div>
        </div>

        <hr>

      </div> <!-- /container -->
      <%@ include file="bandeau/footer.jsp" %>
    </body>
</html>
