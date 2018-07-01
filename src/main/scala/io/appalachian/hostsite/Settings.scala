package io.appalachian.hostsite

import akka.actor._

final class Settings(system: ExtendedActorSystem) extends Extension {
  private val hostSite =
    system.settings.config.getConfig("appalachian.host-site")

  val bindHost: String = hostSite.getString("bind-host")
  val bindPort: Int = hostSite.getInt("bind-port")
}

object Settings extends ExtensionId[Settings] with ExtensionIdProvider {
  override def get(system: ActorSystem): Settings = super.get(system)

  override def lookup: Settings.type = Settings

  override def createExtension(system: ExtendedActorSystem): Settings =
    new Settings(system)
}
