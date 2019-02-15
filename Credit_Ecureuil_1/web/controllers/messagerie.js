(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('MessagerieController', MessagerieController);
 
    MessagerieController.$inject = ['$rootScope','UtilisateurService','MessageService','FlashService','$location'];
    function MessagerieController($rootScope, UtilisateurService, MessageService, FlashService, $location) {
        var vm = this;
  
        vm.user = null;
        vm.allUsers = null;
        vm.messages = null;
        vm.messageRemoved = null;
        vm.displayUsers = displayUsers;
        vm.displayMessages = displayMessages;
        vm.envoyerMessage = envoyerMessage;
        vm.removeMessage = removeMessage;
        
        var allUsersOk = false;
        var messagesOk = false;
        
        initController($rootScope, UtilisateurService, MessageService);

        function initController($rootScope, UtilisateurService, MessageService) {
           vm.user = $rootScope.globals.currentUser;
           UtilisateurService.getAllUsers().then(function(response){
                vm.allUsers = response;
            });
           MessageService.getMessages(vm.user.username).then(function(response){
                vm.messages = response;
            });
        }
        
        function displayUsers() {
            if(vm.allUsers !== null && !allUsersOk){
                var index = vm.allUsers.mail.length;
                document.getElementById("destinataire").innerHTML =
                    '<option value="default" selected>Selectionner un utilisateur</option>';
                var res;
                for(var i = 0; i<index; i++){
                    res += 
                    '<option value="'+ vm.allUsers.mail[i] + '">' + vm.allUsers.mail[i] + '</option>';
                }
                document.getElementById("destinataire").innerHTML += res;
                allUsersOk = true;
            }
        }
        
        function displayMessages() {
            if (vm.messages !== null && !messagesOk){
                var index = vm.messages.sujet.length;
                for(var i = 0; i<index; i++){
                    document.getElementById('table').innerHTML += '<tr data-toggle="collapse" data-target=\"#msg' + i + '" class="accordion-toggle">'
                    + '<td>' + (i+1) + '</td>'
                    + '<td scope="row">' + vm.messages.emetteur[i] + '</td>'
                    + '<td scope="row">' + vm.messages.sujet[i] + '</td>'
                    + '<td scope="row">'
                    +   '<form class="form" ng-submit="vm.removeMessage()" role="form">'
                    +       '<div class="form-group mb-3">'
                    +           '<input type="hidden" class="form-control" ng-model="vm.messageRemoved" value="' + vm.messages.id[i] + '">'
                    +           '<button type="submit" class="btn btn-primary btn-md">Supprimer</button>'
                    +       '</div>'
                    +   '</form>'
                    + '</td></tr>'
                    + '<tr><td colspan="4" class="hiddenRow"><div class="accordian-body collapse" id="msg' + i + '">' + vm.messages.message[i] + '</div></td></tr>'
                    ;
                }
                messagesOk = true;
            }
        }
        
        function envoyerMessage() {
            vm.dataLoading = true;
            var dest = document.getElementById("destinataire");
            var destSelected = dest.options[dest.selectedIndex].value;
            if (destSelected !== 'default' && dest.selectedIndex !== 0) {
                MessageService.sendMessage(vm.user.username, destSelected, vm.message.sujet, vm.message.message)
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
            alert("test");
            if (vm.messageRemoved !== null) {
                vm.dataLoading = true;
                MessageService.removeMessage(vm.messageRemoved)
                    .then(function () {
                        vm.messageRemoved = null;
                        FlashService.Success('Message supprimé avec succès', true);
                        $location.path('/consulter_messagerie');
                    },
                    function (errResponse) {
                        FlashService.Error("Erreur : " + errResponse.data["errorMessage"], true);
                        $location.path('/consulter_messagerie');
                    }
                );
            } else {
                FlashService.Error("Erreur", true);
                    $location.path('/consulter_messagerie');
            }
        }
    }
 
})();

