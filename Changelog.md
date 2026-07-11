## Version 1.4.3
- Fixed metasequoia and dryophyllum leaves dropping ginkgo fruit
- Fixed pachycephalosaurus banner pattern not being tagged as a mesozoic banner pattern
- Slightly decreased the rarity of fossil sites
- Fossil sites now have more fossils than junk items (around 60% fossils and 40% junk)
- Misc tweaks

## Version 1.4.2
- Updated Simplified Chinese translations thanks to Oldazhai
- Mobs now rotate their head first before their body when moving
- Tweaked body rotation values a bit
- Time on land and time on water is no longer saved data for semiaquatic mobs
- Fixed semiaquatic mobs not prioritizing leaving/entering water over wandering
- Fixed living ooze despawning
- Following mobs will now teleport when you are 16 blocks away and sprint when 8 blocks away
- Mosasaurus and praepusa now use their food tags for temporary pacification to be more consistent
- Temporary pacification time has been increased to 1 hour
- Mobs can now be fed their food item to heal them
- Misc tweaks

## Version 1.4.1
- Crash fix

## Version 1.4.0
- Added Aegirocassis
- Added Desmatosuchus
- Added Mosasaurus
- Added Psilopterus
- Added Delitzschala ambient mob
- Added Zhangsolva ambient mob
- Both ambient mobs spawn from their respective plants
- Added banner patterns for mob icons
- Added Pummel and Snatch disc
- Removed unfinished 1.3 and future mobs
- Added carnotaurus headbutt vocal sound
- Increased golden emperor carnotaurus variant spawn chance by 5%
- Fixed potted metasequoia model
- Fixed random sit goal
- Praepusa now sleeps on the surface of water at night
- Overhauled advancements
- Patchouli is now a required dependency
- Paleopedia is now a custom item instead of the normal patchouli book
- Fixed many paleopedia errors
- Added many new entries to paleopedia related to progression
- Increased coelacanthus max size cap to 200
- Coelacanthus will continue to eat mobs even if max size unless pacified
- Added missing coelacanthus food tags
- Switched some raft eggs to water eggs
- Fixed lystrosaurus not playing its walk animation when walking underwater
- Fixed pachycephalosaurus not playing recover animation after sparring
- Tweaked most mob and item tags
- Metriorhynchus is now tamable with a bucket of tropical fish
- Metriorhynchus and ulughbegsaurus now heal a bit of health on kill
- Kaprosuchus and megalania can now be ordered to sit still in water
- Overhauled loot tables for fossil structures. Now there are common, uncommon, rare, and epic fossil and junk loot tables for each era
- Adjusted the rarity of fossil geodes
- Changed variant names for diplocaulus and dunkleosteus to be more generic
- Dunkleosteus variants now spawn based on the depth of the water or randomly if they spawn on land, deeper equals bigger. Any depth above 12 is large, above 6 is medium, and any shallower is small
- Moved all mob textures and sounds to entity/mob/
- Decreased dromaeosaurus speed
- Increased dromaeosaurus run distance
- Dromaeosaurus will now stop sometimes for a very brief period
- Jawless fish and dunkleosteus no longer prefer swimming toward the bottom of water
- Added tail rotations to many mobs
- Diplocaulus can now perform a boomerang dash in water
- Fixed mobs sometimes moving sideways
- Mob rotations are now smoother
- Many misc tweaks and fixes

## Version 1.3.1
- Fixed semiaquatic mobs getting stuck in water
- Fixed some pachycephalosaurus animations not playing
- Tweaked kaprosuchus leap attack
- Increased brachiosaurus health to 400
- Mobs will no longer sleep when following their owner
- Changed pachycephalosaurus warn sound
- Fixed carnotaurus animations not playing properly
- Misc tweaks

## Version 1.3.0
- Added Brachiosaurus
- Added Coelacanthus
- Added Hibbertopterus
- Added Kaprosuchus
- Added Leptictidium
- Added Lobe Finned Fish
- Added Lystrosaurus
- Added Pachycephalosaurus
- Added Praepusa
- Added Pterodactylus
- Added Ulughbegsaurus
- Added many new plants
- Removed fossil site maps and changed how fossils generate
- Many misc tweaks, fixes, and additions

## Version 1.2.3
- Fixed a server crash

## Version 1.2.2
- Added improved path navigation 
- Tweaked semiaquatic navigation switching
- Reduced Metriorhynchus jump distance
- Metriorhynchus swims more often
- Changed priority for avoiding mobs in some mobs
- Added Tartuosteus to big Dunkleosteus targets
- Fixed Dunkleosteus bite animations
- Carnotaurus headbutts less often
- Slightly increased the range for Telecrex to start scattering
- Reduced Telecrex max hp to match Talpanas
- Reduced Telecrex flight interval
- Added a hover, takeoff, and fast flying animation to Telecrex
- Telecrex flies away from danger faster
- Telecrex flies a little bit faster
- Added Carnotaurus and Metriorhynchus to scatters_telecrex tag
- Removed Kentrosaurus and other passive mobs from scatters_telecrex tag
- Added Talpanas shake animation
- Added Talpanas run animation
- Talpanas now seeks shelter from sunlight and bright lights, with a light threshold of 5
- Fixed some PrehistoricMob data not being saved to bucketed mobs
- Changed Dunkleosteus, Onchopristis, and Jawless Fish ground seeking to be a bit better
- Onchopristis and Dunkleosteus swim more often
- Added more Kentrosaurus idle animations
- Misc tweaks

## Version 1.2.1
- Fixed a crash with Oculus
- Metriorhynchus death roll now has a size limit, along with two tags to control what it can carry
- Improved Metriorhynchus death roll visuals

## Version 1.2.0
- Added Metriorhynchus
- Added Tartuosteus
- Added Living Ooze
- Tweaked mob stats
- Tweaked Dunkleosteus stats per variant
- Dunkleosteus can now attack outside of water
- Majungasaurus now attacks faster
- Carnotaurus now attacks faster
- Increased duration for Carnotaurus roar buffs
- Increased health gain when Carnotaurus kills a mob
- Carnotaurus now has a running animation when aggro
- Majungasaurus now has a running animation when aggro
- Tweaked spawn egg colors for Stethacanthus and Talpanas
- Talpanas now drops feathers
- Added spawnable block tags for all mobs that can spawn on ground
- Added Stethacanthus bite sound
- Added Megalania tail whip sounds
- Added Megalania bite sounds
- Fixed bucketed mobs losing their age value
- Talpanas and Telecrex are no longer immune to suffocation
- Removed revive ginkgo and revive lepidodendron advancements
- Split revival advancements into different groups and reordered some advancements
- Added a section for eggs and embryos to the revival category of the paleopedia
- Fixed some jawless fish variants not tilting
- Reduced range needed for majungasaurus to enter stealth mode
- Reduced interval for mob auto healing
- Fixed Kentrosaurus taking damage twice every time it gets hurt
- Fixed Kentrosaurus attack animation being reset if it gets hit during it
- Reduced Kentrosaurus hitbox size
- Increased Kentrosaurus attack range
- Megalania is no longer fire immune in the nether
- Megalania heals faster in warmer biomes and slower in cold biomes
- Increased Megalania speed
- Megalania is now hostile to everything, including other megalania, in the nether
- Megalania tail whip can hit up to 3 targets at once
- Nether Megalania heal on kill
- Fixed Megalania attack animation being reset if it gets hit during it
- Megalania is now immune to poison and also wither while in the nether
- Megalania will no longer run when cold
- Megalania tail whip attack vertical range is now much shorter
- Megalania follow range now depends on its temperature
- Added 3 new idle animations for the different Megalania temperature states
- Fixed Carnotaurus roar not having a subtitle translation
- Increased the radius for Dromaeosaurus run positions
- Fixed Onchopristis overriding death poof effects
- Increased chance of finding machine parts
- Mossy dirt can now spread like grass and mycelium
- Added calamophyton feature
- Talpanas forgot how to swim
- Updated Doomsurf disc
- Tweaked swimming ai
- Added enter water goal for Diplocaulus
- Tweaked exit water goal to be more consistent
- Carnotaurus no longer attacks on sight in peaceful mode
- Dunkleosteus no longer attacks Stethacanthus
- Large Dunkleosteus can now attack Guardians
- Guardians are now scared of Dunkleosteus
- Dunkleosteus does double damage to Guardians
- Reduced interval for Talpanas seeking shelter
- Added Simplified Chinese translations thanks to Oldazhai!
- Misc tweaks

## Version 1.1.2
- Fixed Kimmeridgebrachypteraeschnidium nymphs being able to despawn

## Version 1.1.1
- Onchopristis no longer attacks after getting stepped on if it is pacified
- Updated Dromaeosaurus entry in the Paleopedia
- Added images for each mob entry in the Paleopedia
- Changed persistence rules so aquatic mobs can despawn when spawning naturally
- Changed tempt goal priorities to be prioritized over wandering
- Changed Jawless Fish mob category to water ambient

## Version 1.1.0
- Added Onchopristis
- Added a new music disc "Doomsurf" by ChipsTheCat
- Added a new music disc "MEGALANIA" by ValiantEnvoy
- Added optional compatibility with Patchouli, adding a Paleopedia guidebook with information on various systems and mobs (currently quite incomplete)
- Fixed Mossy Lepidodendron Wood not being craftable
- Added targeting tag for Carnotaurus
- Added a separate sound event for Megalania step sounds
- Fixed a z-fighting issue on the Furcacauda model
- Kimmeridgebrachypteraeschnidium no longer drowns to prevent unwanted deaths when a nymph grows up
- Leashed Prehistoric mobs no longer take any fall damage
- Kentrosaurus is now immune to thorns, sweet berry, and cactus damage
- Kentrosaurus can now pathfind onto cactus and sweet berry bushes
- Kentrosaurus will now attack any nearby players who break cactus or sweet berry bushes
- Kentrosaurus will now follow and defend any mob wearing thorns armor
- Updated Carnotaurus charging, splitting it into three animations rather than one
- Carnotaurus now attacks monsters and animals, they will continue to attack monsters even when pacified
- Carnotaurus headbutt attack can now hit up to 3 targets at once
- Carnotaurus roar now gives the carnotaurus regeneration for a short duration
- Carnotaurus now heals a small amount of health on kill
- Increased the amount of suspicious blocks per fossil, petrified tree, and tar pit structure
- Fossils can now spawn in deserts and badlands
- Fossil Site maps are now separated into different maps for each structure, each with their own icon
- Replaced all fossil site structures with new ones representing each relevant creature in the mod
- Fossils spawn closer to the surface
- Fossils now contain 3 different loot tables: common, rare, and epic, with epic being the only one containing fossils but also with zero junk items
- Added a new array of junk items to common and rare fossil loot tables
- Added random rotations to some egg blockstates
- Fixed water eggs and underwater eggs hatching too quickly
- Miscellaneous tweaks
