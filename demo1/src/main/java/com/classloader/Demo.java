package com.classloader;

import com.sun.crypto.provider.DESKeyFactory;

public class Demo {
	public static void main(String[] args) {
		System.out.println(String.class.getClassLoader());
		System.out.println(DESKeyFactory.class.getClassLoader());
		System.out.println(Demo.class.getClassLoader());
	}
}
