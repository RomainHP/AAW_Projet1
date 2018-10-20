<%-- 
    Document   : bandeau
    Created on : 26 sept. 2018, 09:30:15
    Author     : rcharpen
--%>

<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <a class="navbar-brand" href="index.htm">
        <img src="images/logo.png" width="40" height="40" alt="logo">
    </a>
    <a class="navbar-brand" href="index.htm">Crédit de l'écureuil</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav ml-auto">
      <li class="nav-item active">
        <a class="nav-link" href="index.htm">Accueil<span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item active">
        <a class="nav-link" href="profil.htm">Profil<span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item active dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Gestion des comptes
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="consultation.htm">Consultation</a>
          <a class="dropdown-item" href="virement.htm">Virement</a>
          <a class="dropdown-item" href="ajout_compte.htm">Créer un compte</a>
        </div>
      </li>
      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:choose>
            <c:when test="${pro != null && pro == true}">
                <li class="nav-item active">
                    <a class="nav-link" href="ordre_bourse.htm">Ordre en bourse<span class="sr-only">(current)</span></a>
                </li>
            </c:when>
        </c:choose>
      <li class="nav-item active">
        <a class="nav-link" href="notifications.htm">Notifications<span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item active dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Messagerie
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="envoyer_message.htm">Envoyer un message</a>
          <a class="dropdown-item" href="consulter_messagerie.htm">Consulter la messagerie</a>
        </div>
      </li>
      <li class="nav-item active">
        <a class="nav-link" href="deconnexion.htm">Deconnexion<span class="sr-only">(current)</span></a>
      </li>
    </ul>
  </div>
</nav>
