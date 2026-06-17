plugins {
    id("dev.prism")
}

group = "com.leclowndu93150"
version = "1.1.0"

val envFile = file(".env")
val envProps = java.util.Properties().apply {
    if (envFile.exists()) envFile.reader().use { load(it) }
}
fun secret(name: String): String =
    System.getenv(name) ?: envProps.getProperty(name, "")

prism {
    curseMaven()
    modrinthMaven()
    maven("Bawnorton", "https://maven.bawnorton.com/releases")
    maven("Fuzs Mod Resources", "https://raw.githubusercontent.com/Fuzss/modresources/main/maven/")

    metadata {
        modId = "immersive_horse_riding"
        name = "Immersive Horse Riding"
        description = "Improves horse riding mechanics with smooth acceleration, responsive turning, and realistic head bobbing."
        license = "MIT"
        author("Leclowndu93150")
    }

    publishing {
        type = STABLE
        changelog = "Release ${project.version}"

        curseforge {
            accessToken = providers.provider { secret("CURSEFORGE_TOKEN") }
            projectId = "1453947"
        }

        modrinth {
            accessToken = providers.provider { secret("MODRINTH_TOKEN") }
            projectId = "MmgRSJ2v"
        }
    }

    version("1.20.1") {
        parchmentMinecraftVersion = "1.20.1"
        parchmentMappingsVersion = "2023.09.03"

        common {
            compileOnly("org.ow2.asm:asm-tree:9.5")
            compileOnly("io.github.llamalad7:mixinextras-common:0.5.3")
            annotationProcessor("io.github.llamalad7:mixinextras-common:0.5.3")
            compileOnly("com.github.bawnorton.mixinsquared:mixinsquared-common:0.3.7-beta.2")
            annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-common:0.3.7-beta.2")
        }

        fabric {
            loaderVersion = "0.18.4"
            fabricApi("0.92.7+1.20.1")

            dependencies {
                modImplementation("curse.maven:horseman-1082085:7650571")
                modImplementation("fuzs.forgeconfigapiport:forgeconfigapiport-fabric:8.0.0")
                modImplementation("com.github.bawnorton.mixinsquared:mixinsquared-fabric:0.3.7-beta.2")
                jarJar("com.github.bawnorton.mixinsquared:mixinsquared-fabric:0.3.7-beta.2")
            }
        }
        forge {
            loaderVersion = "47.2.30"
            loaderVersionRange = "[47,)"

            dependencies {
                modImplementation("curse.maven:horseman-1082085:7650572")
                compileOnly("io.github.llamalad7:mixinextras-common:0.5.3")
                annotationProcessor("io.github.llamalad7:mixinextras-common:0.5.3")
                jarJar("io.github.llamalad7:mixinextras-forge:0.5.3")
                implementation("com.github.bawnorton.mixinsquared:mixinsquared-forge:0.3.7-beta.2")
                jarJar("com.github.bawnorton.mixinsquared:mixinsquared-forge:0.3.7-beta.2")

                modRuntimeOnly("curse.maven:dragns-livestock-overhaul-1131669:8162697")
                modRuntimeOnly("curse.maven:patchouli-306770:7731017")
                modRuntimeOnly("curse.maven:geckolib-388172:7553267")
            }
        }
    }

    version("1.21.1") {
        parchmentMinecraftVersion = "1.21"
        parchmentMappingsVersion = "2024.11.10"

        common {
            compileOnly("org.ow2.asm:asm-tree:9.5")
            compileOnly("com.github.bawnorton.mixinsquared:mixinsquared-common:0.3.7-beta.2")
            annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-common:0.3.7-beta.2")
        }

        fabric {
            loaderVersion = "0.16.9"
            fabricApi("0.109.0+1.21.1")

            dependencies {
                modImplementation("curse.maven:horseman-1082085:8166015")
                modImplementation("fuzs.forgeconfigapiport:forgeconfigapiport-fabric:21.1.0")
                modImplementation("com.github.bawnorton.mixinsquared:mixinsquared-fabric:0.3.7-beta.2")
                jarJar("com.github.bawnorton.mixinsquared:mixinsquared-fabric:0.3.7-beta.2")
            }
        }
        neoforge {
            loaderVersion = "21.1.80"
            loaderVersionRange = "[4,)"

            dependencies {
                modImplementation("curse.maven:horseman-1082085:7479795")
                implementation("com.github.bawnorton.mixinsquared:mixinsquared-neoforge:0.3.7-beta.2")
                jarJar("com.github.bawnorton.mixinsquared:mixinsquared-neoforge:0.3.7-beta.2")
                configuration("annotationProcessor", "com.github.bawnorton.mixinsquared:mixinsquared-common:0.3.7-beta.2")
            }
        }
    }

    version("1.21.11") {
        parchmentMinecraftVersion = "1.21.11"
        parchmentMappingsVersion = "2025.12.20"

        common {
            compileOnly("org.ow2.asm:asm-tree:9.5")
            compileOnly("com.github.bawnorton.mixinsquared:mixinsquared-common:0.3.7-beta.2")
            annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-common:0.3.7-beta.2")
        }

        fabric {
            loaderVersion = "0.18.4"
            fabricApi("0.141.3+1.21.11")

            dependencies {
                modImplementation("curse.maven:horseman-1082085:7491954")
                modImplementation("fuzs.forgeconfigapiport:forgeconfigapiport-fabric:21.11.0")
                modImplementation("com.github.bawnorton.mixinsquared:mixinsquared-fabric:0.3.7-beta.2")
                jarJar("com.github.bawnorton.mixinsquared:mixinsquared-fabric:0.3.7-beta.2")
            }
        }
        neoforge {
            loaderVersion = "21.11.38-beta"
            loaderVersionRange = "[4,)"

            dependencies {
                modImplementation("curse.maven:horseman-1082085:7491955")
                implementation("com.github.bawnorton.mixinsquared:mixinsquared-neoforge:0.3.7-beta.2")
                jarJar("com.github.bawnorton.mixinsquared:mixinsquared-neoforge:0.3.7-beta.2")
                configuration("annotationProcessor", "com.github.bawnorton.mixinsquared:mixinsquared-common:0.3.7-beta.2")
            }
        }
    }

    version("26.1.2") {
        parchmentMinecraftVersion = "1.21.11"
        parchmentMappingsVersion = "2025.12.20"

        common {
            compileOnly("org.ow2.asm:asm-tree:9.5")
            compileOnly("com.github.bawnorton.mixinsquared:mixinsquared-common:0.3.7-beta.2")
            annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-common:0.3.7-beta.2")
        }

        fabric {
            loaderVersion = "0.19.3"
            fabricApi("0.151.0+26.1.2")

            dependencies {
                modImplementation("curse.maven:horseman-1082085:7964145")
                modImplementation("fuzs.forgeconfigapiport:forgeconfigapiport-fabric:26.1.5")
                modImplementation("com.github.bawnorton.mixinsquared:mixinsquared-fabric:0.3.7-beta.2")
                jarJar("com.github.bawnorton.mixinsquared:mixinsquared-fabric:0.3.7-beta.2")
            }
        }
        neoforge {
            loaderVersion = "26.1.2.76"
            loaderVersionRange = "[4,)"

            dependencies {
                modImplementation("curse.maven:horseman-1082085:7964146")
                implementation("com.github.bawnorton.mixinsquared:mixinsquared-neoforge:0.3.7-beta.2")
                jarJar("com.github.bawnorton.mixinsquared:mixinsquared-neoforge:0.3.7-beta.2")
                configuration("annotationProcessor", "com.github.bawnorton.mixinsquared:mixinsquared-common:0.3.7-beta.2")
            }
        }
    }
}
