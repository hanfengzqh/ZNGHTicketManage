package com.zng.ticket_manage.commonlibrary.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by zqh on 2018/1/11.
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface FragmentScope {
}
