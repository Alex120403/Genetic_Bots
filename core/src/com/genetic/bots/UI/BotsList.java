package com.genetic.bots.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.genetic.bots.BotsHandling.Bot;
import com.genetic.bots.BotsHandling.CustomBot;
import com.genetic.bots.InputHandler;
import com.genetic.bots.InputObserver;
import com.genetic.bots.SQL.DbHandler;

import java.sql.SQLException;
import java.util.List;

public class BotsList implements Disposable, InputObserver {
    private BotsListItem[] items;
    static Skin skin = new Skin(Gdx.files.internal("data/skin/cloud-form-ui.json"));

    private static final String reallyLongString = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nThis\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
            + "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n";

    private Stage stage;
    private ScrollPane scroll;
    private Table table,container;
    Label label;


    public BotsList(Bot[] bots) {

        this.stage = new Stage();
        InputHandler.addToObservers(this);

        final Label text = new Label(reallyLongString, skin);
       // text.setAlignment(Align.center);
      //  text.setWrap(true);
        final Label text2 = new Label("This is a short string!", skin);
       // text2.setAlignment(Align.center);
       // text2.setWrap(true);
        final Label text3 = new Label(reallyLongString, skin);
       // text3.setAlignment(Align.center);
        //text3.setWrap(true);
        List<CustomBot> customBotList = null;
        try {
            customBotList = DbHandler.getInstance().getAllProducts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final Table scrollTable = new Table();
        Label[] rows = new Label[customBotList.size()];
        for (int i = 0; i < 12; i++) {
            scrollTable.add(new Label("",skin));
            scrollTable.row();
        }

        for (int i = 0; i < rows.length; i++) {
            String name = customBotList.get(i).getName();
            String sp = customBotList.get(i).allSavedPepole+"";
            String ef = customBotList.get(i).allExtinguishedFire+"";
            if(sp.length() == 1) {
                sp+=" ";
            }
            if(ef.length() == 1) {
                ef+=" ";
            }
            String space = "";
            for (int j = 0; j < 14-name.length(); j++) {
                space+=" ";
            }
            scrollTable.add(new CheckBox("",skin));

            rows[i] = new Label("  "+name+space+sp+"     "+ef,skin);
            scrollTable.add(rows[i]);
            scrollTable.row();
        }



        scrollTable.setBounds(-490,113,200,30);

        final ScrollPane scroller = new ScrollPane(scrollTable);

        scroller.setBounds(-490,113,200,30);

        final Table table = new Table();
        table.setFillParent(true);
        table.add(scroller).fill().expand();

        table.setBounds(-490,113,200,30);

        this.stage.addActor(table);
    }

    public void render() {
        stage.act();
        stage.draw();
    }

    /**
     * Releases all resources of this object.
     */
    @Override
    public void dispose() {
        for (int i = 0; i < items.length; i++) {
            items[i].dispose();
        }
    }

    class BotsListItem implements Disposable{
        private Bot bot;

        public BotsListItem(Bot bot) {
            this.bot = bot;

           // Label name =
        }



        /**
         * Releases all resources of this object.
         */
        @Override
        public void dispose() {
            stage.dispose();
        }
    }

    /**
     * Called when a key was pressed
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    /**
     * Called when a key was released
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    /**
     * Called when a key was typed
     *
     * @param character The character
     * @return whether the input was processed
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Called when the screen was touched or a mouse button was pressed. The button parameter will be {@link Input.Buttons#LEFT} on iOS.
     *
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        stage.touchDown(screenX,Gdx.graphics.getHeight()-screenY,pointer,button);
        return false;
    }

    /**
     * Called when a finger was lifted or a mouse button was released. The button parameter will be {@link Input.Buttons#LEFT} on iOS.
     *
     * @param screenX
     * @param screenY
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        stage.touchUp(screenX,Gdx.graphics.getHeight()-screenY,pointer,button);
        return false;
    }

    /**
     * Called when a finger or the mouse was dragged.
     *
     * @param screenX
     * @param screenY
     * @param pointer the pointer for the event.
     * @return whether the input was processed
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        stage.touchDragged(screenX,Gdx.graphics.getHeight()-screenY,pointer);
        return false;
    }

    /**
     * Called when the mouse was moved without any buttons being pressed. Will not be called on iOS.
     *
     * @param screenX
     * @param screenY
     * @return whether the input was processed
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        stage.mouseMoved(screenX,Gdx.graphics.getHeight()-screenY);
        return false;
    }

    /**
     * Called when the mouse wheel was scrolled. Will not be called on iOS.
     *
     * @param amount the scroll amount, -1 or 1 depending on the direction the wheel was scrolled.
     * @return whether the input was processed.
     */
    @Override
    public boolean scrolled(int amount) {
        stage.scrolled(amount);
        return false;
    }
}
