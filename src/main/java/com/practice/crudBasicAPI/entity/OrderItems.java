package com.practice.crudBasicAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_order_items")
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int qty;
    private double subtotal;

//    @ManyToOne
//    @JoinColumn(name = "order_id", referencedColumnName = "id")
//    @JsonIgnore
//    private Orders order;
//
//
//    @ManyToOne
//    @JoinColumn(name = "product_id", referencedColumnName = "id")
//    @JsonIgnore
//    private Products product;

}
