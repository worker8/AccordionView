# AccordionView

This is an implementation of `AccordionView` in Android using `ConstraintLayout` and `ConstraintSet`.

<details>
<summary>
click to show gif
</summary>

![](https://user-images.githubusercontent.com/1988156/44016013-347c01e8-9f0e-11e8-9c43-dda199f57dc8.gif)
</details>



## How to setup

```
allprojects {
    repositories {
        ...
			  maven { url 'https://jitpack.io' }
		}
}
```

```
dependencies {
    implementation 'com.github.worker8:AccordionView:VERSION'
}
```
Replace the `VERSION` with the latest one you can find here: https://github.com/worker8/AccordionView/releases

for example: `implementation 'com.github.worker8:AccordionView:1.0.5'`

You can exclude the support library and use your own version if you face some conflict:
```
implementation("com.github.worker8:AccordionView:$libVersions.accordionView") {
    exclude group: 'com.android.support'
}
```

## How to use
to be filled in... 🚧👷 

#### Include in xml
to be filled in... 🚧👷 

#### Make adapter to supply data
to be filled in... 🚧👷 

## Technical Details
to be filled in... 🚧👷 
