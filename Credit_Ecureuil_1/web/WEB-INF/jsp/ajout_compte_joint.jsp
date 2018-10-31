<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>CE - Ouverture d'un nouveau livret</title>
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

        <script>
            $(document).ready(function(){
                var next = 1;
                $(".add-more").click(function(e){
                    e.preventDefault();
                    var addto = "#field" + next;
                    var addRemove = "#field" + (next);
                    next = next + 1;
                    var newIn = '<input autocomplete="off" class="input" id="field' + next + '" name="field' + next + '" type="text">';
                    var newInput = $(newIn);
                    var removeBtn = '<button id="remove' + (next - 1) + '" class="btn btn-danger remove-me" >-</button></div><div id="field">';
                    var removeButton = $(removeBtn);
                    $(addto).after(newInput);
                    $(addRemove).after(removeButton);
                    $("#field" + next).attr('data-source',$(addto).attr('data-source'));
                    $("#count").val(next);
                    $('.remove-me').click(function(e){
                        e.preventDefault();
                        var fieldNum = this.id.charAt(this.id.length-1);
                        var fieldID = "#field" + fieldNum;
                        $(this).remove();
                        $(fieldID).remove();
                    });
                });
            });
        </script>
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
        <div class="container">
            <div id="login-row" class="row justify-content-center align-items-center">
                <div id="login-column" class="col-md-6">
                    <div class="login-box col-md-12">
                        <form id="login-form" class="form" action="" method="post">
                            <h3 class="text-center text-info">Ouverture d'un compte joint</h3>
                            ${returnMessage}
                            <div class="form-group mb-3">
                                <label for="nom_compte" class="text-info">Livret :</label>
                                <input type="text" class="form-control" name="nom_compte" id="nom_compte" placeholder="Nom du compte">
                            </div>
                            <div class="form-group mb-3">
                                <label class="text-info" for="field1">Co-Propriétaires</label>
                                <div class="controls" id="profs"> 
                                    <div id="field"><input autocomplete="off" class="input" id="field1" name="field1" type="text"/><button id="b1" class="btn btn-primary add-more" type="button">+</button></div>
                                    <br>
                                </div>
                            </div>
                            <div class="form-group">
                                <input type="submit" name="Valider" class="btn btn-primary" value="Valider">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="bandeau/footer.jsp" %>
    </body>
    
</html>
