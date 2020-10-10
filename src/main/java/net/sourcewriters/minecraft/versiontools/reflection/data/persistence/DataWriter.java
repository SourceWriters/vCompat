package net.sourcewriters.minecraft.versiontools.reflection.data.persistence;

import java.io.IOException;

import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.NbtNamedTag;
import com.syntaxphoenix.syntaxapi.nbt.NbtType;
import com.syntaxphoenix.syntaxapi.nbt.tools.NbtDeserializer;
import com.syntaxphoenix.syntaxapi.nbt.tools.NbtSerializer;

public class DataWriter extends Thread {

	private final PersistentContainer container;
	private final Thread hook = new Thread(() -> shutdown());

	private boolean alive = true;

	private int nextCheck = 100;

	protected DataWriter(PersistentContainer container) {
		this.container = container;
		Runtime.getRuntime().addShutdownHook(hook);
	}

	@Override
	public synchronized void start() {
		container.lock.writeLock().lock();
		if (container.location.exists()) {
			NbtNamedTag tag = null;
			try {
				tag = NbtDeserializer.COMPRESSED.fromFile(container.location);
			} catch (IOException ignore) {
			}
			if (tag != null && tag.getTag().getType() == NbtType.COMPOUND) {
				container.fromNbt((NbtCompound) tag.getTag());
			}
		}
		container.lock.writeLock().unlock();
		super.start();
	}

	@Override
	public void run() {
		while (alive) {
			if (nextCheck-- == 0) {
				nextCheck = 100;
				if (container.changed) {
					try {
						NbtSerializer.COMPRESSED.toFile(new NbtNamedTag("root", container.asNbt()), container.location);
					} catch (IOException ignore) {
					}
				}
			}
			try {
				sleep(10);
			} catch (InterruptedException ignore) {
			}
		}
	}

	public void shutdown() {
		alive = false;
		nextCheck = 0;
		while (alive) {
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
			}
		}
		Runtime.getRuntime().removeShutdownHook(hook);
	}

	public void kill() {
		alive = false;
		interrupt();
	}

}
