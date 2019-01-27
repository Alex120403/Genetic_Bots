package com.genetic.bots.UI;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.genetic.bots.Main;
import com.genetic.bots.Paint;

public class BotBehavior {
    private final Texture grid,look,arrow,inspect,move,rotation;
    private static final int X = 8, Y = 220, CELL_SIZE = 65;
    private Texture drawable;
    public static int vectorX, vectorY;

    public BotBehavior() {
        grid = new Texture(new FileHandle("botBehaviorGrid.png"));
        look = new Texture(new FileHandle("look.png"));
        arrow = new Texture(new FileHandle("arrow.png"));
        inspect = new Texture(new FileHandle("inspect.png"));
        move = new Texture(new FileHandle("move.png"));
        rotation = new Texture(new FileHandle("rotation.png"));
    }
    public void render() {
        update();
        Paint.draw(grid,X,Y);
        Paint.draw(drawable,(X+CELL_SIZE)+(CELL_SIZE*vectorX)+1,(Y+CELL_SIZE)+(CELL_SIZE*vectorY)+1);
    }

    private void update() {
        int operation = Main.worlds[Main.getSelectedWorldID()].bestBot.getChromosome().content[WorldStatePanel.operationFlag].getValue();
        if(operation < 8) {
            drawable = move;
        }
        else if(operation < 16) {
            drawable = inspect;
        }
        else if(operation < 24) {
            drawable = look;
        }
        else if(operation < 32) {
            drawable = rotation;
        }
        else {
            drawable = arrow;
        }
    }
}
