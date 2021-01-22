package com.zwj;

import java.util.Arrays;

/**
 * 8 种排序算法
 *
 * @author zwj
 * @date 2021-1-20
 */
public class SortAlgorithm {
    public static void main(String[] args) {
        int[] a = {1, 8, 2, 6, 9, 20, 5, 3, 88};
        // 直接插入排序
//        directSort(a);
        // 希尔排序
//        shellSort(a);
        // 简单选择排序
//        selectSort(a);
        // 冒泡排序
//        bubbleSort(a);
        // 快速排序
//        System.out.println(Arrays.toString(quickSort(a, 0, a.length - 1)));
    }

    /**
     * 1、直接插入排序-对数组升序排序
     * 每次将一个待排序的数据按照大小插入到前面已经排好序的适当位置，直到全部数据插入完成为止。
     *
     * @param a
     */
    private static void directSort(int[] a) {
        if (a == null || a.length < 1) {
            return;
        }
        for (int i = 0; i < a.length; i++) {
            int b = a[i];
            int j = i - 1;
            for (j = j; j >= 0; j--) {
                // i-1位数字与i位数字比大小，
                if (a[j] > a[j + 1]) {
                    // 临时存放数字
                    int tem = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tem;
                }
            }
        }
        System.out.println(Arrays.toString(a));
    }

    /**
     * 2、希尔排序-对直接插入排序的优化
     * 原理：希尔排序在数组中采用跳跃式分组的策略，通过某个增量将数组元素划分为若干组，
     * 然后分组进行插入排序，随后逐步缩小增量，继续按组进行插入排序操作，直至增量为1。
     * 希尔排序通过这种策略使得整个数组在初始阶段达到从宏观上看基本有序，小的基本在前，
     * 大的基本在后。然后缩小增量，到增量为1时，其实多数情况下只需微调即可，不会涉及过多的数据移动。
     *
     * @param a
     */
    private static void shellSort(int[] a) {
        if (a == null || a.length < 1) {
            return;
        }
        int h = a.length;
        while (h != 0) {
            h = h / 2;
            // 分组数
            for (int x = 0; x < h; x++) {
                for (int i = 0; i < a.length; i++) {
                    int j = i - h;
                    for (j = j; j >= 0; j--) {
                        if (a[j] > a[j + h]) {
                            // 临时存放数字
                            int tem = a[j];
                            a[j] = a[j + h];
                            a[j + h] = tem;
                        }
                    }
                }
            }
        }
        System.out.println(Arrays.toString(a));
    }

    /**
     * 3、简单选择排序
     * 原理：首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置，
     * 然后，再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。
     * 以此类推，直到所有元素均排序完毕。
     *
     * @param a
     */
    private static void selectSort(int[] a) {
        if (a == null || a.length < 1) {
            return;
        }
        for (int i = 0; i < a.length; i++) {
            int min = a[i];
            int position = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < min) {
                    min = a[j];
                    position = j;
                }
            }
            a[position] = a[i];
            a[i] = min;
        }
        System.out.println(Arrays.toString(a));
    }

    /**
     * 堆排序-对简单选择排序的优化
     * 原理：1、将序列构建成大顶堆。
     * 2、将根节点与最后一个节点交换，然后断开最后一个节点。
     * 3、重复第一、二步，直到所有节点断开。
     *
     * @param a
     */
    private static void heapSort(int[] a) {

    }

    /**
     * 冒泡排序
     * 原理：比较两个相邻的元素，将值大的元素交换到右边
     * 将序列中所有元素两两比较，将最大的放在最后面。
     * 将剩余序列中所有元素两两比较，将最大的放在最后面。
     * 重复第二步，直到只剩下一个数。
     *
     * @param a
     */
    private static void bubbleSort(int[] a) {
        if (a == null || a.length < 1) {
            return;
        }
        int b = a.length;
        int temp;
        for (int i = 0; i < b; i++) {
            for (int j = 0; j < b - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(a));
    }

    /**
     * 快速排序 --速度最快的排序
     * 原理：要求时间最快时。
     * 选择第一个数为p，小于p的数放在左边，大于p的数放在右边。
     * 递归的将p左边和右边的数都按照第一步进行，直到不能递归。
     * 参考：https://joonwhee.blog.csdn.net/article/details/80071521?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-2.control&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-2.control
     *
     * @param a
     * @param start
     * @param end
     */
    private static int[] quickSort(int[] a, int start, int end) {
        if (a == null || a.length < 1) {
            return a;
        }
        if (start >= end) {
            return a;
        }
        int i = start;
        int j = end;
        int temp = a[i];
        while (i < j) {
            while (i < j && a[j] >= temp) {
                // 从右往左遍历，直到找到比基准小的数字，跳出循环
                j--;
            }
            if (i < j) {
                // 将比基准小的数字赋值给当前i的坑位
                a[i] = a[j];
                // i向右移动一位，为j位置的坑位填坑做准备
                i++;
            }
            while (i < j && a[i] < temp) {
                // i向右遍历，直到找到大于i位置的数字，跳出循环
                i++;
            }
            if (i < j) {
                // 将当前i位置的数字，赋值给j坑位
                a[j] = a[i];
                // j向左移动一位，为下一次和基准比大小做准备
                j--;
            }
        }
        // 将基准放入数组中间
        a[i] = temp;
        // 分治法，递归分段重复上面操作 -基准之前一段，之后一段
        quickSort(a, start, i - 1);
        quickSort(a, i + 1, end);
        return a;
    }

    /**
     * 归并排序：速度仅次于快排，内存少的时候使用，可以进行并行计算的时候使用。
     * 选择相邻两个数组成一个有序序列。
     * 选择相邻的两个有序序列组成一个有序序列。
     * 重复第二步，直到全部组成一个有序序列。
     *
     * @param a
     */
    private static void mergeSort(int[] a) {

    }
}
