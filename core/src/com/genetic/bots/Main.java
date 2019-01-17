package com.genetic.bots;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.genetic.bots.UI.*;
import com.genetic.bots.WorldsHandling.Cell;
import com.genetic.bots.WorldsHandling.World;

import java.util.ArrayList;


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


	public static void setSelectedWorldID(int selectedWorldID1) {
		selectedWorldID = selectedWorldID1;

	}

	public static int getSelectedWorldID() {
		return selectedWorldID;
	}
}
