package com.zwj.common.util;

import com.zwj.common.entity.Person;

import java.util.*;
import java.util.stream.Collectors;

/**Stream操作集合示例
 * @author zwj
 * @date 2021年07月19日
 */
public class UtilStreamList {
    /**
     * 集合stream去重
     * 注：参数为：字符串集合
     *
     * @param list
     * @return
     */
    private List<String> distinct(List<String> list) {
        return list.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 集合stream去重
     * 注：根据对象属性去重
     * @param personList
     * @return
     */
    private List<Person> distinctByProperty(List<Person> personList) {
        return personList.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Person::getName))), ArrayList::new)
        );
    }

    /**
     * 集合stream去重
     * 注：根据多个对象属性去重
     * @param personList
     * @return
     */
    private List<Person> distinctByPropertys(List<Person> personList) {
        return personList.stream().collect(
                Collectors. collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getName() + ";" + o.getSex()))), ArrayList::new)
        );
    }


    /**
     * 集合stream排序
     * 注：根据对象属性排序
     * @param personList
     * @return
     */
    private List<Object> sortByProperty(List<Person> personList) {
        return personList.stream().sorted(Comparator.comparing(Person::getAge).reversed()).collect(Collectors.toList());
    }

    /**
     * 集合stream分组
     * 此处：示例根据person名称分组
     *
     * @param personList
     * @return
     */
    private Map<String, List<Person>> grouping(List<Person> personList) {
        return personList.stream().collect(Collectors.groupingBy(c -> (String) c.getName()));
    }

    /**
     * 集合stream取属性组合新集合
     * @param personList
     * @return
     */
    private List<String> getPropertyNewList(List<Person> personList){
        return personList.stream().map(Person::getName).collect(Collectors.toList());
    }

    /**
     * 集合stream取属性组合Map
     * @param personList
     * @return
     */
    private Map<String,String> listToMap(List<Person> personList){
        return personList.stream().collect(Collectors.toMap(Person::getId, Person::getName));
    }
}
