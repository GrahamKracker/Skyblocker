# Skyblocker Texture Predicates Documentation/Specification

Skyblocker Texture Predicates or "STP" is our high-performance custom texturing system which is based upon the vanilla predicates format with some extensions, the system is packed full of a bunch of different predicates to cover a wide variety of use-cases and maintains a consistent design for re-texturing different things (e.g. items, and armor); the system is also active Hypixel-wide allowing for retexturing things for other games as well.

## Advantages
Compared to the OptiFine format, our format integrates with the vanilla system and offers a wide variety of specialized predicates for different needs for texture pack authors. Skyblocker is very well maintained and updates to new versions on the day of their release (which usually contain new texture pack features) so authors won't need to wait on OptiFine/CITR which have historically been a little behind on their update game. Skyblocker is very well known and has a very large user base for the modern versions meaning that the transition away from the CITR/OptiFine format can be seamless for users.<br><br>

Our specialized predicate for pet info for example enable added resiliency against changes in the game and on Hypixel (e.g. components broke everything), it also means that we can provide a fast path for common scenarios.

## Predicates
Predicates are evaluated in the order in which they are defined, so if one predicate needs to take on a higher priority than another it should be specified first. All predicates specified for a *[model override](https://minecraft.wiki/w/Model#Item_models)* must return true in order for that model to be used, see section on logical predicates if that functionality is undesirable. If you want to specify multiple of one predicate then logical predicates will serve your use case.

### Api ID Predicate
Matches to an item's "API" Id. Check out the [ItemUtils](../utils/ItemUtils.java) file for the conversion logic.

```json
"skyblocker:api_id": "RAINBOW_RUNE_3"
```

## Custom Data Predicate
Allows for matching to arbitrary fields anywhere in an item's custom data.<br>

| Field Name     | Type                              | Description                                               |
|----------------|-----------------------------------|-----------------------------------------------------------|
| path           | String                            | Specify the path in the custom data to the desired field. |
| stringMatcher? | [String Matcher](#string-matcher) | Allows for matching to a String value.                    |
| regexMatcher?  | [Regex Matcher](#regex-matcher)   | Allows for matching the field's value to a regex.         |
| intMatcher?    | [Int Matcher](#number-matcher)    | Allows for matching to an int value.                      |
| longMatcher?   | [Long Matcher](#number-matcher)   | Allows for matching to a long value.                      |
| floatMatcher?  | [Float Matcher](#number-matcher)  | Allows for matching to a float value.                     |
| doubleMatcher? | [Double Matcher](#number-matcher) | Allows for matching to a double value.                    |

*Note*: Question marks suffixing a field's name indicate that the field is optional and may be left out.

```json
"skyblocker:custom_data": {
	"path": "enchantments.ultimate_chimera",
	"intMatcher": {
		"value": 7
	}
}
```

## Dyed Predicate
Checks for whether an item is dyed, also supports the Custom Dye and Custom Animated Dye armor customization features!

```json
"skyblocker:dyed": true
```

## Held By Armor Stand Predicate
Tests for whether the current item is held by an armor stand entity or not.

```json
"skyblocker:held_by_armor_stand": true
```

## Inside Screen Predicate
Tests for whether the current item is inside of a screen or not.

```json
"skyblocker:inside_screen": true
```

## Item Predicate
Allows for testing what the current item is.

```json
"skyblocker:item": "minecraft:golden_sword"
```

## Location Predicate
Tests for the current SkyBlock island.

```json
"skyblocker:location": "mining_3" //Dwarven Mines
```

## Lore Predicate
Searches for an occurrence of a line that matches a string or regex.<br>

| Field Name     | Type                              | Description                                                                |
|----------------|-----------------------------------|----------------------------------------------------------------------------|
| index?         | int                               | Non-negative, zero-indexed lore index. Leave out to search all lore lines. |
| stringMatcher? | [String Matcher](#string-matcher) |                                                                            |
| regexMatcher?  | [Regex Matcher](#regex-matcher)   |                                                                            |

*Note*: Question marks suffixing a field's name indicate that the field is optional and may be left out.

```json
"skyblocker:lore": {
	"index": 17,
	"stringMatcher": {
		"contains": "Wither Impact"
	}
}
```

## Name Predicate
Allows for matching an item's name to some string or regex.<br>

| Field Name     | Type                              |
|----------------|-----------------------------------|
| stringMatcher? | [String Matcher](#string-matcher) |
| regexMatcher?  | [Regex Matcher](#regex-matcher)   |

*Note*: Question marks suffixing a field's name indicate that the field is optional and may be left out.

```json
"skyblocker:name": {
	"stringMatcher": {
		"endsWith": "Astraea"
	}
}
```

## Pet Info Predicate
Allows for matching to fields inside of a pet item's `petInfo` object.<br><br>

| Field Name | Type   | Description                                  |
|------------|--------|----------------------------------------------|
| type?      | String | Allows for matching to a pet type.           |
| tier?      | String | Allows for matching to a pet's tier.         |
| skin?      | String | Allows for matching to a pet's current skin. |

*Note*: Question marks suffixing a field's name indicate that the field is optional and may be left out.

```json
"skyblocker:pet_info": {
	"type": "GOLDEN_DRAGON",
	"tier": "LEGENDARY",
	"skin": "GOLDEN_DRAGON_ANCIENT"
}
```

## Profile Component Predicate
Allows for matching to fields related to the profile item component, useful for doing advanced things with player heads.<br>

| Field Name      | Type                                | Description                                                                |
|-----------------|-------------------------------------|----------------------------------------------------------------------------|
| uuid?           | String                              | Matches to a profile component's uuid.                                     |
| textureMatcher? | Texture Matcher                     | Allows for matching to the texture's base64 via a String or Regex Matcher. |

*Note*: Question marks suffixing a field's name indicate that the field is optional and may be left out.

```json
"skyblocker:profile_component": {
	"uuid": "8616f7e5-27c8-4021-aa15-b744dc317980",
	"textureMatcher": {
		"stringMatcher": {
			"startsWith": "ey"
		}
	}
}
```

#### Matchers
Matchers allow for matching different elements to strings, or regexes in a uniform way.

### String Matcher
Allows for matching to a string, you can either look for an exact (equals) match or match based on whether the target string starts with a given string, ends with a given string, or contains a given string.<br>

| Field Name  | Type   | Description                                               |
|-------------|--------|-----------------------------------------------------------|
| equals?     | String | Requires that the target string match this value exactly. |
| startsWith? | String | Requires that the target string begin with this value.    |
| endsWith?   | String | Requires that the target string end with this value.      |
| contains?   | String | Requires that the target string contain this value.       |

*Note*: Question marks suffixing a field's name indicate that the field is optional and may be left out.<br>

These fields are not mutally exclusive to eachother so specifying both a `startsWith` and `contains` for example will require that the target string both start with a given value and contain a given value.

```json
"stringMatcher": {
	"equals": "Hi"
}
```

### Regex Matcher
Allows for matching values to a regex, you can either use a full or partial regex match.<br>

| Field Name | Type          | Description                                                     |
|------------|---------------|-----------------------------------------------------------------|
| regex      | Regex Pattern | The regex pattern.                                              |
| matchType? | Enum          | Whether a FULL or PARTIAL match will be used. Defaults to FULL. |

*Note*: Question marks suffixing a field's name indicate that the field is optional and may be left out.<br>

```json
"regexMatcher": {
	"regex": "[A-Za-z0-9]+"
	"matchType": "PARTIAL"
}
```

### Number Matcher
Number matchers allow for matching to numeric values, there are currently 4 variants of number matchers. Int Matchers allow you to match to integer values, this matcher covers the `byte`, `short`, and `int` data types. Long Matchers allows you to match to `long` values. Float Matchers allow you to match to `float` values. And Double Matchers allow you to match to `double` values.<br><br>

NB: All matchers follow the exact same format for how they are laid out.

| Field Name | Type          | Description                                                         |
|------------|---------------|---------------------------------------------------------------------|
| value      | Number        | The exact type of number varies depending on which variant you use. |
| operator?  | Comparison Id | Dictates what comparison will be performed. Defaults to Equals.     |

| Comparison Name          | Id | Description                                                 |
|--------------------------|----|-------------------------------------------------------------|
| Equals                   | == | Requires that the input match the value exactly.            |
| Not Equals               | != | Requires that the input not match the value.                |
| Less Than                | <  | Requires that the input is less than the value.             |
| Less Than or Equal To    | <= | Requires that the input is less than or equal to the value. |
| Greater Than             | >  | Requires that the input is greater than the value.          |
| Greater Than or Equal To | >= | Requires that the input is less than or equal to the value. |

*Note*: Question marks suffixing a field's name indicate that the field is optional and may be left out.<br>

This Example will require that the value being passed to the matcher is greater than or equal to 20.

```json
"intMatcher": {
	"value": 20,
	"operator": ">="
}
```

## Logical Predicates
"Logical" predicates perform logical operations on *other* predicates, for example say you want to have more complex logic for overriding a Skyblock item model, due to the default behavior requiring a match for all specified predicates some overrides would need to be specified separately despite doing the exact same thing for example, logical predicates solve this issue by allowing you to perform "OR"s, "AND"s, or "NOT"s on a list of predicates.<br>

| Predicate ID   | Description                                           |
|----------------|-------------------------------------------------------|
| skyblocker:or  | Performs a Logical OR on all predicates in the list.  |
| skyblocker:and | Performs a Logical AND on all predicates in the list. |
| skyblocker:not | Performs a Logical NOT on all predicates in the list. |

#### Examples
If you wanted to perform a Logical AND on an item's api id and name, this can be done with:<br>

```json
"predicate": {
	"skyblocker:and": [
		{
			"skyblocker:api_id": "DARK_CLAYMORE"
		},
		{
			"skyblocker:name": {
				"stringMatcher": {
					"contains": "Withered"
				}
			}
		}
	]
}
```
If you want to check whether a predicate or multiple predicates are false you can do this with the Logical NOT predicate like so:<br>

```json
"predicate": {
	"skyblocker:not": [ //Returns true if the item's api id is not HYPERION
		{
			"skyblocker:api_id": "HYPERION"
		}
	]
}
```

#### Notes
Logical predicates can also be nested inside of eachother!

## Item Re-texturing
Item re-texturing is done through the vanilla predicate and model system which is also what the Custom Model Data system uses, so its expected that you have a basic understanding of that system before using ours. In order to override an item model for a Skyblock item you create a regular vanilla model file and place it inside of the `assets/skyblocker/models/item` folder and the file name must be the item's Skyblock Id (case insensitive).

```json
{
	"parent": "item/handheld",
	"textures": {
		"layer0": "skyblocker:item/aspect_of_the_dragon"
	}
}
```

## Armor Re-texturing
With Armor re-texturing you specify which armor layers you want to be used in place of the vanilla ones on certain items. You're also able to provide sub-overrides based on predicate. All overrides must be specified in the `assets/skyblocker/overrides/armor` folder, using subdirectories inside of there is also supported. The name of the file is up to you and doesn't matter.<br><br>

Skyblocker also supports **re-texturing player heads** in the armour helmet format via this system! (Subject to change)

### Layers
For each armour layer you specify a namespaced `id` for it, optionally its `suffix`, and optionally whether the layer should be `dyeable` which is whether its affected by the armor's current vanilla dye color (if applied). Armor texture paths are resolved in this format: `assets/<namespace>/textures/models/armor/<path>_layer_(isSecondLayer ? 2 : 1)<suffix>.png` (the namespace and path come from the `id`), its recommended to probably use your own namespace.

### Examples

#### Basic Override Example
Say you wanted to provide a custom texture for Storm armor, an example file would look like:<br>

```json
{
	"itemIds": [
		"WISE_WITHER_HELMET",
		"WISE_WITHER_CHESTPLATE",
		"WISE_WITHER_LEGGINGS",
		"WISE_WITHER_BOOTS"
	],
	"layers": [
		{
			"id": "skyblocker:wise_wither",
			"suffix": "",
			"dyeable": false
		},
		{
			"id": "skyblocker:wise_wither",
			"suffix": "_overlay",
			"dyeable": false
		}
	]
}
```
Note that the textures will need to be placed as `assets/skyblocker/textures/models/armor/wise_wither_layer_1.png` and `assets/skyblocker/textures/models/armor/wise_wither_layer_1_overlay.png`.

#### Advanced Override
Say you wanted to provide a custom texture for a Necron Armor, but also use a different texture for it while in dungeons. This can be achieved by:<br>

```json
{
	"itemIds": [
		"POWER_WITHER_HELMET",
		"POWER_WITHER_CHESTPLATE",
		"POWER_WITHER_LEGGINGS",
		"POWER_WITHER_BOOTS"
	],
	"layers": [
		{
			"id": "skyblocker:power_wither",
			"suffix": "",
			"dyeable": false
		},
		{
			"id": "skyblocker:power_wither",
			"suffix": "_overlay",
			"dyeable": false
		}
	],
	"overrides": [
		{
			"predicate": {
				"skyblocker:location": "dungeon"
			},
			"layers": [
				{
					"id": "skyblocker:power_wither_dungeon",
					"suffix": "",
					"dyeable": false
				},
				{
					"id": "skyblocker:power_wither_dungeon",
					"suffix": "_overlay",
					"dyeable": false
				}
			]
		}
	]
}
```

### Notes about armor re-texturing
All armor textures are cached per `ItemStack` instance for at least 5 minutes for performance. The cache is cleared upon Hypixel sending a new instance of the item or when textures are reloaded.

## Custom Block Textures/Models
Skyblocker supports re-texturing blocks in certain islands/areas. With custom block re-texturing you are able to conditionally replace a block's model with your own custom one using the exact same JSON model files that you would use to achieve the same effect with vanilla blocks, you have total control over how your customized blocks will appear and be modelled.<br>

### Model Files
Custom block model files are like regular block model files, just for your own blocks; you can use any feature in them that vanilla supports. The only difference is where you need to place your model files, they must go under the `assets/skyblocker/models/block` directory. As usual we also support placing your model files inside of sub-directories there, and you can name them whatever you want although you will need to reference the path to them in your custom blocks definition file.<br><br>

Here's an example of a model file where the block has the same texture on all six sides:

```json
{
	"parent": "minecraft:block/cube_all",
	"textures": {
		"all": "minecraft:block/glacite"
	}
}
```

### Custom Block Override Files
Custom block override files are specified in the `assets/skyblocker/overrides/block` directory. The files in this directory must be named after the given island's id. For example naming the example file below `mining_3.json` will make this override active while inside the Dwarven Mines.<br><br>

In each file you specify a parent override with a mapping of block ids to model ids for block models that you want to replace. You can also optionally specify overrides to override the parent rule while inside of a certain box/zone. For each override you can specify a name but it doesn't matter.

Here is an example of a block override file that will override light blue wool when in the dwarven mines, and an override that will also replace the packed ice in the Glacite Tunnels with a custom glacite block model:

```json
{
	"name": "Dwarven Mines",
	"replacements": {
		"minecraft:light_blue_wool": "skyblocker:block/mithril_1"
	},
	"overrides": [
		{
			"name": "Glacite Tunnels",
			"box": {
				"pos1": [
					127,
					175,
					183
				],
				"pos2": [
					-129,
					111,
					480
				]
			},
			"replacements": {
				"minecraft:light_blue_wool": "skyblocker:block/mithril_1",
				"minecraft:packed_ice": "skyblocker:block/glacite"
			}
		}
	]
}
```

## Conclusion
Enjoy re-texturing to your heart's content! We plan on adding far more powerful re-texturing features very very soon. If you have any requests let us know and we will add it to our TODO list and get it done!