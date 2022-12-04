package com.spring_util.clazz;

import com.spring_util.entity.ICBCPay;
import com.spring_util.entity.PayService;
import org.junit.Test;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.util.Set;

/**
 * @author pengYongQiang
 * @date 2020/11/21 11:19
 */
public class DemoTest {

	/**
	 * 获取类的属性
	 */
	@Test
	public void doWithLocalFields(){
		//仅当前类，不包括父类的属性
		ReflectionUtils.doWithLocalFields(ICBCPay.class, field -> {
			System.out.println(field.getName());
		});
		//当前类和包括父类的属性
		System.out.println("---------------");
		ReflectionUtils.doWithFields(ICBCPay.class,field -> {
			System.out.println(field.getName());
		});
	}
	/**
	 * 获取类的方法
	 */
	@Test
	public void doWithMethods(){
		//获取到类的所有方法，包括父类
		ReflectionUtils.doWithMethods(ICBCPay.class, method -> {
			System.out.println(method.getName());
		});
		//仅当前类，不包括父类的属性
		System.out.println("---------------");
		ReflectionUtils.doWithLocalMethods(ICBCPay.class, method -> {
			System.out.println(method.getName());
		});

	}

	/**
	 * 读取类元数据信息的类，如类上加的注解，方法上加的注解
	 */
	@Test
	public void MetadataReaderFactory() throws IOException {
		MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();
		MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(PayService.class.getName());
		AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
		Set<MethodMetadata> annotatedMethods = annotationMetadata.getAnnotatedMethods(Async.class.getName());
		System.out.println(annotatedMethods);
	}
}