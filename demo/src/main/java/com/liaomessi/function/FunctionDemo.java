package com.liaomessi.function;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Blue
 * @date 2021/5/30
 * @description 演示 function 系列接口
 */
public class FunctionDemo {
    public static void main(String[] args) {
        // 定义一个生产者方法，创建一个FunctionDemo对象
        Supplier<String> builder = () -> "里奥";
        String named = builder.get();
        System.out.println("起名" + named);
        System.out.println("==============================================================================");


        // 定义一个函数，功能是 给定你名字什么，返回全名
        Function<String, String> fullName = lastName -> lastName + "梅西";
        // 给名字后面加个 ·
        Function<String, String> printLastName = firstName -> {
            System.out.println("大家叫他小名： " + firstName);
            return firstName + "·";
        };
        // 输出全名
        Function<String, String> printFullName = name -> {
            System.out.println("我的全名是： " + name);
            return name;
        };
        //多个andThen，compose,执行顺序：（排列时注意andThen和andThen之间的相对顺序， compose和compose之间的相对顺序）
        // 1. 多个compose按照顺序执行
        // 2. 再执行apply
        // 3. 最后andThen按照顺序执行
        fullName.andThen(printFullName)
                .compose(printLastName)
                .andThen((n -> {
                    System.out.println(n + "长到3岁了");
                    return n;
                }))
                .andThen(n -> {
                    System.out.println(n + "长到10岁了");
                    return n;
                })
                .apply(named);
        System.out.println("==============================================================================");


        // 定义一个断言方法， 判断这个名字包含 liao
        String liao = "里奥";
        String messi = "梅西";
        String testName = "球王梅西";
        Predicate<String> liaoPredicate = name -> name.contains(liao);
        boolean isLiao = liaoPredicate.test(testName);
        // 等于反过来的断言 == !Predicate.test(String string);
        Predicate<String> negate = liaoPredicate.negate();
        boolean notLiao = negate.test(testName);
        System.out.println(testName + "名字含有" + liao + ": test:" + isLiao + "\t negate:" + notLiao + "\t");
        Predicate<String> isMessi = name -> name.contains(messi);
        // and（）同时满足两个断言
        boolean isLiaoMessi = liaoPredicate.and(isMessi).test(testName);
        System.out.println(testName + "名字既含有里奥也含有梅西: " + isLiaoMessi);
        // or() 满足其中一个断言
        boolean isLiaoOrMessi = liaoPredicate.or(isMessi).test(testName);
        System.out.println(testName + "名字含有里奥或者梅西: " + isLiaoOrMessi);

        System.out.println("==============================================================================");

        // 定义一个消费者方法，给定一个名字，输出问候语
        Consumer<String> consumer = name -> System.out.println(name + "高考报名");
        Consumer<String> consumer1 = name -> System.out.println(name + " 第一天考语文");
        Consumer<String> consumer2 = name -> System.out.println(name + " 第二天考数学");
        Consumer<String> consumer3 = name -> System.out.println(name + " 第三天考英语");
        Consumer<String> consumer4 = name -> System.out.println(name + " 第四天考理综");
        // 多个 andThen 本身执行是按照顺序的， 但是andThen一定会在accept后面执行, 下面 Function 多个 andThen， compose 同理，
        consumer.andThen(consumer1).andThen(consumer2).andThen(consumer3).andThen(consumer4).accept("里奥·梅西");

        System.out.println("==============================================================================");
        BiFunction<List<Integer>, Integer, Integer> sumTotal = (summaries, integration) -> {
            int sum = summaries.stream().mapToInt(Integer::intValue).sum() + integration;
            System.out.println("语数外加理综一共总分：" + sum);
            return sum;
        };

        Integer finalTotal = sumTotal.andThen(total -> {
            int totalWithExtra = total + 20;
            System.out.println("获得世界级奖杯，额外加20分，当前总分：" + totalWithExtra);
            return totalWithExtra;
        }).apply(Arrays.asList(100, 130, 90), 180);
        System.out.println("最终里奥·梅西高考成绩为：" + finalTotal);
    }
}
