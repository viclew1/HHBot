package fr.lewon.bot.hh.rest

import fr.lewon.bot.runner.session.AbstractSessionManager
import org.springframework.web.reactive.function.client.WebClient

class HHSessionManager(login: String, password: String, sessionDurability: Long, webClientBuilder: WebClient.Builder) : AbstractSessionManager(login, password, sessionDurability, webClientBuilder) {

    @Throws(Exception::class)
    override fun generateSessionObject(webClient: WebClient, login: String, password: String): HHSession {
        val response = HHRequestProcessor().getSession(webClient, login, password)
        val cookieValues: MutableMap<String, String> = HashMap()
        response.subscribe { r ->
            cookieValues["stay_online"] = r.cookies().getFirst("stay_online")?.value ?: ""
        }
        cookieValues["HAPBK"] = "web5"
        cookieValues["age_verification"] = "1"
        cookieValues["_pk_ses.2.6e07"] = "1"
        cookieValues["lang"] = "fr"
        cookieValues["member_guid"] = "A55C4849-F42D-4A1A-A6C6-11556C261A9C"
        cookieValues["HH_SESS_13"] = "5a465es2k49qbj96ht69d4nvs9"
        cookieValues["_pk_id.2.6e07"] = "cda6c741be7d8090.1562523528.2.1562529541.1562528524."
        var cookieHeaderValue = ""
        for ((key, value) in cookieValues) {
            cookieHeaderValue += "$key=$value; "
        }
        return HHSession("Cookie", cookieHeaderValue)
    }
}