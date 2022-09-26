package com.shopee.study.template_method.example;

public class ConcreteClass2 extends AbstractClass {
    @Override
    protected void method1() {
        //...
    }

    @Override
    protected void method2() {
        //...
    }

    public static void main(String[] args) {
        AbstractClass demo = new ConcreteClass1();
        demo.templateMethod();
    }
}