package com.demo.redis.patterns.entity;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString
@Table
@Builder
public class Product  implements Persistable {


    @Column
    @Id
    private Integer id;
    @Column
    private String description;
    @Column
    private Double price;
    @Column
    private Integer qtyavailable;


    @Transient
    private boolean newProduct;

    @Override
    @Transient
    public boolean isNew() {
        return this.newProduct || id == null;
    }

    public Product setAsNew(){
        this.newProduct = true;
        return this;
    }

}
