/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

/**
 * 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.daphne.lazulite.sys.user.exception;

import com.daphne.lazulite.common.exception.BaseException;

/**
 * <p>User: 
 * <p>Date: 13-3-11 下午8:19
 * <p>Version: 1.0
 */
public class UserException extends BaseException {

    public UserException(String code, Object[] args) {
        super("user", code, args, null);
    }

}