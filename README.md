Cocos2dxWatchFace
=========================
This is an example of create watch face for android wear using Cocos2dx.

<img src="https://github.com/Tinker-S/Cocos2dxWatchFace/blob/master/screenshots/preview.png" style="width:320px;height:320px;"/>

## How to do this?
* First, you must download a version of cocos2dx. My version is 3.6. Unzip it to a dir.

* Configure environment variables in your **~/.bash_profile**

<img src="https://github.com/Tinker-S/Cocos2dxWatchFace/blob/master/screenshots/step1.png"/>

* Enter your workspace, use the command below to create a new cocos2dx project.
```
cd $YOUR_WORKSPACE
cocos new -l cpp -p com.awesomego.cocos.watchface Cocos2dxWatchFace
```

<img src="https://github.com/Tinker-S/Cocos2dxWatchFace/blob/master/screenshots/step2.png"/>

* Compile and run.
```
cocos run -s Cocos2dxWatchFace -p android
```
or enter **proj.android** dir, and execute the internal script.
```
cd $YOUR_WORKSPACE/Cocos2dxWatchFace/proj.android
./build_native.py
```

* Because the **proj.android** is just a common android project with eclipse structure. We won't use it. So let's use android studio to create a watch face project for android wear. If you do not know how to do, please refer to [Android Training](http://developer.android.com/training/wearables/watch-faces/index.html?utm_campaign=building-for-wear-215utm_source=dacutm_medium=blog).

* Now we have a project **WatchFaceDemo** with **MyWatchFace** in it. We need copy java files located in **cocos2d/cocos/platform/android/java/src/** to our new project and change some of the file contents.

    1. We should change **Cocos2dxHelper.java**, modify **Activity sActivity** to **Context sActivity** and update relevant method.
    2. We should add a method to **Cocos2dxHelper.java**. Because default can't support Context parameter. For the implemention you can refer to the code. **public static void init(final Context activity, Cocos2dxHelperListener listener)**
    3. We should change **getGLContextAttrs** which in **Cocos2dxActivity.java** from private to public.
    4. We should change inner class **Cocos2dxEGLConfigChooser** which in **Cocos2dxActivity.java** to static.


## Attention

If you have found any bugs or need some features, please create an issue at [Github Issue Tracker](https://github.com/Tinker-S/Cocos2dxWatchFace/issues).

## License

    Copyright (C) 2015 Tinker Sun

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.