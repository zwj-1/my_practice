package com.zwj.realizetype;

public class MyTest {
    public static void main(String[] args) throws CloneNotSupportedException {
        Realizetype realizetype = new Realizetype();
        Realizetype clone = (Realizetype) realizetype.clone();
        System.out.println(clone == realizetype);

        Citation citation = new Citation("张三", "同学：在2016学年第一学期中表现优秀，被评为三好学生。", "韶关学院");
        System.out.println(citation.name+citation.info+citation.getCollege());
        Citation citation1 = (Citation) citation.clone();
        citation1.setName("李四");
        System.out.println(citation1.name+citation1.info+citation1.getCollege());
    }

    public static class Citation implements Cloneable {
        String name;
        String info;
        String college;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getCollege() {
            return college;
        }

        public void setCollege(String college) {
            this.college = college;
        }

        public Citation(String name, String info, String college) {
            this.name = name;
            this.info = info;
            this.college = college;
        }
        @Override
        public  Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}
