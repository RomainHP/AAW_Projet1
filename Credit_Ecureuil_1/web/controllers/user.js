(function () {
    'use strict';

    angular
        .module('app')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$rootScope'];
    function HomeController($rootScope) {
        
        var vm = this;
        
        vm.user = null;
        
        initController($rootScope);

        function initController($rootScope) {
           vm.user = $rootScope.globals.currentUser;
        }
    }

})();
