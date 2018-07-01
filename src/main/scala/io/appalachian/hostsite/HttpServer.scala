package io.appalachian.hostsite

import akka.http.scaladsl.server._

object HttpServer {
  object Route extends Directives {

    val route: Route =
      extractHost { host =>
        // @TODO use Path to build paths instead of string concat
        val dir = s"./${filterHost(host)}"

        concat(
          pathEndOrSingleSlash {
            getFromFile(s"$dir/index.html")
          },
          getFromBrowseableDirectory(dir)
        )
      }
  }

  private def filterHost(host: String): String =
    host.filter(
      c =>
        (c >= 'A' && c <= 'Z') ||
          (c >= 'a' && c <= 'z') ||
          (c >= '0' && c <= '9') ||
          c == '.' ||
          c == '-')
}
