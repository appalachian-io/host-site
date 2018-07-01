package io.appalachian.hostsite

import akka.http.scaladsl.model._
import akka.http.scaladsl.server._
import java.nio.file.{Files, Path}

object HttpServer {
  object Route extends Directives {

    def route(cwd: Path): Route =
      extractHost { host =>
        val path = cwd.resolve(filterHost(host)).toAbsolutePath

        if (!Files.isSameFile(path.getParent, cwd))
          complete(StatusCodes.NotFound)
        else
          concat(
            pathEndOrSingleSlash {
              getFromFile(path.resolve("index.html").toString)
            },
            getFromBrowseableDirectory(path.toString)
          )
      }
  }

  private def filterHost(host: String): String =
    host
      .dropWhile(_ == '.')
      .filter(
        c =>
          (c >= 'A' && c <= 'Z') ||
            (c >= 'a' && c <= 'z') ||
            (c >= '0' && c <= '9') ||
            c == '.' ||
            c == '-')
      .trim
}
