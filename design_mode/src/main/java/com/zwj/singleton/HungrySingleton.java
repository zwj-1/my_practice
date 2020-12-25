package com.zwj.singleton;
/**
 * 单例模式-饿汉
 *
 * @author zwj
 * @date 2020-11-09
 */
public class HungrySingleton {

    private static volatile HungrySingleton instance = new HungrySingleton();

    private HungrySingleton() {

    }

    public static HungrySingleton getinstance() {
        return instance;
    }
}
