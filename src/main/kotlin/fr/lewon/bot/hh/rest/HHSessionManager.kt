package fr.lewon.bot.hh.rest

import fr.lewon.bot.runner.bot.props.BotPropertyStore
import fr.lewon.bot.runner.session.AbstractSessionManager
import org.springframework.web.reactive.function.client.WebClient

class HHSessionManager(login: String, loginPropertyStore: BotPropertyStore, sessionDurability: Long, webClientBuilder: WebClient.Builder) : AbstractSessionManager(login, loginPropertyStore, sessionDurability, webClientBuilder) {

    @Throws(Exception::class)
    override fun generateSessionObject(webClient: WebClient, login: String, loginPropertyStore: BotPropertyStore): HHSession {
        val password = loginPropertyStore.getByKey("Password") as String
        val rp = HHRequestProcessor()
        rp.login(webClient, login, password)
        return HHSession(rp)
    }
}