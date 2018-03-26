val ApacheSpark = Integrations.ApacheSpark
val LihaoyiUtest = Integrations.LihaoyiUtest
val ScalaScala = Integrations.ScalaScala
val ScalaCenterVersions = Integrations.ScalaCenterVersions
val integrations = List(ApacheSpark, LihaoyiUtest, ScalaScala, ScalaCenterVersions)

val dummy = project
  .in(file("."))
  .aggregate(integrations: _*)
  .enablePlugins(IntegrationPlugin)
  .settings(
    name := "bloop-integrations-build",
    enableIndexCreation := true,
    integrationIndex := {
      Map(
        "spark" -> bloopConfigDir.in(ApacheSpark).value,
        "utest" -> bloopConfigDir.in(LihaoyiUtest).value,
        "scala" -> bloopConfigDir.in(ScalaScala).value,
        "versions" -> bloopConfigDir.in(ScalaCenterVersions).value
      )
    },
    cleanAllBuilds := {
      cleanAllBuilds.value
      clean.in(ApacheSpark).value
      clean.in(LihaoyiUtest).value
      clean.in(ScalaScala).value
      clean.in(ScalaCenterVersions).value
    }
  )
