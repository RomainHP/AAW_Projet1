(function () {
    'use strict';

    angular
        .module('app')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$rootScope'];
    function HomeController() {
        
        var vm = this;
        
        vm.user = null;
        
        initController();

        function initController($rootScope) {
           //vm.user = $rootScope.globals.currentUser;
        }
    }

})();
