package com.zwj.realizetype;

/**原型模式-浅克隆
 * @author zwj
 * @date 2020-11-09
 */
public class Realizetype implements Cloneable{
    @Override
    public Object clone() throws CloneNotSupportedException {
        return (Realizetype)super.clone();
    }
}
