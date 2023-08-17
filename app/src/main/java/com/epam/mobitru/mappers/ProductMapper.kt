package com.epam.mobitru.mappers

import com.epam.mobitru.models.Product
import com.epam.mobitru.network.models.ProductItem

class ProductMapper {
    fun map(rawProducts: List<ProductItem>): List<Product> {
        return rawProducts.map {
            map(it)
        }
    }

    fun map(it: ProductItem) = Product(
        id = it.id,
        name = it.name,
        price = it.price,
        discountPrice = it.discountPrice,
        discountValue = it.discountValue,
        image = it.image,
    )
}