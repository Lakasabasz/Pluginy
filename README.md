# Plugin Mymcworld Klasy/Drop to server Spigot version 1.9

This plugin allows you to create classes and receiving drops from the given block. Classes can be developed by raising the level and thereby increase the chance of dropping. For each class, you can set another drop and a separate block which will fall items.

## Plugin version

Plugin: 1.0.0 for Minecraft 1.9.*
Java: 1.8

## Files
### config.yml
```
#Log directory
historyDir: logs

#Debug Mode
debug: true

#Class configuration
klasy:
  Killer: # The unique name of the class
    name: 'Zabójca' # Displayed class name
    id: 0 # Id of class, must be int
    maxlvl: 100 # Max level of class
    drop:
      items:
        diamond_sword: # The unique name of item
          itemId: diamond_sword # Item id
          chance: 0.05 # Basic chance
        diamond_axe:
          itemId: diamond_axe
          chance: 0.01
        diamond_helmet:
          itemId: diamond_helmet
          chance: 0.025
        diamond_chestplate:
          itemId: diamond_chestplate
          chance: 0.02
        diamond_leggings:
          itemId: diamond_leggings
          chance: 0.01
        diamond_boots:
          itemId: diamond_boots
          chance: 0.05
      from: obsidian # From what the block has be dropped
```

## Features

* Optimization
* Database support
* Log to file
* More variables in messages.yml

## Author

Łukasz 'Łakasabasz' Mastalerz
