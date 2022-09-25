package com.spring_ext.factorybean;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;


/**
 * 在spring中一般与 @Enablexxxx 开启某个功能
 * 在@Enablexxxx注解中 加@Import(MyImportSelector.class)
 *
 */
public class MyImportSelector implements ImportSelector {
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[]{PayServiceImpl.class.getName()};
	}
}
