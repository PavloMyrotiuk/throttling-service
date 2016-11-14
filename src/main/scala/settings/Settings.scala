package settings

import com.typesafe.config.{Config, ConfigFactory}

object Settings {

    private val config: Config = ConfigFactory.load()
    config.checkValid(config, "throttling-service")

    val graceRps = config.getInt("throttling-service.grace-rps")

}