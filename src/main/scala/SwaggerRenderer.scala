import gitbucket.core.controller.Context
import gitbucket.core.plugin.{RenderRequest, Renderer}
import gitbucket.core.service.RepositoryService.RepositoryInfo
import play.twirl.api.Html
import scala.util.{Failure, Success, Try}

class SwaggerRenderer extends Renderer {

  def render(request: RenderRequest): Html = {
    import request._
    Html(Try(toHtml(filePath, fileContent, branch, repository, enableWikiLink, enableRefsLink)(context)) match {
      case Success(v) => v
      case Failure(e) => s"""<h2>Error</h2>"""
    })
  }

  def shutdown(): Unit = {
  }

  def toHtml(
              filePath: List[String],
              content: String,
              branch: String,
              repository: RepositoryInfo,
              enableWikiLink: Boolean,
              enableRefsLink: Boolean)(implicit context: Context): String = {
    val path = context.baseUrl
    val basename = filePath.head
    val ext = basename.split("\\.").last

    val processFilePatterns = List(
      "openapi.yml", "openapi.yaml", "openapi.Yaml", "openapi.YML", "openapi.json", "openapi.JSON",
      "swagger.yml", "swagger.yaml", "swagger.Yaml", "swagger.YML", "swagger.json", "swagger.JSON",
    )

    if (!processFilePatterns.contains(basename)) {
      return s"""<tt><pre class="plain">$content</pre></tt>"""
    }

    val jsonExtPatterns = List("json")

    val commonMaterial =
      s"""
         |<link rel="stylesheet" type="text/css" href="$path/plugin-assets/swagger/swagger-ui.css">
         |<link rel="stylesheet" type="text/css" href="$path/plugin-assets/swagger/style.css">
         |<script src="$path/plugin-assets/swagger/swagger-ui-bundle.js"></script>
         |<div id="swagger-viewer"></div>
         |<div id="spec" hidden>$content</div>
         |""".stripMargin

    if (jsonExtPatterns.contains(ext.toLowerCase)) {
      s"""
         |$commonMaterial
         |<script>
         |  function render_swagger() {
         |    const ui = SwaggerUIBundle({
         |      spec: JSON.parse(document.getElementById('spec').innerHTML),
         |      dom_id: '#swagger-viewer'
         |    })
         |    window.ui = ui
         |  }
         |  window.onload = render_swagger()
         |</script>
         |""".stripMargin
    } else {
      s"""
         |$commonMaterial
         |<script src="$path/plugin-assets/swagger/js-yaml.min.js"></script>
         |<script>
         |  function render_swagger() {
         |    const ui = SwaggerUIBundle({
         |      spec: jsyaml.load(document.getElementById('spec').innerHTML),
         |      dom_id: '#swagger-viewer'
         |    })
         |    window.ui = ui
         |  }
         |  window.onload = render_swagger()
         |</script>
         |""".stripMargin
    }
  }
}
