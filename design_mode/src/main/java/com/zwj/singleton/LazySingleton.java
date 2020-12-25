package com.zwj.singleton;

/**
 * 单例模式-懒汉
 *
 * @author zwj
 * @date 2020-11-09
 */
public class LazySingleton {
    /**
     * 保证 instance 在所有线程中同步
     */
    private static volatile LazySingleton instance = null;

    /**
     * private 避免类在外部被实例化
     */
    private LazySingleton() {
    }

    public static synchronized LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
