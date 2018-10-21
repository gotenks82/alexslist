package example.clients.models

import java.math.BigDecimal

data class TradeSummary(
        val id : String,
        val nsteps: Int,
        val userStatus: TradeOpportunityStatus,
        val tradeStatus: TradeOpportunityStatus,
        val priceDelta: BigDecimal,
        val buying: Interest,
        val selling: Interest
) {
    fun getAdditionalPeople() = nsteps - 1
    fun isPending() : Boolean = tradeStatus == TradeOpportunityStatus.PENDING
    fun isAccepted() : Boolean = tradeStatus == TradeOpportunityStatus.ACCEPTED
    fun isRejected() : Boolean = tradeStatus == TradeOpportunityStatus.REJECTED
}