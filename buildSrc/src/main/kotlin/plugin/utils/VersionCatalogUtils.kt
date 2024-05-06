package plugin.utils

import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.kotlin.dsl.getByType

fun getVersionProperty(extensions: ExtensionContainer, versionProperty: String): String {
    val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")
    var propertyValue = ""

    versionCatalog.findVersion(versionProperty).ifPresent {
        propertyValue = it.displayName
    }

    return propertyValue
}
