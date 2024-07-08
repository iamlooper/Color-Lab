package com.looper.seeker.model

data class Overlay(
    val owningPackage: String,
    val name: String,
    val targetPackage: String,
    val resources: List<Resource>
)