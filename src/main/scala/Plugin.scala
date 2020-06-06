import gitbucket.core.plugin.{AccountHook, PluginRegistry}
import gitbucket.core.service.SystemSettingsService
import io.github.gitbucket.solidbase.model.Version
import javax.servlet.ServletContext

class Plugin extends gitbucket.core.plugin.Plugin {
  override val pluginId: String = "swagger"
  override val pluginName: String = "Swagger renderer Plugin"
  override val description: String = "Rendering swagger files."
  override val versions: List[Version] = List(
    new Version("1.0.0"),
  )

  override val assetsMappings = Seq("/swagger" -> "/swagger/assets")

  override def initialize(registry: PluginRegistry, context: ServletContext, settings: SystemSettingsService.SystemSettings): Unit = {
    val swagger = new SwaggerRenderer()
    registry.addRenderer("yml", swagger)
    registry.addRenderer("yaml", swagger)
    registry.addRenderer("Yaml", swagger)
    registry.addRenderer("YAML", swagger)
    registry.addRenderer("json", swagger)
    registry.addRenderer("JSON", swagger)
    super.initialize(registry, context, settings)
  }
}
