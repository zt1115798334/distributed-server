package com.example.distributedbookdetails.externalService.functional;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2019/6/14 14:16
 * description:
 */
@FunctionalInterface
public interface SpecialEventInfoFunction<T, U, H, R> {
    R toList(T t, U u, H h);
}
