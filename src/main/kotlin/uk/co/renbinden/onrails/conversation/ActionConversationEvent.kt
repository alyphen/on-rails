package uk.co.renbinden.onrails.conversation

class ActionConversationEvent(val action: () -> Unit) : ConversationEvent {
    override fun invoke() {
        action()
    }
}