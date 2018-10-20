package example.clients.models

data class TradeStep(val userId: String, val interest: Interest)

data class TradeStepWithUserName(
        val userId: String,
        val interest: Interest,
        val buyerName: String,
        val sellerName: String
)