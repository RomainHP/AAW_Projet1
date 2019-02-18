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
            
            .when('/registerpro', {
                controller: 'RegisterProController',
                templateUrl: 'registerpro.html',
                controllerAs: 'vm'
            })
            
            .when('/disconnect', {
                controller: 'DisconnectController',
                templateUrl: 'registerpro.html',
                controllerAs: 'vm'
            })
            
            .when('/consultation', {
                controller: 'ConsultationController',
                templateUrl: 'consultation.html',
                controllerAs: 'vm'
            })
            
            .when('/create_account', {
                controller: 'ConsultationController',
                templateUrl: 'create_account.html',
                controllerAs: 'vm'
            })
            
            .when('/virement', {
                controller: 'ConsultationController',
                templateUrl: 'virement.html',
                controllerAs: 'vm'
            })
            
            .when('/envoyer_message', {
                controller: 'MessagerieController',
                templateUrl: 'envoyer_message.html',
                controllerAs: 'vm'
            })
            
            .when('/consulter_messagerie', {
                controller: 'MessagerieController',
                templateUrl: 'consulter_messagerie.html',
                controllerAs: 'vm'
            })
            
            .when('/create_linked_account', {
                controller: 'ConsultationController',
                templateUrl: 'create_linked_account.html',
                controllerAs: 'vm'
            })
            
            .when('/details', {
                controller: 'ConsultationController',
                templateUrl: 'details.html',
                controllerAs: 'vm'
            })
            
            .when('/profil', {
                controller: 'HomeController',
                templateUrl: 'profil.html',
                controllerAs: 'vm'
            })
            
            .otherwise({ redirectTo: '/login' });
    }

    run.$inject = ['$rootScope', '$location', '$cookies', '$http'];
    
    function run($rootScope, $location, $cookies, $http) {
        
        // garde l'utilisateur connecte, meme apres rafraichissement de la page
        $rootScope.globals = $cookies.getObject('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata;
        }

        // teste si l'utilisateur a le droit d'acceder a la page
        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // pages qui sont restreintes aux personnes non connectees
            var restrictedInvitePage = $.inArray($location.path(), ['/login', '/register', '/registerpro']) !== -1 ;
            // pages qui sont restreintes aux personnes connectees
            var restrictedPage = $.inArray($location.path(), ['/consultation']) !== -1 ;
            var restrictedPage = $.inArray($location.path(), ['/disconnect']) !== -1 ;
            // pages seulement pour les pros
            var restrictedProPage = $.inArray($location.path(), []) !== -1 ;
            // pages seulement pour les admin
            var restrictedAdminPage = $.inArray($location.path(), []) !== -1 ;
            var loggedIn = $rootScope.globals.currentUser;
            if ((typeof loggedIn) === 'undefined' || !loggedIn){    // non connecte
                if (restrictedPage) {
                    $location.path('/');
                    $rootScope.flash = {
                        message: 'Vous n\'avez pas les autorisations requises pour acceder a la page.',
                        type: 'error', 
                        keepAfterLocationChange: false
                    };
                }
            } else {
                var isPro = $rootScope.globals.currentUser.pro;
                var isAdmin = $rootScope.globals.currentUser.admin;
                 // si la personne n'est pas connectee (ou non pro) et que la page est restreinte, on le renvoie sur l'accueuil
                if (restrictedInvitePage 
                        || (restrictedProPage && ((typeof isPro) === 'undefined' || !isPro))
                        || (restrictedAdminPage && ((typeof isAdmin) === 'undefined' || !isAdmin))) {
                    $location.path('/');
                    $rootScope.flash = {
                        message: 'Vous n\'avez pas les autorisations requises pour acceder a la page.',
                        type: 'error', 
                        keepAfterLocationChange: false
                    };
                } else {
                    delete $rootScope.flash;
                }
            }
        });
    }

})();