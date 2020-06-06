# gitbucket-swagger-plugin

[![Build Status](https://travis-ci.org/onukura/gitbcket-swagger-plugin.svg?branch=master)](https://travis-ci.org/onukura/gitbcket-swagger-plugin)

A GitBucket plugin for rendering swagger (openAPI) file using [swagger-ui](https://github.com/swagger-api/swagger-ui).

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
"openapi.yml", "openapi.yaml", "openapi.json", "swagger.yml", "swagger.yaml", "swagger.json", "OpenAPI.YML", "openapi.Yaml", "openapi.JSON"
```

## Version

Plugin version|GitBucket version
:---|:---
1.0|4.33.x
