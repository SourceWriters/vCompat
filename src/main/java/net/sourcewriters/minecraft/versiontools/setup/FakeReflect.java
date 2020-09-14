package net.sourcewriters.minecraft.versiontools.setup;

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

	public boolean putConstructor(String name, Constructor<?> constructor) {
		return true;
	}

	public boolean putMethod(String name, Method method) {
		return true;
	}

	public boolean putField(String name, Field field) {
		return true;
	}

	/*
	 * 
	 */

	public boolean containsMethod(String name) {
		return false;
	}

	public boolean containsField(String name) {
		return false;
	}

	/*
	 * 
	 */

	public Object init() {
		return null;
	}

	public Object init(String name, Object... args) {
		return null;
	}

	/*
	 * 
	 */

	public FakeReflect execute(String name, Object... args) {
		return this;
	}

	public FakeReflect execute(Object source, String name, Object... args) {
		return this;
	}

	public Object run(String name, Object... args) {
		return null;
	}

	public Object run(Object source, String name, Object... args) {
		return null;
	}

	/*
	 * 
	 */

	public Object getFieldValue(String name) {
		return null;
	}

	public Object getFieldValue(String name, Object source) {
		return null;
	}

	public void setFieldValue(String name, Object value) {
	}

	public void setFieldValue(Object source, String name, Object value) {
	}

	/*
	 * 
	 */

	public Constructor<?> getConstructor(String name) {
		return null;
	}

	public Method getMethod(String name) {
		return null;
	}

	public Field getField(String name) {
		return null;
	}

	/*
	 * 
	 */

	public FakeReflect searchConstructor(Predicate<AbstractReflect> predicate, String name, Class<?>... args) {
		return this;
	}

	public FakeReflect searchConstructor(String name, Class<?>... args) {
		return this;
	}

	public FakeReflect searchConstructorsByArguments(String base, Class<?>... arguments) {
		return this;
	}

	/*
	 * 
	 */

	public FakeReflect searchMethod(Predicate<AbstractReflect> predicate, String name, String methodName,
		Class<?>... arguments) {
		return this;
	}

	public FakeReflect searchMethod(String name, String methodName, Class<?>... arguments) {
		return this;
	}

	public FakeReflect searchMethodsByArguments(String base, Class<?>... arguments) {
		return this;
	}

	/*
	 * 
	 */

	public FakeReflect searchField(Predicate<AbstractReflect> predicate, String name, String fieldName) {
		return this;
	}

	public FakeReflect searchField(String name, String fieldName) {
		return this;
	}

	public FakeReflect searchFields(String name, String fieldName) {
		return this;
	}

	public FakeReflect searchField(String name, Class<?> type) {
		return this;
	}

	public FakeReflect searchFields(String name, Class<?> type) {
		return this;
	}

}