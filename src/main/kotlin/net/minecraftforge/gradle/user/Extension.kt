package net.minecraftforge.gradle.user

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.minecraft(configure: UserExtension.() -> Unit) = configure(configure)