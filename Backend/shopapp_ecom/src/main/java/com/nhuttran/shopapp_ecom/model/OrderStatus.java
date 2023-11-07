package com.nhuttran.shopapp_ecom.model;

import lombok.Builder;

@Builder
public class OrderStatus {
    public static final String PROCESSING = "processing";
    public static final String PENDING = "pending";
    public static final String SHUPPED = "shipped";
    public static final String DELIVERED = "delivered";
    public static final String CANCELLED = "cancelled";
}
