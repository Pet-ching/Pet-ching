package com.mandarin.petching.aop.login;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)//실행 시에 동작을 해야합니다.
@Target(ElementType.METHOD)
public @interface LoginCheck {
}
