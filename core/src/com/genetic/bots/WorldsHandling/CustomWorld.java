package com.genetic.bots.WorldsHandling;

import com.genetic.bots.BotsHandling.Bot;
import com.genetic.bots.UI.WorldsPanelItem;
import com.genetic.bots.WorldUpdater;

public class CustomWorld extends World {

    public CustomWorld(Bot[] bots, int botsCount, WorldsPanelItem toLink, WorldUpdater wa) {
        super(bots, botsCount, toLink, wa);
    }

    @Override
    Bot[] generateStarterBots(int size) {
        return super.generateStarterBots(size);
    }

    @Override
    synchronized void nextPopulation() {
        super.nextPopulation();
    }

    @Override
    public synchronized void update() {
        super.update();
    }
}
