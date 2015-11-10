package net.androidla.Main;

import net.androidla.sqlite.SqliteHelper;

public class InitThread implements Runnable {
	@Override
	public void run() {
		SqliteHelper.createTBALINK();
		SqliteHelper.createTBEMAIL();
	}
}
