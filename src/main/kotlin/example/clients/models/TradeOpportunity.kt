package example.clients.models

import example.clients.models.TradeOpportunityStatus.*

data class TradeOpportunity(
        val id: String,
        val rootUserId: String,
        val steps: List<TradeStep>,
        val users: Map<String, TradeOpportunityStatus>,
        val maxUsers: Int
) {

    fun getStatus(): TradeOpportunityStatus = when {
        users.any { it.value == REJECTED } -> REJECTED
        users.all { it.value == ACCEPTED } -> ACCEPTED
        users.any { it.value == DONE } -> DONE
        else -> PENDING
    }
    fun toSummary(userId: String): TradeSummary {
        val selling = requireNotNull(steps.find { it.interest.itemUserid == userId })
        val buying = requireNotNull(steps.find { it.userId == userId })

        return TradeSummary(
                id = id,
                nsteps = steps.count(),
                userStatus = users[userId] ?: TradeOpportunityStatus.PENDING,
                tradeStatus = getStatus(),
                priceDelta = selling.interest.itemPrice - buying.interest.itemPrice,
                selling = selling.interest,
                buying = buying.interest
        )
    }
}

enum class TradeOpportunityStatus {
    PENDING,
    REJECTED,
    ACCEPTED,
    DONE
}