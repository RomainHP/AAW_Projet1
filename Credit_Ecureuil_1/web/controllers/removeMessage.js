(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('RemoveMessageController', RemoveMessageController);
 
    RemoveMessageController.$inject = ['$rootScope', '$location', 'FlashService', 'MessageService'];
    
    function RemoveMessageController($rootScope, $location, FlashService, MessageService) {
        var vm = this;
  
        vm.user = null;
        vm.removeMessage = removeMessage;
        
        initController($rootScope, $location);

        function initController($rootScope, $location) {
            vm.user = $rootScope.globals.currentUser;
            var messageRemoved = $location.search().id;
            removeMessage(messageRemoved);
        }
        
        function removeMessage(messageRemoved) {
            if (messageRemoved !== null) {
                vm.dataLoading = true;
                MessageService.removeMessage(messageRemoved)
                    .then(function () {
                        FlashService.Success('Message supprime avec succes', true);
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


