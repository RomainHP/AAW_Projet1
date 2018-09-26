<%-- 
    Document   : bandeau
    Created on : 26 sept. 2018, 09:30:15
    Author     : rcharpen
--%>

<nav class="navbar navbar-expand-lg navbar-light" style="background-color: #e0e0e0;">
    <a class="navbar-brand" href="index.jsp">
        <img src="images/logo.png" width="40" height="40" alt="logo">
    </a>
    <a class="navbar-brand" href="index.jsp">Crédit de l'écureuil</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav ml-auto">
      <li class="nav-item active">
        <a class="nav-link" href="index.jsp">Accueil<span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item active">
        <a class="nav-link" href="Profil">Profil<span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item active dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Gestion des comptes
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="Consultation">Consultation</a>
          <a class="dropdown-item" href="Virement">Virement</a>
          <a class="dropdown-item" href="Ordre">Ordre en bourse</a>
        </div>
      </li>
      <li class="nav-item active">
        <a class="nav-link" href="Notifications">Notifications<span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item active">
        <a class="nav-link" href="Messagerie">Messagerie<span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item active">
        <a class="nav-link" href="Deconnexion">Deconnexion<span class="sr-only">(current)</span></a>
      </li>
    </ul>
  </div>
</nav>
