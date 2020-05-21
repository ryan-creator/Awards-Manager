/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @author Ubaada
 */
"use strict";
let serviceURI = 'http://localhost:8081/api';

//---- Login Page ----------------
let module = angular.module('LoginModule', ['ngResource']);
module.factory('userLoginApi', function ($resource) {
    return $resource(serviceURI + '/user/login');
});

module.controller('LoginController', function (userLoginApi) {
    this.test = "It works!";
    this.oka = "sad";
    this.login = function (credentials) {
        var userDetails = userLoginApi.save({}, credentials);

        userDetails.$promise.catch(function (response) {
            M.toast({html: 'Wrong username or password'});
        });

        userDetails.$promise.then(function () {
            localStorage.setItem('user-firstName', userDetails.firstName);
            localStorage.setItem('user-lastName', userDetails.lastName);
            localStorage.setItem('user-department', userDetails.department);
            localStorage.setItem('user-userType', userDetails.userType);
            localStorage.setItem('userID', userDetails.userID);

            if (userDetails.userType === "Nominee") {
                window.location.href = '/application-page.html';
            } else if (userDetails.userType === "Department") {
                window.location.href = '/nominee-select.html';
            } else if (userDetails.userType === "Committee") {
                window.location.href = '/voting.html';
            }
        });
    };
});


//---Logout button-----
function logout() {
    //clear user details
    localStorage.clear();
    //clear session
    document.cookie = "session-token" + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    //go back to login page
    window.location.href = '/';
}

//--- Nominee Select Page ---------
let selectModule = angular.module('SelectModule', ['ngResource']);

selectModule.factory('nomineeseByCategoryApi', function ($resource) {
    return $resource(serviceURI + '/applications');
});
selectModule.factory('userApi', function ($resource) {
    return $resource(serviceURI + '/user');
});

selectModule.controller('SelectController', function (nomineeseByCategoryApi, userApi) {
    var ctrl = this;
    this.ApplicationsByCategory = nomineeseByCategoryApi.query();
    this.ApplicationsByCategory.$promise.catch(function (response) {
        M.toast({html: 'Error Occoured!'});
        window.location.href = '/';
    });
    this.ApplicationsByCategory.$promise.then(function () {

    });
    this.department = localStorage.getItem("user-department");
    ;
    this.inittabs = function () {
        var elem = document.querySelector('.tabs');
        const instance = M.Tabs.init(elem, {dismissible: false});

        var elemtxt = document.querySelector('.input-field ');
        M.updateTextFields();
        M.AutoInit();
    };

    this.selectTab = function (mytab) {
        ctrl.selectedTab = mytab;
        M.updateTextFields();
    };

    this.submitNom = function (nomination) {
        nomination.category = ctrl.selectedTab;
        var nomPost = userApi.save({}, nomination);

        nomPost.$promise.catch(function (response) {
            M.toast({html: 'Something Went Wrong :('});
        });

        nomPost.$promise.then(function () {
            M.toast({html: 'Submitted :)'});
            location.reload();
        });
    };

    this.loggedUserName = localStorage.getItem("user-firstName") + ' ' + localStorage.getItem("user-lastName");
    this.loggedUserType = localStorage.getItem("user-userType");


});

//--- Voting Page ---------
//    public String voteID;
//    private String userID;
//    private String applicationID;
//    private String comment;

let votingModule = angular.module('VotingModule', ['ngResource']);
votingModule.factory('voteApi', function ($resource) {
    return $resource(serviceURI + '/vote');
});
votingModule.factory('nomineeseByCategoryApi2', function ($resource) {
    return $resource(serviceURI + '/applications');
});
votingModule.controller('VotingController', function (nomineeseByCategoryApi2, voteApi) {
    var vtctrl = this;
    this.ApplicationsByCategory = nomineeseByCategoryApi2.query();
    this.ApplicationsByCategory.$promise.catch(function (response) {
        M.toast({html: 'Error Occoured!'});
        window.location.href = '/';
    });
    this.ApplicationsByCategory.$promise.then(function () {

    });
    this.department = localStorage.getItem("user-department");
    this.inittabs = function () {
        var elem = document.querySelector('.tabs');
        const instance = M.Tabs.init(elem, {dismissible: false});

        var elemtxt = document.querySelector('.input-field ');
        M.updateTextFields();
        M.AutoInit();
    };
    this.selectTab = function (mytab) {
        ctrl.selectedTab = mytab;
        M.updateTextFields();
    };

    this.submitVote = function (category, vote) {
        vote.userID = localStorage.getItem("userID");
        vote.applicationID = document.querySelector('input[name="group-' + category + '"]:checked').value;
        vote.voteID = vote.userID + '-' + vote.applicationID;
        var votePost = voteApi.save({}, vote);

        votePost.$promise.catch(function (response) {
            M.toast({html: 'Something Went Wrong :('});
        });

        votePost.$promise.then(function () {
            M.toast({html: 'Submitted :)'});
            location.reload();
        });
    };

    this.loggedUserName = localStorage.getItem("user-firstName") + ' ' + localStorage.getItem("user-lastName");
    this.loggedUserType = localStorage.getItem("user-userType");


});

//---Application Page------
//    public String applicationID;
//    private String userCandidate;
//    private String category;
//    private String linkToCV;
//    private String personalStatement;
//    private String references;
//    private String status = "Pending";
///api/application/:user_id
let applicationModule = angular.module('ApplicationModule', ['ngResource']);
applicationModule.factory('applicationApi', function ($resource) {
    return $resource(serviceURI + '/application/' + localStorage.getItem("userID"), {}, {
            query: { method: "GET", isArray: true },
            create: { method: "POST"},
            get: { method: "GET"},
            remove: { method: "DELETE"},
            update: { method: "PUT"}
        });
});
applicationModule.controller('ApplicationController', function (applicationApi) {
    var ctrl = this;
    this.loggedUserName = localStorage.getItem("user-firstName") + ' ' + localStorage.getItem("user-lastName");
    this.loggedUserType = localStorage.getItem("user-userType");
    this.application = applicationApi.get();
    this.application.$promise.catch(function (response) {
        M.toast({html: 'Error Occoured!'});
    });
    this.application.$promise.then(function () {
       
    });
    this.saveApplication = function (filled_application) {
        ctrl.application.linkToCV = filled_application.linkToCV;
        ctrl.application.personalStatement = filled_application.personalStatement;
        ctrl.application.references = filled_application.references;
        var appPut = applicationApi.update({}, ctrl.application);
        appPut.$promise.catch(function (response) {
            M.toast({html: 'Error Occoured while saving!'});
        });
        appPut.$promise.then(function () {

        });
    };
    this.initxt = function () {
        M.AutoInit();
    };
});
