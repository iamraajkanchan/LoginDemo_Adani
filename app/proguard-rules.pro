
-keepnames class com.example.logindemo_adani.model.User

-keepattributes *Annotation*
-optimizationpasses 3
-keep,includedescriptorclasses class net.sqlcipher.** { *; }
-keep,includedescriptorclasses interface net.sqlcipher.** { *; }