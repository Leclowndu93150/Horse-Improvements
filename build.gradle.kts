plugins {
    id("dev.prism")
}

group = "com.leclowndu93150"
version = "2.0.0"

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
    maven("Leclown", "https://maven.leclowndu93150.dev/releases")
    maven("Xander", "https://maven.isxander.dev/releases")

    metadata {
        modId = "immersive_horse_riding"
        name = "Immersive Horse Riding"
        description = "Improves horse riding mechanics with smooth acceleration, responsive turning, and realistic head bobbing."
        license = "MIT"
        author("Leclowndu93150")
    }

    publishing {
        type = STABLE
        changelog = """
            - Added compatibility with Leawind's Third Person: horse turning and head bobbing now work in its third person view
            - Added compatibility with Shoulder Surfing Reloaded: horse turning and head bobbing now work in its shoulder surfing view
            - Added compatibility with Better Third Person: horse turning and head bobbing now work in its third person view
            - Fixed camera bobbing being overwritten by Real Camera
            - Added a config screen (Mod Menu / mods list)
            - Smooth acceleration, key turning, camera bobbing and horse head bobbing can now each be turned on or off
            - Added a Reverse Speed option to control how fast the horse walks backwards, or disable walking backwards
            - Added a Camera Steering option: when enabled, the horse turns toward where you look instead of being steered with the strafe keys
            - Added a Third Person Bobbing option (on by default): the camera now also bobs while riding in third person
            - Added a Look With Camera option (on by default): in third person camera mods, the rider looks where the camera looks instead of spinning toward the direction the horse moves
            - Now requires BaguetteLib and YetAnotherConfigLib
        """.trimIndent()

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
            modCompileOnly("curse.maven:shoulder-surfing-reloaded-243190:8725611")
            modCompileOnly("curse.maven:leawind-third-person-930880:5961735")
            modCompileOnly("curse.maven:perspective-api-1575322:8595558")
            modCompileOnly("com.leclowndu93150.baguettelib:baguettelib-1.20.1-common:2.0.5")
            modCompileOnly("dev.isxander:yet-another-config-lib:3.6.6+1.20.1-forge")
            modCompileOnly("curse.maven:better-third-person-435044:4596768")
            modCompileOnly("curse.maven:real-camera-851574:8333284")
        }

        fabric {
            loaderVersion = "0.19.2"
            fabricApi("0.92.7+1.20.1")

            configuration("emfMods")
            configuration("emfResourcePacks")

            dependencies {
                modImplementation("curse.maven:horseman-1082085:7650571")
                modImplementation("fuzs.forgeconfigapiport:forgeconfigapiport-fabric:8.0.0")
                modImplementation("com.github.bawnorton.mixinsquared:mixinsquared-fabric:0.3.7-beta.2")
                jarJar("com.github.bawnorton.mixinsquared:mixinsquared-fabric:0.3.7-beta.2")
                modCompileOnly("curse.maven:shoulder-surfing-reloaded-243190:8725615")
                modCompileOnly("curse.maven:leawind-third-person-930880:5961736")
                modCompileOnly("curse.maven:perspective-api-1575322:8595555")
                modImplementation("com.leclowndu93150.baguettelib:baguettelib-1.20.1-fabric:2.0.5")
                modCompileOnly("dev.isxander:yet-another-config-lib:3.6.6+1.20.1-fabric")
                modRuntimeOnly("dev.isxander:yet-another-config-lib:3.6.6+1.20.1-fabric")
                modCompileOnly("curse.maven:better-third-person-435044:4596636")
                modCompileOnly("curse.maven:real-camera-851574:8333285")

                configuration("emfMods", "curse.maven:entity-model-features-844662:8799078")
                configuration("emfMods", "curse.maven:entity-texture-features-fabric-568563:8789235")
                configuration("emfResourcePacks", "curse.maven:prettier-horses-1238331:8154340@zip")
            }

            publishingDependencies {
                requires("baguettelib")
                requires("yacl")
            }

            rawLoom {
                runs.create("clientEmf") {
                    client()
                    configName = "1.20.1 Fabric Client (Emf)"
                    runDir("runs/emf")
                }
            }

            rawProject {
                val loaderProject = this
                val copyEmfMods = loaderProject.tasks.register<Copy>("copyEmfMods") {
                    from(loaderProject.configurations.named("emfMods"))
                    into(loaderProject.file("runs/emf/mods"))
                }
                val copyEmfResourcePacks = loaderProject.tasks.register<Copy>("copyEmfResourcePacks") {
                    val packs = loaderProject.configurations.named("emfResourcePacks")
                    from(packs)
                    into(loaderProject.file("runs/emf/resourcepacks"))
                    doLast {
                        val options = loaderProject.file("runs/emf/options.txt")
                        if (!options.exists()) {
                            val enabled = packs.get().files.joinToString(",") { "\"file/${it.name}\"" }
                            options.writeText("resourcePacks:[\"vanilla\",\"fabric\",$enabled]\n")
                        }
                    }
                }
                loaderProject.tasks.matching { it.name == "runClientEmf" || it.name == "ideaSyncTask" }.configureEach {
                    dependsOn(copyEmfMods, copyEmfResourcePacks)
                }
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
                modCompileOnly("curse.maven:shoulder-surfing-reloaded-243190:8725611")
                modCompileOnly("curse.maven:leawind-third-person-930880:5961735")
                modCompileOnly("curse.maven:perspective-api-1575322:8595558")
                modImplementation("com.leclowndu93150.baguettelib:baguettelib-1.20.1-forge:2.0.5")
                modCompileOnly("dev.isxander:yet-another-config-lib:3.6.6+1.20.1-forge")
                modRuntimeOnly("dev.isxander:yet-another-config-lib:3.6.6+1.20.1-forge")
                modCompileOnly("curse.maven:better-third-person-435044:4596768")
                modCompileOnly("curse.maven:real-camera-851574:8333284")
            }

            publishingDependencies {
                requires("baguettelib")
                requires("yacl")
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
            compileOnly("curse.maven:shoulder-surfing-reloaded-243190:8725628")
            compileOnly("curse.maven:leawind-third-person-930880:6996698")
            compileOnly("curse.maven:perspective-api-1575322:8595570")
            modCompileOnly("com.leclowndu93150.baguettelib:baguettelib-1.21.1-common:2.0.5")
            modCompileOnly("dev.isxander:yet-another-config-lib:3.8.1+1.21.1-neoforge")
            compileOnly("curse.maven:better-third-person-435044:5833474")
        }

        fabric {
            loaderVersion = "0.19.2"
            fabricApi("0.109.0+1.21.1")

            configuration("shoulderSurfingMods")
            configuration("leawindMods")
            configuration("leawindLegacyMods")
            configuration("betterThirdPersonMods")
            configuration("realCameraMods")
            configuration("emfMods")
            configuration("emfResourcePacks")

            dependencies {
                modImplementation("curse.maven:horseman-1082085:8166015")
                modImplementation("fuzs.forgeconfigapiport:forgeconfigapiport-fabric:21.1.0")
                modImplementation("com.github.bawnorton.mixinsquared:mixinsquared-fabric:0.3.7-beta.2")
                jarJar("com.github.bawnorton.mixinsquared:mixinsquared-fabric:0.3.7-beta.2")
                modCompileOnly("curse.maven:shoulder-surfing-reloaded-243190:8725633")
                modCompileOnly("curse.maven:leawind-third-person-930880:6996680")
                modCompileOnly("curse.maven:perspective-api-1575322:8595568")

                configuration("shoulderSurfingMods", "curse.maven:shoulder-surfing-reloaded-243190:8725633")
                configuration("leawindMods", "curse.maven:leawind-third-person-930880:8602832")
                configuration("leawindMods", "curse.maven:perspective-api-1575322:8595568")
                configuration("leawindLegacyMods", "curse.maven:leawind-third-person-930880:6996680")
                configuration("leawindLegacyMods", "curse.maven:architectury-api-419699:8492725")
                configuration("leawindLegacyMods", "curse.maven:cloth-config-348521:5729125")
                configuration("betterThirdPersonMods", "curse.maven:better-third-person-435044:5439141")
                configuration("realCameraMods", "curse.maven:real-camera-851574:8333299")
                configuration("realCameraMods", "curse.maven:cloth-config-348521:5729125")
                configuration("emfMods", "curse.maven:entity-model-features-844662:8799080")
                configuration("emfMods", "curse.maven:entity-texture-features-fabric-568563:8789237")
                configuration("emfResourcePacks", "curse.maven:prettier-horses-1238331:8154340@zip")
                modImplementation("com.leclowndu93150.baguettelib:baguettelib-1.21.1-fabric:2.0.5")
                modCompileOnly("dev.isxander:yet-another-config-lib:3.8.1+1.21.1-fabric")
                modRuntimeOnly("dev.isxander:yet-another-config-lib:3.8.1+1.21.1-fabric")
                modCompileOnly("curse.maven:better-third-person-435044:5439141")
            }

            publishingDependencies {
                requires("baguettelib")
                requires("yacl")
            }

            rawLoom {
                mapOf(
                    "ShoulderSurfing" to "shoulderSurfing",
                    "Leawind" to "leawind",
                    "LeawindLegacy" to "leawindLegacy",
                    "BetterThirdPerson" to "betterThirdPerson",
                    "RealCamera" to "realCamera",
                    "Emf" to "emf"
                ).forEach { (runSuffix, dir) ->
                    runs.create("client$runSuffix") {
                        client()
                        configName = "1.21.1 Fabric Client ($runSuffix)"
                        runDir("runs/$dir")
                    }
                }
            }

            rawProject {
                val loaderProject = this
                mapOf(
                    "ShoulderSurfing" to "shoulderSurfing",
                    "Leawind" to "leawind",
                    "LeawindLegacy" to "leawindLegacy",
                    "BetterThirdPerson" to "betterThirdPerson",
                    "RealCamera" to "realCamera",
                    "Emf" to "emf"
                ).forEach { (runSuffix, dir) ->
                    val copyMods = loaderProject.tasks.register<Copy>("copy${runSuffix}Mods") {
                        from(loaderProject.configurations.named("${dir}Mods"))
                        into(loaderProject.file("runs/$dir/mods"))
                    }
                    loaderProject.tasks.matching { it.name == "runClient$runSuffix" || it.name == "ideaSyncTask" }.configureEach {
                        dependsOn(copyMods)
                    }
                }
                val copyEmfResourcePacks = loaderProject.tasks.register<Copy>("copyEmfResourcePacks") {
                    val packs = loaderProject.configurations.named("emfResourcePacks")
                    from(packs)
                    into(loaderProject.file("runs/emf/resourcepacks"))
                    doLast {
                        val options = loaderProject.file("runs/emf/options.txt")
                        if (!options.exists()) {
                            val enabled = packs.get().files.joinToString(",") { "\"file/${it.name}\"" }
                            options.writeText("resourcePacks:[\"vanilla\",\"fabric\",$enabled]\n")
                        }
                    }
                }
                loaderProject.tasks.matching { it.name == "runClientEmf" || it.name == "ideaSyncTask" }.configureEach {
                    dependsOn(copyEmfResourcePacks)
                }
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
                compileOnly("curse.maven:shoulder-surfing-reloaded-243190:8725628")
                compileOnly("curse.maven:leawind-third-person-930880:6996698")
                compileOnly("curse.maven:perspective-api-1575322:8595570")
                modImplementation("com.leclowndu93150.baguettelib:baguettelib-1.21.1-neoforge:2.0.5")
                modCompileOnly("dev.isxander:yet-another-config-lib:3.8.1+1.21.1-neoforge")
                modRuntimeOnly("dev.isxander:yet-another-config-lib:3.8.1+1.21.1-neoforge")
                compileOnly("curse.maven:better-third-person-435044:5833474")
            }

            publishingDependencies {
                requires("baguettelib")
                requires("yacl")
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
            compileOnly("curse.maven:shoulder-surfing-reloaded-243190:8596508")
            compileOnly("curse.maven:leawind-third-person-930880:7854598")
            compileOnly("curse.maven:perspective-api-1575322:8595574")
            modCompileOnly("com.leclowndu93150.baguettelib:baguettelib-1.21.11-common:2.0.5")
            modCompileOnly("dev.isxander:yet-another-config-lib:3.8.1+1.21.11-neoforge")
            compileOnly("curse.maven:better-third-person-435044:6455836")
        }

        fabric {
            loaderVersion = "0.19.2"
            fabricApi("0.141.3+1.21.11")

            dependencies {
                modImplementation("curse.maven:horseman-1082085:7491954")
                modImplementation("fuzs.forgeconfigapiport:forgeconfigapiport-fabric:21.11.0")
                modImplementation("com.github.bawnorton.mixinsquared:mixinsquared-fabric:0.3.7-beta.2")
                jarJar("com.github.bawnorton.mixinsquared:mixinsquared-fabric:0.3.7-beta.2")
                modCompileOnly("curse.maven:shoulder-surfing-reloaded-243190:8596515")
                modCompileOnly("curse.maven:leawind-third-person-930880:7854602")
                modCompileOnly("curse.maven:perspective-api-1575322:8595571")
                modImplementation("com.leclowndu93150.baguettelib:baguettelib-1.21.11-fabric:2.0.5")
                modCompileOnly("dev.isxander:yet-another-config-lib:3.8.1+1.21.11-fabric")
                modRuntimeOnly("dev.isxander:yet-another-config-lib:3.8.1+1.21.11-fabric")
                modCompileOnly("curse.maven:better-third-person-435044:6455829")
            }

            publishingDependencies {
                requires("baguettelib")
                requires("yacl")
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
                compileOnly("curse.maven:shoulder-surfing-reloaded-243190:8596508")
                compileOnly("curse.maven:leawind-third-person-930880:7854598")
                compileOnly("curse.maven:perspective-api-1575322:8595574")
                modImplementation("com.leclowndu93150.baguettelib:baguettelib-1.21.11-neoforge:2.0.5")
                modCompileOnly("dev.isxander:yet-another-config-lib:3.8.1+1.21.11-neoforge")
                modRuntimeOnly("dev.isxander:yet-another-config-lib:3.8.1+1.21.11-neoforge")
                compileOnly("curse.maven:better-third-person-435044:6455836")
            }

            publishingDependencies {
                requires("baguettelib")
                requires("yacl")
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
            compileOnly("curse.maven:shoulder-surfing-reloaded-243190:8749275")
            compileOnly("curse.maven:leawind-third-person-930880:8511898")
            compileOnly("curse.maven:perspective-api-1575322:8595578")
            modCompileOnly("com.leclowndu93150.baguettelib:baguettelib-26.1.2-common:2.0.5")
            modCompileOnly("dev.isxander:yet-another-config-lib:3.9.6+26.1-neoforge")
            compileOnly("curse.maven:better-third-person-435044:6455836")
        }

        fabric {
            loaderVersion = "0.19.3"
            fabricApi("0.151.0+26.1.2")

            dependencies {
                modImplementation("curse.maven:horseman-1082085:7964145")
                modImplementation("fuzs.forgeconfigapiport:forgeconfigapiport-fabric:26.1.5")
                modImplementation("com.github.bawnorton.mixinsquared:mixinsquared-fabric:0.3.7-beta.2")
                jarJar("com.github.bawnorton.mixinsquared:mixinsquared-fabric:0.3.7-beta.2")
                modCompileOnly("curse.maven:shoulder-surfing-reloaded-243190:8749274")
                modCompileOnly("curse.maven:leawind-third-person-930880:8511893")
                modCompileOnly("curse.maven:perspective-api-1575322:8595576")
                modImplementation("com.leclowndu93150.baguettelib:baguettelib-26.1.2-fabric:2.0.5")
                modCompileOnly("dev.isxander:yet-another-config-lib:3.9.6+26.1-fabric")
                modRuntimeOnly("dev.isxander:yet-another-config-lib:3.9.6+26.1-fabric")
                modCompileOnly("curse.maven:better-third-person-435044:6455829")
            }

            publishingDependencies {
                requires("baguettelib")
                requires("yacl")
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
                compileOnly("curse.maven:shoulder-surfing-reloaded-243190:8749275")
                compileOnly("curse.maven:leawind-third-person-930880:8511898")
                compileOnly("curse.maven:perspective-api-1575322:8595578")
                modImplementation("com.leclowndu93150.baguettelib:baguettelib-26.1.2-neoforge:2.0.5")
                modCompileOnly("dev.isxander:yet-another-config-lib:3.9.6+26.1-neoforge")
                modRuntimeOnly("dev.isxander:yet-another-config-lib:3.9.6+26.1-neoforge")
                compileOnly("curse.maven:better-third-person-435044:6455836")
            }

            publishingDependencies {
                requires("baguettelib")
                requires("yacl")
            }
        }
    }

    version("26.2") {
        neoFormVersion = "26.2-2"

        common {
            compileOnly("org.ow2.asm:asm-tree:9.5")
            compileOnly("com.github.bawnorton.mixinsquared:mixinsquared-common:0.3.7-beta.3")
            annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-common:0.3.7-beta.3")
            compileOnly("curse.maven:shoulder-surfing-reloaded-243190:8749237")
            compileOnly("curse.maven:leawind-third-person-930880:8511898")
            compileOnly("curse.maven:perspective-api-1575322:8595582")
            modCompileOnly("com.leclowndu93150.baguettelib:baguettelib-26.2-common:2.0.5")
            modCompileOnly("dev.isxander:yet-another-config-lib:3.9.6+26.2-neoforge")
            compileOnly("curse.maven:better-third-person-435044:6455836")
        }

        fabric {
            loaderVersion = "0.19.5"
            fabricApi("0.160.0+26.2")

            dependencies {
                modImplementation("curse.maven:horseman-1082085:8735226")
                modImplementation("fuzs.forgeconfigapiport:forgeconfigapiport-fabric:26.2.1")
                modImplementation("com.github.bawnorton.mixinsquared:mixinsquared-fabric:0.3.7-beta.3")
                jarJar("com.github.bawnorton.mixinsquared:mixinsquared-fabric:0.3.7-beta.3")
                modCompileOnly("curse.maven:shoulder-surfing-reloaded-243190:8749236")
                modCompileOnly("curse.maven:leawind-third-person-930880:8511893")
                modCompileOnly("curse.maven:perspective-api-1575322:8595580")
                modImplementation("com.leclowndu93150.baguettelib:baguettelib-26.2-fabric:2.0.5")
                modCompileOnly("dev.isxander:yet-another-config-lib:3.9.6+26.2-fabric")
                modRuntimeOnly("dev.isxander:yet-another-config-lib:3.9.6+26.2-fabric")
                modCompileOnly("curse.maven:better-third-person-435044:6455829")
            }

            publishingDependencies {
                requires("baguettelib")
                requires("yacl")
            }
        }
        neoforge {
            loaderVersion = "26.2.0.86"
            loaderVersionRange = "[4,)"

            dependencies {
                modImplementation("curse.maven:horseman-1082085:8735225")
                implementation("com.github.bawnorton.mixinsquared:mixinsquared-neoforge:0.3.7-beta.3")
                jarJar("com.github.bawnorton.mixinsquared:mixinsquared-neoforge:0.3.7-beta.3")
                configuration("annotationProcessor", "com.github.bawnorton.mixinsquared:mixinsquared-common:0.3.7-beta.3")
                compileOnly("curse.maven:shoulder-surfing-reloaded-243190:8749237")
                compileOnly("curse.maven:leawind-third-person-930880:8511898")
                compileOnly("curse.maven:perspective-api-1575322:8595582")
                modImplementation("com.leclowndu93150.baguettelib:baguettelib-26.2-neoforge:2.0.5")
                modCompileOnly("dev.isxander:yet-another-config-lib:3.9.6+26.2-neoforge")
                modRuntimeOnly("dev.isxander:yet-another-config-lib:3.9.6+26.2-neoforge")
                compileOnly("curse.maven:better-third-person-435044:6455836")
            }

            publishingDependencies {
                requires("baguettelib")
                requires("yacl")
            }
        }
    }
}
