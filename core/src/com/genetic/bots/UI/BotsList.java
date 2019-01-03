package com.genetic.bots.UI;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;
import com.genetic.bots.BotsHandling.Bot;

public class BotsList implements Disposable {
    private BotsListItem[] items;

    public BotsList(Bot[] bots) {
        for (int i = 0; i < items.length; i++) {
            items[i] = new BotsListItem(bots[i]);
        }
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
        private Stage stage;

        public BotsListItem(Bot bot) {
            this.bot = bot;
            stage = new Stage();

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
}
