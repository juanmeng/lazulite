/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.daphne.lazulite.common.entity;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * <p> 抽象实体基类，提供统一的ID，和相关的基本功能方法
 * <p> 如果是如mysql这种自动生成主键的，请参考{@link BaseEntity}
 * <p/>
 * 子类只需要在类头上加 @SequenceGenerator(name="seq", sequenceName="你的sequence名字")
 * <p/>
 * <p/>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-12 下午4:05
 * <p>Version: 1.0
 */
//@MappedSuperclass
public abstract class BaseOracleEntity<PK extends Serializable> extends AbstractEntity<PK> {

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private PK id;

    @Override
    public PK getId() {
        return id;
    }

    @Override
    public void setId(PK id) {
        this.id = id;
    }
}