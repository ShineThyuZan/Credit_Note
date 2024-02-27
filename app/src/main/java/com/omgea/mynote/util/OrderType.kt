package com.omgea.mynote.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}