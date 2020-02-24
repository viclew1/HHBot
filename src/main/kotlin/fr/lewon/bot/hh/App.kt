package fr.lewon.bot.hh

import fr.lewon.bot.runner.util.BotOperationRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.PropertySource

@SpringBootApplication(scanBasePackages = ["fr.lewon.bot.runner"])
@PropertySource(value = ["classpath:appli.properties"], ignoreResourceNotFound = true)
open class App : ApplicationRunner {

    @Autowired
    private lateinit var botOperationRunner: BotOperationRunner

    @Throws(Exception::class)
    override fun run(args: ApplicationArguments) {
        val params = HashMap<String, String?>()
        val bot = HHBotBuilder().buildBot("kikooviclew", "vic12345", HashMap())
        bot.start()
    }

}

fun main(args: Array<String>) {
    SpringApplication.run(App::class.java, *args)
}