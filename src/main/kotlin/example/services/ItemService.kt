package example.services

import example.models.Item
import java.util.*
import javax.inject.Singleton

@Singleton
class ItemService {

    private val items: List<Item> = createItems()

    fun listItems(
            searchText: String = "",
            category: String = "",
            from: Int = 0,
            max: Int = 1000,
            sortBy: String? = null,
            asc: Boolean = true
    ): Pair<List<Item>, Map<String, Int>> {
        if (items.isEmpty()) {
            //bootstrapitems
        }
        return items.filter {
            category.isEmpty() || it.category == category
        }.filter {
            searchText.isEmpty() ||
                    it.name.contains(searchText, true) ||
                    it.description?.contains(searchText, true) ?: false
        }.let {
            paginateItems(it, from, max, sortBy, asc) to it.fold(mutableMapOf()) { categories, item ->
                categories[item.category] = (categories[item.category] ?: 0) + 1
                categories
            }
        }
    }

    fun paginateItems(items: List<Item>, from: Int = 0, max: Int = 10, sortBy: String? = null, asc: Boolean = true): List<Item> {
        return items.sortedWith(compareBy {
            when (sortBy) {
                "price" -> it.price
                else -> it.id
            }
        })
                .let { if (!asc) it.reversed() else it }
                .subList(from.coerceAtMost(items.lastIndex), (from + max).coerceAtMost(items.lastIndex + 1))
    }

    fun getItem(id: String): Item? {
        return items.find { it.id == id }
    }

    fun getItemsByUser(userId: String): List<Item> {
        return items.filter { it.userId == userId }
    }

    fun getItemsCount(): Int {
        return items.size
    }

    private fun createItems(): List<Item> {
        return 0.rangeTo(30).map { i ->
            val (name, category) = itemNames.shuffled().first()
            Item(
                    id = i.toString(),
                    name = name,
                    category = category,
                    description = "This is a great ${name}. Barely Used, It's a deal!",
                    imgUrl = "/static/${name.toLowerCase()}.jpg",
                    userId = userCategory[category] ?: Random().nextInt(5).plus(5).toString(),
                    price = Random().nextInt(100).toBigDecimal().multiply(10.toBigDecimal()).coerceAtLeast(100.toBigDecimal())
            )
        }.toList()
    }
}

val itemNames = listOf(
        "Guitar" to "Musical Instruments",
        "Motorbike" to "Auto & Moto",
        "Laptop" to "Computers",
        "Dishwasher" to "Kitchen Appliances",
        "Smartphone" to "Phones",
        "Playstation" to "Videogame Consoles",
        "Bookshelf" to "Home Decor",
        "Hifi" to "Audio/Video",
        "Saxophone" to "Musical Instruments"
)

val userCategory = mapOf(
        "Musical Instruments" to "1",
        "Auto & Moto" to "2",
        "Audio/Video" to "3",
        "Home Decor" to "4"
)