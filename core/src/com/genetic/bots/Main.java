package com.genetic.bots;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.genetic.bots.BotsHandling.BotFactory;
import com.genetic.bots.BotsHandling.BotStatistics;
import com.genetic.bots.BotsHandling.CustomBot;
import com.genetic.bots.SQL.DbHandler;
import com.genetic.bots.UI.BotInfo;
import com.genetic.bots.UI.Menu;
import com.genetic.bots.UI.PanelsHandler;
import com.genetic.bots.WorldsHandling.Cell;
import com.genetic.bots.WorldsHandling.World;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class Main extends ApplicationAdapter {
	private Paint paint;
	public static World[] worlds;
	private InputHandler inputHandler;
	private Menu menu;
	private boolean paused,started = true,stopped;
	private PanelsHandler panelsHandler;
	private static int selectedWorldID = -1;


	// Runs when program starts
	@Override
	public void create () {

		menu = new Menu(this);
		inputHandler = new InputHandler();
		paint = new Paint();
		panelsHandler = new PanelsHandler(this);
		worlds = new World[6];
		setSelectedWorldID(-1);
		for (int i = 0; i < 10; i++) {
			try {
				DbHandler.getInstance().addBot(new BotFactory().createCustomBot(new BotStatistics(5,new Random().nextInt(100),
						8,"1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6","Custom bot!")));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}



	}

	// Runs when user clicks on start button
	public void start() {
		started = true;
		//mainWorld = new World(null,Config.BOTS_COUNT);
	}

	// Runs if user clicks on pause button
	public void paused() {
		paused = true;
	}

	// TODO remove or update it
	public void stop() {
		stopped = true;
		started = false;
	}

	// Draw current world and UI
	@Override
	public void render () {
		if(Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
			System.gc();
		}

		Gdx.gl.glClearColor(0.9f, 0.95f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (selectedWorldID!= -1 && !stopped){
			worlds[selectedWorldID].render();
		}
		menu.render();
		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && Gdx.input.isKeyPressed(Input.Keys.D)) {
			Config.IS_DEVELOPER_MODE_ENABLED = true;
		}
		panelsHandler.render();

	}

	@Override
	public void dispose () {
		Cell.dispose();
		BotInfo.dispose();
		paint.dispose();
		menu.dispose();
		for (World world:worlds) {
			if (world != null) {
				world.dispose();
			}
		}
	}

	public boolean isPaused() {
		return paused;
	}

	public boolean isStarted() {
		return started;
	}

	public boolean isStopped() {
		return stopped;
	}

	public static void setSelectedWorldID(int selectedWorldID1) {
		selectedWorldID = selectedWorldID1;

	}

	public static int getSelectedWorldID() {
		return selectedWorldID;
	}
}
