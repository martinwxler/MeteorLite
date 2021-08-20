# MeteorLite ![](https://i.imgur.com/Y6ghBw3.png) 

## openosrs-injector
```
MeteorLite injects directly into the deobfuscated client, where-as OPRS and RuneLite inject into the obfuscated client.
This means we are running without obfuscated getters/descriptors or any of the unneeded obfuscation instructions.

Reflection in rs-client has been modified to still support jagex reflection checks, you won't get input denied ever.
The various injectors/raws have all been fixed to accommodate the new injection target.

I maintained the rl api where I could. Due to injecting rs-api, there are a very small handful of name clashes after
injection. if an rs-api method is "missing", it likely has a $api suffex (ie. model.draw$api)
```

## meteor-client
```
MeteorLite uses a mixture of Swing and JavaFX. 
The UI is controlled via JavaFX and FXML/CSS and supports the eventbus/Guice injection
The overlays are rendered via Swing but you can use JavaFX here if you want too.

UIs can be rapidly built using SceneBuilder for JavaFX

It should be noted that the client is mostly setup for a high end PC. The following defaults have been implaced:
GPU: enabled
drawDistance: 100
antiAliasing: 8x
filtering: 16x
Stretched: enabled

It maintains solid performance with these settings on my RTX 2060M
```

## Building
MeteorLite is built using JDK 1.8 currently due to a meteor-agent requirement.
It is built using the gradle task
```
clean build run
```

## Credits  
OpenOSRS staff :  https://github.com/open-osrs/runelite  
RuneLite staff :  https://github.com/runelite/runelite  
RuneScape staff :  https://oldschool.runescape.com/  
Sponge Mixins :  https://github.com/SpongePowered/Mixin  
Jfoenix :  https://github.com/sshahine/JFoenix  
