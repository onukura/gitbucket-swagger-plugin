import javax.servlet.ServletContext

import gitbucket.core.plugin.PluginRegistry
import gitbucket.core.service.SystemSettingsService
import gitbucket.core.service.SystemSettingsService.SystemSettings
import io.github.gitbucket.solidbase.model.Version

import scala.util.Try

class Plugin extends gitbucket.core.plugin.Plugin {
  override val pluginId: String = "swagger"
  override val pluginName: String = "Swagger renderer Plugin"
  override val description: String = "Rendering swagger files."
  override val versions: List[Version] = List(
    new Version("1.0.0"),
    new Version("1.0.1"),
    new Version("1.0.2"),
    new Version("1.0.3"),
    new Version("1.0.4"),
    new Version("1.0.5"),
    new Version("1.0.6"),
    new Version("1.0.7"),
  )

  private[this] var renderer: Option[SwaggerRenderer] = None

  override def initialize(registry: PluginRegistry, context: ServletContext, settings: SystemSettingsService.SystemSettings): Unit = {
    val test = Try{ new SwaggerRenderer() }
    val swagger = test.get
    registry.addRenderer("yml", swagger)
    registry.addRenderer("yaml", swagger)
    registry.addRenderer("json", swagger)
    renderer = Option(swagger)
    super.initialize(registry, context, settings)
  }

  override def shutdown(registry: PluginRegistry, context: ServletContext, settings: SystemSettings): Unit = {
    renderer.foreach(r => r.shutdown())
  }

  override val assetsMappings = Seq("/swagger" -> "/swagger/assets")

}
