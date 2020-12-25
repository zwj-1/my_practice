package com.zwj.factory.simpleness;

/**
 * 简单工厂
 * @author zwj
 */
public class Client {
    public static void main(String[] args) {
        SimpleFactory.makeProduct(1).show();
    }

    public static class Prodect1 implements Product {
        public  void show() {
            System.out.println("具体产品1......！");
        }
    }

    public static class Prodect2 implements Product {
        public void show() {
            System.out.println("具体产品2......！");
        }
    }
    final class Const{
        static final int PRODUCT_A = 0;
        static final int PRODUCT_B = 1;
        static final int PRODUCT_C = 2;
    }

    static class SimpleFactory {
        public static Product makeProduct(int kind) {
            switch (kind) {
                case Const.PRODUCT_A:
                    return new Prodect1();
                case Const.PRODUCT_B:
                    return new Prodect2();
            }
            return null;
        }
    }
}

