/**
 * 2016 Dungeon of Dooom University of Bath.
 */

game = [];
game.menu = [];
game.auth = [];
game.constants = [];
game.func = [];
game.match = [];

game.constants.api = "http://localhost:8080/";
game.constants.loginFailed = "Oops, that didn't work. Make sure your username/password are correct.";
game.constants.registrationFailed = "Oops, that didn't work. Fields cannot be empty or more than 255 characters.";

game.func.get = function(url, data, success, error) {
    $.ajax({
        type: "GET",
        url: url,
        data: data,
        success: success,
        error: error,
        xhrFields: {
            withCredentials: true
        }
    });
};

game.func.post = function(url, data, success, error) {
    $.ajax({
        type: "POST",
        url: url,
        data: data,
        success: success,
        error: error,
        xhrFields: {
            withCredentials: true
        }
    });
};

game.func.getApiPath = function(controller, action) {
    return game.constants.api + controller + "/" + action;
};

game.func.error = function( data, reason, exception ) {
    alert(' an error occurred :(');
    console.log(reason);
    console.log(exception);
};

game.auth.registerHook = function( data ) {
    game.menu.clearValidation();
    game.menu.login.css('display','none');
    game.menu.menu.css('display','block');
};

game.auth.loginHook = function( data ) {
    game.menu.clearValidation();
    game.menu.login.css('display','none');
    game.menu.menu.css('display','block');
};

game.menu.loginFormValidation = function(message ) {
    $('#login-validation').html(message);
};

game.menu.clearValidation = function() {
    var validatorelements = $('.validation');
    validatorelements.html('');
    validatorelements.css('display', 'none');
};

game.auth.register = function() {
    var endpoint = game.func.getApiPath("player","register");
    var username = $("#username").val();
    var password = $("#password").val();

    game.func.post(endpoint,
        { "username" : username, "password" : password },
        game.auth.registerHook,
        function() { game.menu.loginFormValidation(game.constants.registrationFailed)});
};

game.auth.login = function() {
    var endpoint = game.func.getApiPath("player","login");
    var username = $("#username").val();
    var password = $("#password").val();

    game.func.post(endpoint,
        { "username" : username, "password" : password },
        game.auth.loginHook,
        game.func.error);
        //function() { game.menu.loginFormValidation(game.constants.loginFailed)});
};

game.menu.openMatchLobby = function() {
    game.menu.menu.css('display','none');
    game.menu.lobby.css('display','block');
    game.match.list();
};

game.match.list = function() {
    //todo what if the webservice thinks you're already in a match?
    var endpoint = game.func.getApiPath("match","list");
    game.func.get(endpoint, {}, game.menu.displayMatchList, game.menu.error);
};

game.menu.displayMatchList = function( data ) {
    var matchList = $('#match-list');
    matchList.empty();

    $.each( data, function( i, match ) {
        var entry = $( String.format("<p><a data-id='{2}' class='join-link'>Join</a> {0}'s game with {1} players</p>", match.playerNames[0], match.playerNames.length, match.id) );
        matchList.append(entry);
    });

    $(".join-link").click(game.match.join);
};

game.match.join = function( data ) {
    var id = $(data.currentTarget).data("id");

    var endpoint = game.func.getApiPath("match","join");
    game.func.post(endpoint, { "matchId" : id }, game.menu.displayMatchMenu, game.menu.error);
};

game.match.new = function() {
    var endpoint = game.func.getApiPath("match","new");
    //todo add level choosing
    game.func.post(endpoint, { "level" : "1" }, game.menu.displayMatchMenu);
};

game.menu.displayMatchMenu = function( data ) {
    game.menu.lobby.css('display','none');
    game.menu.match.css('display','block');

    var matchDeatils = $("#match-details");
    matchDeatils.empty();

    matchDeatils.append($("<h2>Waiting to start.</h2>"));
    matchDeatils.append($("<h3>Players</h3>"));

    $.each( data.playerNames, function( i, name ) {
        var entry = $( String.format("<p>{0}</p>", name) );
        matchDeatils.append(entry);
    });
}

$( document ).ready(function() {
    game.menu.login = $('#login');
    game.menu.menu = $('#menu');
    game.menu.lobby = $('#lobby');
    game.menu.match = $('#match');
    game.menu.game = $('#game');

    game.menu.login.css('display', 'block');

    $('#register-btn').click(game.auth.register);
    $('#login-btn').click(game.auth.login);
    $('#menu-match-btn').click(game.menu.openMatchLobby);
    $('#start-match').click(game.match.new);
});

