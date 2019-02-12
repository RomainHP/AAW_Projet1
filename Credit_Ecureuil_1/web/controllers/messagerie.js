(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('MessagerieController', MessagerieController);
 
    MessagerieController.$inject = ['$rootScope'];
    function MessagerieController($rootScope) {
        var vm = this;
  
        vm.user = null;
        vm.displayUsers = displayUsers;
        vm.displayMessages = displayMessages;
        vm.envoyerMessage = envoyerMessage;
        
        initController($rootScope);

        function initController($rootScope) {
           vm.user = $rootScope.globals.currentUser;
        }
        
        function displayUsers() {
            if(vm.users !== null){
                var index = vm.users.length;
                document.getElementById("id").innerHTML =
                    '<option value="default" selected>Selectionner un compte</option>';
                for(var i = 0; i<index; i++){
                    document.getElementById('id').innerHTML += 
                    '<option value="'+ vm.accounts.Compte[i].id[0]+'">' + vm.accounts.Compte[i].name[0] + '</option>';
                }
            }
        }
        
        function displayMessages() {
            
        }
        
        function envoyerMessage() {
            
        }
    }
 
})();

