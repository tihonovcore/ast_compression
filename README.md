### Kotlin AST compression 

For using application, run method `main()` at `ast_compression/src/main/kotlin/Main.kt`

File `ast_compression/src/main/kotlin/Configuration.kt` contains settings.
There are next properties:
* `sourceDirectory` - path to *.kt files (`"src"` by default)
* `outputFile` - path to file for saving compressed AST (`"out/dump.txt"` by default)
* `nCompressions` - compression count (`4` by default)
* `printTreeBefore` - if set `true`, application will print AST before compression (`true` by default)
* `printTreeAfter` - if set `true`, application will print AST after compression (`true` by default)
* `saveToFile` - if set `true`, compressed AST will save to `outputFile` (`true` by default)

You may change any of these properties.
