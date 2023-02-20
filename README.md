
Requirements for version 1
Features we will add include:
1. An inheritance structure for managing entities in the World (Player, Monster, Item classes)

2. Reading in "game world" files to create game worlds from

3. An improved game world
    Managing an arbitrary numbers of entities in the world
    Managing a map (Map class)
    Consumable items in the world

4. Saving and loading of player data files (new main menu commands: load, save)

5. And other smaller improvements such as:
    Player level-up
    Player "damage perk" (bonus damage that applies only while in the world)
    Minor main menu command changes (start command)

Additional non-feature requirements:

1. Since this system is now getting larger, it's important to consider designing your code to avoid redundant code through the effective use of methods, loops, arrays (or ArrayLists) and class inheritance.

2. You must also handle errors using Exceptions (creating your own custom exceptions classes, and throwing and catching them where appropriate).

