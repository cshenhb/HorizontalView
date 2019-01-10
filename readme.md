# recyclerView 水平进度条

![](https://github.com/cshenhb/HorizontalView/blob/master/shot/Screenshot_1547104964.png)



* Xml代码

  ```xml
  <?xml version="1.0" encoding="utf-8"?>
  <LinearLayout
      xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto"
      xmlns:tools="http://schemas.android.com/tools"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      android:orientation="vertical"
      tools:context=".MainActivity">
  
  
      <android.support.v7.widget.RecyclerView
          android:id="@+id/hRecyclerView"
          android:layout_width="match_parent"
          android:layout_height="wrap_content">
  
      </android.support.v7.widget.RecyclerView>
  
      <!--这个width 就是绿色的背景,可以根据需求自定义  height也一样-->
      <LinearLayout
          android:background="@color/colorPrimaryDark"
          android:layout_width="100dp"
          android:layout_height="20dp"
          android:layout_gravity="center"
          >
  
          <!--这个width 就是红色的部分,随意定义,在onMesure 会根据recyclerView 的可以滑动的距离动态计算,
            height应该和父布局的高度一样-->
          <com.shb.github.horizontalview.view.HorizontalView
              android:id="@+id/horView"
              android:layout_width="50dp"
              android:layout_height="20dp">
  
          </com.shb.github.horizontalview.view.HorizontalView>
      </LinearLayout>
  </LinearLayout>
  ```

  

* 绑定recyclerView

  * ```java
    mHorizontalView.bindRecyclerView(mRecyclerView);
    ```

* 具体可以见app/src/MainActivity.java

