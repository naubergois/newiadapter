package br.unifor.iadapter.searchclass;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import br.unifor.iadapter.threadGroup.workload.WorkLoad;

public class SearchClassWorkLoad {

	public static List<WorkLoad> getInstances(List<String> classNames)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		List<WorkLoad> list = new ArrayList<>();
		for (String string : classNames) {
			list.add((WorkLoad) Class.forName(string).getConstructor().newInstance());

		}
		return list;

	}

	public static WorkLoad getInstance(String className)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {

		return (WorkLoad) Class.forName(className).getConstructor().newInstance();

	}

	public static List<WorkLoad> getInstances(String[] classNames)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		List<WorkLoad> list = new ArrayList<>();
		for (String string : classNames) {
			list.add((WorkLoad) Class.forName(string).getConstructor().newInstance());

		}
		return list;

	}

	public static WorkLoad get(String className)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {

		return (WorkLoad) Class.forName(className).getConstructor().newInstance();

	}

	public static List<String> getClasses() {

		List<String> list = new ArrayList<String>();

		Reflections reflections = new Reflections("br.unifor.iadapter");
		Set classes = reflections.getSubTypesOf(WorkLoad.class);
		for (Object object : classes) {
			list.add(((Class) object).getCanonicalName());
		}

		return list;

	}

	public static void main(String[] args) {
		/*
		 * BeanDefinitionRegistry bdr = new SimpleBeanDefinitionRegistry();
		 * ClassPathBeanDefinitionScanner s = new
		 * ClassPathBeanDefinitionScanner(bdr);
		 * 
		 * TypeFilter tf = new AssignableTypeFilter(IAlgorithm.class);
		 * s.addIncludeFilter(tf); Set<BeanDefinition> beans =
		 * s.findCandidateComponents("br.unifor.iadapter"); for (BeanDefinition
		 * bd : beans) { System.out.println(bd.getBeanClassName()); // The
		 * BeanDefinition class gives access to the Class<?> and other //
		 * attributes. }
		 * 
		 * s.scan("br.unifor.iadapter");
		 */

		Reflections reflections = new Reflections("br.unifor.iadapter");
		Set classes = reflections.getSubTypesOf(WorkLoad.class);
		for (Object object : classes) {
			System.out.println(((Class) object).getCanonicalName());
		}
		// String[] beans = bdr.getBeanDefinitionNames();
		// System.out.println(beans[1]);

	}

}
