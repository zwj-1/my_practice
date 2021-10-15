package com.zwj;

/**
 * 视洞科技二面面试题
 * 100个人，每个人随机发放黑白不同颜色的各50顶帽子，每个人说自己帽子的颜色，怎么保证说对的正确率最高。
 *
 * @author zwj
 * @date 2021年10月13日
 */
public class InterviewQuestions {
    int total = 100;
    int trueTotal = 50;
    int falseTotal = 50;

    public static void main(String[] args) {

    }

    /**
     * 查询说对自己帽子颜色正确率
     */
    private void getRadio(int trueTotal, int falseTotal, int userTotal) {
        // 最后一个人可以看到前面99人的帽子颜色，从而说出自己帽子颜色，颜色奇数即为自己的颜色。
        // 同理第99人在知道第100人颜色后，再知道前98人颜色，即可推理出+第100颜色，奇数颜色为自己颜色。
        // 默认第100人颜色帽子为true(白色)
        boolean defaultColor = true;
        if (defaultColor) {
            trueTotal += 1;

            if (true) {

            }
        }


    }
}
