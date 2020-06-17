import java.io

import gitbucket.core.controller.Context
import gitbucket.core.plugin.{RenderRequest, Renderer}
import gitbucket.core.service.RepositoryService.RepositoryInfo
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.JsonMethods
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
      "openapi.yml", "openapi.yaml", "openapi.json",
      "swagger.yml", "swagger.yaml", "swagger.json",
      "OpenAPI.YML", "openapi.Yaml", "openapi.JSON"
    )

    if (!processFilePatterns.contains(basename)) {
      return content
    }

    val yamlExtPatterns = List(
      "yml", "yml", "YAML", "Yaml", "YML"
    )

    val commonPackages =
      s"""
         |<link rel="stylesheet" type="text/css" href="$path/plugin-assets/swagger/swagger-ui.css">
         |<link rel="stylesheet" type="text/css" href="$path/plugin-assets/swagger/style.css">
         |<script src="$path/plugin-assets/swagger/swagger-ui-bundle.js"></script>
         |""".stripMargin

    val renderMaterials =
      s"""
         |<div id="swagger-viewer"></div>
         |<div id="spec" hidden>$content</div>
         |""".stripMargin

    val renderFunctions =
      """
        |function render_swagger() {
        |  const ui = SwaggerUIBundle({
        |    spec: spec,
        |    dom_id: '#swagger-viewer'
        |  })
        |  window.ui = ui
        |}
        |window.onload = render_swagger()
        |try {
        |  var preview = document.getElementById("btn-preview");
        |  preview.onclick = render_swagger()
        |}
        |catch (e) {}
        |""".stripMargin

      if (yamlExtPatterns.contains(ext)) {
        s"""
           |$commonPackages
           |<script src="$path/plugin-assets/swagger/js-yaml.min.js"></script>
           |$renderMaterials
           |<script>
           |  var spec = jsyaml.load(document.getElementById('spec').innerHTML)
           |  $renderFunctions
           |</script>
           |""".stripMargin
      } else {
        s"""
           |$commonPackages
           |$renderMaterials
           |<script>
           |  var spec = JSON.parse(document.getElementById('spec').innerHTML)
           |  $renderFunctions
           |</script>
           |""".stripMargin
      }
  }
}
