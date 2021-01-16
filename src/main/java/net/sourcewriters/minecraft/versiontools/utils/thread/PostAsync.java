package net.sourcewriters.minecraft.versiontools.utils.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class PostAsync {

	private static ExecutorService SERVICE = Executors.newCachedThreadPool();

	public static void start() {
		if (isAlive())
			return;
		SERVICE = Executors.newCachedThreadPool();
	}

	public static void shutdown() {
		if (isDead())
			return;
		SERVICE.shutdown();
		SERVICE = null;
	}

	public static boolean isAlive() {
		return SERVICE != null;
	}

	public static boolean isDead() {
		return SERVICE == null;
	}

	public static void post(Runnable runnable) {
		if (isDead())
			return;
		SERVICE.submit(runnable);
	}

	public static void forcePost(Runnable runnable) {
		if (isDead())
			start();
		post(runnable);
	}

}