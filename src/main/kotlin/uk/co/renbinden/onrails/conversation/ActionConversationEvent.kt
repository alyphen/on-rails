package uk.co.renbinden.onrails.conversation

import uk.co.renbinden.ilse.ecs.Engine

class ActionConversationEvent(val action: () -> Unit) : ConversationEvent {
    override fun invoke(engine: Engine) {
        action()
    }
}