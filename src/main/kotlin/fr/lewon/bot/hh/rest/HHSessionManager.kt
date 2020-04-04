package fr.lewon.bot.hh.rest

import fr.lewon.bot.runner.bot.props.BotPropertyStore
import fr.lewon.bot.runner.session.AbstractSessionManager
import org.springframework.web.reactive.function.client.WebClient

class HHSessionManager(login: String, loginPropertyStore: BotPropertyStore, sessionDurability: Long, webClientBuilder: WebClient.Builder) : AbstractSessionManager(login, loginPropertyStore, sessionDurability, webClientBuilder) {

    @Throws(Exception::class)
    override fun generateSessionObject(webClient: WebClient, login: String, loginPropertyStore: BotPropertyStore): HHSession {
        val password = loginPropertyStore.getByKey("Password") as String
        val response = HHRequestProcessor().getSession(webClient, login, password)
                ?: throw Exception("Unable to connect to Hentai Heroes, website may be down")
        val cookieValues: MutableMap<String, String> = HashMap()
        response.cookies().let {
            cookieValues["stay_online"] = it.getFirst("stay_online")?.value
                    ?: throw Exception("Unable to connect to Hentai Heroes, your user/password may be wrong")
            cookieValues["HH_SESS_13"] = it.getFirst("HH_SESS_13")?.value
                    ?: throw Exception("Unable to connect to Hentai Heroes, your user/password may be wrong")
        }
        cookieValues["HAPBK"] = "web5"
        cookieValues["age_verification"] = "1"
        cookieValues["_pk_ses.2.6e07"] = "1"
        cookieValues["lang"] = "fr"
        cookieValues["member_guid"] = "A55C4849-F42D-4A1A-A6C6-11556C261A9C"
        cookieValues["_pk_id.2.6e07"] = "cda6c741be7d8090.1562523528.2.1562529541.1562528524."
        val cookieHeaderValue = cookieValues
                .map { "${it.key}=${it.value}" }
                .joinToString("; ")

        response.releaseBody().block()
        return HHSession("Cookie", cookieHeaderValue)
    }
}