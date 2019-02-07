(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('ConsultationController', ConsultationController);
 
    ConsultationController.$inject = ['$rootScope', 'CompteService', '$location', 'FlashService'];
    function ConsultationController($rootScope, CompteService, $location, FlashService) {
        var vm = this;
  
        vm.user = null;
        vm.accounts = null;
        
        var mail = $rootScope.globals.currentUser.username;
        
        initConsultation();
        function initConsultation(){
            vm.user = $rootScope.globals.currentUser;
            consultation(mail);
        }
          
        function consultation(mail) {
            CompteService.consultation(mail)
                    .then(function(response){
                        vm.accounts = response;
                    }
            );
        };
    };
 
})();

