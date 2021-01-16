package net.sourcewriters.minecraft.versiontools.reflection.data.persistence;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

import com.syntaxphoenix.syntaxapi.data.container.nbt.NbtAdapterRegistry;
import com.syntaxphoenix.syntaxapi.data.container.nbt.NbtContainer;
import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.NbtTag;
import com.syntaxphoenix.syntaxapi.utils.key.IKey;

public class PersistentContainer<K> extends NbtContainer {

	protected final ReadWriteLock lock = new ReentrantReadWriteLock();
	protected final DataObserver observer;

	protected final K key;

	protected final File location;
	protected boolean changed;

	@SuppressWarnings("rawtypes")
	protected Consumer<PersistentContainer> consumer;

	public PersistentContainer(K key, File location, NbtAdapterRegistry registry) {
		super(registry);
		this.key = key;
		this.location = location;
		this.observer = new DataObserver(this);
	}

	public final DataObserver getObserver() {
		return observer;
	}

	@SuppressWarnings("rawtypes")
	public final void setLoadingAction(Consumer<PersistentContainer> consumer) {
		this.consumer = consumer;
	}

	public final void forceLoad() {
		observer.load();
	}

	public final K getKey() {
		return key;
	}

	public final File getLocation() {
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
	public Set<String> getKeyspaces() {
		lock.readLock().lock();
		Set<String> keys = new LinkedHashSet<>(super.getKeyspaces());
		lock.readLock().unlock();
		return keys;
	}

	@Override
	public IKey[] getKeys() {
		lock.readLock().lock();
		IKey[] keys = super.getKeys();
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
		return observer.isAlive();
	}

	public void clear() {
		lock.writeLock().lock();
		super.getRoot().clear();
		changed = true;
		lock.writeLock().unlock();
	}

	protected void read(NbtCompound compound) {
		if (observer.isAlive()) {
			return;
		}
		super.fromNbt(compound);
	}

	protected void shutdown() {
		observer.shutdown();
	}

	protected void delete() {
		lock.writeLock().lock();
		observer.save();
		super.getRoot().clear();
		observer.shutdown();
		lock.writeLock().unlock();
	}

}