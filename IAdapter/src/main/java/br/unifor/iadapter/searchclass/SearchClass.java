package br.unifor.iadapter.searchclass;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import br.unifor.iadapter.algorithm.AbstractAlgorithm;

public class SearchClass {

	public static List<AbstractAlgorithm> getInstances(List<String> classNames)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		List<AbstractAlgorithm> list = new ArrayList<>();
		for (String string : classNames) {
			list.add((AbstractAlgorithm) Class.forName(string).getConstructor().newInstance());

		}
		return list;

	}
	
	public static AbstractAlgorithm get(String className) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException{
		
		return (AbstractAlgorithm) Class.forName(className).getConstructor().newInstance();
		
	}

	public static List<String> getClasses() {

		List<String> list = new ArrayList<String>();

		Reflections reflections = new Reflections("br.unifor.iadapter");
		Set classes = reflections.getSubTypesOf(AbstractAlgorithm.class);
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
		Set classes = reflections.getSubTypesOf(AbstractAlgorithm.class);
		for (Object object : classes) {
			System.out.println(((Class) object).getCanonicalName());
		}
				// String[] beans = bdr.getBeanDefinitionNames();
		// System.out.println(beans[1]);

	}

}
