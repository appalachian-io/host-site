# host-site

## Overview

A small [Akka HTTP](https://doc.akka.io/docs/akka-http/current/) server that serves up content based on a request's `Host` value.

## Getting started

First, create a directory to store your sites. For instance, the following structure serves `example.org` and `example.com`:

> Note that all directories have listing enabled. However, the top level directory will serve up an `index.html` file by default if present.

```
$HOME/sites/example.com/index.html
$HOME/sites/example.org/index.html
```

Next, run the server:

```bash
docker run \
  -p 9090:9090 \
  -v "$HOME/sites:/data" \
  --rm -ti appalachian/host-site:<version>
```

Finally, do some requests:

```bash
curl -H 'Host: example.com' 127.0.0.1:9090
<h1>You're at example.com</h1>

curl -H 'Host: example.org' 127.0.0.1:9090
<h1>Hello for example.org</h1>
```

## Releasing

1. Fresh clone from upstream
2. `sbt release`
3. `git checkout v<version>`
4. `docker build -t appalachian/host-site:<version> .`
5. `docker push appalachian/host-site:<version>`

## License

Copyright (C) 2018 Jason Longshore (https://www.jasonlongshore.com/).

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this project except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0.

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
