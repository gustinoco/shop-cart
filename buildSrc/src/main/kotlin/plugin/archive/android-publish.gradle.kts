import plugin.utils.getVersionProperty
import groovy.util.Node
import groovy.util.NodeList

apply(plugin = "maven-publish")

project.tasks.create("installArchive") {
    dependsOn("publish")
}

configure<PublishingExtension> {
    isCI()
    publications {
        createFrom("release")
    }
}

fun PublishingExtension.isCI() {
    val outputPath = System.getenv("CI_SERVER")?.let {
        "file://" + File(it).absolutePath
    } ?: run {
        "file://" + File(System.getProperty("user.home"), ".m2/repository").absolutePath
    }

    repositories { maven(url = outputPath) }
}

fun PublicationContainer.createFrom(type: String) {
    create<MavenPublication>(type) {
        val versionName = getVersionProperty(extensions, "module.version.name")
        val hasVariants = getVersionProperty(extensions, "module.hasvariants").toBoolean()

        groupId = getVersionProperty(extensions, "module.groupid")
        artifactId = getVersionProperty(extensions, "module.name")
        version = if (hasVariants) "$versionName-$type" else "$versionName"

        afterEvaluate {
            from(components[type])
        }
        setupPom()
    }
}

fun MavenPublication.setupPom() {
    pom {
        name.set(getVersionProperty(extensions, "module.name"))
        url.set(getVersionProperty(extensions, "module.vcs.url"))

        description.set(getVersionProperty(extensions, "module.description"))

        scm {
            connection.set(getVersionProperty(extensions, "module.scm.url"))
            developerConnection.set(getVersionProperty(extensions, "module.scm.url"))
            url.set(getVersionProperty(extensions, "module.vcs.url"))
        }
    }
}

fun XmlProvider.changeRuntimeScope() {
    asNode().get("dependencies").asNodeList().map { it.asNode() }.forEach { dependency ->
        dependency.children()?.map { it.asNode() }?.forEach { attributes ->
            attributes.get("scope").asNodeList().apply {
                val scopeNode = this.first().asNode()
                scopeNode.setValue("compile")
            }
        }
    }
}

@Throws(RuntimeException::class)
fun Any?.asNode(): Node {
    if (this == null) {
        throw RuntimeException("cannot cast null to node")
    }
    return this as Node
}

@Throws(RuntimeException::class)
fun Any?.asNodeList(): NodeList {
    if (this == null) {
        throw RuntimeException("cannot cast null to node list")
    }
    return this as NodeList
}
