# gitbucket-swagger-plugin

[![Build Status](https://travis-ci.org/onukura/gitbucket-swagger-plugin.svg?branch=master)](https://travis-ci.org/onukura/gitbucket-swagger-plugin)

A GitBucket plugin for rendering swagger (openAPI) file using [swagger-ui](https://github.com/swagger-api/swagger-ui).

## Screenshot

![screenshot](https://github.com/onukura/gitbcket-swagger-plugin/blob/assets/screenshot.png?raw=true)

## Install

1. Download *.jar from Releases.
2. Deploy it to `GITBUCKET_HOME/plugins`.
3. Restart GitBucket.

## Build from source

```sbt
sbt clean package
```

The built package is located at
`target/scala-2.13/gitbucket-swagger-plugin_2.13-{plugin-version}.jar`.

```sbt
sbt assembly
```

This makes the assembly package
`target/scala-2.13/gitbucket-swagger-plugin-{plugin-version}.jar`
for deployment.


## Usage

This plugin process files only with its name in the following list.

```bash
"openapi.yml", "openapi.yaml", "openapi.Yaml", "openapi.YML", "openapi.json", "openapi.JSON",
"swagger.yml", "swagger.yaml", "swagger.Yaml", "swagger.YML", "swagger.json", "swagger.JSON"
```

## Supported swagger file version

This plugin uses [swagger-ui v3.28.0](https://github.com/swagger-api/swagger-ui/releases/tag/v3.28.0).

Supported version fields are swagger: "2.0" and those that match openapi: 3.0.n (for example, openapi: 3.0.0).

See also [compatibility](https://github.com/swagger-api/swagger-ui#compatibility).

## Version

Plugin version|GitBucket version
:---|:---
1.0.x |4.32.x -
