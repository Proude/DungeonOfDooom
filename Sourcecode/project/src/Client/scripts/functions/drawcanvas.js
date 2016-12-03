var sizeOfShapes = 50;
function draw() {

    //load data from JSON
    var dTiles = [];
    $.getJSON('../../assets/maps/Level2.json')
        .done(function(json_file) {
            for(i = 0; i < json_file.lev.map.length; i++) {
                dTiles[i] = [];
                for (j = 0; j < json_file.lev.map[i].length; j++) {
                    dTiles[i][j] = new Tile(i, j, json_file.lev.map[i][j].type);
                }
            }
            //Create the renderer
            var worldx = dTiles[0].length * sizeOfShapes;
            var worldy = dTiles.length * sizeOfShapes;
            var renderer = PIXI.autoDetectRenderer(worldx, worldy);
            renderer.backgroundColor = 0x8c8c8c;

            //Add the canvas to the HTML document
            document.body.appendChild(renderer.view);

            //Create a container object called the `stage`
            var stage = new PIXI.Container();
            var graphics = drawShapes(dTiles);
            stage.addChild(graphics);

            //Tell the `renderer` to `render` the `stage`
            renderer.render(stage);
        });
}

function drawShapes(dTiles) {
    var graphics = new PIXI.Graphics();
    console.log(dTiles.length + " " + dTiles[0].length + " " + "dsdada");
    for (i = 0; i < dTiles.length; i++) {
        for (j = 0; j < dTiles[i].length; j++) {
            if (dTiles[i][j].type == 0) {
                graphics.beginFill(0x000000);
                graphics.drawRect(j * sizeOfShapes, i * sizeOfShapes, sizeOfShapes, sizeOfShapes);
                graphics.endFill();
            }
            else if (dTiles[i][j].type == 1) {
                graphics.beginFill(0xbf8040);
                graphics.drawRect(j * sizeOfShapes, i * sizeOfShapes, sizeOfShapes, sizeOfShapes);
                graphics.endFill();
            }
            else if (dTiles[i][j].type == 2) {
                graphics.beginFill(0xffff66);
                graphics.drawRect(j * sizeOfShapes, i * sizeOfShapes, sizeOfShapes, sizeOfShapes);
                graphics.endFill();
            }
        }
    }
    return graphics;
}