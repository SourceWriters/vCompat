package net.sourcewriters.minecraft.versiontools.reflection.data.persistence;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.syntaxphoenix.syntaxapi.data.container.nbt.NbtAdapterRegistry;
import com.syntaxphoenix.syntaxapi.data.container.nbt.NbtContainer;
import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.NbtTag;

public class PersistentContainer extends NbtContainer {

	protected final ReadWriteLock lock = new ReentrantReadWriteLock();
	protected final DataWriter writer = new DataWriter(this);

	protected final File location;
	protected boolean changed;

	public PersistentContainer(File location, NbtAdapterRegistry registry) {
		super(registry);
		this.location = location;
		this.writer.start();
	}

	public File getLocation() {
		return location;
	}

	@Override
	public Object get(String key) {
		lock.readLock().lock();
		Object value = super.get(key);
		lock.readLock().unlock();
		return value;
	}

	@Override
	public void set(String key, NbtTag tag) {
		lock.writeLock().lock();
		super.set(key, tag);
		changed = true;
		lock.writeLock().unlock();
	}

	@Override
	public boolean remove(String key) {
		lock.writeLock().lock();
		boolean state = super.remove(key);
		changed = true;
		lock.writeLock().unlock();
		return state;
	}

	@Override
	public Set<String> getKeys() {
		lock.readLock().lock();
		Set<String> keys = new LinkedHashSet<>(super.getKeys());
		lock.readLock().unlock();
		return keys;
	}

	@Override
	public int size() {
		lock.readLock().lock();
		int size = super.size();
		lock.readLock().unlock();
		return size;
	}

	@Override
	public NbtCompound getRoot() {
		return asNbt();
	}

	@Override
	public void fromNbt(NbtCompound nbt) {
		lock.writeLock().lock();
		super.fromNbt(nbt);
		changed = true;
		lock.writeLock().unlock();
	}

	@Override
	public NbtCompound asNbt() {
		lock.readLock().lock();
		NbtCompound compound = super.asNbt();
		lock.readLock().unlock();
		return compound;
	}

	public boolean isPersistent() {
		return writer.isAlive();
	}

	public void clear() {
		lock.writeLock().lock();
		super.getRoot().clear();
		changed = true;
		lock.writeLock().unlock();
	}

	protected void read(NbtCompound compound) {
		if (writer.isAlive())
			return;
		super.fromNbt(compound);
	}
	
	protected void shutdown() {
		writer.shutdown();
	}

	protected void delete() {
		lock.writeLock().lock();
		super.getRoot().clear();
		writer.kill();
		lock.writeLock().unlock();
	}

}