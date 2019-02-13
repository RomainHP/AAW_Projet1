(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('MessagerieController', MessagerieController);
 
    MessagerieController.$inject = ['$rootScope','UtilisateurService','MessageService'];
    function MessagerieController($rootScope, UtilisateurService, MessageService) {
        var vm = this;
  
        vm.user = null;
        vm.allUsers = null;
        vm.messages = null;
        vm.displayUsers = displayUsers;
        vm.displayMessages = displayMessages;
        vm.envoyerMessage = envoyerMessage;
        vm.removeMessage = removeMessage;
        
        initController($rootScope, UtilisateurService, MessageService);

        function initController($rootScope, UtilisateurService, MessageService) {
           vm.user = $rootScope.globals.currentUser;
           UtilisateurService.getAllUsers().then(function(response){
                vm.allUsers = response;
            });
           MessageService.getMessages().then(function(response){
                vm.messages = response;
            });
        }
        
        function displayUsers() {
            if(vm.allUsers !== null){
                var index = vm.allUsers.mail.length;
                document.getElementById("destinataire").innerHTML =
                    '<option value="default">Selectionner un utilisateur</option>';
                for(var i = 0; i<index; i++){
                    document.getElementById('destinataire').innerHTML += 
                    '<option value="'+ vm.allUsers.mail[i] + '">' + vm.allUsers.mail[i] + '</option>';
                }
            }
        }
        
        function displayMessages() {
            if (vm.messages !== null){
                var index = vm.messages.sujet.length;
                for(var i = 0; i<index; i++){
                    document.getElementById('table').innerHTML += '<tr data-toggle="collapse" data-target=\"#msg' + cpt + '" class="accordion-toggle">'
                    + '<td>' + i+1+ '</td>'
                    + '<td scope="row">' + vm.messages.emetteur[i] + '</td>'
                    + '<td scope="row">' + vm.messages.sujet[i] + '</td>'
                    + '<td scope="row">'
                    +   '<form class="form" action="vm.removeMessage()" method="post">'
                    +       '<div class="form-group mb-3">'
                    +           '<input type=\"hidden\" class=\"form-control\" ng-model="vm.messageRemoved.id" value="' + vm.messages.id[i] + '">'
                    +           '<button type=\"submit\" class=\"btn btn-primary btn-md\">Supprimer</button>'
                    +       '</div>'
                    +   '</form>'
                    + '</td></tr>'
                    + '<tr><td colspan="4" class="hiddenRow"><div class="accordian-body collapse" id="msg' + cpt + '">' + vm.messages.message[i] + '</div></td></tr>'
                    ;
                }
            }
        }
        
        function envoyerMessage() {
            alert("test");
            vm.dataLoading = true;
            var dest = document.getElementById("destinataire");
            var destSelected = dest.options[dest.selectedIndex].value;
            if (destSelect != 0) {
                MessageService.sendMessage(destSelected, vm.message.sujet, vm.message.message)
                    .then(function () {
                        FlashService.Success('Message envoyé avec succès', true);
                        $location.path('/');
                    },
                    function (errResponse) {
                        FlashService.Error("Erreur : " + errResponse.data["errorMessage"], true);
                        $location.path('/');
                    }
                );
            } else {
                FlashService.Error("Erreur : Veuillez saisir un destinataire", true);
                $location.path('/envoyer_message');
            }
        }
        
        function removeMessage() {
            vm.dataLoading = true;
            MessageService.removeMessage(vm.messageRemoved.id[0])
                .then(function () {
                    FlashService.Success('Message supprimé avec succès', true);
                    $location.path('/consulter_messagerie');
                },
                function (errResponse) {
                    FlashService.Error("Erreur : " + errResponse.data["errorMessage"], true);
                    $location.path('/consulter_messagerie');
                }
            );
        }
    }
 
})();

