package uk.co.renbinden.onrails.conversation

import uk.co.renbinden.ilse.ecs.Engine

interface ConversationEvent {
    operator fun invoke(engine: Engine)
}