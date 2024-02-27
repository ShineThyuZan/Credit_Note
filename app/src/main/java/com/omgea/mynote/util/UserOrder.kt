package com.omgea.mynote.util

sealed class UserOrder(val userOrderType: OrderType) {
    class Title(orderType: OrderType) : UserOrder(orderType)
    //  class Date(orderType: OrderType): UserOrder(orderType)

    fun copy(orderType: OrderType): UserOrder {
        return when (this) {
            is Title -> Title(orderType)
            // is Date -> Date(orderType)
        }
    }
}