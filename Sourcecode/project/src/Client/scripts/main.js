/**
 * 2016 Dungeon of Dooom University of Bath.
 */

game = [];
game.menu = [];
game.auth = [];
game.constants = [];
game.func = [];
game.match = [];
game.var = [];
game.match.var = [];
game.camera = {};

game.var.init = function() {
    game.var.xSize = 900;
    game.var.ySize = 600;
    game.var.playerCharacter = {};
    game.var.scale = 50;
    game.var.tiles = [];
    game.var.characters = [];
    game.var.renderer = {};
    game.var.stage = {};
    game.var.graphics = {};
    game.var.playerTitles = [];
    game.var.isRunning = false;
    game.var.delta = 0;
    game.var.timeStep = 1000 / 20;
    game.var.lastFrameTimestamp = 0;
}
game.var.init();
game.var.colours = [];
game.var.colours.background = 0x000000;
game.var.colours.wall = 0x8c8c8c;
game.var.colours.floor = 0xbf8040;
game.var.colours.gold = 0xffff66;
game.var.colours.player = 0xff2222;

game.match.var.isLobbying = false;
game.match.var.isWaitingTostart = false;
game.match.var.delta = 0;
game.match.var.timeStep = 1000 / 5;
game.match.var.lastFrameTimestamp = 0;

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

game.auth.hook = function( data ) {
    game.menu.clearValidation();
    $('#guest-header').css('display', 'none');
    $('#logged-in-header').css('display','block');
    game.menu.openMatchLobby();
};

game.menu.loginFormValidation = function(message ) {
    $('#login-validation').html(message);
};

game.menu.clearValidation = function() {
    var validatorElements = $('.validation');
    validatorElements.html('');
    validatorElements.css('display', 'none');
};

game.auth.register = function() {
    var endpoint = game.func.getApiPath("player","register");
    var username = $("#username").val();
    var password = $("#password").val();

    game.func.post(endpoint,
        { "username" : username, "password" : password },
        game.auth.hook,
        function() { game.menu.loginFormValidation(game.constants.registrationFailed)});
};

game.auth.login = function() {
    var endpoint = game.func.getApiPath("player","login");
    var username = $("#username").val();
    var password = $("#password").val();

    game.func.post(endpoint,
        { "username" : username, "password" : password },
        game.auth.hook,
        function() { game.menu.loginFormValidation(game.constants.loginFailed)});
};

game.menu.openMatchLobby = function() {
    game.menu.allSections.css('display','none');
    game.menu.lobby.css('display','block');
    game.match.var.isLobbying = true;
    requestAnimationFrame(game.match.updateMatchList);
};

game.menu.openTutorial = function() {
    game.match.var.isLobbying = false;
    game.menu.allSections.css('display','none');
    game.menu.tutorial.css('display','block');
};

game.menu.openScoreboard = function() {
    game.match.var.isLobbying = false;
    game.menu.allSections.css('display','none');
    game.menu.scoreboard.css('display','block');
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
    game.match.var.isLobbying = false;
    game.match.var.isWaitingTostart = true;

    var endpoint = game.func.getApiPath("match","join");
    game.func.post(endpoint, { "matchId" : id }, game.menu.displayMatchMenu, game.menu.error);
    requestAnimationFrame(game.match.updateStatus);
};

game.match.new = function() {
    var endpoint = game.func.getApiPath("match","new");
    //todo add level choosing
    game.match.var.isLobbying = false;
    game.match.var.isWaitingTostart = true;

    game.func.post(endpoint, { "level" : "1" }, game.menu.displayMatchMenu);
    requestAnimationFrame(game.match.updateStatus);
};

game.match.start = function() {
    game.match.var.isWaitingTostart = false;
    var endpoint = game.func.getApiPath("match","start");
    requestAnimationFrame(function() {game.func.post(endpoint, null, game.menu.initGameScreen, game.func.error) });
};

game.menu.initGameScreen = function() {
    game.var.init();
    game.menu.gameContainer.empty();

    game.var.renderer = PIXI.autoDetectRenderer(game.var.xSize, game.var.ySize);
    game.var.renderer.backgroundColor = game.var.colours.background;
    game.menu.gameContainer.append(game.var.renderer.view);

    game.var.stage = new PIXI.Container();
    game.var.graphics = new PIXI.Graphics();
    game.var.stage.addChild(game.var.graphics);

    game.menu.match.css('display', 'none');
    game.menu.game.css('display', 'block');

    game.var.isRunning = true;
    requestAnimationFrame(game.updateGame);
};

game.initPlayerTitles = function( characters ) {
    var style = {
        fontFamily : 'Arial',
        fontSize : '18px',
        fontStyle : 'italic',
        fontWeight : 'bold',
        fill : '#F7EDCA',
        stroke : '#4a1850',
        strokeThickness : 5,
        dropShadow : true,
        dropShadowColor : '#000000',
        dropShadowAngle : Math.PI / 6,
        dropShadowDistance : 4
    };

    $.each( characters, function(i, character) {
        game.var.playerTitles[character.playerName] = new PIXI.Text(character.playerName, style);
        game.var.stage.addChild(game.var.playerTitles[character.playerName]);
    });
};

game.render = function() {
    game.var.graphics.clear();

    for(x = 0; x < game.var.tiles.length; x++) {
        var row = game.var.tiles[x];
        if(typeof row !== 'undefined') {
            for (y = 0; y < game.var.tiles[x].length; y++) {
                var tile = game.var.tiles[x][y];
                if(typeof tile !== 'undefined') {
                    var tilePositionX = (x * game.var.scale) - game.camera.x;
                    var tilePositionY = (y * game.var.scale) - game.camera.y;

                    if (tile.type == 0) {
                        game.var.graphics.beginFill(game.var.colours.wall);
                        game.var.graphics.drawRect(tilePositionX, tilePositionY, game.var.scale, game.var.scale);
                        game.var.graphics.endFill();
                    }
                    else if (tile.type == 1) {
                        game.var.graphics.beginFill(game.var.colours.floor);
                        game.var.graphics.drawRect(tilePositionX, tilePositionY, game.var.scale, game.var.scale);
                        game.var.graphics.endFill();
                    }
                    else if (tile.type == 2) {
                        game.var.graphics.beginFill(game.var.colours.floor);
                        game.var.graphics.drawRect(tilePositionX, tilePositionY, game.var.scale, game.var.scale);
                        game.var.graphics.endFill();

                        game.var.graphics.beginFill(game.var.colours.gold);
                        game.var.graphics.drawCircle(tilePositionX + game.var.scale / 2, tilePositionY + game.var.scale / 2, game.var.scale / 4);
                        game.var.graphics.endFill();
                    }

                    if (tile.character !== null) {
                        var positionX = tilePositionX - game.var.scale / 2;
                        var positionY = tilePositionY - game.var.scale / 2;

                        game.var.graphics.beginFill(game.var.colours.player);
                        game.var.graphics.drawCircle(positionX, positionY, game.var.scale / 2);
                        game.var.graphics.endFill();

                        game.var.playerTitles[game.var.tiles[x][y].character.playerName].x = positionX - game.var.scale / 2;
                        game.var.playerTitles[game.var.tiles[x][y].character.playerName].y = positionY - game.var.scale;
                    }
                }
            }
        }
    }

    game.var.isRunning = true;
    game.var.renderer.render(game.var.stage);
};

game.updateStatus = function( status ) {
    game.var.characters = status.characters;
    game.var.playerCharacter = status.playerCharacter;

    game.camera.x = (game.var.playerCharacter.position.x * game.var.scale) - (game.var.xSize / 2);
    game.camera.y = (game.var.playerCharacter.position.y * game.var.scale) - (game.var.ySize / 2);

    $.each( status.tiles, function ( i, tile ) {
        tile.character = null;
        game.var.addTile(tile);
    });

    $.each( status.characters, function( i, character) {
        game.var.tiles[character.position.x][character.position.y].character = character;
    });

    if(game.var.playerTitles.length == 0) {
        game.initPlayerTitles(status.characters);
    }

    game.render();
};

game.var.addTile = function( tile ) {
    var pos = tile.position;

    if(typeof game.var.tiles[pos.x] === 'undefined') {
        game.var.tiles[pos.x] = [];
    }

    game.var.tiles[pos.x][pos.y] = tile;
};

game.fetchStatus = function() {
    var endpoint = game.func.getApiPath("game","status");
    game.func.get(endpoint, {}, game.updateStatus, game.func.error);
};

game.updateGame = function( timestamp ) {
    if(game.var.lastFrameTimestamp == 0) {
        game.var.lastFrameTimestamp = timestamp + game.var.timeStep;
    }
    game.var.delta += timestamp - game.var.lastFrameTimestamp;
    game.var.lastFrameTimestamp = timestamp;

    if(game.var.delta > game.var.timeStep) {
        game.fetchStatus();
        game.var.delta -= game.var.timeStep;
    }

    if(game.var.isRunning) {
        requestAnimationFrame(game.updateGame);
    }
};

game.match.updateMatchList = function( timestamp ) {
    if(game.match.var.lastFrameTimestamp == 0) {
        game.match.var.lastFrameTimestamp = timestamp + game.match.var.timeStep;
    }
    game.match.var.delta += timestamp - game.match.var.lastFrameTimestamp;
    game.match.var.lastFrameTimestamp = timestamp;

    if(game.match.var.delta >= game.match.var.timeStep) {
        game.match.list();
        game.match.var.delta -= game.match.var.timeStep;
    }

    if(game.match.var.isLobbying) {
        requestAnimationFrame(game.match.updateMatchList);
    }
};

game.match.updateStatus = function( timestamp ) {
    if(game.match.var.lastFrameTimestamp == 0) {
        game.match.var.lastFrameTimestamp = timestamp + game.match.var.timeStep;
    }
    game.match.var.delta += timestamp - game.match.var.lastFrameTimestamp;
    game.match.var.lastFrameTimestamp = timestamp;

    if(game.match.var.delta >= game.match.var.timeStep) {
        game.match.fetchStatus();
        game.match.var.delta -= game.match.var.timeStep;
    }

    if(game.match.var.isWaitingTostart) {
        requestAnimationFrame(game.match.updateStatus);
    }
};

game.match.fetchStatus = function() {
    var endpoint = game.func.getApiPath("match","status");
    game.func.get(endpoint, {}, game.menu.displayMatchMenu, game.func.error);
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
};

game.match.leave = function() {
    game.var.isRunning = false;

    var endpoint =  game.func.getApiPath("match","leave");
    requestAnimationFrame(function() {game.func.post(endpoint, { }, game.menu.openMatchLobby, game.func.error)});
};

$( document ).ready(function() {
    game.menu.login = $('#login');
    game.menu.lobby = $('#lobby');
    game.menu.match = $('#match');
    game.menu.tutorial = $('#tutorial');
    game.menu.end = $('#end-game');
    game.menu.scoreboard = $('#score');
    game.menu.game = $('#game');
    game.menu.gameContainer = $('#game-container');
    game.menu.allSections = $('section');

    game.menu.login.css('display', 'block');

    $('#register-btn').click(game.auth.register);
    $('#login-btn').click(game.auth.login);
    $('#new-match-btn').click(game.match.new);
    $('#start-match-btn').click(game.match.start);
    $('#match-leave-btn').click(game.match.leave);
    $('#return-btn').click(game.menu.openMatchLobby);
    $('#lobby-link').click(game.menu.openMatchLobby);
    $('#tutorial-link').click(game.menu.openTutorial);
    $('#score-link').click(game.menu.openScoreboard)
});