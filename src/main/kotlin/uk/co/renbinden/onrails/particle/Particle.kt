package uk.co.renbinden.onrails.particle

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper
import uk.co.renbinden.ilse.particle.ParticleEffect

data class Particle(val effect: ParticleEffect) : Component {
    companion object : ComponentMapper<Particle>(Particle::class)
}