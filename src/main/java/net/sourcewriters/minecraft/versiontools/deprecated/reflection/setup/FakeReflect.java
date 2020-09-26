package net.sourcewriters.minecraft.versiontools.deprecated.reflection.setup;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

import com.syntaxphoenix.syntaxapi.reflection.AbstractReflect;
import com.syntaxphoenix.syntaxapi.reflection.Reflect;

public class FakeReflect extends Reflect {

	public static final FakeReflect FAKE = new FakeReflect();

	@SuppressWarnings("rawtypes")
	private final Collection collection = Collections.unmodifiableCollection(Arrays.asList());

	private FakeReflect() {
		super((Class<?>) null);
	}

	/*
	 * 
	 */

	@Override
	public Class<?> getOwner() {
		return null;
	}

	/*
	 * 
	 */

	@Override
	public void delete() {
	}

	/*
	 * 
	 */

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Constructor<?>> getConstructors() {
		return collection;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Method> getMethods() {
		return collection;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Field> getFields() {
		return collection;
	}

	/*
	 * 
	 */

	@Override
	public boolean putConstructor(String name, Constructor<?> constructor) {
		return true;
	}

	@Override
	public boolean putMethod(String name, Method method) {
		return true;
	}

	@Override
	public boolean putField(String name, Field field) {
		return true;
	}

	/*
	 * 
	 */

	@Override
	public boolean containsMethod(String name) {
		return false;
	}

	@Override
	public boolean containsField(String name) {
		return false;
	}

	/*
	 * 
	 */

	@Override
	public Object init() {
		return null;
	}

	@Override
	public Object init(String name, Object... args) {
		return null;
	}

	/*
	 * 
	 */

	@Override
	public FakeReflect execute(String name, Object... args) {
		return this;
	}

	@Override
	public FakeReflect execute(Object source, String name, Object... args) {
		return this;
	}

	@Override
	public Object run(String name, Object... args) {
		return null;
	}

	@Override
	public Object run(Object source, String name, Object... args) {
		return null;
	}

	/*
	 * 
	 */

	@Override
	public Object getFieldValue(String name) {
		return null;
	}

	@Override
	public Object getFieldValue(String name, Object source) {
		return null;
	}

	@Override
	public void setFieldValue(String name, Object value) {
	}

	@Override
	public void setFieldValue(Object source, String name, Object value) {
	}

	/*
	 * 
	 */

	@Override
	public Constructor<?> getConstructor(String name) {
		return null;
	}

	@Override
	public Method getMethod(String name) {
		return null;
	}

	@Override
	public Field getField(String name) {
		return null;
	}

	/*
	 * 
	 */

	@Override
	public FakeReflect searchConstructor(Predicate<AbstractReflect> predicate, String name, Class<?>... args) {
		return this;
	}

	@Override
	public FakeReflect searchConstructor(String name, Class<?>... args) {
		return this;
	}

	@Override
	public FakeReflect searchConstructorsByArguments(String base, Class<?>... arguments) {
		return this;
	}

	/*
	 * 
	 */

	@Override
	public FakeReflect searchMethod(Predicate<AbstractReflect> predicate, String name, String methodName,
		Class<?>... arguments) {
		return this;
	}

	@Override
	public FakeReflect searchMethod(String name, String methodName, Class<?>... arguments) {
		return this;
	}

	@Override
	public FakeReflect searchMethodsByArguments(String base, Class<?>... arguments) {
		return this;
	}

	/*
	 * 
	 */

	@Override
	public FakeReflect searchField(Predicate<AbstractReflect> predicate, String name, String fieldName) {
		return this;
	}

	@Override
	public FakeReflect searchField(String name, String fieldName) {
		return this;
	}

	@Override
	public FakeReflect searchFields(String name, String fieldName) {
		return this;
	}

	@Override
	public FakeReflect searchField(String name, Class<?> type) {
		return this;
	}

	@Override
	public FakeReflect searchFields(String name, Class<?> type) {
		return this;
	}

}