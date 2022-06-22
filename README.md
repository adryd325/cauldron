# cauldron

### Credits:

 - masa: A lot of MaLiLib and MiniHUD were referenced in the creation of rendering parts of the library
 - unascribed: Reference for the mixin config plugin

### Using

I think this is pretty simple, don't touch anything unless its in the api package

### Features

 FINISHED:

 - Client command manager  
   Separate commands make it obvious a command is going to be run on the client  
   Provides command suggestions  
   Creating commands is identical to creating vanilla serverside commands  
   Not enabled unless a mod registers a command  

 - Render helpers  
   Handles creation of buffer builders and rendering, all you need to do is write to the buffer builder  
   Utility functions to draw boxes, lines and colors  
   Provides a proxied buffer builder that handles rendering for you  
 
 WIP:

 - Keybind manager, events and registration  
   Why?:  
   Fabric API's keybinds end up being saved in options.txt and reset if the mod is ever removed  
   I'd like to have more control over the keybinds, both in development and for the user. Similar to MaLiLib.  

 TODO:
 - Make more Color4f constructors

 - Mixin plugin, allow for "banned" mixins similar to fabrication in configs  
   Why?:  
   I haven't found any APIs that do this  
   Can definitely help with mod conflicts  

 - Reading and writing files/configs. reading needs to happen very early on for mixin plugin  
   Why?:  
   Storing data in options.txt is unreliable and prone to deletion if the mod is temporarily uninstalled.  
   JSON makes more sense as a storage format
 
 - Config types and management, GUI, widgets, etc, Config screen  
   Why?:  
   While cloth config is very feature complete, I have my own designs of how I want a config screen for parachute to work.  
   MaLiLib comes close with structure but is missing a few critical features and doesn't have the UI style I would like.
   In terms of design, I'm aiming for something a lot more compact, similar to sodium's video settings
   Config management will probably end up borrowing a lot from MaLiLib, Cloth Config and others.
 
PLANS FOR THE FAR FUTURE, MAYBE:

 - Serverside configs using the same UI as client side configs, or just server configs in general  
   Why?:  
   Would be fun to implement
   Command configuration  
   Use fabric permissions API instead of just OP level
   Allow clients to configure the server with a gui? maybe?

  [[ BEWARE OF FEATURE CREEP ]]
 
 - Command manager improvements  
   Namespaced commands, or even larger mods implementing their own prefix.  
   Why?:  
   An example of own-prefix implementation would be a friends planned twitch integration mod; .ban is very ambigous so !ban or .twitch:ban can make things much easier to understand  
   Help command could say what mod registered what command

PLANS OUT OF SCOPE OF THIS PROJECT BUT I THOUGHT OF THEM WHILE WRITING THIS:

 - Vanilla command permissions for fabric permissions API  
   Why?:  
   No way to grant vanilla command permissions with fabric permissions API


PLANS THAT ARE FUCKING CURSED AND WON'T HAPPEN (maybe in another mod):

 - Hijack clientside command registration of other mods 
   Why?:  
   MAKE THEM SEPARATE MAKE THEM OBVIOUS PLEASE I BEG YOU

 - Hijack Fabric API's keybind api to save to outside of options.txt 
   Why?:  
   I'm tired of having to redo my keybinds for Iris, ResolutionControl+ and Simple Voice Chat every time I disable all mods for testing


```
Parachute config - 0.4.0+abcdefab
[[Features]] [ Tweaks ] [ Debug Renderers ] 

WorldEdit CUI                 [ ENABLED ] [RESET] [X]
Enable rendering                 [ TRUE ] [RESET]
Position 1 color           [ #FF0000 ] [] [RESET]
Position 2 color           [ #0000FF ] [] [RESET]
Selection color            [ #FFFFFF ] [] [RESET]
Toggle rendering             [ UNSET ] [] [RESET]
Clear selection              [ UNSET ] [] [RESET]

Overlay rendering options
Line draw mode                [ VANILLA ] [RESET]
Quads draw mode               [ ENABLED ] [RESET]
```

```
Parachute config - 0.4.0+abcdefab
[ Features ] [[Tweaks]] [ Debug Renderers ] 

Step assist                  [ DISABLED ] [RESET] [X]
Toggle enabled               [ UNSET ] [] [RESET] 
Step Height                  []------- [] [RESET]

Titlebar customization        [ ENABLED ] [RESET] [X]
Hide modded                      [ TRUE ] [RESET]    
Hide game status                [ FALSE ] [RESET] 

Crosshair                 [ ALWAYS SHOW ] [RESET] [X]
Toggle enabled               [ UNSET ] [] [RESET] 
```
 