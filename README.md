# AccordionView

<img src="https://user-images.githubusercontent.com/1988156/44387037-52893500-a55f-11e8-999b-13533fc29b11.png" width="200px" />

_icons are taken from freepik.com_

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

Example: 
```
implementation 'com.github.worker8:AccordionView:1.0.x'
```

You can exclude the support library and use your own version if you face some conflict:

```
implementation("com.github.worker8:AccordionView:$libVersions.accordionView") {
    exclude group: 'com.android.support'
}
```

## How to use
To use `AccordionView`, you can refer to the example in this repo:

https://github.com/worker8/AccordionView/blob/master/app/src/main/java/beepbeep/accordionView/

It works very similarly to `RecyclerView`. 

### Include in xml
First you need to include it in the xml. Example can be found [here](https://github.com/worker8/AccordionView/blob/6878cf41dec69e092143f5a6a46f7140fc27ba43/app/src/main/res/layout/activity_random_fact.xml)

```
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <beepbeep.accordian_library.AccordionView
        android:id="@+id/accordian_view"
        android:layout_width="0dp"
        android:layout_height="0dp"
        android:background="@color/md_white_1000"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</android.support.constraint.ConstraintLayout>
```

`AccordionView` is a child of `ConstraintLayout`. However, don't include any children inside `AccordionView`, the behavior will be unexpected.

Instead, supply the data using `AccordionAdapter`.


### AccordionAdapter
`AccordionAdapter` is the supplier of data. for `AccordionView`. Example can be found [here](https://github.com/worker8/AccordionView/blob/6878cf41dec69e092143f5a6a46f7140fc27ba43/app/src/main/java/beepbeep/accordionView/sample/RandomAdapter.kt).

It works similarly to [RecyclerView.Adapter](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter):

`AccordionAdapter` has 5 methods to be overriden:

```
    fun onCreateViewHolderForTitle(parent: ViewGroup): AccordionView.ViewHolder
    fun onCreateViewHolderForContent(parent: ViewGroup): AccordionView.ViewHolder
    fun onBindViewForTitle(viewHolder: AccordionView.ViewHolder, position: Int, arrowDirection: ArrowDirection)
    fun onBindViewForContent(viewHolder: AccordionView.ViewHolder, position: Int)
fun getItemCount(): Int
```
#### view creation
`onCreateViewHolderForTitle` and `onCreateViewHolderForContent` are used for view creation:

![readme-oncreate](https://user-images.githubusercontent.com/1988156/44570711-b018be00-a7b9-11e8-9113-e32d0ed3f5cd.png)

#### view binding
`onBindViewForTitle` and `onBindViewForContent` on the other hand are used for view binding, such as: setting up the data, and setting up listeners, loading images, etc.

#### data count
`getItemCount` on the other hand tells `AccordionView` how many items you have.

## The Implementation Details of AccordionView
Refer to this blog post where I described in depth:

https://bloggie.io/@_junrong/the-making-of-accordionview-using-constraintlayout

## Caveat
This view does not take into account if you supply too many data and fill up the screen, it is best used for cases where you only have a few items to be shown.
