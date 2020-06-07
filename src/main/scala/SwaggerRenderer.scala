import java.io

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
      case Failure(e) => s"""<h2>Error</h2><div class="ipynb-error"><pre>$e</pre></div>"""
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
    val f = new io.File(context.currentPath)
    val basename = f.getName()

    val processFilePatterns = List(
      "openapi.yml", "openapi.yaml", "openapi.json",
      "swagger.yml", "swagger.yaml", "swagger.json",
      "OpenAPI.YML", "openapi.Yaml", "openapi.JSON"
    )

    if (processFilePatterns.contains(basename)) {
      s"""
      <link rel="stylesheet" type="text/css" href="$path/plugin-assets/swagger/swagger-ui.css">
      <link rel="stylesheet" type="text/css" href="$path/plugin-assets/swagger/style.css">
      <script src="$path/plugin-assets/swagger/swagger-ui-bundle.js"></script>
      <div id="swagger-viewer">
      <script>
      window.onload = function() {
        const ui = SwaggerUIBundle({
          url: "${context.request.getRequestURL + "?raw=true"}",
          dom_id: '#swagger-viewer'
        })
        window.ui = ui
      }
      </script>
      """
    } else {
      content
    }

  }

}
