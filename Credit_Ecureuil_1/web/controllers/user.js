(function () {
    'use strict';

    angular
        .module('app')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['UserService', '$rootScope', '$location', 'AuthenticationService', 'FlashService'];
    function HomeController(UserService, $rootScope, $location, AuthenticationService, FlashService) {
        
        var vm = this;
        
        initController();

        function initController() {
           
        }
    }

})();
