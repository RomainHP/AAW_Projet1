(function () {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngCookies'])
        .config(config)
        .run(run);

    config.$inject = ['$routeProvider'];
    function config($routeProvider) {
        $routeProvider
            .when('/', {
                controller: 'HomeController',
                templateUrl: 'home.html',
                controllerAs: 'vm'
            })

            .when('/login', {
                controller: 'LoginController',
                templateUrl: 'login.html',
                controllerAs: 'vm'
            })

            .when('/register', {
                controller: 'RegisterController',
                templateUrl: 'register.html',
                controllerAs: 'vm'
            })

            .otherwise({ redirectTo: '/login' });
    }

    run.$inject = ['$rootScope', '$location', '$cookies', '$http'];
    
    function run($rootScope, $location, $cookies, $http) {
        
        // keep user logged in after page refresh
        $rootScope.globals = $cookies.getObject('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata;
        }

        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // pages qui sont restreintes aux personnes connectees
            var restrictedPage = $.inArray($location.path(), []) !== -1 ;
            // pages seulement pour les pros
            var restrictedProPage = $.inArray($location.path(), []) !== -1 ;
            // pages seulement pour les admin
            var restrictedAdminPage = $.inArray($location.path(), []) !== -1 ;
            var loggedIn = $rootScope.globals.currentUser;
            var isPro = $rootScope.globals.currentUser.pro;
            var isAdmin = $rootScope.globals.currentUser.admin;
            // si la personne n'est pas connectee (ou non pro) et que la page est restreinte, on le renvoie sur l'accueuil
            if ((restrictedPage && ((typeof loggedIn) === 'undefined' || !loggedIn)) 
                    && (restrictedProPage && ((typeof isPro) === 'undefined' || !isPro))
                    && (restrictedAdminPage && ((typeof isAdmin) === 'undefined' || !isAdmin))) {
                $location.path('/');
                $rootScope.flash = {
                    message: 'Vous n\'avez pas les autorisations requises pour acceder a la page.',
                    type: 'success', 
                    keepAfterLocationChange: false
                };
            } else {
                delete $rootScope.flash;
            }
        });
    }

})();