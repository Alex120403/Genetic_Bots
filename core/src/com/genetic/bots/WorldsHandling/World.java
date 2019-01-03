package com.genetic.bots.WorldsHandling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import com.genetic.bots.*;
import com.genetic.bots.BotsHandling.Bot;
import com.genetic.bots.BotsHandling.BotFactory;
import com.genetic.bots.UI.BotState;
import com.genetic.bots.UI.ChromosomeDisplay;
import com.genetic.bots.UI.WorldsPanelItem;

import java.util.ArrayList;
import java.util.Random;

public class World implements Disposable {

    static Random random = new Random();

    // Constants
    static final int OPERATION_TIME = 100;
    static final float DEFAULT_MUTATE_PERCENTS = 1/8;
    public static int BOTS_COUNT;

    // Statement
    int width,height;
    BotFactory botFactory;
    Bot[] bots;
    Cell[][] map;
    BotState botState;
    int steps,sleepIterations;
    WorldUpdater worldUpdater;
    static Graph graph;
    public  int aliveBots;
    public  Bot bestBot;
    String name;
    WorldsPanelItem link;

    ArrayList<Bot> newBotsArrayList;

    static MapGenerator generator;

    @Override
    public void dispose() {
        //graph.dispose();
    }

    public World(Bot[] bots, int botsCount, WorldsPanelItem toLink,WorldUpdater wa) {
        if(bots == null) {
            BOTS_COUNT = botsCount;
            botFactory = new BotFactory();
            generator = new MapGenerator(Config.DEGREE_OF_WALLS,Config.DEGREE_OF_HUMANS,Config.DEGREE_OF_FIRE);
            map = generator.generateMap();
            this.bots = generateStarterBots(BOTS_COUNT);
            //botState = new BotState(this.bots);
            addBotsToMap(map, this.bots);
            graph = new Graph();
            worldUpdater = new WorldUpdater(this);
            worldUpdater.start();
        }
        else {
            map = generator.generateMap();
            this.bots = bots;
            addBotsToMap(map, this.bots);
            //botState = new BotState(bots);
            worldUpdater = wa;
            worldUpdater.setWorld(this);
        }

        link = toLink;

        for (int i = 0; i < this.bots.length; i++) {
            this.bots[i].setWorldID(link.getOrder());
        }


        aliveBots = BOTS_COUNT;
        bestBot = this.bots[0];
    }

    Bot[] generateStarterBots(int size) { // Generating array of bots with random genes
        Bot[] b = new Bot[size];
        for (int i = 0; i < size; i++) {
            b[i] = botFactory.createNewBot();
        }
        return b;
    }

    public byte getCellValue(int x, int y) {
        return map[x][y].getContent();
    }
    public Cell getCell(int x, int y) {
        return map[x][y];
    }

    public void addRandomFire() {
        byte rX;
        byte rY;
        do {
            rX = (byte)random.nextInt(Config.MAP_WIDTH);
            rY = (byte)random.nextInt(Config.MAP_HEIGHT);
        } while (map[rX][rY].getContent()!=Cell.TYPE_NOTHING);
        map[rX][rY].update(Cell.TYPE_FIRE);
    }
    public void addRandomHuman() {
        byte rX;
        byte rY;
        do {
            rX = (byte)random.nextInt(Config.MAP_WIDTH);
            rY = (byte)random.nextInt(Config.MAP_HEIGHT);
        } while (map[rX][rY].getContent()!=Cell.TYPE_NOTHING);
        map[rX][rY].update(Cell.TYPE_HUMAN);
    }


    void addBotsToMap(Cell[][] map,Bot[] bots) {
        short order = 0;
        if(order<=bots.length-1) {
            byte nX;
            byte nY;
            for (int i = order; i < bots.length; i++) {
                do {
                    nX = (byte) random.nextInt(Config.MAP_WIDTH);
                    nY = (byte) random.nextInt(Config.MAP_HEIGHT);
                } while (map[nX][nY].getContent()!=Cell.TYPE_NOTHING);
                bots[order].setCoords(nX,nY);
                map[nX][nY].putBot(bots[order]);
                order++;
            }
        }
    }


    synchronized void nextPopulation() {
        BotFactory botFactory = new BotFactory();
        if(newBotsArrayList == null || true) {
            copyBestBots();
        }
        if(Main.getSelectedWorldID() == link.getOrder()) graph.add((float)bots[0].getFitnessFunc());
        for (byte i = 0; i < bots.length; i++) {
            bots[i] = botFactory.generateByChromosome(newBotsArrayList.get(i));
            if(i%8==0) {
                botFactory.mutate(bots[i],Config.CHANCE_TO_MUTATE_ANOTHER_ONE_GENE);
            }
        }

        try {
            link.nextPopulation(bestBot.getFitnessFunc());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Main.worlds[link.getOrder()] = new World(bots,BOTS_COUNT,link,worldUpdater);

    }

    public Cell[][] getMap() {
        return map;
    }

    public void render() {

        for (int i = 0; i < Config.MAP_WIDTH; i++) {
            for (int j = 0; j < Config.MAP_HEIGHT; j++) {
                map[i][j].render();
            }
        }
        //botState.render();
        graph.render();



    }

    void copyBestBots() {

        Bot[] bestBots = new Bot[bots.length/8];
        bubbleSorter();

        newBotsArrayList = new ArrayList<Bot>();
        for (byte i = 0; i < bestBots.length; i++) {
            bestBots[i] = bots[i];
        }
        for (byte i = 0; i < bestBots.length*8; i++) {
            newBotsArrayList.add(bestBots[i/8]);
        }
    }

    public synchronized void update() {
        if(!link.isStart()) return;

        for (int i = 0; i < bots.length; i++) {  // Main cycle
            if(bots[i].isAlive()) {
                bots[i].makeStep();
                if(bots[i].getFitnessFunc()>bestBot.getFitnessFunc()) {
                    bestBot = bots[i];
                }
            }
            if(aliveBots==Config.BOTS_COUNT/8 && false){
                copyBestBots();
            }
            else if(aliveBots < 1) {
                nextPopulation();
                aliveBots = BOTS_COUNT;
            }

        }

        steps++;

    }

    void bubbleSorter(){
        for (int out = bots.length - 1; out >= 1; out--){
            for (int in = 0; in < out; in++){
                if(bots[in+1].getFitnessFunc() > bots[in].getFitnessFunc())
                    toSwap(in, in + 1);
            }
        }
    }
    void toSwap(int i1, int i2) {
        Bot buffer = bots[i1];
        bots[i1] = bots[i2];
        bots[i2] = buffer;

    }

    public WorldsPanelItem getLink() {
        return link;
    }

    public Bot[] getBots() {
        return bots;
    }

    public String getName() {
        return name;
    }

}
