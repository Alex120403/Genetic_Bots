package com.genetic.bots.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.genetic.bots.Main;
import com.genetic.bots.WorldsHandling.World;

public class WorldStatePanel extends Panel {
    private static ChromosomeDisplay chromosomeDisplay;
    private BotBehavior botBehavior;
    public static int operationFlag, operation;
    private Stage stage;
    private Label content;
    public WorldStatePanel(PanelsHandler handler) { //TODO dispose
        super(handler);
    }

    public void dispose() {
        button.dispose();
    }

    // Runs with constructor
    @Override
    void init() {
        button = new SelectButton(this,1,"CurrentWorldSelectButton.png");
        botBehavior = new BotBehavior();
        Skin skin = new Skin(Gdx.files.internal("data/skin/cloud-form-ui.json"));
        content = new Label("",skin);
        content.setX(20);
        content.setY(400);
        stage = new Stage();
        stage.addActor(content);
    }

    // Draw panel content
    @Override
    void render() {

        if(Main.getSelectedWorldID() != -1) {
            if (chromosomeDisplay == null) {
                chromosomeDisplay = new ChromosomeDisplay(Main.worlds[Main.getSelectedWorldID()].getBots()[0].getChromosome().content);
            }
            operation = Main.worlds[Main.getSelectedWorldID()].bestBot.getChromosome().content[operationFlag].getValue();
            content.setText("Operation flag = "+operationFlag+"\n" +
                    "Operation = "+operation+"\n" +
                    "Energy = "+Main.worlds[Main.getSelectedWorldID()].bestBot.getHealth());
            //stage.draw();
            chromosomeDisplay.drawFlag();
            chromosomeDisplay.render();
            chromosomeDisplay.botInfo.setBot(Main.worlds[Main.getSelectedWorldID()].bestBot);
            chromosomeDisplay.setChromosome(Main.worlds[Main.getSelectedWorldID()].bestBot.getChromosome().content);
            botBehavior.render();
        }
    }
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
