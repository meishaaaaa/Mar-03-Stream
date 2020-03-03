package com.thoughtworks;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {

    public static void main(String[] args) {
        // 以下是执行交易的交易员和发生的一系列交易,请为以下八个查询方法提供实现。
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        List<Transaction> transactions = Arrays.asList(new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710), new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        // 1.找出2011年的所有交易并按交易额排序(从低到高)
        System.out.println(get2011Transactions(transactions));

        // 2.交易员都在哪些不同的􏱜城市工作过
        System.out.println(getTradersCity(transactions));

        // 3.查找所有来自于剑桥的交易员，并按姓名排序
        System.out.println(getCambridgeTraders(transactions));

        // 4.返回所有交易员的姓名字符串，按字母顺序排序
        System.out.println(getTradersName(transactions));

        // 5.有没有交易员是在米兰工作的
        System.out.println(hasMilanTrader(transactions));

        // 6.返回交易员是剑桥的所有交易的交易额
        System.out.println(getCambridgeTransactionsValue(transactions));

        // 7.所有交易中，最高的交易额是多少
        System.out.println(getMaxTransactionValue(transactions));

        // 8.返回交易额最小的交易
        System.out.println(getMinTransaction(transactions));
    }

    public static List<Transaction> get2011Transactions(List<Transaction> transactions) {
        Stream<Transaction> stream = transactions.stream();
        Stream<Transaction> stream1 = stream.filter((year) -> year.getYear() == 2011);
        Stream<Transaction> stream2 = stream1.sorted(Comparator.comparing(Transaction::getValue));

        return stream2.collect(Collectors.toList());
    }

    public static List<String> getTradersCity(List<Transaction> transactions) {
        Stream<String> stream = transactions.stream().map(Transaction::getTrader).map(Trader::getCity);
        Stream<String> stream1 = stream.distinct();
        return stream1.collect(Collectors.toList());
    }

    public static List<Trader> getCambridgeTraders(List<Transaction> transactions) {
        Stream<Transaction> stream = transactions.stream();
        Stream<Transaction> stream1 = stream.filter((city) -> city.getTrader().getCity().equals("Cambridge"));
        Stream<Trader> stream2 = stream1.map(Transaction::getTrader);
        return stream2.sorted(Comparator.comparing(Trader::getName)).collect(Collectors.toList());
    }

    public static List<String> getTradersName(List<Transaction> transactions) {
        Stream<Transaction> stream = transactions.stream();
        Stream<Trader> stream1 = stream.map(Transaction::getTrader);
        return stream1.map(Trader::getName).sorted().collect(Collectors.toList());
    }

    //5.有没有交易员是在米兰工作的
    public static boolean hasMilanTrader(List<Transaction> transactions) {
        Stream<Transaction> stream = transactions.stream();
        return stream.anyMatch(e -> e.getTrader().getCity().equals("Milan"));
    }

    // 6.返回交易员是剑桥的所有交易的交易额

    public static List<Integer> getCambridgeTransactionsValue(List<Transaction> transactions) {
        Stream<Transaction> stream = transactions.stream();
        Stream<Integer> stream1 = stream.map(Transaction::getValue);

        return stream1.collect(Collectors.toList());

    }

    // 7.所有交易中，最高的交易额是多少
    public static Integer getMaxTransactionValue(List<Transaction> transactions) {
        Stream<Transaction> stream = transactions.stream();
        Optional<Transaction> max = stream.max(Comparator.comparing(Transaction::getValue));

        if (max.isPresent()) {
            return max.get().getValue();
        } else return null;

    }


    // 8.返回交易额最小的交易
    public static Transaction getMinTransaction(List<Transaction> transactions) {
        Stream<Transaction> stream = transactions.stream();
        Optional<Transaction> min = stream.min(Comparator.comparing(Transaction::getValue));

        return min.orElse(null);
    }
}
