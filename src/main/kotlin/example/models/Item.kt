package example.models

import java.math.BigDecimal

data class Item(
        val id: String,
        val name: String,
        val description: String?,
        val category: String,
        val imgUrl: String?,
        val userId: String,
        val price: BigDecimal
)